"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.index = index;
exports.create = create;
exports.update = update;
exports.del = del;
exports.show = show;
exports.showParticipants = showParticipants;
exports.countAttendees = countAttendees;
exports.enrollToEvent = enrollToEvent;

var _userModel = _interopRequireDefault(require("../../model/user-model"));

var _eventModel = _interopRequireDefault(require("../../model/event-model"));

var _moment = _interopRequireDefault(require("moment"));

var Auth = _interopRequireWildcard(require("../../services/auth-service"));

var _stringUtil = require("../../utilities/string-util");

var _arrayUtil = require("../../utilities/array-util");

function _getRequireWildcardCache() { if (typeof WeakMap !== "function") return null; var cache = new WeakMap(); _getRequireWildcardCache = function _getRequireWildcardCache() { return cache; }; return cache; }

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } var cache = _getRequireWildcardCache(); if (cache && cache.has(obj)) { return cache.get(obj); } var newObj = {}; if (obj != null) { var hasPropertyDescriptor = Object.defineProperty && Object.getOwnPropertyDescriptor; for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) { var desc = hasPropertyDescriptor ? Object.getOwnPropertyDescriptor(obj, key) : null; if (desc && (desc.get || desc.set)) { Object.defineProperty(newObj, key, desc); } else { newObj[key] = obj[key]; } } } } newObj.default = obj; if (cache) { cache.set(obj, newObj); } return newObj; }

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); if (enumerableOnly) symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; }); keys.push.apply(keys, symbols); } return keys; }

function _objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i] != null ? arguments[i] : {}; if (i % 2) { ownKeys(source, true).forEach(function (key) { _defineProperty(target, key, source[key]); }); } else if (Object.getOwnPropertyDescriptors) { Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)); } else { ownKeys(source).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } } return target; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

function index(req, res) {
  if (Auth.isLoggedIn(req)) {
    const id = Auth.getUserId(req);

    _userModel.default.findOne({
      _id: id
    }, (error, user) => {
      if (error || !user) {
        return res.status(500).send({
          errors: error.message
        });
      }

      _eventModel.default.find({}).lean().exec(function (error, events) {
        if (error) {
          return res.status(500).json();
        }

        events = events.map(event => _objectSpread({}, event, {
          isEnrolled: _arrayUtil.ArrayUtil.contains(event.attendees, user.id)
        }));
        return res.status(200).json({
          events: events
        });
      });
    });
  } else {
    _eventModel.default.find({}, (error, events) => {
      if (error) {
        return res.status(500).send({
          errors: error.message
        });
      }

      return res.status(200).json({
        events: events
      });
    });
  }
} // CREATE EVENT


function create(req, res) {
  const id = Auth.getUserId(req);
  const validation = validateIndex(req.body);

  if (!validation.isValid) {
    return res.status(400).send({
      errors: validation.errors
    });
  }

  _userModel.default.findOne({
    _id: id
  }, (error, user) => {
    if (error && !user) {
      return res.status(500).send({
        errors: error.message
      });
    }

    const event = new _eventModel.default(req.body.event);
    event.owner = user._id;
    event.fromDate = (0, _moment.default)(event.fromDate);
    event.toDate = (0, _moment.default)(event.toDate);
    event.save(error => {
      if (error) {
        return res.status(500).send({
          errors: error.message
        });
      }

      return res.status(201).json();
    });
  });

  return res.status(201).json();
}

function validateIndex(body) {
  let errors = [];

  if (_stringUtil.StringUtil.isEmpty(body.event.title)) {
    errors.push('Title is required.');
  }

  if (_stringUtil.StringUtil.isEmpty(body.event.description)) {
    errors.push('Description is required.');
  }

  if (_stringUtil.StringUtil.isEmpty(body.event.longDescription)) {
    errors.push('Long Description is required.');
  }

  if (_stringUtil.StringUtil.isEmpty(body.event.fromDate)) {
    errors.push('From date is required.');
  }

  if (_stringUtil.StringUtil.isEmpty(body.event.toDate)) {
    errors.push('To date is required.');
  }

  if (_stringUtil.StringUtil.isEmpty(body.event.maxAttendees)) {
    errors.push('Maximum amount of attendees is required.');
  }

  return {
    isValid: errors.length > 0 ? false : true,
    errors: errors
  };
}

