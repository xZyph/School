<template>
<div class="events-single card bg-dark ml-auto mr-auto">

  <img src="../../assets/images/event-header-dummy.jpg" class="card-img-top" alt="Dummy Event Banner">

  <div class="card-header">
    <h5 class="card-title mb-1">{{ event.title }}</h5>
    <p v-if="event.fromDate === event.toDate" class="card-text"><small class="text-muted">{{ event.fromDate | date }}</small></p>
    <p v-else class="card-text"><small class="text-muted">{{ event.fromDate | date }} - {{ event.toDate | date }}</small></p>
  </div>

  <div class="card-body">
    <h6 class="card-subtitle mb-2 text-muted">About the event</h6>
    <p class="card-text">{{ event.longDescription }}</p>
    
    <h6 class="card-subtitle mb-2 text-muted">Attendees</h6>
    <p class="card-text">{{ event.attendees.length }} out of {{ event.maxAttendees }}</p>
  </div>

  <div v-if="$store.state.isAthlete" class="card-footer">
    <h6 class="card-subtitle mb-2 text-muted">Actions</h6>
    <a 
      v-if="$store.state.isAthlete && !event.isEnrolled"
      class="btn btn-success m-2"
      v-on:click.prevent="currentEventId = event._id"
      v-b-modal.enrollModal
    >Enroll</a>
    <button
      v-if="$store.state.isAthlete && event.isEnrolled"
      class="btn btn-secondary m-2"
      disabled
    >Enrolled</button>
  </div>

  <div v-if="event.owner == $store.state.userId" class="card-footer">
    <h6 class="card-subtitle mb-2 text-muted">Actions</h6>
    <div class="d-flex flex-row">
      <router-link 
        :to="{ name: 'events-participants', params: { id: event._id }}"
        class="btn btn-primary m-2"
      >View Participants</router-link>
      
      <button
        class="btn btn-secondary m-2"
        disabled
      >Edit Event</button>

      <button
        class="btn btn-secondary m-2"
        disabled
      >Register Results</button>
    </div>
  </div>

  <b-modal id="enrollModal" ref="modal" centered title="Enroll to event">
    <p class="my-4">Are you sure you would like to enroll to this event?</p>
    <div slot="modal-footer" class="w-100 text-right">
      <b-btn slot="md" class="mr-1" variant="success" @click="enrollToEvent">Enroll</b-btn>
      <b-btn slot="md" variant="secondary" @click="cancelModal">Cancel</b-btn>
    </div>
  </b-modal>

</div>
</template>

<script>
  import * as eventService from '../../services/EventService';
  import moment from 'moment';

  export default {
    name: "events-single",
    data: function() {
      return {
        event: {
          id: '',
          title: '',
          longDescription: '',
          fromDate: '',
          toDate: '',
          attendees: ''
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
            event.id = to.params.id;
            event.fromDate = moment(event.fromDate).format("YYYY-MM-DD");
            event.toDate = moment(event.toDate).format("YYYY-MM-DD");
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