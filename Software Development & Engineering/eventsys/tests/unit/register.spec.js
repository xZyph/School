import { expect } from 'chai'
import { mount } from '@vue/test-utils'
import Register from '@/views/authentication/Register.vue'

describe('Register', () => {
  let wrapper;

  beforeEach(() => {
    wrapper = mount(Register);
  })

  it('should have a heading', (done) => {
    wrapper.vm.$nextTick(() => {
        expect(wrapper.html()).to.contain('Register');
        done();
    });
  })

  it('should have a first name input', (done) => {
    wrapper.vm.$nextTick(() => {
        expect(wrapper.find("input#first")).to.be.ok;
        done();
    });
  })

  it('should have a last name input', (done) => {
    wrapper.vm.$nextTick(() => {
        expect(wrapper.find("input#last")).to.be.ok;
        done();
    });
  })

  it('should have a username input', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.find("input#username")).to.be.ok;
          done();
      });
  })

  it('should have an email input', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.find("input#email")).to.be.ok;
          done();
      });
  })

  it('should have a password input', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.find("input#password")).to.be.ok;
          done();
      });
  })

  it('should have a second password input', (done) => {
      wrapper.vm.$nextTick(() => {
          expect(wrapper.find("input#password2")).to.be.ok;
          done();
      });
  })


  it('should have a register button', (done) => {
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