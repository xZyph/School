<?php
namespace api\validation{
  abstract class ValidatorEngine{
    public const MAX_FILESIZE = 5 << 20;
    public static function validateArgumentLength($argument, $min, $max){
      if( strlen($argument) > $max ){
        throw new ArgumentTooLargeException($argument);
      }
      else{
        return strlen($argument) >= $min;
      }
    }
    public static function validateStandardInput($argument){
      if(preg_match("/[\x00\x0f\\\<\>\"\']/")){
        throw new ValidationException("Illegal characters");
      }
    }

    public static function validateRequiredArguments($args, $required){
      if( self::arglength($args) < self::arglength($required) ){
        throw new ValidationException("Too few fields supplied");
      }
      foreach ($required as $argtype => $list) {
        foreach ($list as $field) {
          if( !array_key_exists($field, $args[$argtype]) || empty($args[$argtype][$field]) ){
            throw new ValidationException("Required arguments not supplied");
          }
        }
      }
    }
    public static function validateOptionalArguments($args, $optionals){
      if( self::arglength($args) > self::arglength($optionals) ){
        throw new ValidationException("Too many fields supplied");
      }
      else foreach ($args as $argtype => $arg) {
        foreach ($arg as $key => $value) {
          if( !empty($value) &&
            (!array_key_exists($argtype, $optionals) ||
            !in_array($key, $optionals[$argtype]))
          ){
            throw new ValidationException("Wrong fields supplied");
          }
        }
      }
    }
    private static function arglength($args){
      $argl = 0;
      foreach ($args as $arg) {
        foreach ($arg as $key => $value) {
          if(!empty($value)){
            $argl++;
          }
        }
      }
      return $argl;
    }

    static function validateUsername($username){
      if(
        isset($username) &&
        ValidatorEngine::validateArgumentLength($username, 3, 45) &&
        preg_match('/^[a-zA-Z_][a-zA-Z0-9_]{3,45}$/', $username)
      ){
        return true;
      }
      else{
        throw new ValidationException("Invalid username format");
      }
    }
    static function validateUniqueUsername($username){
      //check with database
      $cmd = new UsernameExistsCommand($username);
      $username_exists = $cmd->execute();
      if($username_exists){
        throw new ValidationException("Username not unique");
      }
      else{
        return true;
      }
    }

    static function validateEmail($email){

      if(
        isset($email) &&
        ValidatorEngine::validateArgumentLength($email, 5, 254) &&
        filter_var($email, FILTER_VALIDATE_EMAIL) &&
        preg_match('/^([^"\'<>@]){1,63}\@([\w.-]+)$/', $email)
      ){
        return true;
      }
      else{
        throw new ValidationException("Invalid email format");
      }
    }
    static function validateUniqueEmail($email){
      //check with database
      $cmd = new EmailExistsCommand($email);
      $email_exists = $cmd->execute();
      if($email_exists){
        throw new ValidationException("Email not unique");
      }
      else{
        return true;
      }
    }

