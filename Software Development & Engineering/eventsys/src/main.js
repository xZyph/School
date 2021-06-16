import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap'
import './assets/css/style.css'
import BootstrapVue from 'bootstrap-vue';
import moment from 'moment';
import { library } from '@fortawesome/fontawesome-svg-core'
import { faBackward } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

Vue.use(BootstrapVue);
Vue.config.productionTip = process.env.NODE_ENV === 'production ';

library.add(faBackward);
Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.filter('date', (value) => {
  if(!value) {
    return "";
  }

  return moment(value).format("DD MMM YYYY");
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
