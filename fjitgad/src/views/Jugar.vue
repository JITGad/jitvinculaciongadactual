<template>
  <main-layout-juego :backgroundBlanc="true">
    <div class="dashboard-juego">
      <encabezado-juego />
      <div class="page-current-juego">
        <informacion-juego />
        <slot></slot>
      </div>
    </div>
  </main-layout-juego>
</template>

<script>
import InformacionJuego from "../components/InformacionJuego.vue";
import EncabezadoJuego from "../components/EncabezadoJuego.vue";

export default {
  name: "Jugar",
  components: {
    InformacionJuego,
    EncabezadoJuego,
  },
  setup(props, context) {
    document.body.style.backgroundColor = "#f8f9fa";
    document.body.style.background = "";
  },
};
</script>