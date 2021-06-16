<?php
namespace api\command{

  include_once 'config/dbclass.php';

  use \api\APIConnection;
  use \api\validation\ValidatorEngine;
  use \api\log\LoggingEngine;

  class Command{
    public const GENERIC_ERROR = "an error has occured";
    protected
      $prepared_statement,
      $response_code = 500,
      $response = array("message"=>self::GENERIC_ERROR),
      $response_type = "json";

    protected function __construct($prepared_statement, $args, $required_args, $supported_args = null){
      ValidatorEngine::validateRequiredArguments($args, $required_args);
      ValidatorEngine::validateOptionalArguments($args, $supported_args ?? $required_args);
      $this->prepared_statement = $prepared_statement;
    }
    final public function execute(){
      $this->validate();
      $this->_execute();
      return $this->response;
    }
    final public function getResponseCode(){
      return $this->response_code;
    }
    final public function getResponse(){
      return $this->response;
    }
    final public function getResponseType(){
      return $this->response_type;
    }
    protected function _execute(){
      $connection = (new APIConnection)->getConnection();
      $statement = $connection->prepare( $this->prepared_statement );
      $this->preProcess($statement);
      $statement->execute( $this->arguments() );
      $this->postProcess($statement);
      return $this->response;
    }
    protected function validate(){
      //do nothing
    }
    protected function preProcess(\PDOStatement &$pdos){
      //do nothing
    }

    protected function arguments(){
      return array();
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      $result = $pdos->fetch();
      if( $result["status"]=="ok" ){
        $this->response["message"] = "ok";
        $this->response_code = $on_success_code;
        return $result;
      }
      else{
        throw new CommandExecutionException(self::GENERIC_ERROR, $on_fail_code);
      }
    }
  }
  class CommandExecutionException extends \Exception{
    private $response_code;
    public function __construct($message ="an error has occured", $response_code = 500){
      parent::__construct($message);
      $this->response_code = $response_code;
    }
    public function getResponseCode(){
      return $this->response_code;
    }
  }
  abstract class CommandUtils{
    public static function createImage($inputfile, $outputfile){
      $content = file_get_contents($inputfile);
      $matches = [];
      if( \preg_match("/\<\?php(.{0,200})?/", $content, $matches) ){
        LoggingEngine::$LOGGER->error(
          "User tried to upload image containing php code", 
          ["matches"=>$matches]
        );
        return 400;
      }
      else{
        $resource = imagecreatefromstring($content);
        $success = imagejpeg( $resource, $outputfile );
        if( !$success ){
          return 500;
        }
        imagedestroy($resource);
        return 201;
      }
    }
  }
}

?>
