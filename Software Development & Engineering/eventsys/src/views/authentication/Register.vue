<template>
  <div>

    <div v-if="errors.length > 0" class="ml-2">
      <div v-for="error in errors" v-bind:key="error" class="alert alert-danger">{{ error }}</div>
    </div>

    <h1>Register</h1>
    <form class="custom-form" v-on:submit.prevent="onSubmit">
      <div class="form-group">
        <label for="username">Username</label>
        <input v-model="username" type="text" class="form-control" id="username" placeholder="Enter username" />
      </div>
      <div class="form-group">
        <label for="username">Email</label>
        <input v-model="email" type="text" class="form-control" id="email" placeholder="Enter email" />
      </div>
      <div class="form-group">
        <label for="username">First name</label>
        <input v-model="first" type="text" class="form-control" id="first" placeholder="Enter first name" />
      </div>
      <div class="form-group">
        <label for="username">Last name</label>
        <input v-model="last" type="text" class="form-control" id="last" placeholder="Enter last name" />
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input v-model="password" type="password" class="form-control" id="password" placeholder="Enter password" />
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input v-model="password2" type="password" class="form-control" id="password2" placeholder="Enter password" />
      </div>
      <div class="form-group">
        <label for="userlevel">Account type</label>
        <select class="form-control" id="userlevel">
          <option value="3">Athlete</option>
          <option value="2">Event Manager</option>
        </select>
      </div>
      <div class="form-group">
        <button type="submit" id="btn-register" class="btn btn-secondary">Register</button>
      </div>
    </form>
  </div>
</template>

<script>
  import * as Auth from '../../services/AuthService'

  export default {
    name: "register",
    data: function() {
      return {
        username: '',
        password: '',
        password2: '',
        first: '',
        last: '',
        email: '',
        userlevel: '',
        errors: []
      }
    },
    methods: {
      onSubmit: async function() {
        const user = {
          username: this.username,
          email: this.email,
          password: this.password,
          password2: this.password2,
          first: this.first,
          last: this.last,
          userlevel: this.userlevel.value
        };

        try {
          await Auth.registerUser(user);
          await Auth.login(user);
          this.$router.push({ name: 'home', params: { justRegistered: true } });
        }
        catch (error) {
          this.errors = error.response.data.errors;
        }
      }
    }
  }
</script>