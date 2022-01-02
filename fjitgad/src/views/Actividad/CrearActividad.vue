<template>
  <form-actividad @submit="handleSubmit" />
</template>

<script>
import FormActividad from "../../components/forms/FormActividad.vue";
import ActividadesService from "../../api/ActividadesService.js";

export default {
  name: "CrearActividad",
  components: {
    FormActividad,
  },
  setup(props, context) {
    const handleSubmit = async (model, callback) => {
      const Response = await ActividadesService.postActividad(model);
      callback(Response);
    };

    return {
      handleSubmit,
    };
  },
};
</script>