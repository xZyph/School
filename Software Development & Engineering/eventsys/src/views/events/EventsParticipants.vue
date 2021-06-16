<template>
  <div>
    <h1 class="mb-4">Event Participants - {{ event.title }}</h1>
    
    <table class="table table-striped table-dark">
      <thead>
        <tr>
          <th scope="col">Username</th>
          <th scope="col">Name</th>
          <th scope="col">Email</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="person in event.attendees" v-bind:key="person._id">
          <td>{{ person.username }}</td>
          <td>{{ person.first + " " + person.last }}</td>
          <td>{{ person.email }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  import * as eventService from '../../services/EventService';

  export default {
    name: "events-participants",
    data: function() {
      return {
        event: {
          title: '',
          owner: '',
          attendees: ''
        }
      }
    },
    beforeRouteEnter(to, from, next) {
      eventService.getEventParticipantsById(to.params.id)
      .then(response => {
        if(!response) {
          next("/events");
        }
        else {
          next(vm => {
            const event = response.data.event;

            event.attendees.forEach(attendee => {
              attendee.first = attendee.first.charAt(0).toUpperCase() + attendee.first.slice(1);
              attendee.last = attendee.last.charAt(0).toUpperCase() + attendee.last.slice(1);
            });

            vm.event = event;
          })
        }
      })
    },
    methods: {
      cancelModal: function() {
        this.$refs.modal.hide();
      },

      enrollToEvent: async function() {
        try {
          await eventService.enrollToEvent(this.currentEventId);
          this.$refs.modal.hide();

          eventService.getEventById(this.event.id)
          .then(res => {
            this.event = res.data.event;
          })
        }
        catch(error) {
          this.errors = error.response.data.errors;
        }
      }
    }
  }
</script>