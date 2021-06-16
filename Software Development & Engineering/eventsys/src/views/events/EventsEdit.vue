<template>
  <div>
    <h1>Edit Event</h1>
    <form class="custom-form" v-on:submit.prevent="onSubmit">
      <div class="form-group">
        <label for="title">Title</label>
        <input v-model="event.title" type="text" class="form-control" id="title" placeholder="Enter title" />
      </div>
      <div class="form-group">
        <label for="description">Description</label>
        <textarea v-model="event.description" class="form-control" cols="30" rows="10" id="description" placeholder="Enter description"></textarea>
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
        <button type="submit" class="btn btn-secondary">Save Changes</button>
      </div>
    </form>
  </div>
</template>

<script>
  import * as eventService from '../../services/EventService';
  import moment from 'moment';

  export default {
    name: "events-edit",
    data: function() {
      return {
        event: {
          title: '',
          description: '',
          fromDate: '',
          toDate: ''
        }
      }
    },
    beforeRouteEnter(to, from, next) {
      eventService.getEventById(to.params.id)
      .then(response => {
        if(!response) {
          next("/events");
        }
        else {
          next(vm => {
            const event = response.data.event;
            event.fromDate = moment(event.fromDate).format("YYYY-MM-DD");
            event.toDate = moment(event.toDate).format("YYYY-MM-DD");
            vm.event = event;
          })
        }
      })
    },
    methods: {
      onSubmit: async function() {
        const request = {
          event: this.event
        }
        await eventService.updateEvent(request);
        this.$router.push({ name: 'events-all' });
      }
    }
  }
</script>