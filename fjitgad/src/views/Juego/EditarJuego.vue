<template>
  <form-juego
    @submit="handleSubmit"
    :idgame="idgame"
    title="Editar juego"
    :edit="true"
  />
</template>

<script>
import FormJuego from "../../components/forms/FormJuego.vue";
import JuegosService from "../../api/JuegosService.js";
import { ref } from "vue";
import { useRoute } from "vue-router";

export default {
  name: "EditarJuego",
  components: {
    FormJuego,
  },
  setup(props, context) {
    const route = useRoute();
    const handleSubmit = async (model, callback) => {
      const Response = await JuegosService.putJuego(model);
      callback(Response);
    };
    const idgame = ref(parseInt(route.params["id"]));
    return {
      handleSubmit,
      idgame,
    };
  },
};
</script>