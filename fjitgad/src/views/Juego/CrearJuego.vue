<template>
  <form-juego @submit="handleSubmit" />
</template>

<script>
import FormJuego from "../../components/forms/FormJuego.vue";
import JuegosService from "../../api/JuegosService.js";

export default {
  name: "CrearJuego",
  components: {
    FormJuego,
  },
  setup(props, context) {
    const handleSubmit = async (model, callback) => {
      const Response = await JuegosService.postJuego(model);
      callback(Response);
    };

    return {
      handleSubmit,
    };
  },
};
</script>