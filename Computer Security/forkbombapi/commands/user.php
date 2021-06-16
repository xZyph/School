<?php
namespace api\command\user{

  include_once 'command.php';
  include_once 'validation/validation_engine.php';
  include_once 'validation/auth_engine.php';

  use \api\validation\ValidatorEngine;
  use \api\authorization\AuthEngine;
  use \api\log\LoggingEngine;
  use \api\command\Command;
  use \api\authorization\AuthenticationException;

  class RegisterLecturerCommand extends Command{

    private const PREPARED_STATEMENT   = "CALL `user::register_lecturer`( :username, :email, :password, :avatar )";
    private const REQUIRED_ARGUMENTS   = array("method"=>array("username","password","email"));
    private const SUPPORTED_ARGUMENTS = array("method"=>array("username","password","email"),"files"=>array("image"));
    private $username, $email, $password, $avatar, $avatar_name;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::REQUIRED_ARGUMENTS, self::SUPPORTED_ARGUMENTS);
      $this->username = $args["method"]["username"];
      $this->email = $args["method"]["email"];
      $this->password = $args["method"]["password"];
      $this->avatar = @$args["files"]["image"];
      $this->avatar_name = isset($this->avatar) ? hash( "sha256", $this->username . bin2hex(random_bytes(8)) ) : "default";
    }

    protected function validate(){
      ValidatorEngine::validateUsername($this->username);
      ValidatorEngine::validateUniqueUsername($this->username);
      ValidatorEngine::validateEmail($this->email);
      ValidatorEngine::validateUniqueEmail($this->email);
      ValidatorEngine::validatePassword($this->password);
      ValidatorEngine::validateAvatar(@$this->avatar);
    }

    protected function arguments(){
      return array(
        ":username"  =>$this->username,
        ":email"    =>$this->email,
        ":password"  =>AuthEngine::pwd_encode($this->password),
        ":avatar"    =>$this->avatar_name
      );
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      $result = parent::postProcess($pdos);

      if( isset($this->avatar) ){
        $output_file = "data/avatar/$this->avatar_name.jpg";
        $content = file_get_contents();
        $this->response_code = \api\command\CommandUtils::createImage(
          $this->avatar['tmp_name'],
          "data/avatar/$this->avatar_name.jpg"
        );
        switch($this->response_code){
          case 201:
            $this->response["message"]="ok";
            break;
          case 400:
            $this->response["message"]="bad request";
            break;
          default:
            throw new \api\command\CommandExecutionException("Could not create image",500);
            break;
        }
      }
      $this->response_code = 201;
    }
  }
  class RegisterStudentCommand extends Command{

    private const PREPARED_STATEMENT   = "CALL `user::register_student`( :username, :email, :password, :major, :year )";
    private const EXPECTED_ARGUMENTS   = array("method"=>array("username","password","email","major","year"));
    private $username, $email, $password, $salt, $major, $year;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::EXPECTED_ARGUMENTS);
      $this->username = $args["method"]["username"];
      $this->email     = $args["method"]["email"];
      $this->password = $args["method"]["password"];
      $this->major     = $args["method"]["major"];
      $this->year     = $args["method"]["year"];
    }

    protected function validate(){
      ValidatorEngine::validateUsername($this->username);
      ValidatorEngine::validateUniqueUsername($this->username);

      ValidatorEngine::validateEmail($this->email);
      ValidatorEngine::validateUniqueEmail($this->email);

      ValidatorEngine::validatePassword($this->password);
      ValidatorEngine::validateMajor($this->major);
      ValidatorEngine::validateYear($this->year);
    }

    protected function arguments(){
      return array(
        ":username"  =>$this->username,
        ":email"    =>$this->email,
        ":password"  =>AuthEngine::pwd_encode($this->password),
        ":major"    =>$this->major,
        ":year"      =>$this->year
      );
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      parent::postProcess($pdos);
      $this->response_code = 201;
    }
  }

  class LoginCommand extends Command{
    private const EXPECTED_ARGUMENTS = array("method"=>array("username","password"));
    private const PREPARED_STATEMENT = "CALL `user::login`( :username )";
    private $username;
    private $password;
    private $start_time;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::EXPECTED_ARGUMENTS);
      $this->username = $args["method"]["username"];
      $this->password = $args["method"]["password"];
      $this->start_time = microtime(true);
    }

    protected function validate(){
      ValidatorEngine::validateUsername($this->username);
      ValidatorEngine::validatePassword($this->password);
    }
    protected function arguments(){
      return array(
        ":username"  =>$this->username
      );
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      $result = $pdos->fetch();
      $dt = \microtime(true) - $this->start_time;
      \usleep( rand(0, $dt > 300000 ? 0 : 300000-$dt) );
      if(
        $result["status"]=="ok" &&
        isset($result["pwd"]) &&
        AuthEngine::pwd_verify($this->password, $result["pwd"])
      ){
        $this->response["message"] = "ok";
        $this->response["token"] = AuthEngine::token_encode(array(
            "usr"=>$this->username,
            "typ"=>$result["type"],
            "token_type"=>"user",
            "aud"=>"TBI", //device fingerprint
            "iat"=>time(),
            "nbf"=>time()+1,
            "exp"=>time()+(60*60*24*7) //one week
          ),
          AuthEngine::USER_TOKEN
        );
        $this->response["type"] = $result["type"];
        $this->response_code = 200;
      }
      else {
        throw new \api\authorization\AuthorizationException();
      }
    }
  }

  class UpdatePasswordCommand extends Command{
    private const EXPECTED_ARGUMENTS = array("method"=>array("password"), "headers"=>array("token"));
    private const PREPARED_STATEMENT = "CALL `user::update_password`( :username, :password )";
    private $token;
    private $password;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::EXPECTED_ARGUMENTS);
      $this->token     = AuthEngine::token_decode($args["headers"]["token"], AuthEngine::USER_TOKEN);
      $this->password = $args["method"]["password"];
    }
    protected function validate(){
      AuthEngine::authorizeToken($this->token);
      ValidatorEngine::validatePassword($this->password);
    }

    protected function arguments(){
      return  array(
        ":username"  =>$this->token["usr"],
        ":password"  =>AuthEngine::pwd_encode($this->password)
      );
    }
  }
  class UpdateAvatarCommand extends Command{
    private const PREPARED_STATEMENT   = "CALL `user::update_avatar`( :username, :avatar )";
    private const EXPECTED_ARGUMENTS   = array("files"=>array("image"),"headers"=>array("token"));
    private $token, $avatar, $avatar_name;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::EXPECTED_ARGUMENTS);
      $this->token = AuthEngine::token_decode($args["headers"]["token"], AuthEngine::USER_TOKEN);
      $this->avatar = $args["files"]["image"];
      $this->avatar_name = hash( "sha256", $this->token["usr"] . bin2hex(random_bytes(8)) );
    }

    protected function validate(){
      ValidatorEngine::validateAvatar($this->avatar);
      AuthEngine::authorizeToken($this->token);
      AuthEngine::authenticateLecturer($this->token);
    }

    protected function arguments(){
      return array(
        ":username"  =>$this->token["usr"],
        ":avatar"    =>$this->avatar_name
      );
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      $result = $pdos->fetch();
      $old_src = isset($result["old_avatar"]) ? "data/avatar/" . $result["old_avatar"] . ".jpg" : null;

      if( $result["status"]=="ok" && preg_match("/^([a-zA-Z0-9]{64}|default)$/", $result["old_avatar"] )
      ){
        $this->response_code = \api\command\CommandUtils::createImage(
          $this->avatar['tmp_name'],
          "data/avatar/$this->avatar_name.jpg"
        );
        switch($this->response_code){
          case 201:
            if( isset($result["old_avatar"]) &&
              $result["old_avatar"] != "default" &&
              file_exists($old_src)
            ){
              $success = unlink($old_src);
              if(!$success){
                throw new \api\command\CommandExecutionException("Could not delete old image",500);
              }
            }
            $this->response["message"]="ok";
            break;
          case 400:
            $this->response["message"]="bad request";
            break;
          default:
            throw new \api\command\CommandExecutionException("Could not create image",500);
            break;
        }
      } 
      else{
        throw new \api\command\CommandExecutionException("Old image invalid");
      }
    }
  }

  class GetLecturersCommand extends Command{
    private const PREPARED_STATEMENT   = "CALL `lecturer::all`()";
    private const EXPECTED_ARGUMENTS   = array(
      "headers"=>array("token")
    );
    private $token;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::EXPECTED_ARGUMENTS);
      $this->token = AuthEngine::token_decode($args["headers"]["token"], AuthEngine::USER_TOKEN);
    }
    protected function validate(){
      AuthEngine::authorizeToken($this->token);
      AuthEngine::authenticateUserType($this->token, ["admin"]);
    }

    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      $result = $pdos->fetchAll();
      if($result){
        $this->response["message"] = "ok";
        $this->response["lecturers"] = $result;
        $this->response_code = 200;
      }
      else{
        throw new \api\command\CommandExecutionException();
      }
    }
  }
  class GetAvatarCommand extends Command{
    private const REQUIRED_ARGUMENTS = array("uri"=>array("username"));
    private const SUPPORTED_ARGUMENTS = [
      "uri"=>["username"],
      "headers"=>["token"]
    ];
    private const PREPARED_STATEMENT = "CALL `user::get_avatar`( :username )";
    private $username;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::REQUIRED_ARGUMENTS, self::SUPPORTED_ARGUMENTS);
      $this->username = $args["uri"]["username"];
    }
    protected function validate(){
      ValidatorEngine::validateUsername($this->username);
    }

    protected function arguments(){
      return  array(":username"  =>$this->username);
    }

    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      $result = $pdos->fetch();
      $src = isset($result["avatar"]) ? "data/avatar/" . $result["avatar"] . ".jpg" : null;
      if( $result["status"]=="ok" &&
        file_exists($src) &&
        preg_match("/^([a-zA-Z0-9]{64}|default)$/", $result["avatar"] ?? "default" )
      ){
        $this->response["message"] = "ok";
        $this->response["content"] = file_get_contents($src);
        $this->response["filepath"] = $src;
        $this->response_code = 200;
        $this->response_type = "image";
        return $result;
      }
      else{
        $this->response["message"] = "Not found";
        $this->response_code = 404;
        //Should not be logged?
      }
    }
  }
  class GetMessagesCommand extends Command{
    private const PREPARED_STATEMENT   = "CALL `user::get_messages`( :username )";

    private const EXPECTED_ARGUMENTS   = array(
      "headers"=>array("token")
    );
    private $token;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::EXPECTED_ARGUMENTS);
      $this->token = AuthEngine::token_decode($args["headers"]["token"], AuthEngine::USER_TOKEN);
    }

    protected function validate(){
      AuthEngine::authorizeToken($this->token);
    }

    protected function arguments(){
      return array(
        ":username"=>$this->token["usr"]
      );
    }

    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      $result = $pdos->fetchAll();
      $this->response["message"] = "ok";
      $this->response["messages"] = $result;
      $this->response_code = 200;
    }
  }
  class GetCommentsCommand extends Command{
    private const PREPARED_STATEMENT   = "CALL `user::get_comments`( :username, :messageid )";

    private const EXPECTED_ARGUMENTS   = array(
      "headers"=>["token"],
      "uri"=>["messageid"]
    );
    private $token, $messageid;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::EXPECTED_ARGUMENTS);
      $this->messageid = $args["uri"]["messageid"];
      $this->token = AuthEngine::token_decode($args["headers"]["token"], AuthEngine::USER_TOKEN);
    }

    protected function validate(){
      AuthEngine::authorizeToken($this->token);
      AuthEngine::authenticateUserType($this->token, ["student","admin"]);
    }

    protected function arguments(){
      return array(
        ":username"=>$this->token["usr"],
        ":messageid"=>$this->messageid
      );
    }

    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      $result = $pdos->fetchAll();
      $this->response["message"] = "ok";
      $this->response["comments"] = $result;
      $this->response_code = 200;
    }
  }
  
  class GetCoursesCommand extends Command{
    private const PREPARED_STATEMENT = "CALL `lecturer::get_courses`( :_username )";
    private const EXPECTED_ARGUMENTS = [
      "headers"=>["token"]
    ];
    private $token;
    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::EXPECTED_ARGUMENTS);
      $this->token = AuthEngine::token_decode($args["headers"]["token"], AuthEngine::USER_TOKEN);
    }
    protected function validate(){
      AuthEngine::authorizeToken($this->token);
      AuthEngine::authenticateUserType($this->token, ["admin","lecturer"]);
    }
    protected function arguments(){
      return [":_username"=>$this->token["usr"]];
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      $result = $pdos->fetchAll();
      $this->response["message"] = "ok";
      $this->response["courses"] = $result;
      $this->response_code = 200;
    }
  }

}
?>
