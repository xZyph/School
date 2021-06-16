# URL:
  /user/student/register
## Method:
POST
## URL Params:
### Required:
-
### Optional:
-
## Data Params:
### Required:
username=[string]
password=[string]
email=[string]
major=[string]
year=[string]
### Optional:
-
## Header Params:
### Required:
-
### Optional:
-
## File Params:
### Required:
-
### Optional:
-
## Success response:
code: 201
response: {"message":"ok"}
==========================
# URL:_    
/user/lecturer/register
## Method:
POST
## URL Params:
### Required:
-
### Optional:
-
## Data Params:
### Required:
username=[string]
password=[string]
email=[string]
### Optional:
-
## Header Params:
### Required:
-
### Optional:
-
## File Params:
### Required:
-
### Optional:
image=[image]
## Success response:
code: 201
response: {"message":"ok"}
==========================
# URL:_    
/user/login
## Method:
POST
## URL Params:
### Required:
-
### Optional:
-
## Data Params:
### Required:
username=[string]
password=[string]
### Optional:
-
## Header Params:
### Required:
-
### Optional:
-
## File Params:
### Required:
-
### Optional:
-
## Success response:
code: 200
response: {"message":"ok","token":[jwt_token]}
==========================
# URL:_    
/user/update_password
## Method:
POST
## URL Params:
### Required:
-
### Optional:
-
## Data Params:
### Required:
password=[string]
### Optional:
-
## Header Params:
### Required:
token=[jwt_token]
### Optional:
-
## File Params:
### Required:
-
### Optional:
-
## Success response:
code: 200
response: {"message":"ok"}
==========================
# URL:_    
/user/update_image
## Method:
POST
## URL Params:
### Required:
-
### Optional:
-
## Data Params:
### Required:
-
### Optional:
-
## Header Params:
### Required:
token=[jwt_token]
### Optional:
-
## File Params:
### Required:
image=[image]
### Optional:
-
## Success response:
code: 200
response: {"message":"ok"}
==========================
# URL:_    
/course/$COURSEID/check_pin
## Method:
POST
## URL Params:
### Required:
$COURSEID=[string{4}]
### Optional:
-
## Data Params:
### Required:
pin=[string{4}]
### Optional:
-
## Header Params:
### Required:
-
### Optional:
-
## File Params:
### Required:
-
### Optional:
-
## Success response:
code: 200
response: {"message":"ok","coursetoken":[jwt_token]}
==========================
# URL:_    
/course/$COURSEID/post_message
## Method:
POST
## URL Params:
### Required:
$COURSEID=[string{4}]
### Optional:
-
## Data Params:
### Required:
message=[string]
### Optional:
-
## Header Params:
### Required:
token=[jwt_token]
coursetoken=[jwt_token]
### Optional:
-
## File Params:
### Required:
-
### Optional:
-
## Success response:
code: 201
response: {"message":"ok"}
==========================
# URL:_    
/course/$COURSEID/message/$MESSAGEID/report
## Method:
POST
## URL Params:
### Required:
$COURSEID=[string{4}]
$MESSAGEID=[integer]
### Optional:
-
## Data Params:
### Required:
-
### Optional:
-
## Header Params:
### Required:
coursetoken=[jwt_token]
### Optional:
token=[jwt_token]
## File Params:
### Required:
-
### Optional:
-
## Success response:
code: 201
response: {"message":"ok"}
==========================
# URL:_    
/course/$COURSEID/message/$MESSAGEID/post_comment
## Method:
POST
## URL Params:
### Required:
$COURSEID=[string{4}]
$MESSAGEID=[integer]
### Optional:
-
## Data Params:
### Required:
content=[string]
### Optional:
-
## Header Params:
### Required:
token|coursetoken=[jwt_token]
### Optional:
-
## File Params:
### Required:
-
### Optional:
-
## Success response:
code: 201
response: {"message":"ok"}
==========================
# URL:_    
/course/$COURSEID/message/$MESSAGEID/port_reply
## Method:
POST
## URL Params:
### Required:
$COURSEID=[string{4}]
$MESSAGEID=[integer]
### Optional:
-
## Data Params:
### Required:
content=[string]
### Optional:
-
## Header Params:
### Required:
token=[jwt_token]
### Optional:
-
## File Params:
### Required:
-
### Optional:
-
## Success response:
code: 201
response: {"message":"ok"}
==========================
# URL:_    
/course/$COURSEID/message/$MESSAGEID/comment/$COMMENTID/report
## Method:
POST
## URL Params:
### Required:
$COURSEID=[string{4}]
$MESSAGEID=[integer]
$COMMENTID=[integer]
### Optional:
-
## Data Params:
### Required:
-
### Optional:
-
## Header Params:
### Required:
coursetoken=[jwt_token]
### Optional:
token=[jwt_token]
## File Params:
### Required:
-
### Optional:
-
## Success response:
code: 201
response: {"message":"ok"}
==========================
# URL:_    
/user/lecturer/
## Method:
GET
## URL Params:
### Required:
-
### Optional:
-
## Data Params:
### Required:
-
### Optional:
-
## Header Params:
### Required:
token=[jwt_token]
### Optional:
-
## File Params:
### Required:
-
### Optional:
-
## Success response:
code: 200
response: {"message":"ok"}
==========================
# URL:_    
/user/$USERNAME/img
## Method:
GET
## URL Params:
### Required:
$USERNAME=[string]
### Optional:
-
## Data Params:
### Required:
-
### Optional:
-
## Header Params:
### Required:
-
### Optional:
-
## File Params:
### Required:
-
### Optional:
-
## Success response:
code: 200
response: [image]
==========================
# URL:_    
/user/message/
## Method:
GET
## URL Params:
### Required:
-
### Optional:
-
## Data Params:
### Required:
-
### Optional:
-
## Header Params:
### Required:
token=[jwt_token]
### Optional:
-
## File Params:
### Required:
-
### Optional:
-
## Success response:
code: 200
response: {"message":"ok","messages":[]}
==========================
# URL:_    
/course/
## Method:
GET
## URL Params:
### Required:
-
### Optional:
-
## Data Params:
### Required:
-
### Optional:
-
## Header Params:
### Required:
-
### Optional:
-
## File Params:
### Required:
-
### Optional:
-
## Success response:
code: 200
response: {"message":"ok","courses":[]}
==========================
# URL:_    
/course/$COURSEID/message/
## Method:
GET
## URL Params:
### Required:
$COURSEID=[string{4}]
### Optional:
-
## Data Params:
### Required:
-
### Optional:
-
## Header Params:
### Required:
token|coursetoken=[jwt_token]
### Optional:
-
## File Params:
### Required:
-
### Optional:
-
## Success response:
code: 200
response: {"message":"ok","messages":[]}
==========================
# URL:_    
/course/$COURSEID/message/$MESSAGEID/comment/
## Method:
GET
## URL Params:
### Required:
$COURSEID=[string{4}]
$MESSAGEID=[integer]
### Optional:
-
## Data Params:
### Required:
-
### Optional:
-
## Header Params:
### Required:
token|coursetoken=[jwt_token]
### Optional:
-
## File Params:
### Required:
-
### Optional:
-
## Success response:
code: 200
response: {"message":"ok","comments":[]}
==========================
