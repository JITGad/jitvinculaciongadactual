<template>
  <main-layout-juego :backgroundBlanc="true">
    <my-loading v-if="Loading" />
    <div v-else class="container" style="margin-top: 10vh">
      <encabezado-juego :routes="RoutesEncabezadoJuego" />
      <template v-for="(tipojuego, i) in JuegosPorActividad" :key="i">
        <tipo-juego-actividad
          :image="tipojuego.image"
          :index="i"
          :label="tipojuego.name"
        />
        <div class="row">
          <template v-for="(juego, j) in tipojuego.detalles" :key="j">
            <juego-tipo-juego-actividad
              :idgame="juego.idgame"
              :image="juego.image"
              :label="juego.name"
            />
          </template>
        </div>
      </template>
    </div>
  </main-layout-juego>
</template>

<script>
import { useRoute } from "vue-router";
import { ref, onMounted } from "vue";
import TipoJuegosService from "../api/TipoJuegosService.js";
import { message_error } from "../util/Messages.js";
import JuegoTipoJuegoActividad from "../components/JuegoTipoJuegoActividad.vue";
import TipoJuegoActividad from "../components/TipoJuegoActividad.vue";
export default {
  name: "JuegosPorActividad",
  components: {
    JuegoTipoJuegoActividad,
    TipoJuegoActividad,
  },
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