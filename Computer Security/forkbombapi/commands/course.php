<?php
namespace api\command\course{
  include_once 'command.php';
  include_once 'validation/validation_engine.php';
  include_once 'validation/auth_engine.php';

  use \api\validation\ValidatorEngine;
  use \api\authorization\AuthEngine;
  use \api\command\Command;
  use \api\authorization\AuthenticationException;
  use \api\authorization\AuthorizationException;

  class CheckCoursePinCommand extends Command{

    private const PREPARED_STATEMENT   = "CALL `course::check_pin`( :courseid, :pin )";
    private const REQUIRED_ARGUMENTS   = array(
      "method"=>array("pin"),
      "uri"=>array("courseid")
    );
    private const SUPPORTED_ARGUMENTS   = array(
      "method"=>array("pin"),
      "uri"=>array("courseid"),
      "headers"=>["token"]
    );
    private $courseid, $pin, $start_time;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::REQUIRED_ARGUMENTS, self::SUPPORTED_ARGUMENTS);
      $this->courseid = $args["uri"]["courseid"];
      $this->pin = $args["method"]["pin"];
      $this->start_time = microtime(true);
    }

    protected function validate(){
      ValidatorEngine::validateCourseId($this->courseid);
      ValidatorEngine::validateCourseExists($this->courseid);
      ValidatorEngine::validatePin($this->pin);
    }

    protected function arguments(){
      return array(
        ":courseid"  =>$this->courseid,
        ":pin"  =>$this->pin,
      );
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      $result = $pdos->fetch();
      $dt = \microtime(true) - $this->start_time;
      \usleep( rand(0, $dt > 300000 ? 0 : 300000-$dt) );
      if($result["status"]=="ok"){
        $this->response["message"]="ok";
        $this->response_code = 200;
        $this->response["coursetoken"] = AuthEngine::token_encode(array(
          "crs"=>$this->courseid,
          "token_type"=>"course",
          "aud"=>"TBI",
          "iat"=>time(),
          "nbf"=>time()+1,
          "exp"=>time()+ (60*60*24*7) //one week
        ),
        AuthEngine::COURSE_TOKEN
      );
      }
      else{
        $this->response_code = 401;
        throw new \api\authorization\AuthorizationException("Wrong pincode");
      }
    }
  }

  class PostMessageCommand extends Command{

    private const PREPARED_STATEMENT   = "CALL `course::post_message`( :username, :courseid, :message )";
    private const EXPECTED_ARGUMENTS   = array(
      "method"=>array("content"),
      "uri"=>array("courseid"),
      "headers"=>array("token")
    );
    private $message, $courseid, $token;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::EXPECTED_ARGUMENTS);
      $this->message = $args["method"]["content"];
      $this->courseid = $args["uri"]["courseid"];
      $this->token = AuthEngine::token_decode($args["headers"]["token"], AuthEngine::USER_TOKEN);
    }

    protected function validate(){
      ValidatorEngine::validateCourseId($this->courseid);
      ValidatorEngine::validateCourseExists($this->courseid);
      AuthEngine::authorizeToken($this->token);
      AuthEngine::authenticateUserType($this->token,["student","admin"]);
      ValidatorEngine::validateMessage($this->message, 1, 120);
    }

    protected function arguments(){
      return array(
        ":username"  =>$this->token["usr"],
        ":courseid"  =>$this->courseid,
        ":message"  =>$this->message
      );
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      parent::postProcess($pdos, 201, 500);
    }
  }
  class PostCommentCommand extends Command{

    private const PREPARED_STATEMENT   = "CALL `message::post_comment`( :username, :courseid, :messageid, :content )";
    private const REQUIRED_ARGUMENTS   = array(
      "method"=>array("content"),
      "uri"=>array("courseid","messageid")
    );
    private const SUPPORTED_ARGUMENTS  = array(
      "method"=>array("content"),
      "uri"=>array("courseid","messageid"),
      "headers"=>array("token","coursetoken")
    );
    private $content, $courseid, $messageid, $token, $course_token;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::REQUIRED_ARGUMENTS, self::SUPPORTED_ARGUMENTS);
      $this->content = $args["method"]["content"];
      $this->messageid = $args["uri"]["messageid"];
      $this->courseid = $args["uri"]["courseid"];
      $this->token = empty($args["headers"]["token"]) ? null : AuthEngine::token_decode($args["headers"]["token"], AuthEngine::USER_TOKEN);
      $this->course_token = isset($args["headers"]["coursetoken"]) ? AuthEngine::token_decode($args["headers"]["coursetoken"], AuthEngine::COURSE_TOKEN) : null;
    }

    protected function validate(){
      ValidatorEngine::validateCourseId($this->courseid);
      ValidatorEngine::validateCourseExists($this->courseid);
      ValidatorEngine::validateMessageExistsInCourse($this->courseid, $this->messageid);
      ValidatorEngine::validateMessage($this->content, 1, 120);
      AuthEngine::authenticateUsageOfCourse($this->token, $this->course_token, $this->courseid);
    }

    protected function arguments(){
      return array(
        ":username"  =>@$this->token["usr"],
        ":courseid"  =>$this->courseid,
        ":messageid"  =>$this->messageid,
        ":content" =>$this->content
      );
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      parent::postProcess($pdos, 201, 500);
    }
  }
  class PostReplyCommand extends Command{

    private const PREPARED_STATEMENT   = "CALL `message::post_reply`( :username, :courseid, :messageid, :content )";
    private const EXPECTED_ARGUMENTS   = array(
      "method"=>array("content"),
      "uri"=>array("courseid","messageid"),
      "headers"=>array("token")
    );
    private $content, $courseid, $messageid, $token;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::EXPECTED_ARGUMENTS);
      $this->content = $args["method"]["content"];
      $this->messageid = $args["uri"]["messageid"];
      $this->courseid = $args["uri"]["courseid"];
      $this->token = AuthEngine::token_decode($args["headers"]["token"], AuthEngine::USER_TOKEN);
    }

    protected function validate(){
      ValidatorEngine::validateCourseId($this->courseid);
      ValidatorEngine::validateCourseExists($this->courseid);
      ValidatorEngine::validateMessageExistsInCourse($this->courseid, $this->messageid);
      AuthEngine::authorizeToken($this->token);
      AuthEngine::authenticateLecturer($this->token);
      AuthEngine::authenticateLecturerHasRightsToCourse($this->token, $this->courseid);
      ValidatorEngine::validateMessage($this->content, 1, 120);
    }

    protected function arguments(){
      return array(
        ":username"  =>$this->token["usr"],
        ":courseid"  =>$this->courseid,
        ":messageid"  =>$this->messageid,
        ":content" =>$this->content
      );
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      parent::postProcess($pdos, 201, 500);
    }
  }

  class ReportMessageCommand extends Command{
    private const PREPARED_STATEMENT   = "CALL `message::report`( :username, :courseid, :messageid )";
    private const REQUIRED_ARGUMENTS   = array(
      "uri"=>array("courseid","messageid")
    );
    private const SUPPORTED_ARGUMENTS = array(
      "uri"=>array("courseid","messageid"),
      "headers"=>array("token","coursetoken")
    );
    private $messageid, $courseid, $token, $course_token;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::REQUIRED_ARGUMENTS, self::SUPPORTED_ARGUMENTS);
      $this->messageid = $args["uri"]["messageid"];
      $this->courseid = $args["uri"]["courseid"];
      $this->token = isset($args["headers"]["token"]) ? AuthEngine::token_decode($args["headers"]["token"], AuthEngine::USER_TOKEN) : null;
      $this->course_token = isset($args["headers"]["coursetoken"]) ? AuthEngine::token_decode($args["headers"]["coursetoken"], AuthEngine::COURSE_TOKEN) : null;
    }

    protected function validate(){
      ValidatorEngine::validateCourseId($this->courseid);
      ValidatorEngine::validateCourseExists($this->courseid);
      ValidatorEngine::validateMessageExistsInCourse($this->courseid, $this->messageid);
      AuthEngine::authenticateUsageOfCourse($this->token, $this->course_token, $this->courseid);
    }

    protected function arguments(){
      return array(
        ":username"  =>@$this->token["usr"],
        ":courseid"  =>$this->courseid,
        ":messageid"  =>$this->messageid
      );
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      parent::postProcess($pdos, 201, 500);
    }
  }
  class ReportCommentCommand extends Command{
    private const PREPARED_STATEMENT   = "CALL `comment::report`( :username, :courseid, :messageid, :commentid )";
    private const REQUIRED_ARGUMENTS   = array(
      "uri"=>array("courseid","messageid","commentid")
    );
    private const SUPPORTED_ARGUMENTS = array(
      "uri"=>array("courseid","messageid","commentid"),
      "headers"=>array("token","coursetoken")
    );
    private $messageid, $courseid, $commentid, $token, $course_token;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::REQUIRED_ARGUMENTS, self::SUPPORTED_ARGUMENTS);
      $this->messageid = $args["uri"]["messageid"];
      $this->courseid = $args["uri"]["courseid"];
      $this->commentid = $args["uri"]["commentid"];
      $this->token = isset($args["headers"]["token"]) ? AuthEngine::token_decode($args["headers"]["token"], AuthEngine::USER_TOKEN) : null;
      $this->course_token = isset($args["headers"]["coursetoken"]) ? AuthEngine::token_decode($args["headers"]["coursetoken"], AuthEngine::COURSE_TOKEN) : null;
    }

    protected function validate(){
      ValidatorEngine::validateCourseId($this->courseid);
      ValidatorEngine::validateCourseExists($this->courseid);
      ValidatorEngine::validateMessageExistsInCourse($this->courseid, $this->messageid);
      AuthEngine::authenticateUsageOfCourse($this->token, $this->course_token, $this->courseid);
    }

    protected function arguments(){
      return array(
        ":username"  =>@$this->token["usr"],
        ":courseid"  =>$this->courseid,
        ":messageid"=>$this->messageid,
        ":commentid"=>$this->commentid
      );
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      parent::postProcess($pdos, 201, 500);
    }
  }

  class GetCourseCommand extends Command{
    private const PREPARED_STATEMENT   = "CALL `course::get`( :_courseid)";
    private const REQUIRED_ARGUMENTS   = ["uri"=>["courseid"]];
    private const SUPPORTED_ARGUMENTS  = ["uri"=>["courseid"],"headers"=>["token","coursetoken"]];
    private $courseid, $token, $coursetoken, $has_access, $can_reply;
    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::REQUIRED_ARGUMENTS, self::SUPPORTED_ARGUMENTS);
      $this->courseid = $args["uri"]["courseid"];
      $this->token = @AuthEngine::$TOKEN;
      $this->coursetoken = isset($args["headers"]["coursetoken"]) ? 
        AuthEngine::token_decode($args["headers"]["coursetoken"], AuthEngine::COURSE_TOKEN) : 
        NULL;
    }
    protected function validate(){
      ValidatorEngine::validateCourseId($this->courseid);
      ValidatorEngine::validateCourseExists($this->courseid);
      try{
        AuthEngine::authenticateUsageOfCourse($this->token, $this->coursetoken, $this->courseid);
        $this->has_access = true;
      }
      catch(\Exception $e){
        $this->has_access = false;
      }
      try{
        $this->can_reply = false;
        if(isset($this->token)){
          AuthEngine::authenticateLecturer($this->token);
          AuthEngine::authenticateLecturerHasRightsToCourse($this->token, $this->courseid);
          $this->can_reply = true;
        }
      }
      catch(\Exception $e){
        $this->can_reply = false;
      }
    }
    protected function arguments(){
      return [":_courseid"=>$this->courseid];
    }

    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      $result = $pdos->fetch();
      if($result["status"]==="ok"){
        $this->response["message"] = "ok";
        $this->response["name"] = $result["name"];
        $this->response["has_access"] = $this->has_access;
        $this->response["can_reply"] = $this->can_reply;
        $this->response_code = 200;
      }
      else{
        throw new \api\command\CommandExecutionException();
      }
    }
  }
  class GetCoursesCommand extends Command{
    private const PREPARED_STATEMENT   = "CALL `course::all`()";
    private const REQUIRED_ARGUMENTS   = [];
    private const SUPPORTED_ARGUMENTS  = ["headers"=>["token"]];

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::REQUIRED_ARGUMENTS, self::SUPPORTED_ARGUMENTS);
    }

    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      $result = $pdos->fetchAll();
      if($result){
        $this->response["message"] = "ok";
        $this->response["courses"] = $result;
        $this->response_code = 200;
      }
      else{
        throw new \api\command\CommandExecutionException();
      }
    }
  }
  class GetMessagesCommand extends Command{
    private const PREPARED_STATEMENT   = "CALL `message::all`( :courseid )";

    private const REQUIRED_ARGUMENTS   = array(
      "uri"=>array("courseid")
    );
    private const SUPPORTED_ARGUMENTS = array(
      "uri"=>array("courseid"),
      "headers"=>array("token","coursetoken")
    );
    private $courseid, $course_token, $token;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::REQUIRED_ARGUMENTS, self::SUPPORTED_ARGUMENTS);
      $this->courseid = $args["uri"]["courseid"];
      $this->course_token = isset($args["headers"]["coursetoken"]) ? AuthEngine::token_decode($args["headers"]["coursetoken"], AuthEngine::COURSE_TOKEN) : null;
      $this->token = isset($args["headers"]["token"]) ? AuthEngine::token_decode($args["headers"]["token"], AuthEngine::USER_TOKEN) : null;
    }

    protected function validate(){
      ValidatorEngine::validateCourseId($this->courseid);
      ValidatorEngine::validateCourseExists($this->courseid);
      AuthEngine::authenticateUsageOfCourse($this->token, $this->course_token, $this->courseid);
    }

    protected function arguments(){
      return array(
        ":courseid"=>$this->courseid
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
    private const PREPARED_STATEMENT   = "CALL `comment::all`( :courseid, :messageid )";
    private const REQUIRED_ARGUMENTS   = array(
      "uri"=>array("courseid","messageid")
    );
    private const SUPPORTED_ARGUMENTS = array(
      "uri"=>array("courseid","messageid"),
      "headers"=>array("token","coursetoken")
    );
    private $courseid, $messageid, $course_token, $token;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::REQUIRED_ARGUMENTS, self::SUPPORTED_ARGUMENTS);
      $this->courseid = $args["uri"]["courseid"];
      $this->messageid = $args["uri"]["messageid"];
      $this->course_token = isset($args["headers"]["coursetoken"]) ? AuthEngine::token_decode($args["headers"]["coursetoken"], AuthEngine::COURSE_TOKEN) : null;
      $this->token = isset($args["headers"]["token"]) ? AuthEngine::token_decode($args["headers"]["token"], AuthEngine::USER_TOKEN) : null;
    }

    protected function validate(){
      ValidatorEngine::validateCourseId($this->courseid);
      ValidatorEngine::validateCourseExists($this->courseid);
      ValidatorEngine::validateMessageExistsInCourse($this->courseid,$this->messageid);
      AuthEngine::authenticateUsageOfCourse($this->token, $this->course_token, $this->courseid);
    }

    protected function arguments(){
      return array(
        ":courseid"=>$this->courseid,
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

  class TenureApplicationCommand extends Command{
    private const PREPARED_STATEMENT = "CALL `course::apply`( :_username, :_courseid )";
    private const EXPECTED_ARGUMENTS = [
      "uri"=>["courseid"],
      "headers"=>["token"]
    ];
    private $token, $courseid;

    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::EXPECTED_ARGUMENTS);
      $this->token = AuthEngine::token_decode($args["headers"]["token"], AuthEngine::USER_TOKEN);
      $this->courseid = $args["uri"]["courseid"];
    }

    protected function validate(){
      AuthEngine::authorizeToken($this->token);
      AuthEngine::authenticateUserType($this->token, ["lecturer"]);
      ValidatorEngine::validateCourseId($this->courseid);
      ValidatorEngine::validateCourseExists($this->courseid);
    }

    protected function arguments(){
      return [
        ":_username"=>$this->token["usr"],
        ":_courseid"=>$this->courseid
      ];
    }

    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      return parent::postProcess($pdos, 202, 409);
    }
  }
  abstract class UpdateTenureCommand extends Command{
    private const PREPARED_STATEMENT = "CALL `course::update_tenure`( :_username, :_courseid, :_confirmed)";
    private const EXPECTED_ARGUMENTS = [
      "uri"=>["courseid","username"],
      "headers"=>["token"]
    ];
    protected $courseid, $username, $token;
    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::EXPECTED_ARGUMENTS);
      $this->courseid = $args["uri"]["courseid"];
      $this->username = $args["uri"]["username"];
      $this->token = AuthEngine::token_decode( $args["headers"]["token"], AuthEngine::USER_TOKEN);
    }
    protected function validate(){
      ValidatorEngine::validateCourseId($this->courseid);
      ValidatorEngine::validateCourseExists($this->courseid);
      ValidatorEngine::validateUsername($this->username);
      AuthEngine::authorizeToken($this->token);
      AuthEngine::authenticateUserType($this->token, ["admin"]);
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      return parent::postProcess($pdos, 200, 409);
    }
  }
  class ConfirmTenureCommand extends UpdateTenureCommand{
    public function __construct($args){
      parent::__construct($args);
    }
    protected function arguments(){
      return [
        ":_username"=>$this->username,
        ":_courseid"=>$this->courseid,
        ":_confirmed"=>'1'
      ];
    }
  }
  class RejectTenureCommand extends UpdateTenureCommand{
    public function __construct($args){
      parent::__construct($args);
    }
    protected function arguments(){
      return [
        ":_username"=>$this->username,
        ":_courseid"=>$this->courseid,
        ":_confirmed"=>'0'
      ];
    }
  }

  class DeleteMessageCommand extends Command{
    private const PREPARED_STATEMENT = "CALL `message::delete`( :_courseid, :_messageid )";
    private const EXPECTED_ARGUMENTS = [
      "uri"=>["courseid","messageid"],
      "headers"=>["token"]
    ];
    private $token, $courseid, $messageid;
    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::EXPECTED_ARGUMENTS);
      $this->token = AuthEngine::token_decode( $args["headers"]["token"], AuthEngine::USER_TOKEN);
      $this->courseid = $args["uri"]["courseid"];
      $this->messageid = $args["uri"]["messageid"];
    }
    protected function validate(){
      ValidatorEngine::validateCourseId($this->courseid);
      ValidatorEngine::validateCourseExists($this->courseid);
      ValidatorEngine::validateMessageExistsInCourse($this->courseid, $this->messageid);
      AuthEngine::authorizeToken($this->token);
      AuthEngine::authenticateUserType($this->token, ["admin"]);
    }
    protected function arguments(){
      return [
        ":_courseid"=>$this->courseid,
        ":_messageid"=>$this->messageid
      ];
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      return parent::postProcess($pdos, 200, 409);
    }
  }

  class DeleteCommentCommand extends Command{
    private const PREPARED_STATEMENT = "CALL `comment::delete`( :_messageid, :_commentid )";
    private const EXPECTED_ARGUMENTS = [
      "uri"=>["courseid","messageid","commentid"],
      "headers"=>["token"]
    ];
    private $token, $courseid, $messageid;
    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::EXPECTED_ARGUMENTS);
      $this->token = AuthEngine::token_decode( $args["headers"]["token"], AuthEngine::USER_TOKEN);
      $this->courseid = $args["uri"]["courseid"];
      $this->messageid = $args["uri"]["messageid"];
      $this->commentid = $args["uri"]["commentid"];
    }
    protected function validate(){
      ValidatorEngine::validateCourseId($this->courseid);
      ValidatorEngine::validateCourseExists($this->courseid);
      ValidatorEngine::validateMessageExistsInCourse($this->courseid, $this->messageid);
      AuthEngine::authorizeToken($this->token);
      AuthEngine::authenticateUserType($this->token, ["admin"]);
    }
    protected function arguments(){
      return [
        ":_messageid"=>$this->messageid,
        ":_commentid"=>$this->commentid
      ];
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      return parent::postProcess($pdos, 200, 409);
    }
  }
}
?>
