"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = void 0;

var _mongoose = _interopRequireDefault(require("mongoose"));

var _stringUtil = require("../utilities/string-util");

var _bcryptNodejs = _interopRequireDefault(require("bcrypt-nodejs"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

const userSchema = new _mongoose.default.Schema({
  username: String,
  first: String,
  last: String,
  password: String,
  email: String,
  userlevel: {
    type: Number,
    default: 3
  }
});
userSchema.set('timestamps', true);
userSchema.virtual('fullName').get(function () {
  const first = _stringUtil.StringUtil.capitalize(this.first.toLowerCase());

  const last = _stringUtil.StringUtil.capitalize(this.last.toLowerCase());

  return "".concat(first, " ").concat(last);
});

userSchema.statics.passwordMatches = function (password, hash) {
  return _bcryptNodejs.default.compareSync(password, hash);
};

userSchema.pre('save', function (next) {
  this.username = this.username.toLowerCase();
  this.first = this.first;
  this.last = this.last;
  this.email = this.email.toLowerCase();
  const unsafePass = this.password;
  this.password = _bcryptNodejs.default.hashSync(unsafePass);

  _mongoose.default.model('user', userSchema).findOne({
    username: this.username
  }, (error, user) => {
    if (user) {
      next(new Error("Username already exists."));
    }

    next();
  });
});

var _default = _mongoose.default.model('user', userSchema);

exports.default = _default;