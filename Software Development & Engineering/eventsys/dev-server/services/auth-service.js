import jwt from 'jsonwebtoken';

export function generateJWT(user) {
  const tokenData = {
    id: user._id,
    username: user.username,
    userlevel: user.userlevel
  }

  return jwt.sign({ user: tokenData }, process.env.TOKEN_SECRET);
}

export function requireLogin(req, res, next) {
  const token = decodeToken(req);

  if(!token) {
    return res.status(401).json({ message: 'You have to be logged in...' });
  }

  next();
}

export function decodeToken(req) {
  const token = req.headers.authorization || req.headers['authorization'];

  if(!token) {
    return null;
  }
  
  try {
    return jwt.verify(token, process.env.TOKEN_SECRET);
  }
  catch(error) {
    return null;
  }
}

export function getUsername(req) {
  const token = decodeToken(req);

  if(!token) {
    return null;
  }

  return token.user.username;
}

export function isLoggedIn(req) {
  const token = decodeToken(req);

  if(!token) {
    return false
  }
  else {
    return true
  }
}

export function getUserId(req) {
  const token = decodeToken(req);

  if(!token) {
    return null;
  }

  return token.user.id;
}

export function getUserlevel(req) {
  const token = decodeToken(req);

  if(!token) {
    return null;
  }

  return token.user.userlevel;
}