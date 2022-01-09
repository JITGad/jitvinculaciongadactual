<template>
  <form-color @submit="handleSubmit" />
</template>

<script>
import FormColor from "../../components/forms/FormColor.vue";
import ColoresService from "../../api/ColoresService.js";

export default {
  name: "CrearColor",
  components: {
    FormColor,
  },
  setup(props, context) {
    const handleSubmit = async (model, callback) => {
      const Response = await ColoresService.postColor(model);
      callback(Response);
    };

    return {
      handleSubmit,
    };
  },
};
</script>