import { StringUtil } from '../../utilities/string-util';
import User from '../../model/user-model';
import { generateJWT } from '../../services/auth-service';

export function index(req, res) {
  const validation = validateIndex(req.body);

  if(!validation.isValid) {
    return res.status(400).send({ errors: validation.errors })
  }

  User.findOne({ username: req.body.username.toLowerCase() }, (error, user) => {
    if(error) {
      return res.status(500).send({ errors: [error] });
    }

    if(!user) {
      return res.status(401).send({
        errors: ["User does not exist."]
      });
    }

    const passwordsMatch = User.passwordMatches(req.body.password, user.password);
    if(!passwordsMatch) {
      return res.status(401).send({
        errors: ["Invalid login credentials."]
      });
    }
    
    const token = generateJWT(user);
    return res.status(200).json({ token: token });
  })
}

function validateIndex(body) {
  let errors = [];

  if(StringUtil.isEmpty(body.username)) {
    errors.push('Username is required.');
  }
  if(StringUtil.isEmpty(body.password)) {
    errors.push('Password is required.');
  }

  return {
    isValid: errors.length > 0 ? false : true,
    errors: errors
  }
}