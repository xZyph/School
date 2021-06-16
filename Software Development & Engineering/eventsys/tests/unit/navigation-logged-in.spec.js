import { expect } from 'chai'
import { shallowMount, createLocalVue } from '@vue/test-utils'
import Navigation from '@/components/Navigation.vue'

import Vuex from 'vuex';
import vuexstore from '../../src/store/index'
import Router from 'vue-router'

describe('Navigation (Logged in)', () => {
  let wrapper;
  let store;

  const localVue = createLocalVue();
  localVue.use(Vuex);
  localVue.use(Router);

  beforeEach(() => {
    store = new Vuex.Store(vuexstore);
    store.state.isLoggedIn = true;
    store.state.username = "testuser"

    wrapper = shallowMount(Navigation, { store, localVue });
  })

  it('should have a logo', (done) => {
    wrapper.vm.$nextTick(() => {
        expect(wrapper.find("img#logo")).to.be.ok;
        done();
    });
  })

  it('should have a heading', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.html()).to.contain('Eventsys');
          done();
    });
  })

  it('should display username', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.html()).to.contain('testuser');
          done();
    });
  })
  
  it('should display logout button', (done) => {
    wrapper.vm.$nextTick(() => {
      expect(wrapper.find(".btn-logout")).to.be.ok;
      done();
    });
  })
  
  it('should display events button', (done) => {
    wrapper.vm.$nextTick(() => {
      expect(wrapper.find(".btn-events")).to.be.ok;
      done();
    });
  })
  
  it('should display home button', (done) => {
    wrapper.vm.$nextTick(() => {
      expect(wrapper.find(".btn-home")).to.be.ok;
      done();
    });
  })

});