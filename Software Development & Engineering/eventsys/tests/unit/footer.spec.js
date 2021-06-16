import { expect } from 'chai'
import { mount } from '@vue/test-utils'
import Footer from '@/components/Footer.vue'

describe('Footer', () => {
  let wrapper;

  beforeEach(() => {
    wrapper = mount(Footer);
  })

  it('should have copyright', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.html()).to.contain('© 2019 Eventsys');
          done();
      });
  })

  // Links for Privacy and Terms of Service should be tested after they are added
});