import { expect } from 'chai'
import { shallowMount, createLocalVue } from '@vue/test-utils'
import Home from '@/views/Home.vue'

import Vuex from 'vuex';
import vuexstore from '../../src/store/index'
import Router from 'vue-router'

describe('Home', () => {
  let wrapper;
  let store;

  const localVue = createLocalVue();
  localVue.use(Vuex);
  localVue.use(Router);

  beforeEach(() => {
    store = new Vuex.Store(vuexstore);
    wrapper = shallowMount(Home, { 
      store, 
      localVue,
      stubs: {
        Jumbotron: "<div class='jumbotron'></div>",
        Welcome: "<div class='welcome'></div>"
      }
    });
  })

  it('should display jumbotron', (done) => {
    wrapper.vm.$nextTick(() => {
        expect(wrapper.find("div.jumbotron")).to.be.ok;
        done();
    });
  })

  it('should display welcome message if just registered', (done) => {
    wrapper.vm.$nextTick(() => {
        store.state.justRegistered = true;
        store.state.username = "testuser";

        expect(wrapper.find("div.welcome")).to.be.ok;
        done();
    });
  })

});