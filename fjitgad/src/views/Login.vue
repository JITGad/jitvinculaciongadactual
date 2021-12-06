<template>
  <div class="text-center">
    <NavBar></NavBar>
    <div class="form-signin">
      <form v-on:submit.prevent="login">
        <img
          class="mb-4"
          src="../assets/escudouteq.png"
          alt=""
          width="72"
          height="57"
        />
        <h1 class="h3 mb-3 fw-normal">Iniciar session</h1>

        <div class="form-floating">
          <input
            type="email"
            class="form-control"
            id="floatingInput"
            placeholder="name@example.com"
            v-model="email"
          />
          <label for="floatingInput">Correo electronico</label>
        </div>
        <div class="form-floating">
          <input
            type="password"
            class="form-control"
            id="floatingPassword"
            placeholder="Password"
            v-model="password"
          />
          <label for="floatingPassword">Contraseña</label>
        </div>

        <div class="checkbox mb-3">
          <label>
            <input type="checkbox" value="remember-me" /> Recuerdame
          </label>
        </div>
        <button class="w-100 btn btn-lg btn-primary" type="submit">
          Sign in
        </button>
      </form>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import Index from "@/components/Index.vue";
import NavBar from "@/components/NavBar.vue";
import {Login} from '../api/Usuarios';

export default {
  name: "Login",
  data(){
    return{
      email: "",
      password: ""
    }
  },
  components: {
    Index,
    NavBar,
  },
  methods: {
    async login(){
      console.log(this.email);
      console.log(this.password);
      console.log(await Login(this.email, this.password))
    }
  }
};
</script>

<style>
html,
body {
  height: 100%;
}

body {
  padding-top: 40px;
  padding-bottom: 40px;
}

.form-signin {
  width: 100%;
  max-width: 500px;
  padding: 15px;
  margin: auto;
}

.form-signin .checkbox {
  font-weight: 400;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>