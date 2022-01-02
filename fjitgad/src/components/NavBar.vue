<template>
  <nav
    class="navbar fixed-top navbar-dark bg-primary p-1"
    id="nav"
    role="navigation"
  >
    <div class="ms-auto" style="display: flex">
      <router-link class="nav-link" style="color: black" to="/">
        Inicio
      </router-link>
      <router-link class="nav-link" style="color: black" to="/about">
        Acerca de
      </router-link>
      <router-link
        v-if="isLogin"
        class="nav-link"
        style="color: black"
        to="/dashboard"
      >
        Administrador
      </router-link>
      <router-link
        v-if="isLogin"
        class="nav-link"
        style="color: black"
        to="/logout"
      >
        Cerrar session
      </router-link>
      <router-link
        v-if="!isLogin"
        class="nav-link"
        style="color: black"
        to="/login"
      >
        Iniciar session
      </router-link>
    </div>
  </nav>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";

export default {
  name: "NavBar",
  setup(props, context) {
    document.body.style.height = "100%";
    document.body.style.backgroundRepeat = "no-repeat";
    document.body.style.background = `url(${require("../assets/image/fondo2.png")}) no-repeat center center fixed`;
    document.body.style.backgroundSize = "100% 100%";
    const store = useStore();
    const isLogin = computed(() => store.getters["auth/isLoged"]);
    return {
      isLogin,
    };
  },
};
</script>