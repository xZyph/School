<?php
namespace api;

require 'commands/user.php';
require 'commands/course.php';
require 'commands/admin.php';
require 'vendor/autoload.php';
require 'logging/logger.php';

header("X-Frame-Options: DENY");
header("X-Content-Type-Options: nosniff");
header('Content-Type: application/json; charset=UTF-8');
header("Content-Security-Policy: frame-ancestors'none'");
header("Upgrade-Insecure-Requests: 1");
use \api\log\LoggingEngine;
$logger = LoggingEngine::init();
\api\authorization\AuthEngine::init();

/**
 * Returns a generic message after incorrect use of API, or after another error
 * 
 * @param $message the message to display
 * 
 * @return null
 */
function Generic_deny($message = "An error has occured")
{
  exit(
    json_encode(
      array(
        "message"=>$message,
        "tracking_id"=>LoggingEngine::$TRACKING_ID
      )
    )
  );
}


//app does not provide proper user-agent
if( empty(getallheaders()["User-Agent"]) ){
  $logger->error("User failed to identify user-agent");
  http_response_code(401);
  Generic_deny("unauthorized");
}
else if( $_SERVER['REQUEST_URI']==="/make_coffee" ){
  $logger->info("User tried to make coffee with a teapot");
  header("HTTP/1.1 418 I'm a teapot");
  Generic_deny("i'm a teapot");
}
/**
 * Matches the first occurrence and returns the group
 * 
 * @param $regex  the regex to match
 * @param $string the string to check
 * @param $key    the group to return
 * 
 * @return string the first match in the group
 */
