"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.setEnvironment = setEnvironment;

var _express = _interopRequireDefault(require("express"));

var _morgan = _interopRequireDefault(require("morgan"));

var _cors = _interopRequireDefault(require("cors"));

var _bodyParser = _interopRequireDefault(require("body-parser"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function setEnvironment(app) {
  // FIXED BUG! For some reason it reads the extra space from NODE_ENV
  if (process.env.NODE_ENV !== "production ") {
    setDevEnv(app);
  } else {
    setProdEnv(app);
  }
}

function setDevEnv(app) {
  process.env.NODE_ENV = 'development';
  process.env.DB_URL = 'mongodb://localhost:27017/eventsys-dev-db';
  process.env.TOKEN_SECRET = 'BH*LnaUG=s!xgs.I9qT6v~#M{Hz4B8hk<pFj+tigS}M71[&Jq8@H)Y_pmO*UogU';
  app.use(_bodyParser.default.json());
  app.use((0, _morgan.default)('dev'));
  app.use((0, _cors.default)());
}

function setProdEnv(app) {
  process.env.DB_URL = 'mongodb+srv://eventsys:wOlTCw8sCAR3hhPo@eventsys-rubfw.mongodb.net/test?retryWrites=true&w=majority';
  process.env.TOKEN_SECRET = 'p4Y4)7/X-v_IMipcvfn7yu}Ud[pg`QEF7d^tCBZk!^{+1QZT+{Re_b5Q@V3r42K';
  app.use(_bodyParser.default.json());
  app.use(_express.default.static(__dirname + "/../dist"));
}