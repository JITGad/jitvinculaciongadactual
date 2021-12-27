<template>
  <div>
    <NavBar></NavBar>
    <div class="form-signin">
      <my-form @submit="handleLogin">
        <div class="text-center">
          <img
            class="mb-4"
            src="../assets/image/escudouteq.png"
            alt=""
            width="72"
            height="57"
          />
          <h1 class="h3 mb-3 fw-normal">Iniciar session</h1>
        </div>

        <my-input
          v-model="user.email"
          type="email"
          label="Correo electronico"
          placeholder="Escriba el correo electronico"
          validations="requerido"
        />

        <my-input
          v-model="user.password"
          type="password"
          label="Contraseña"
          placeholder="Escriba la contraseña"
          validations="requerido"
        />

        <my-input
          v-model="user.recuerdame"
          type="checkbox"
          label="Recuerdame"
        />

        <my-button label="Iniciar session" :loading="loading"/>
        
        <div class="alert alert-danger" role="alert" v-if="responseError.error">
          {{ responseError.message }}
        </div>
      </my-form>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import NavBar from "@/components/NavBar.vue";
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

export default {
  name: "Login",
  components: {
    NavBar
  },
  setup(props, context) {
    const store = useStore();
    const router = useRouter();
    const user = reactive({
      email: "",
      password: "",
      recuerdame: false,
    });
    const responseError = reactive({
      message: "",
      error: false,
    });
    const loading = ref(false);
    const rememberMe = ref(false);
    const handleLogin = function () {
      responseError.error = false;
      responseError.message = "";
      loading.value = true;

      store.dispatch("auth/login", user).then(
        () => {
          router.push("/dashboard");
        },
        (error) => {
          loading.value = false;
          responseError.error = true;
          responseError.message = error.toString();
        }
      );
    };

    return {
      user,
      responseError,
      loading,
      handleLogin,
      rememberMe,
    };
  },
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