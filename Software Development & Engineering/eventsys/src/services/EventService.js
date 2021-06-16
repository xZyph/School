import { http } from './HttpService';

export function getAllEvents() {
  return http().get('/events');
}

export function getEventById(id) {
  return http().get(`/events/${id}`);
}

export function getEventParticipantsById(id) {
  return http().get(`/events/${id}/participants`);
}

export function createEvent(event) {
  return http().post('/events', event);
}

export function delEvent(id) {
  return http().delete(`/events/${id}`);
}

export function updateEvent(event) {
  return http().put('/events', event);
}

export function enrollToEvent(event) {
  return http().get(`/events/${event}/enroll`);
}