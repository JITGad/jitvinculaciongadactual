<template>
  <form-tipo-juego @submit="handleSubmit" />
</template>

<script>
import FormTipoJuego from "../../components/forms/FormTipoJuego.vue";
import TipoJuegosService from "../../api/TipoJuegosService.js";

export default {
  name: "CrearTipoJuego",
  components: {
    FormTipoJuego,
  },
  setup(props, context) {
    const handleSubmit = async (model, callback) => {
      const Response = await TipoJuegosService.postTipoJuego(model);
      callback(Response);
    };

    return {
      handleSubmit,
    };
  },
};
</script>