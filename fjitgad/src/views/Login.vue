<template>
  <div class="text-center">
    <NavBar></NavBar>
    <div class="form-signin">
      <my-form @submit="handleLogin">
        <img
          class="mb-4"
          src="../assets/escudouteq.png"
          alt=""
          width="72"
          height="57"
        />
        <h1 class="h3 mb-3 fw-normal">Iniciar session</h1>

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

        <div class="checkbox mb-3">
          <label>
            <input type="checkbox" value="remember-me" /> Recuerdame
          </label>
        </div>

        <button
          class="w-100 btn btn-lg btn-primary"
          type="submit"
          :disabled="loading"
        >
          <span
            v-show="loading"
            class="spinner-border spinner-border-sm"
          ></span>
          <span>Iniciar session</span>
        </button>
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
import { reactive, ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

export default {
  name: "Login",
  components: {
    NavBar,
  },
  setup(props, context) {
    const store = useStore();
    const loggedIn = computed(() => store.state.auth.status.loggedIn);
    const tokenValid = computed(() => store.getters["auth/isTokenValid"]);
    const router = useRouter();

    if (loggedIn.value && tokenValid.value) {
      router.push("/dashboard");
    }

    const user = reactive({
      email: "",
      password: "",
    });
    const responseError = reactive({
      message: "",
      error: false,
    });
    const loading = ref(false);
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
      loggedIn,
      tokenValid,
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