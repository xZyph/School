<template>
<header>
  <nav class="navbar navbar-expand-md navbar-dark fixed-top custom-bg-dark">
    <router-link to="/" class="navbar-brand">
      <img alt="Eventsys logo" id="logo" style="max-height: 40px" src="../assets/logo.png"> Eventsys
    </router-link>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
      <ul class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/" exact class="nav-link">Home</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/events" exact class="nav-link btn-home">Events</router-link>
        </li>
        <li v-if="!$store.state.isLoggedIn" class="nav-item">
          <router-link to="/register" exact class="nav-link btn-register">Register</router-link>
        </li>
        <li v-if="!$store.state.isLoggedIn" class="nav-item">
          <router-link to="/login" exact class="nav-link btn-login">Login</router-link>
        </li>
        <li v-if="$store.state.isLoggedIn" class="nav-item">
          <a v-on:click.prevent="logout()" class="nav-link btn-logout" href="#">Logout</a>
        </li>
        <li v-if="$store.state.isLoggedIn" class="nav-item">
          <a class="nav-link" href="#">{{ this.$store.state.username ? this.$store.state.username : 'User' }}</a>
        </li>
      </ul>
    </div>
  </nav>
</header>
</template>

<script>
  import * as Auth from '../services/AuthService';

  export default {
    name: "Navigation",
    methods: {
      logout: function() {
        Auth.logout();
        this.$router.push({ name: 'home' }).catch(err => { err });
      }
    }
  }
</script>