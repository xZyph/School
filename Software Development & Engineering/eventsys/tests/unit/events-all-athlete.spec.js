import { expect } from 'chai'
import { shallowMount, createLocalVue } from '@vue/test-utils'
import EventsAll from '@/views/events/EventsAll.vue'

import Vuex from 'vuex';
import vuexstore from '../../src/store/index'
import Router from 'vue-router'

describe('Events-All (Logged in as Athlete)', () => {
  let wrapper;
  let store;

  const localVue = createLocalVue();
  localVue.use(Vuex);
  localVue.use(Router);

  beforeEach(() => {
    store = new Vuex.Store(vuexstore);
    store.state.isLoggedIn = true;
    store.state.isAthlete = true;

    localVue.filter('date', date => date)

    wrapper = shallowMount(EventsAll, { store, localVue });
  })

  it('should have a heading', (done) => {
    wrapper.vm.$nextTick(() => {
        expect(wrapper.html()).to.contain('Events');
        done();
    });
  })

  it('should not have a create event button', (done) => {
    wrapper.vm.$nextTick(() => {
        expect(wrapper.find("#btnCreateEvent").exists()).to.be.false;
        done();
    });
  })

  it('should display error when given by API', (done) => {
    wrapper.vm.$nextTick(() => {
      wrapper.setData({ errors: ["test error"] });
      expect(wrapper.find(".alert-danger").exists()).to.be.true;
      done();
    })
  })

  it('should display info when no events are passed to the component', (done) => {
    wrapper.vm.$nextTick(() => {
      wrapper.setData({ events: [] });
      expect(wrapper.find(".alert-info").exists()).to.be.true;
      done();
    })
  })

  it('should display event when event is passed to component', (done) => {
    wrapper.vm.$nextTick(() => {
      wrapper.setData({ 
        events: [{
          title: "Test",
          description: "Test",
          fromDate: "2019-11-28T00:00:00.000+00:00",
          toDate: "2019-11-29T00:00:00.000+00:00",
          maxAttendees: 200,
          attendees: []
        }] 
      });

      expect(wrapper.find(".card-body").exists()).to.be.true;
      done();
    })
  })

  it('should generate a router-link to the event', (done) => {
    wrapper.vm.$nextTick(() => {
      wrapper.setData({ 
        events: [{
          _id: "testid",
          title: "Test",
          description: "Test",
          fromDate: "2019-11-28T00:00:00.000+00:00",
          toDate: "2019-11-29T00:00:00.000+00:00",
          maxAttendees: 200,
          attendees: []
        }] 
      });

      expect(wrapper.find(".btn-event").exists()).to.be.true;
      done();
    })
  })
  
  it('should display correct amount of attendees', (done) => {
    wrapper.vm.$nextTick(() => {
      wrapper.setData({ 
        events: [{
          _id: "testid",
          title: "Test",
          description: "Test",
          fromDate: "2019-11-28T00:00:00.000+00:00",
          toDate: "2019-11-29T00:00:00.000+00:00",
          maxAttendees: 200,
          attendees: [{}, {}, {}]
        }] 
      });

      expect(wrapper.html()).to.contain('Attendees: 3 / 200');
      done();
    })
  })

});