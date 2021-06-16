import User from '../../model/user-model';
import Event from '../../model/event-model';
import moment from 'moment';
import * as Auth from '../../services/auth-service';
import { StringUtil } from '../../utilities/string-util';
import { ArrayUtil } from '../../utilities/array-util';

export function index(req, res) {
  if(Auth.isLoggedIn(req)) {
    const id = Auth.getUserId(req);

    User.findOne({ _id: id }, (error, user) => {
      if(error || !user) {
        return res.status(500).send({ errors: error.message });
      }

      Event.find({}).lean().exec( function(error, events) {
        if(error) {
          return res.status(500).json();
        }

        events = events.map(event => ({ ...event, isEnrolled: ArrayUtil.contains(event.attendees, user.id) }));

        return res.status(200).json({ events: events });
      });
    });
  }
  else {
    Event.find({}, (error, events) => {
      if(error) {
        return res.status(500).send({ errors: error.message });
      }
      return res.status(200).json({ events: events });
    });
  }
}

// CREATE EVENT
export function create(req, res) {
  const id = Auth.getUserId(req);
  const validation = validateIndex(req.body);

  if(!validation.isValid) {
    return res.status(400).send({ errors: validation.errors })
  }

  User.findOne({ _id: id }, (error, user) => {
    if(error && !user) {
      return res.status(500).send({ errors: error.message });
    }

    const event = new Event(req.body.event);
    event.owner = user._id;
    event.fromDate = moment(event.fromDate);
    event.toDate = moment(event.toDate);

    event.save(error => {
      if(error) {
        return res.status(500).send({ errors: error.message });
      }
      return res.status(201).json();
    });
  });

  return res.status(201).json();
}

function validateIndex(body) {
  let errors = [];

  if(StringUtil.isEmpty(body.event.title)) {
    errors.push('Title is required.');
  }
  if(StringUtil.isEmpty(body.event.description)) {
    errors.push('Description is required.');
  }
  if(StringUtil.isEmpty(body.event.longDescription)) {
    errors.push('Long Description is required.');
  }
  if(StringUtil.isEmpty(body.event.fromDate)) {
    errors.push('From date is required.');
  }
  if(StringUtil.isEmpty(body.event.toDate)) {
    errors.push('To date is required.');
  }
  if(StringUtil.isEmpty(body.event.maxAttendees)) {
    errors.push('Maximum amount of attendees is required.');
  }

  return {
    isValid: errors.length > 0 ? false : true,
    errors: errors
  }
}

export function update(req, res) {
  // EDIT EVENT
  const id = Auth.getUserId(req);

  User.findOne({ _id: id }, (error, user) => {
    if(error) {
      return res.status(500).json();
    }
    if(!user) {
      return res.status(404).json();
    }

    const event = new Event(req.body.event);
    event.owner = user._id;
    event.fromDate = moment(event.fromDate);
    event.toDate = moment(event.toDate);

    Event.findByIdAndUpdate({ _id: event._id }, event, error => {
      if(error) {
        return res.status(500).json();
      }
      return res.status(204).json();
    });
  })
}

export function del(req, res) {
  // DELETE EVENT
  const id = Auth.getUserId(req);

  Event.findOne({ _id: req.params.id }, (error, event) => {
    if(error) {
      return res.status(500).json();
    }
    if(!event) {
      return res.status(404).json();
    }
    if(event.owner._id.toString() !== id) {
      return res.status(403).json({ message: 'Not the owner of the event...' });
    }

    Event.deleteOne({ _id: req.params.id }, error => {
      if(error) {
        res.status(500).json();
      }
      return res.status(204).json();
    });
  })
}

// SHOW SINGLE EVENT
export function show(req, res) {
  if(Auth.isLoggedIn(req)) {
    const id = Auth.getUserId(req);

    User.findOne({ _id: id }, (error, user) => {
      if(error || !user) {
        return res.status(500).send({ errors: error.message });
      }

      Event.findOne({ _id: req.params.id }).lean().exec( function(error, event) {
        if(error) {
          return res.status(500).json();
        }

        if(!event) {
          return res.status(404).json();
        }

        event.isEnrolled = ArrayUtil.contains(event.attendees, user.id);

        return res.status(200).json({ event: event });
      });
    });
  }
  else {
    Event.findOne({ _id: req.params.id }).lean().exec( function(error, event) {
      if(error) {
        return res.status(500).json();
      }

      if(!event) {
        return res.status(404).json();
      }

      return res.status(200).json({ event: event });
    });
  }
}

// SHOW SINGLE EVENT PARTICIPANTS
export function showParticipants(req, res) {
  const id = Auth.getUserId(req);

  User.findOne({ _id: id }, (error, user) => {
    if(error || !user) {
      return res.status(500).send({ errors: error.message });
    }

    Event.findOne({ _id: req.params.id }).populate({path: 'attendees', select: 'first last email username'}).lean().exec( function(error, event) {
      if(error) {
        return res.status(500).json();
      }

      if(!event) {
        return res.status(404).json();
      }

      if(event.owner != user.id) {
        return res.status(500).json();
      }

      return res.status(200).json({ event: event });
    });
  });
}

export function countAttendees(req, res) {
  Event.findOne({ _id: req.params.id }, (error, event) => {
    if(error) {
      return res.status(500).json();
    }
    if(!event) {
      return res.status(400).json();
    }
    return res.status(200).json({ attendees: event.attendees.length });
  });
}

export function enrollToEvent(req, res) { 

  User.findOne({ _id: Auth.getUserId(req) }, (error, user) => {
    if(error) {
      return res.status(500).json();
    }
    if(!user) {
      return res.status(404).json();
    }

    Event.findById(
      req.params.id,
      (error, event) => {
        if(error) {
          return res.status(500).json();
        }
  
        if(!event) {
          return res.status(400).json();
        }

        if(event.attendees.length + 1 > event.maxAttendees) {
          return res.status(400).send({ errors: ["The event is unfortunately fully booked."] });
        }

        if(event.attendees.indexOf(user._id) != -1) {
          return res.status(400).send({ errors: ["You have already signed up for this event."] });
        }

        event.attendees.push(user._id);
        event.markModified('attendees');
        event.save((error) => {
          if(error) {
            res.status(500).json();
          }
        })

        return res.status(200).json();
      }
    )

  })
}