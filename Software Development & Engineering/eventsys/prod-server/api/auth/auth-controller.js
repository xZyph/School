"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.index = index;

var _stringUtil = require("../../utilities/string-util");

var _userModel = _interopRequireDefault(require("../../model/user-model"));

var _authService = require("../../services/auth-service");

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function index(req, res) {
  const validation = validateIndex(req.body);

  if (!validation.isValid) {
    return res.status(400).send({
      errors: validation.errors
    });
  }

  _userModel.default.findOne({
    username: req.body.username.toLowerCase()
  }, (error, user) => {
    if (error) {
      return res.status(500).send({
        errors: [error]
      });
    }

    if (!user) {
      return res.status(401).send({
        errors: ["User does not exist."]
      });
    }

    const passwordsMatch = _userModel.default.passwordMatches(req.body.password, user.password);

    if (!passwordsMatch) {
      return res.status(401).send({
        errors: ["Invalid login credentials."]
      });
    }

    const token = (0, _authService.generateJWT)(user);
    return res.status(200).json({
      token: token
    });
  });
}

function validateIndex(body) {
  let errors = [];

  if (_stringUtil.StringUtil.isEmpty(body.username)) {
    errors.push('Username is required.');
  }

  if (_stringUtil.StringUtil.isEmpty(body.password)) {
    errors.push('Password is required.');
  }

  return {
    isValid: errors.length > 0 ? false : true,
    errors: errors
  };
}