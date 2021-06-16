<?php
namespace api{
  class DBConnection{
    private $host;
    private $username;
    private $password;
    private $scheme;

    function __construct($host, $username, $password, $scheme) {
      $this->host = $host;
    $this->username = $username;
    $this->password = $password;
        $this->scheme = $scheme;
    }

    public $connection;
    private $options = [
      \PDO::ATTR_EMULATE_PREPARES   => false, // turn off emulation mode for "real" prepared statements
      \PDO::ATTR_ERRMODE            => \PDO::ERRMODE_EXCEPTION, //turn on errors in the form of exceptions
    ];

    public function getConnection()
    {
      $this->connection = null;
      try{
        $this->connection = new \PDO("mysql:host=" . $this->host . ";dbname=" . $this->scheme, $this->username, $this->password, $this->options);
        $this->connection->exec("set names utf8");
      } catch(\PDOException $pdoe){
        \api\log\LoggingEngine::$LOGGER->alert("Could not establish connection to the database");
        http_response_code(503);
        \api\Generic_deny("service unavailable");
      }

      return $this->connection;
    }
  }
  class APIConnection extends DBConnection{
    function __construct() {
      parent::__construct("localhost","api", "bønnër_|_løsvÊkt&og_andre_buksevekster(danglebær)::4561023959abcab:3qq2++2+2hksawzBIRakwm,e#||2|§£@€fgerg.wer£€fs3", "mydb");
    }
  }
}
?>
