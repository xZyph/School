import { expect } from 'chai'
import { mount } from '@vue/test-utils'
import Login from '@/views/events/EventsCreate.vue'

describe('Events-Create', () => {
  let wrapper;

  beforeEach(() => {
    wrapper = mount(Login);
  })

  it('should have a heading', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.html()).to.contain('Create Event');
          done();
      });
  })

  it('should have a title input', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.find("input#title")).to.be.ok;
          done();
      });
  })

  it('should have a description input', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.find("input#description")).to.be.ok;
          done();
      });
  })

  it('should have a from-date input', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.find("input#fromDate")).to.be.ok;
          done();
      });
  })

  it('should have a to-date input', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.find("input#toDate")).to.be.ok;
          done();
      });
  })

  it('should have a create button', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.find("button#submit")).to.be.ok;
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

});