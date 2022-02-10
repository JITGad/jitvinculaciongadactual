<template>
  <main-layout-juego :backgroundBlanc="true">
    <div class="dashboard-juego">
      <encabezado-juego :routes="Routes" />
      <div class="page-current-juego">
        <informacion-juego />
        <slot></slot>
      </div>
    </div>
  </main-layout-juego>
</template>

<script>
import InformacionJuego from "../components/InformacionJuego.vue";
import { ref } from 'vue';

export default {
  name: "Jugar",
  components: {
    InformacionJuego,
  },
  setup(props, context) {
    document.body.style.backgroundColor = "#f8f9fa";
    document.body.style.background = "";
    const Routes = ref([]);

    return {
      Routes
    }
  },
};
</script>