    static function validatePassword($password){
      if(
        isset($password) &&
        ValidatorEngine::validateArgumentLength($password, 8, 45)
      ){
        return true;
      }
      else{
        throw new ValidationException("Invalid password format");
      }
    }
    static function validateAvatar($file){
      if( empty($file) ){
        return true;
      }
      else if($file["size"] === 0){
        throw new ValidationException("File empty",400);
      }
      else if( $file["size"] > ValidatorEngine::MAX_FILESIZE){
        throw new ValidationException("File too large", 413);
      }
      else if(
        empty(getimagesize($file["tmp_name"])) ||
        empty(strstr(getimagesize($file["tmp_name"])["mime"], "image") ) ||
        empty(strstr(mime_content_type($file["tmp_name"]), "image"))
      ){
        throw new ValidationException("Unsupported file-type", 415);
      }
      else{
        return true;
      }
    }
    static function validateMajor($major){
      if(
        isset($major) &&
        ValidatorEngine::validateArgumentLength($major, 3, 45) &&
        preg_match('/^[a-zA-Z][a-zA-Z0-9]*$/', $major)
      ){
        return true;
      }
      else{
        throw new ValidationException("Invalid major format");
      }
    }
    static function validateYear($year){
      if(
        isset($year) &&
        ValidatorEngine::validateArgumentLength($year, 4, 4) &&
        filter_var(
          $year,
          \FILTER_VALIDATE_INT,
          array("options" => array("min_range"=>1900, "max_range"=>date("Y")))
          )
      ) {
        return true;
      }
      else{
        throw new ValidationException("Invalid year format");
      }
    }
    static function validateCourseId($courseid){
      if(
        isset($courseid) &&
        ValidatorEngine::validateArgumentLength($courseid, 4, 4) &&
        preg_match('/^[a-zA-Z0-9]{4}$/', $courseid)
      ){
        return true;
      }
      else{
        throw new ValidationException("Invalid courseid format");
      }
    }
    static function validateCourseExists($courseid){
      //check with database
      $cmd = new CourseExistsCommand($courseid);
      $course_exists = $cmd->execute();
      if(!$course_exists){
        throw new ValidationException("Course does not exist",404);
      }
      else{
        return true;
      }
    }
    static function validatePin($pin){
      if(
        isset($pin) &&
        ValidatorEngine::validateArgumentLength($pin, 4, 4) &&
        preg_match('/^[a-zA-Z0-9]{4}$/', $pin)
      ){
        return true;
      }
      else{
        throw new ValidationException("Invalid courseid format");
      }
    }
    static function validateMessage($message){
      if(
        !self::validateArgumentLength($message, 1, 120) ||
        preg_match('/[\x00\x0f\\\<\>\"\']/',$message)
      ){
        throw new ValidationException("Invalid message format");
      }
    }
    static function validateMessageExistsInCourse($courseid, $messageid){
      $cmd = new MessageExistsCommand($courseid, $messageid);
      $message_exists = $cmd->execute();
      if(!$message_exists){
        throw new ValidationException("Message does not exist",404);
      }
      else{
        return true;
      }
    }
  }

  use \api\command\Command;
  abstract class ValidationCommand extends Command{
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      $result = $pdos->fetch();
      $this->response = $result["status"]=="ok";
      $this->responseCode = $this->response ? 200 : 400;
      return $result["status"]=="ok";
    }
  }
  abstract class ObjectExistsCommand extends ValidationCommand{
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      $result = $pdos->fetch();
      if( $result["exists"]==1 ){
        $this->response_code = 200;
        $this->response = true;
      }
      else if($result["exists"]==0){
        $this->response_code = 200;
        $this->response = false;
      }
      else{
        throw new \Exception(self::GENERIC_ERROR);
      }
    }
  }
  class UsernameExistsCommand extends ObjectExistsCommand{
    private $username;
    public function __construct($username){
      $this->prepared_statement = "CALL `validation::username_exists`( :_username )";
      $this->username = $username;
    }
    protected function arguments(){
      return array(
        ":_username" => $this->username
      );
    }
  }
  class EmailExistsCommand extends ObjectExistsCommand{
    private $email;
    public function __construct($email){
      $this->prepared_statement = "CALL `validation::email_exists`( :_email )";
      $this->email = $email;
    }
    protected function arguments(){
      return array(
        ":_email" => $this->email
      );
    }
  }
  class CourseExistsCommand extends ObjectExistsCommand{
    private $courseid;
    public function __construct($courseid){
      $this->prepared_statement = "CALL `validation::course_exists`( :_courseid )";
      $this->courseid = $courseid;
    }
    protected function arguments(){
      return array(
        ":_courseid" => $this->courseid
      );
    }
  }
  class MessageExistsCommand extends ObjectExistsCommand{
    private $corurseid, $messageid;
    public function __construct($courseid, $messageid){
      $this->prepared_statement = "CALL `validation::message_exists`( :_courseid, :_messageid )";
      $this->messageid = $messageid;
      $this->courseid = $courseid;
    }
    protected function arguments(){
      return array(
        ":_courseid" => $this->courseid,
        ":_messageid" => $this->messageid
      );
    }
  }


  class ValidationException extends \api\command\CommandExecutionException{
    public function __construct($message, $response_code = 400){
      parent::__construct($message, $response_code);
    }
  }
  class ArgumentTooLargeException extends ValidationException{
    private $size, $snippet;
    private const MAX_SIZE = 45; //By limiting how much of the argument is logged, we mitigate some DOS potential.
    public function __construct($argument, $message = "Argument too large", $response_code = 413){
      parent::__construct($message, $response_code);
      $this->size = strlen($argument);
      $this->snippet = substr( $argument, 0, min(ArgumentTooLargeException::MAX_SIZE, $this->size) );
    }
    public function getSize(){
      return $this->size;
    }
    public function getSnippet(){
      return $this->size > self::MAX_SIZE ? $this->snippet . "..." : $this->snippet;
    }
  }
}
?>
