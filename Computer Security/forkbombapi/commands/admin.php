<?php
namespace api\command\admin{
  use \api\validation\ValidatorEngine;
  use \api\authorization\AuthEngine;
  use \api\command\Command;
  use \api\authorization\AuthenticationException;
  class GetReportsCommand extends Command{
    private const PREPARED_STATEMENT = "CALL `report::all`()";
    private const EXPECTED_ARGUMENTS = ["headers"=>["token"]];
    private $token;
    public function __construct($args){
      parent::__construct(self::PREPARED_STATEMENT, $args, self::EXPECTED_ARGUMENTS);
      $this->token = AuthEngine::token_decode( $args["headers"]["token"], AuthEngine::USER_TOKEN);
    }
    protected function validate(){
      AuthEngine::authorizeToken($this->token);
      AuthEngine::authenticateUserType($this->token, ["admin"]);
    }
    protected function postProcess(\PDOStatement &$pdos, $on_success_code = 200, $on_fail_code = 500){
      $result = $pdos->fetchAll(\PDO::FETCH_COLUMN|\PDO::FETCH_GROUP,2);
      $this->response["message"] = "ok";
      $this->response["reports"] = $result;
      $this->response_code = 200;
    }
  }
}
?>
