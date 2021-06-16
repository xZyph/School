"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.registerEvents = registerEvents;

var _eventsRoutes = _interopRequireDefault(require("./api/events/events-routes"));

var _authRoutes = _interopRequireDefault(require("./api/auth/auth-routes"));

var _registerRoutes = _interopRequireDefault(require("./api/register/register-routes"));

var _userRoutes = _interopRequireDefault(require("./api/user/user-routes"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function registerEvents(app) {
  app.use("/api", _eventsRoutes.default);
  app.use("/api", _authRoutes.default);
  app.use("/api", _registerRoutes.default);
  app.use("/api", _userRoutes.default);
}