<template>
  <div class="d-flex flex-column">

    <div v-if="errors.length > 0" class="ml-2">
      <div v-for="error in errors" v-bind:key="error" class="alert alert-danger">{{ error }}</div>
    </div>
    
    <div class="d-flex justify-content-between">
      <h1>Events</h1>
      <div class="mb-4">
        <router-link v-if="$store.state.isEventManager" id="btnCreateEvent" to="/events/new" class="btn btn-success ml-2" exact>Create Event</router-link>
      </div>
    </div>

    <div v-if="events && events.length > 0" class="d-flex flex-wrap justify-content-start">

      <div v-for="event in events" v-bind:key="event._id" class="card mb-2 ml-2 text-white bg-dark" style="width: 18rem;">
        <div class="card-body">
          <h4 class="card-title">{{ event.title }}</h4>
          <span class="date">{{ event.fromDate | date }} - {{ event.toDate | date }}</span>
          <p class="card-text mt-2 mb-1">{{ event.description }}</p>
          <span class="attendees mb-3">Attendees: {{ event.attendees.length }} / {{ event.maxAttendees }}</span>
          <router-link :to="{ name: 'events-single', params: { id: event._id }}" class="btn btn-primary btn-event">View Event</router-link>
        </div>
      </div>
    </div>

    <div v-if="events && events.length === 0" class="ml-2 mt-2">
      <div class="alert alert-info">No events found.</div>
    </div>

  </div>
</template>

<script>
  import * as eventService from '../../services/EventService';

  export default {
    name: "events-all",
    data: function() {
      return {
        events: null,
        currentEventId: null,
        errors: []
      }
    },
    beforeRouteEnter(to, from, next) {
      eventService.getAllEvents()
      .then(res => {
        next(vm => {
          vm.events = res.data.events;
        })
      })
    },
    methods: {

    }
  }
</script>