function Preg_Match_one($regex, $string, $key = 0)
{
    $matches = array();
    preg_match($regex, $string, $matches);
    return $matches[$key];
}
$cmd = null;
$response = null;
$responseCode = null;
try{
  $token = @getallheaders()["token"];
  $coursetoken = @getallheaders()["coursetoken"];

  $method = $_SERVER['REQUEST_METHOD'];
  if( $method != "POST" && $method != "GET"){
    $logger->error("Attempting to access API through an illegal method");
    http_response_code(405);
    Generic_deny("Method not allowed");
  }

  //validate URI
  $uri_arguments = array();
  $valid_uri = false;
  $request = $_SERVER['REQUEST_URI'];

  $username  = null;
  $courseid  = null;
  $messageid = null;
  $commentid = null;
  {
    $username_regex = "/\/(?:user|lecturer)\/(?<username>[a-zA-Z_][a-zA-Z0-9_]{3,45})\/(?:img|confirm|reject)$/";
    $courseid_regex = "/\/course\/(?<courseid>[a-z0-9_]{4})\//";
    $messageid_regex = "/\/message\/(?<messageid>[0-9]{1,4})\//";
    $commentid_regex = "/\/comment\/(?<commentid>[0-9]{1,4})\//";
    $uri_regex = "/^[\/a-zA-Z0-9_]{6,50}$/";
    $valid_uri = preg_match( $uri_regex, $request);
    $username  = @Preg_Match_one($username_regex, $request, "username");
    $courseid  = @Preg_Match_one($courseid_regex, $request, "courseid");
    $messageid = @Preg_Match_one($messageid_regex, $request, "messageid");
    $commentid = @Preg_Match_one($commentid_regex, $request, "commentid");
  }

  $trackingId = "to be implemented";

  if( $valid_uri ){

    $post_endpoints = array(
      "/user/student/register"                                        =>"api\\command\\user\\RegisterStudentCommand",
      "/user/lecturer/register"                                       =>"api\\command\\user\\RegisterLecturerCommand",
      "/user/login"                                                   =>"api\\command\\user\\LoginCommand",
      "/user/update_password"                                         =>"api\\command\\user\\UpdatePasswordCommand",
      "/user/update_image"                                            =>"api\\command\\user\\UpdateAvatarCommand",
      "/course/$courseid/apply"                                       =>"api\\command\\course\\TenureApplicationCommand",
      "/course/$courseid/check_pin"                                   =>"api\\command\\course\\CheckCoursePinCommand",
      "/course/$courseid/post_message"                                =>"api\\command\\course\\PostMessageCommand",
      "/course/$courseid/message/$messageid/report"                   =>"api\\command\\course\\ReportMessageCommand",
      "/course/$courseid/message/$messageid/post_comment"             =>"api\\command\\course\\PostCommentCommand",
      "/course/$courseid/message/$messageid/post_reply"               =>"api\\command\\course\\PostReplyCommand",
      "/course/$courseid/message/$messageid/delete"                   =>"api\\command\\course\\DeleteMessageCommand",
      "/course/$courseid/message/$messageid/comment/$commentid/report"=>"api\\command\\course\\ReportCommentCommand",
      "/course/$courseid/message/$messageid/comment/$commentid/delete"=>"api\\command\\course\\DeleteCommentCommand",
      "/course/$courseid/lecturer/$username/reject"                   =>"api\\command\\course\\RejectTenureCommand",
      "/course/$courseid/lecturer/$username/confirm"                  =>"api\\command\\course\\ConfirmTenureCommand",
      "/user/$username/update_status"                                 =>"",
      "/course/token/extend"                                          =>"",
      "/course/token/verify"                                          =>"",
      "/user/token/extend"                                            =>"",
      "/user/token/verify"                                            =>""
    );
    $get_endpoints = array(
      "/user/"                                       =>"",
      "/reports/"                                    =>"api\\command\\admin\\GetReportsCommand",
      "/user/lecturer/"                              =>"api\\command\\user\\GetLecturersCommand",
      "/user/course/"                                =>"api\\command\\user\\GetCoursesCommand",
      "/user/$username/img"                          =>"api\\command\\user\\GetAvatarCommand",
      "/user/message/"                               =>"api\\command\\user\\GetMessagesCommand",
      "/user/message/$messageid/comment/"            =>"api\\command\\user\\GetCommentsCommand",
      "/course/"                                     =>"api\\command\\course\\GetCoursesCommand",
      "/course/$courseid/index"                      =>"api\\command\\course\\GetCourseCommand",
      "/course/$courseid/message/"                   =>"api\\command\\course\\GetMessagesCommand",
      "/course/$courseid/message/$messageid/comment/"=>"api\\command\\course\\GetCommentsCommand" //messageid må sjekkes
    );

    $endpoints_available_from_method = ($method === "POST" ? $post_endpoints : $get_endpoints);
    $method_arguments = $_SERVER['REQUEST_METHOD'] == "GET" ? $_GET : $_POST;

    if( array_key_exists($request, $endpoints_available_from_method) ){
      if($endpoints_available_from_method[$request]===""){
        $logger->info("Attempting to access unimplemented API endpoint", [
          "uri"=>$_SERVER["REQUEST_URI"]
        ]);
        http_response_code(501);
        Generic_deny("Not yet implemented");
      }
      $arguments = array(
        "method"=>$method_arguments,
        "files"=>$_FILES,
        "uri"=>array(
          "username"=>$username,
          "courseid"=>$courseid,
          "messageid"=>$messageid,
          "commentid"=>$commentid
        ),
        "headers"=>array(
          "token"=>$token,
          "coursetoken"=>$coursetoken
        )
      );
      $cmd = new $endpoints_available_from_method[$request]($arguments);
      $cmd->execute();
      $response     = $cmd->getResponse()     ?? "An error occured";
      $responseCode = $cmd->getResponseCode() ?? 500;
      $responseType = $cmd->getResponseType() ?? "json";

      http_response_code($responseCode ?? 500);
      if($responseCode >= 200 && $responseCode <=299){
        if( $cmd->getResponseType() == "json"){
          echo json_encode($response);
        }
        else if($cmd->getResponseType() == "image"){
          header('Content-type: image/jpeg');
          header('Content-Length: ' . filesize($response["filepath"]));
          exit($response["content"]);
        }
      }
      else{
        echo json_encode($response);
      }
    }
    else if( array_key_exists($request,$get_endpoints) || array_key_exists($request,$post_endpoints) ){
      $logger->error("Attempting to access endpoint with an illegal method");
      http_response_code(405);
      Generic_deny("Method not allowed");
    }
    else{
      $logger->error("Attempting to access non-existent API endpoint");
      http_response_code(400);
      Generic_deny("bad request");
    }
  }
  else{
    $logger->error("Attempting to access an invalid API endpoint");
    http_response_code(400);
    Generic_deny("bad request");
  }
}
catch(\api\authorization\AuthenticationException $e){
  $logger->error("User unathenticated for resource", ["error_message"=>urlencode($e->getMessage())]);
  http_response_code($e->getResponseCode() ?? 403);
  Generic_deny("forbidden");
}
catch(\api\authorization\AuthorizationException $e){
  $logger->warning("User not authorized", ["error_message"=>urlencode($e->getMessage())]);
  http_response_code($e->getResponseCode() ?? 401);
  Generic_deny("unauthorized");
}
catch(\api\validation\ArgumentTooLargeException $e){
  $logger->warning("Payload too large for endpoint", ["
    error_message"=>urlencode($e->getMessage()), 
    "snippet"=>urlencode($e->getSnippet()),
    "size"=>urlencode($e->getSize())
    ]);
  http_response_code($e->getResponseCode() ?? 413);
  Generic_deny("payload too large");
}
catch(\api\validation\ValidationException $e){
  $logger->warning("Endpoint was incorrectly used", ["error_message"=>urlencode($e->getMessage())]);
  http_response_code($e->getResponseCode() ?? 400);
  Generic_deny("bad request");
}
catch(\api\command\CommandExecutionException $e){
  $logger->error("An unexpected error was thrown during a command execution", ["error_message"=>urlencode($e->getMessage())]);
  http_response_code($e->getResponseCode() ?? 500);
  Generic_deny();
}
catch(\Exception $e){
  $logger->critical("An unexpected error was thrown during a command execution", ["error_message"=>urlencode($e->getMessage())]);
  if( isset($cmd) ){
    http_response_code($cmd->getResponseCode() ?? 500);
    Generic_deny();
  }
  else{
    http_response_code(500);
    Generic_deny();
  }

}
?>
