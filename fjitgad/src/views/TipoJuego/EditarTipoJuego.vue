<template>
  <form-tipo-juego
    @submit="handleSubmit"
    :idgametype="idgametype"
    title="Editar tipo de juego"
    :edit="true"
  />
</template>

<script>
import FormTipoJuego from "../../components/forms/FormTipoJuego.vue";
import TipoJuegoService from "../../api/TipoJuegosService.js";
import { ref } from "vue";
import { useRoute } from "vue-router";

export default {
  name: "EditarActividad",
  components: {
    FormTipoJuego,
  },
  setup(props, context) {
    const route = useRoute();
    const handleSubmit = async (model, callback) => {
      const Response = await TipoJuegoService.putTipoJuego(model);
      callback(Response);
    };
    const idgametype = ref(parseInt(route.params["id"]));
    return {
      handleSubmit,
      idgametype,
    };
  },
};
</script>