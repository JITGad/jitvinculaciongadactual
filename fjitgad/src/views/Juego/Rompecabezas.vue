<template>
  <div id="divJuego" className="div-canvasPanel"></div>
</template>

<script>
import { DiagramaJuego } from "../../fabric/diagrama.js";
import { onMounted, ref } from "vue";

export default {
  name: "ROmpecabezas",
  setup(props, context) {
    const Diagrama = ref(null);
    onMounted(() => {
      Diagrama = new DiagramaJuego.Diagram("divJuego");
    });
  },
};
</script>