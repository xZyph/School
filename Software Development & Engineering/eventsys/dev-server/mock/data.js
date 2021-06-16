import UserModel from '../model/user-model';
import EventModel from '../model/event-model';

let athleteId, eventmanagerId;


const mongoose = require('mongoose');
mongoose.connect("mongodb://localhost:27017/eventsys-dev-db", {
  useNewUrlParser: true,
  useUnifiedTopology: true
});
const conn = mongoose.connection;

createData();

// DEFINING FUNCTIONS
async function createData() {
  await conn.dropDatabase();

  await createUsers();

  await createEvents();

  return conn.close();
}

async function createUsers() {
  const athlete = new UserModel({
    username: "sensor-athlete",
    password: "sensor",
    first: "Sensor",
    last: "Sensorsen",
    email: "sensor@sensor.sensor",
    userlevel: 3
  });

  const eventmanager = new UserModel({
    username: "sensor-eventmanager",
    password: "sensor",
    first: "Sensor",
    last: "Sensorsen",
    email: "sensor@sensor.sensor",
    userlevel: 2
  });

  await athlete.save();
  await eventmanager.save();

  athleteId = athlete._id;
  eventmanagerId = eventmanager._id;

  return console.log("Users saved...")
}

async function createEvents() {

  let event = new EventModel({
      title: 'Opening Event',
      description: 'This is the opening event celebrating the Eventsys system with a million burpees',
      longDescription: "Stare at ceiling throw down all the stuff in the kitchen plan steps for world domination yet i will be pet i will be pet and then i will hiss so meowing chowing and wowing eat a plant, kill a hand. Sleep in the bathroom sink litter kitter kitty litty little kitten big roar roar feed me. Stare at imaginary bug meow meow mama reaches under door into adjacent room. Climb leg purrrrrr. Miaow then turn around and show you my bum scratch at fleas, meow until belly rubs, hide behind curtain when vacuum cleaner is on scratch strangers and poo on owners food yet shove bum in owner's face like camera lens pee in human's bed until he cleans the litter box. Climb a tree, wait for a fireman jump to fireman then scratch his face. Hunt anything that moves dead stare with ears cocked. Tuxedo cats always looking dapper.",
      fromDate: '2019-11-28T00:00:00.000+00:00',
      toDate: '2019-11-30T00:00:00.000+00:00',
      maxAttendees: '256',
      owner: eventmanagerId,
      attendees: [athleteId]
  });

  await event.save();
  return console.log("Events saved...")
}