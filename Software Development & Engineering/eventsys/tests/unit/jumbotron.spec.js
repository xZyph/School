import { expect } from 'chai'
import { shallowMount, createLocalVue } from '@vue/test-utils'
import Jumbotron from '@/components/Jumbotron.vue'

import Router from 'vue-router'

describe('Jumbotron', () => {
  let wrapper;

  beforeEach(() => {
    const localVue = createLocalVue();
    localVue.use(Router);

    wrapper = shallowMount(Jumbotron, { localVue });
  })

  it('should have a heading', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.html()).to.contain('Eventsys - the only system you need for your sports-events');
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