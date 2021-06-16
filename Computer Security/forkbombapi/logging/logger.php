<?php
namespace api\log{
  require_once 'vendor/autoload.php';
  use \Monolog\Logger;
  use \Monolog\Handler\StreamHandler;
  use \Monolog\Handler\LogglyHandler;
  use \Monolog\Formatter\LogglyFormatter;
  use \Monolog\Handler\FingersCrossedHandler;
  use \Monolog\Handler\FingersCrossed\ErrorLevelActivationStrategy;
  use \Monolog\Handler\GelfHandler;
  use \Gelf\Message;
  use \Monolog\Formatter\GelfMessageFormatter;
  use \api\authorization\AuthEngine;

  abstract class LoggingEngine{
    public static $LOGGER;
    public static $TRACKING_ID;
    public static function init(){
      $logger = new Logger("API");
      self::$TRACKING_ID = bin2hex(random_bytes(8));

      $transport = new \Gelf\Transport\UdpTransport("127.0.0.1", 12769);
      $publisher = new \Gelf\Publisher($transport);
      $handler = new GelfHandler($publisher,Logger::DEBUG);

      $logger->pushHandler(new StreamHandler(__DIR__ . '/log/app.log', Logger::DEBUG));
      $logger->pushHandler($handler);

      $logger->pushProcessor(function ($record) {
        $record['extra']['ip'] = urlencode($_SERVER['REMOTE_ADDR']);
        $record['extra']['endpoint'] = urlencode($_SERVER['REQUEST_URI']);
        $record['extra']['method'] = urlencode($_SERVER['REQUEST_METHOD']);
        $record['extra']['tracking_id'] = LoggingEngine::$TRACKING_ID;
        return $record;
      });

      self::$LOGGER = $logger;
      return $logger;
    }
  }
}
?>
