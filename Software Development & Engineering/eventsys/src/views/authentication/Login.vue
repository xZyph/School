<template>
  <div>

    <div v-if="errors.length > 0" class="ml-2">
      <div v-for="error in errors" v-bind:key="error" class="alert alert-danger">{{ error }}</div>
    </div>

    <h1>Login</h1>

    <form class="custom-form" v-on:submit.prevent="onSubmit">
      <div class="form-group">
        <label for="username">Username</label>
        <input v-model="username" type="text" class="form-control" id="username" placeholder="Enter username" />
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input v-model="password" type="password" class="form-control" id="password" placeholder="Enter password" />
      </div>
      <div class="form-group">
        <button type="submit" class="btn btn-secondary">Login</button>
      </div>
    </form>

  </div>
</template>

<script>
  import * as Auth from '../../services/AuthService'

  export default {
    name: "login",
    data: function() {
      return {
        username: '',
        password: '',
        errors: ''
      }
    },
    methods: {
      onSubmit: async function() {
        const user = {
          username: this.username,
          password: this.password
        }

        try {
          await Auth.login(user);
          this.$router.push({ name: 'events-all' });
        }
        catch (error) {
          this.errors = error.response.data.errors;
        }
      }
    }
  }
</script>