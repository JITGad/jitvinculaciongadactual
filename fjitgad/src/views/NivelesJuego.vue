<template>
  <main-layout-juego :backgroundBlanc="true">
    <my-loading v-if="Loading" />
    <div v-else class="container" style="margin-top: 10vh">
      <encabezado-juego :routes="RoutesJuego" />
      <div class="row">
        <template v-for="i in Juego.level" :key="i">
          <nivel-juego :game="Juego" :level="i" />
        </template>
      </div>
    </div>
  </main-layout-juego>
</template>

<script>
import { useRoute } from "vue-router";
import { ref, onMounted, reactive } from "vue";
import { message_error } from "../util/Messages.js";
import JuegosService from "../api/JuegosService.js";
import NivelJuego from "../components/NivelJuego.vue";

export default {
  name: "NivelesJuego",
  components: {
    NivelJuego,
  },
  setup(props, context) {
    const InitialState = {
      idgame: 0,
      idactivitiestype: 0,
      idgametype: 0,
      name: "",
      state: true,
      level: 1,
      image: null,
      nameactivities: "",
      namegametype: "",
      detalles: [],
    };
    const route = useRoute();
    const Loading = ref(true);
    const Juego = reactive({ ...InitialState });
    const IdJuego = route.params["id"];
    const RoutesJuego = ref(["Actividad"]);
    onMounted(async () => {
      Loading.value = true;
      const response = await JuegosService.getJuego(IdJuego);
      if (!response.status.error) {
        Object.assign(Juego, response.data);
        RoutesJuego.value = ["Actividad", Juego.nameactivities, Juego.namegametype];
        Loading.value = false;
      } else {
        message_error(response.status.message);
      }
    });

    return {
      Juego,
      Loading,
      RoutesJuego,
    };
  },
};
</script>