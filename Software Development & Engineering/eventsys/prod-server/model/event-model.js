"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = void 0;

var _mongoose = _interopRequireDefault(require("mongoose"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

const eventSchema = new _mongoose.default.Schema({
  title: String,
  description: String,
  longDescription: String,
  fromDate: Date,
  toDate: Date,
  owner: {
    type: _mongoose.default.Schema.Types.ObjectId,
    ref: 'user'
  },
  maxAttendees: Number,
  attendees: [{
    type: _mongoose.default.Schema.Types.ObjectId,
    ref: 'user'
  }]
});
eventSchema.set('timestamps', true);

var _default = _mongoose.default.model('event', eventSchema);

exports.default = _default;