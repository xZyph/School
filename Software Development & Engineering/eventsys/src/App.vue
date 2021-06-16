<template>
  <div id="app">
    <Navigation />
    <div id="app-container">
      <router-view/>
    </div>
    <Footer />
  </div>
</template>

<script>
import Navigation from '@/components/Navigation.vue'
import Footer from '@/components/Footer.vue'

export default {
  name: "App",
  components: {
    Navigation,
    Footer
  },
  beforeCreate: function() {
    this.$store.dispatch('authenticate');
  }
}
</script>