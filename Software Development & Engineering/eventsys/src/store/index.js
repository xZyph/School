import Vue from 'vue'
import Vuex from 'vuex'
import * as Auth from '../services/AuthService'

Vue.use(Vuex)

let initialState = {
  isLoggedIn: false,
  isEventManager: false,
  isAthlete: false,
  apiUrl: "http://localhost:3000/api",
  username: null,
  userId: null,
  userlevel: null
};

const store = new Vuex.Store({
  state: initialState,
  mutations: {
    authenticate(state) {
      state.isLoggedIn = Auth.isLoggedIn();

      if(state.isLoggedIn) {
        state.isEventManager = Auth.isEventManager();
        state.isAthlete = Auth.isAthlete();
        state.username = Auth.getUsername();
        state.userId = Auth.getUserId();
        state.userlevel = Auth.getUserlevel();
      }
      else {
        state.userId = null;
        state.username = null;
        state.userlevel = null;
      }
    }
  },
  actions: {
    authenticate(context) {
      context.commit('authenticate')
    }
  },
  modules: {
  }
})

export default store