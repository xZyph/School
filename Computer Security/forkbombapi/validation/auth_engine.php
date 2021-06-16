<?php
namespace api\authorization{
  use \Firebase\JWT\JWT;
  use \api\validation\ValidationCommand;
  use \api\log\LoggingEngine;
  class AuthEngine{
    public static $TOKEN;
    public static $COURSE_TOKEN;
    private const PEPPER = "aaah!asdhg 9e450+u";
    private const USER_TOKEN_KEY = "hurgus burgus and t;he thirtynine horny witchës";
    private const COURSE_TOKEN_KEY = "Sooka BLyaT, the red comrades are at it again with the [redacted]";

    public const USER_TOKEN = "user";
    public const COURSE_TOKEN = "course";

    public static function init(){
      $t = @getallheaders()['token'];
      $c = @getallheaders()['coursetoken'];
      
      LoggingEngine::$LOGGER->pushProcessor(function ($record){
        $caller = @getallheaders()['User-Agent'];
        if($caller === "StubFootMessengerSuperUltraPlusApp/0.1"){
          $record['extra']['caller'] = "App";
        }
        else if($caller === "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:7.0.1) Gecko/20100101 Firefox/7.0.1"){
          $record['extra']['caller'] = "Web";
        }
        else{
          $record['extra']['caller'] = "Third party source";
        }
        
        return $record;
      });
      try{
        if(!empty($t)){
          LoggingEngine::$LOGGER->pushProcessor(function ($record){
            $record['extra']['token_hash'] = hash("sha256", getallheaders()['token']);
            return $record;
          });
  
          self::$TOKEN = AuthEngine::token_decode($t, AuthEngine::USER_TOKEN);

          LoggingEngine::$LOGGER->pushProcessor(function ($record){
            $record['extra']['user_hash'] = hash("sha256",AuthEngine::$TOKEN["usr"]);
            $record['extra']['user_type'] = AuthEngine::$TOKEN["typ"];
            return $record;
          });
        }
        if(!empty($c)){
          self::$COURSE_TOKEN = AuthEngine::token_decode($c, AuthEngine::COURSE_TOKEN);
        }
      }
      catch(\Firebase\JWT\ExpiredException $e){
        LoggingEngine::$LOGGER->info("Attempted to authorize with expired token", [
          "error_message"=>urlencode($e->getMessage())
        ]);
        http_response_code(401);
        Generic_deny("token expired");
      }
      catch(\Firebase\JWT\BeforeValidException $e){
        LoggingEngine::$LOGGER->critical("Attempted to authorize too quickly", [
          "error_message"=>urlencode($e->getMessage())
        ]);
        http_response_code(425);
        \api\Generic_deny("too early");
      }
      catch(Firebase\JWT\SignatureInvalidException $e){
        LoggingEngine::$LOGGER->critical("Token signature failed", [
          "error_message"=>urlencode($e->getMessage())
        ]);
        http_response_code(401);
        Generic_deny("unauthorized");
      }
      catch(\Exception $e){
        LoggingEngine::$LOGGER->critical(
          "Attempting to authorize with malformed token", 
          ["error_message"=>urlencode($e->getMessage())]
        );
        http_response_code(400);
        \api\Generic_deny("bad request");
      }
    }

    static function pwd_encode($password){
      return \password_hash( $password, \PASSWORD_BCRYPT);
    }
    static function pwd_verify($pwd, $hash){
      return \password_verify($pwd, $hash);
    }
    static function token_decode($token, $token_type){
      switch ($token_type) {
        case self::USER_TOKEN: return (array) JWT::decode($token, AuthEngine::USER_TOKEN_KEY, array('HS256')); break;
        case self::COURSE_TOKEN: return (array) JWT::decode($token, AuthEngine::COURSE_TOKEN_KEY, array('HS256')); break;
        default: throw new \AuthorizationException("Malformed token"); break;
      }
    }

    static function token_encode($payload, $token_type){
      switch ($token_type) {
        case self::USER_TOKEN: return JWT::encode($payload, self::USER_TOKEN_KEY, 'HS256'); break;
        case self::COURSE_TOKEN: return JWT::encode($payload, self::COURSE_TOKEN_KEY, 'HS256'); break;
        default: throw new \Exception(); break;
      }
    }
    static function authenticateLecturerHasRightsToCourse($decoded_token, $courseid){
      if($decoded_token["typ"]=="admin" || $decoded_token["typ"]=="lecturer"){
        $cmd = new IsLecturerCommand($decoded_token["usr"], $courseid);
        return $cmd->execute();
      }
      else{
        throw new AuthenticationException();
      }
    }
    static function authenticateUserType($decoded_token, $usertypes){
      if(!in_array($decoded_token["typ"], $usertypes)){
        throw new AuthenticationException();
      }
    }
    static function authenticateLecturer($decoded_token){
      self::authenticateUserType($decoded_token, ["lecturer","admin"]);
    }
    static function authenticateStudent($decoded_token){
      self::authenticateUserType($decoded_token, ["student"]);
    }
    static function authenticateToken($decoded_token, $resource){
      switch ($decoded_token["token_type"]) {
        case self::USER_TOKEN:
          // code...
          break;
        case self::COURSE_TOKEN:
          if(@$decoded_token["crs"] !== $resource){
            throw new AuthenticationException("Forbidden token access: Token not issued for $resource");
          }
          break;
        default:
          // code...
          break;
      }
    }
    static function authorizeToken($decoded_token){
      switch ($decoded_token["token_type"]) {
        case self::USER_TOKEN:
          if( empty($decoded_token["usr"]) ){
            throw new AuthorizationException("Malformed user token");
          }
          break;
        case self::COURSE_TOKEN:
          if( empty($decoded_token["crs"]) ){
            throw new AuthorizationException("Malformed course token");
          }
          break;
        default:
          throw new AuthorizationException("Malformed token");
          break;
      }
    }
    static function authenticateUsageOfCourse($token, $coursetoken, $courseid){
      if( !isset($coursetoken) && !isset($token) ){
        throw new AuthorizationException();
      }
      if( isset($token) ){
        AuthEngine::authorizeToken($token);
      }
      if( isset($coursetoken) ){
        AuthEngine::authorizeToken($coursetoken);
        AuthEngine::authenticateToken($coursetoken, $courseid);
      }
      else if( $token["typ"]==="lecturer"){
        AuthEngine::authenticateLecturerHasRightsToCourse($token, $courseid);
      }
      else if( @$token["typ"]!=="admin" ){
        throw new AuthenticationException("coursetoken empty, and does not have inherent right to course");
      }
    }
  }
  class AuthorizationException extends \api\command\CommandExecutionException{
    public function __construct($message = "Unauthorized", $response_code = 401){
      parent::__construct($message, $response_code);
    }
  }
  class AuthenticationException extends \api\command\CommandExecutionException{
    public function __construct($message = "Forbidden", $response_code = 403){
      parent::__construct($message, $response_code);
    }
  }
  /**
   * Pecondition: validate token and courseid.
   */
  class IsLecturerCommand extends ValidationCommand{

    private $username;
    private $courseid;

    public function __construct($username, $courseid){
      $this->prepared_statement = "CALL `validation::is_lecturer`( :username, :courseid )";
      $this->username = $username;
      $this->courseid = $courseid;
    }
    protected function arguments(){
      return  array(
        ":username"  =>$this->username,
        ":courseid"  =>$this->courseid
      );
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      $result = $pdos->fetch();
      if($result["status"]!="ok"){
        throw new AuthenticationException("User not registered in subject, or not yet confirmed");
      }
      else{
        return true;
      }
    }
  }
}
?>
