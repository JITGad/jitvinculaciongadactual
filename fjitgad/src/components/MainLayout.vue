<template>
  <div class="dashboard">
    <div class="dashboard-nav">
        <Sidebar/>
    </div>
    <div class="dashboard-app">
      <header class="dashboard-toolbar">
        <a class="menu-toggle btn-link"><i class="fas fa-bars"></i></a>
        <div class="ms-auto" style="display: flex">
          <span class="navbar-text">{{usuario}}</span>
          <router-link class="nav-link" style="color: black" to="/logout">
            Cerrar<span style="text-transform: lowercase"> session</span>
          </router-link>
        </div>
      </header>
      <div class="dashboard-content">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { onMounted, computed } from "vue";
import { toogleMenu } from "../util/EventsMenu.js";
import Sidebar from "./Sidebar.vue";

export default {
    name: "MainLayout",
    components: {
        Sidebar
    },
    setup(props, context) {
        const store = useStore();
        const usuario = computed(() => {
            return store.state.auth.user.names + store.state.auth.user.last_name
        });

        onMounted(() => toogleMenu());

        return {
            usuario
        }
    },
}
</script>