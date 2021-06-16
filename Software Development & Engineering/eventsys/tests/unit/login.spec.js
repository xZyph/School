import { expect } from 'chai'
import { mount } from '@vue/test-utils'
import Login from '@/views/authentication/Login.vue'

describe('Login', () => {
  let wrapper;

  beforeEach(() => {
    wrapper = mount(Login);
  })

  it('should have a heading', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.html()).to.contain('Login');
          done();
      });
  })

  it('should have a username input', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.find("input#username")).to.be.ok;
          done();
      });
  })

  it('should have a password input', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.find("input#password")).to.be.ok;
          done();
      });
  })

  it('should have a login button', (done) => {
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