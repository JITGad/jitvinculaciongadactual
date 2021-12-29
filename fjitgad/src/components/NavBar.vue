<template>
  <nav
    class="navbar fixed-top navbar-dark bg-primary p-1"
    id="nav"
    role="navigation"
  >
    <div class="ms-auto" style="display: flex">
      <router-link class="nav-link" style="color: black" to="/"
        >Inicio</router-link
      >
      <router-link class="nav-link" style="color: black" to="/about">
        A<span style="text-transform: lowercase">cerca de</span>
      </router-link>
      <div v-if="isLogin">
        <router-link class="nav-link" style="color: black" to="/logout">
          Cerrar<span style="text-transform: lowercase"> session</span>
        </router-link>
      </div>
      <div v-else>
        <router-link class="nav-link" style="color: black" to="/login">
          Iniciar<span style="text-transform: lowercase"> session</span>
        </router-link>
      </div>
    </div>
    <!--/.container -->
  </nav>
</template>

<script>
import { reactive, ref, computed } from "vue";
import { useStore } from "vuex";

export default {
  name: "NavBar",
  setup(props, context) {
    const store = useStore();
    const isLogin = computed(() => store.getters["auth/isLoged"]);
    return {
      isLogin,
    };
  },
};
</script>