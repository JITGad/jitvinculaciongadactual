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
              :levels="juego.level"
            />
          </template>
        </div>
      </template>
    </div>
  </main-layout-juego>
</template>

<script>
import { useRoute } from "vue-router";
import { ref, onMounted, reactive } from "vue";
import TipoJuegosService from "../api/TipoJuegosService.js";
import { message_error } from "../util/Messages.js";
import JuegoTipoJuegoActividad from "../components/JuegoTipoJuegoActividad.vue";
import TipoJuegoActividad from "../components/TipoJuegoActividad.vue";
import ActividadesService from "../api/ActividadesService.js";
export default {
  name: "JuegosPorActividad",
  components: {
    JuegoTipoJuegoActividad,
    TipoJuegoActividad,
  },
  setup(props, context) {
    const InitialState = {
      idactivitiestype: 0,
      name: "",
      image: null,
      state: true,
    };
    const JuegosPorActividad = ref([]);
    const route = useRoute();
    const Loading = ref(true);
    const ActividadId = route.params["id"];
    const Actividad = reactive({ ...InitialState });
    const RoutesEncabezadoJuego = ref(["Actividad"]);
    onMounted(async () => {
      Loading.value = true;
      const ActividadResponse = await ActividadesService.getActividad(
        ActividadId
      );
      if (!ActividadResponse.status.error) {
        Object.assign(Actividad, ActividadResponse.data);
        RoutesEncabezadoJuego.value = ["Actividad", Actividad.name];
        const response = await TipoJuegosService.getTipoJuegoPorActividad(
          ActividadId
        );
        Loading.value = false;
        if (!response.status.error) {
          JuegosPorActividad.value = response.data;
        } else {
          message_error(response.status.message);
        }
      } else {
        message_error(ActividadResponse.status.message);
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