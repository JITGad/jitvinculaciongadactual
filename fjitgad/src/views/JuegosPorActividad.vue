<template>
  <main-layout-juego>
    <my-loading v-if="Loading" />
    <div v-else class="container">
        <encabezado-juego :routes="RoutesEncabezadoJuego"/>
    </div>
  </main-layout-juego>
</template>

<script>
import { useRoute } from "vue-router";
import { ref, onMounted } from "vue";
import TipoJuegosService from "../api/TipoJuegosService.js";
import { message_error } from "../util/Messages.js";
export default {
  name: "JuegosPorActividad",
  setup(props, context) {
    const JuegosPorActividad = ref([]);
    const route = useRoute();
    const Loading = ref(true);
    const Actividad = route.params;
    console.log(Actividad);
    const RoutesEncabezadoJuego = ref(["Actividad", Actividad.nombre]);
    onMounted(async () => {
      const response = await TipoJuegosService.getTipoJuegoPorActividad(
        Actividad.id
      );
      Loading.value = false;
      if (!response.status.error) {
        JuegosPorActividad.value = response.data;
        console.log(JuegosPorActividad.value);
      } else {
        message_error(response.status.message);
      }
    });

    return {
      Loading,
      JuegosPorActividad,
      RoutesEncabezadoJuego,
    };
  },
};
</script>