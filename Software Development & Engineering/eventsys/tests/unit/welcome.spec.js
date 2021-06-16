import { expect } from 'chai'
import { shallowMount, createLocalVue } from '@vue/test-utils'
import Welcome from '@/components/Welcome.vue'

import Vuex from 'vuex';
import vuexstore from '../../src/store/index'
import Router from 'vue-router'

describe('Welcome', () => {
  let wrapper;
  let store;

  const localVue = createLocalVue();
  localVue.use(Vuex);
  localVue.use(Router);

  beforeEach(() => {
    store = new Vuex.Store(vuexstore);
    store.state.justRegistered = true;
    store.state.username = "testuser";

    wrapper = shallowMount(Welcome, { 
      store, 
      localVue
    });
  })

  it('should have a heading', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.html()).to.contain('Welcome to Eventsys, testuser!');
          done();
      });
  })

  it('should have button to view events', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.find(".btn-viewEvents")).to.be.ok;
          done();
      });
  })

});