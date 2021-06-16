import { StringUtil } from '../../utilities/string-util';
import User from '../../model/user-model';

export function index(req, res) {
  const validation = validateIndex(req.body);

  if(!validation.isValid) {
    return res.status(400).send({ errors: validation.errors })
  }

  const user = new User({
    username: req.body.username,
    password: req.body.password,
    first: req.body.first,
    last: req.body.last,
    email: req.body.email,
    userlevel: req.body.userlevel
  });

  user.save(error => {
    if(error) {
      return res.status(500).send({ errors: [error.message] });
    }
    return res.status(201).json();
  });
}

function validateIndex(body) {
  let errors = [];

  if(StringUtil.isEmpty(body.username)) {
    errors.push('Username is required.');
  }
  if(StringUtil.isEmpty(body.email)) {
    errors.push('Email is required.');
  }
  if(StringUtil.isEmpty(body.first)) {
    errors.push('First name is required.');
  }
  if(StringUtil.isEmpty(body.last)) {
    errors.push('Last name is required.');
  }
  if(StringUtil.isEmpty(body.password)) {
    errors.push('Password is required.');
  }
  if(body.password !== body.password2) {
    errors.push('Passwords do not match.');
  }
  if(body.userlevel < 2 || body.userlevel > 3) {
    errors.push('You cannot deviate from the given userlevels.');
  }

  return {
    isValid: errors.length > 0 ? false : true,
    errors: errors
  }
}