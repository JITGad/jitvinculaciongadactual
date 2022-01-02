<template>
  <form-actividad
    @submit="handleSubmit"
    :idactividad="idactividad"
    title="Editar actividad"
    :edit="true"
  />
</template>

<script>
import FormActividad from "../../components/forms/FormActividad.vue";
import ActividadesService from "../../api/ActividadesService.js";
import { ref } from "vue";
import { useRoute } from "vue-router";

export default {
  name: "EditarActividad",
  components: {
    FormActividad,
  },
  setup(props, context) {
    const route = useRoute();
    const handleSubmit = async (model, callback) => {
      const Response = await ActividadesService.putActividad(model);
      callback(Response);
    };
    const idactividad = ref(parseInt(route.params["id"]));
    return {
      handleSubmit,
      idactividad,
    };
  },
};
</script>