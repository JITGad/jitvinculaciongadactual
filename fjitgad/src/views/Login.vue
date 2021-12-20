<template>
  <div class="text-center">
    <NavBar></NavBar>
    <div class="form-signin">
      <Form @submit="handleLogin" :validation-schema="schema">
        <img
          class="mb-4"
          src="../assets/escudouteq.png"
          alt=""
          width="72"
          height="57"
        />
        <h1 class="h3 mb-3 fw-normal">Iniciar session</h1>

        <div class="form-floating">
          <Field
            type="email"
            name="email"
            class="form-control"
            placeholder="name@example.com"
          />
          <label for="email">Correo electronico</label>
          <ErrorMessage name="email" class="error-feedback" />
        </div>
        <div class="form-floating">
          <Field
            type="password"
            class="form-control"
            name="password"
            placeholder="Password"
          />
          <label for="password">Contraseña</label>
          <ErrorMessage name="password" class="error-feedback" />
        </div>

        <div class="checkbox mb-3">
          <label>
            <input type="checkbox" value="remember-me" /> Recuerdame
          </label>
        </div>
        <button class="w-100 btn btn-lg btn-primary" type="submit" :disabled="loading">
            <span
              v-show="loading"
              class="spinner-border spinner-border-sm"
            ></span>
            <span>Iniciar session</span>
        </button>
        <div class="alert alert-danger" role="alert" v-if="error">
          {{messagge}}
        </div>
      </form>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import Index from "@/components/Index.vue";
import NavBar from "@/components/NavBar.vue";
import {Login} from '../api/Usuarios';
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";

export default {
  name: "Login",
  data(){
    const schema = yup.object().shape({
      username: yup.string().required("Username is required!"),
      password: yup.string().required("Password is required!"),
    });
    
    return{
      email: "",
      password: "",
      error: false,
      messagge: "",
      loading: false,
      schema
    }
  },
  components: {
    Index,
    NavBar,
  },
  methods: {
    async login(){
      var user = {
                'email': email,
                'password': password
            };

      var response = await Login(this.email, this.password);
      if(response){
          var responseJSON = JSON.parse(response);
          console.log(responseJSON);
          if (responseJSON.flag){
            localStorage.setItem('user', JSON.stringify(responseJSON.data));
          } else {
            this.error_msg = responseJSON.message;
            this.error = true;    
          }
      } else{
        this.error_msg = "Usuario o clave incorrectos";
        this.error = true;
      }
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