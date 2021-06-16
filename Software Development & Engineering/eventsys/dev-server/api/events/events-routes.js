import express from 'express';
const router = express.Router();
import * as controller from './events-controller';
import * as Auth from '../../services/auth-service';

router.post("/events", Auth.requireLogin, controller.create);

router.get("/events", controller.index);

router.get("/events/:id", controller.show);

router.get("/events/:id/participants", controller.showParticipants);

router.get("/events/:id/attendees", controller.countAttendees);

router.get("/events/:id/enroll", controller.enrollToEvent);

router.put("/events", Auth.requireLogin, controller.update);

router.delete("/events", Auth.requireLogin, controller.del);

export default router;