function update(req, res) {
  // EDIT EVENT
  const id = Auth.getUserId(req);

  _userModel.default.findOne({
    _id: id
  }, (error, user) => {
    if (error) {
      return res.status(500).json();
    }

    if (!user) {
      return res.status(404).json();
    }

    const event = new _eventModel.default(req.body.event);
    event.owner = user._id;
    event.fromDate = (0, _moment.default)(event.fromDate);
    event.toDate = (0, _moment.default)(event.toDate);

    _eventModel.default.findByIdAndUpdate({
      _id: event._id
    }, event, error => {
      if (error) {
        return res.status(500).json();
      }

      return res.status(204).json();
    });
  });
}

function del(req, res) {
  // DELETE EVENT
  const id = Auth.getUserId(req);

  _eventModel.default.findOne({
    _id: req.params.id
  }, (error, event) => {
    if (error) {
      return res.status(500).json();
    }

    if (!event) {
      return res.status(404).json();
    }

    if (event.owner._id.toString() !== id) {
      return res.status(403).json({
        message: 'Not the owner of the event...'
      });
    }

    _eventModel.default.deleteOne({
      _id: req.params.id
    }, error => {
      if (error) {
        res.status(500).json();
      }

      return res.status(204).json();
    });
  });
} // SHOW SINGLE EVENT


function show(req, res) {
  if (Auth.isLoggedIn(req)) {
    const id = Auth.getUserId(req);

    _userModel.default.findOne({
      _id: id
    }, (error, user) => {
      if (error || !user) {
        return res.status(500).send({
          errors: error.message
        });
      }

      _eventModel.default.findOne({
        _id: req.params.id
      }).lean().exec(function (error, event) {
        if (error) {
          return res.status(500).json();
        }

        if (!event) {
          return res.status(404).json();
        }

        event.isEnrolled = _arrayUtil.ArrayUtil.contains(event.attendees, user.id);
        return res.status(200).json({
          event: event
        });
      });
    });
  } else {
    _eventModel.default.findOne({
      _id: req.params.id
    }).lean().exec(function (error, event) {
      if (error) {
        return res.status(500).json();
      }

      if (!event) {
        return res.status(404).json();
      }

      return res.status(200).json({
        event: event
      });
    });
  }
} // SHOW SINGLE EVENT PARTICIPANTS


function showParticipants(req, res) {
  const id = Auth.getUserId(req);

  _userModel.default.findOne({
    _id: id
  }, (error, user) => {
    if (error || !user) {
      return res.status(500).send({
        errors: error.message
      });
    }

    _eventModel.default.findOne({
      _id: req.params.id
    }).populate({
      path: 'attendees',
      select: 'first last email username'
    }).lean().exec(function (error, event) {
      if (error) {
        return res.status(500).json();
      }

      if (!event) {
        return res.status(404).json();
      }

      if (event.owner != user.id) {
        return res.status(500).json();
      }

      return res.status(200).json({
        event: event
      });
    });
  });
}

function countAttendees(req, res) {
  _eventModel.default.findOne({
    _id: req.params.id
  }, (error, event) => {
    if (error) {
      return res.status(500).json();
    }

    if (!event) {
      return res.status(400).json();
    }

    return res.status(200).json({
      attendees: event.attendees.length
    });
  });
}

function enrollToEvent(req, res) {
  _userModel.default.findOne({
    _id: Auth.getUserId(req)
  }, (error, user) => {
    if (error) {
      return res.status(500).json();
    }

    if (!user) {
      return res.status(404).json();
    }

    _eventModel.default.findById(req.params.id, (error, event) => {
      if (error) {
        return res.status(500).json();
      }

      if (!event) {
        return res.status(400).json();
      }

      if (event.attendees.length + 1 > event.maxAttendees) {
        return res.status(400).send({
          errors: ["The event is unfortunately fully booked."]
        });
      }

      if (event.attendees.indexOf(user._id) != -1) {
        return res.status(400).send({
          errors: ["You have already signed up for this event."]
        });
      }

      event.attendees.push(user._id);
      event.markModified('attendees');
      event.save(error => {
        if (error) {
          res.status(500).json();
        }
      });
      return res.status(200).json();
    });
  });
}