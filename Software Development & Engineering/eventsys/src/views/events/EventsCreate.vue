<template>
  <div>

    <div v-if="errors.length > 0" class="ml-2">
      <div v-for="error in errors" v-bind:key="error" class="alert alert-danger">{{ error }}</div>
    </div>

    <h1>Create Event</h1>
    <form class="custom-form" v-on:submit.prevent="onSubmit">
      <div class="form-group">
        <label for="title">Title</label>
        <input v-model="event.title" type="text" class="form-control" id="title" placeholder="Enter title" />
      </div>
      <div class="form-group">
        <label for="description">Short Description</label>
        <textarea v-model="event.description" class="form-control" cols="30" rows="4" id="description" placeholder="Enter short description"></textarea>
      </div>
      <div class="form-group">
        <label for="longDescription">Long Description</label>
        <textarea v-model="event.longDescription" class="form-control" cols="30" rows="10" id="longDescription" placeholder="Enter long description"></textarea>
      </div>
      <div class="form-group">
        <label for="fromDate">From</label>
        <input v-model="event.fromDate" type="date" class="form-control" id="fromDate" placeholder="Enter date" />
      </div>
      <div class="form-group">
        <label for="toDate">From</label>
        <input v-model="event.toDate" type="date" class="form-control" id="toDate" placeholder="Enter date" />
      </div>
      <div class="form-group">
        <label for="maxAttendees">Maximim amount of attendees</label>
        <input v-model="event.maxAttendees" type="number" class="form-control" id="toDate" placeholder="Enter maximum amount of attendees" />
      </div>
      <div class="form-group">
        <button type="submit" class="btn btn-secondary">Create</button>
      </div>
    </form>
  </div>
</template>

<script>
  import * as eventService from '../../services/EventService';

  export default {
    name: "events-create",
    data: function() {
      return {
        event: {
          title: '',
          description: '',
          longDescription: '',
          fromDate: '',
          toDate: '',
          maxAttendees: '',
        },
        errors: []
      }
    },
    methods: {
      onSubmit: async function() {
        const request = {
          event: this.event
        }

        try {
          await eventService.createEvent(request);

          // Temp bugfix to allow events to load before entering overview
          await new Promise(function (resolve) { setTimeout(resolve, 300); });

          this.$router.push({ name: 'events-all' });
        }
        catch (error) {
          this.errors = error.response.data.errors;
        }
      }
    }
  }
</script>