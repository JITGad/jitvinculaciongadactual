<template>
  <div class="dashboard dashboard-admin">
    <div class="dashboard-nav">
      <Sidebar />
    </div>
    <div class="dashboard-app">
      <header class="dashboard-toolbar">
        <my-button-toggle-menu />
        <router-link class="nav-link" style="color: black" to="/instrucciones">
          Instrucciones de uso
        </router-link>
        <div class="ms-auto" style="display: flex">
          <span class="navbar-text">{{ usuario }}</span>
          <router-link class="nav-link" style="color: black" to="/logout">
            Cerrar<span style="text-transform: lowercase"> sesión</span>
          </router-link>
        </div>
      </header>
      <div class="dashboard-content">
        <slot></slot>
      </div>
    </div>
    <my-footer />
  </div>
</template>

<script>
import { useStore } from "vuex";
import { computed } from "vue";
import Sidebar from "./Sidebar.vue";

export default {
  name: "MainLayoutAdmin",
  components: {
    Sidebar,
  },
  setup(props, context) {
    document.body.style.backgroundColor = "#f8f9fa";
    document.body.style.background = "";
    const store = useStore();
    const usuario = computed(() => {
      return (
        store.state.auth.user.names + " " + store.state.auth.user.last_name
      );
    });
    return {
      usuario,
    };
  },
};
</script>