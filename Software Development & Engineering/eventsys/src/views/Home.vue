<template>
  <div id="eventsys-home">
    <Welcome v-if="justRegistered" />
    <Jumbotron v-else />
  </div>
</template>

<script>
import Jumbotron from '@/components/Jumbotron.vue'
import Welcome from '@/components/Welcome.vue'

export default {
  name: 'home',
  data: function() {
    return {
      justRegistered: false
    }
  },
  components: {
    Jumbotron,
    Welcome
  }
}
</script>
