import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/authentication/Login.vue'
import Register from '../views/authentication/Register.vue'
import EventsCreate from '../views/events/EventsCreate.vue'
import EventsAll from '../views/events/EventsAll.vue'
import EventsEdit from '../views/events/EventsEdit.vue'
import EventsParticipants from '../views/events/EventsParticipants.vue'
import EventsSingle from '../views/events/EventsSingle.vue'
import * as Auth from '../services/AuthService'

Vue.use(VueRouter)

const canManageEvents = true;

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/events',
    name: 'events-all',
    component: EventsAll
  },
  {
    path: '/events/view/:id',
    name: 'events-single',
    component: EventsSingle
  },
  {
    path: '/participants/:id',
    name: 'events-participants',
    component: EventsParticipants,
    beforeEnter: (to, from, next) => {
      if(Auth.isLoggedIn()) {
        next();
      }
      else {
        next("/login");
      }
    }
  },
  {
    path: '/events/new',
    name: 'events-create',
    component: EventsCreate,
    beforeEnter: (to, from, next) => {
      if(Auth.isLoggedIn() && canManageEvents) {
        next();
      }
      else {
        next("/login");
      }
    }
  },
  {
    path: '/events/:id',
    name: 'events-edit',
    component: EventsEdit,
    beforeEnter: (to, from, next) => {
      if(Auth.isLoggedIn() && canManageEvents) {
        next();
      }
      else {
        next("/login");
      }
    }
  },
  {
    path: '/register',
    name: 'register',
    component: Register,
    beforeEnter: (to, from, next) => {
      if(Auth.isLoggedIn()) {
        next("/");
      }
      else {
        next();
      }
    }
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
    beforeEnter: (to, from, next) => {
      if(Auth.isLoggedIn()) {
        next("/");
      }
      else {
        next();
      }
    }
  },
  {
    path: '*',
    redirect: '/'
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  linkActiveClass: 'active'
})

export default router
