<template>
  <div
    class="ctrtemas"
    @click="verActividad"
    onmouseover="this.style.background='#f2f0f0'"
    onmouseout="this.style.background=''"
  >
    <div class="tema">
      <img
        :src="RutaImagenCompleta"
        width="150"
        height="150"
      />════════<br /><span style="font-size: 30px">{{ tema }}</span>
    </div>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import { computed } from "vue";
import { isBase64 } from "../util/Utilities.js";

export default {
  name: "Actividad",
  props: {
    tema: {
      type: String,
      default: "",
    },
    urlimagen: {
      type: String,
      default: "",
    },
    idactividad: {
      type: Number,
      default: 0,
    },
  },
  setup(props, context) {
    const Router = useRouter();
    const RutaImagenCompleta = computed(() => {
      if (props.urlimagen == null || props.urlimagen.length == 0) {
        return "";
      }
      if (isBase64(props.urlimagen)) {
        return props.urlimagen;
      }
      return `${process.env.VUE_APP_BASE_URL}${props.urlimagen}`;
    });
    const verActividad = function () {
      Router.push({
        name: "JuegosPorActividad",
        params: { id: props.idactividad, nombre: props.tema },
      });
    };

    return {
      verActividad,
      RutaImagenCompleta,
    };
  },
};
</script>