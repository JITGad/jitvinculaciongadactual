<template>
  <form-color
    @submit="handleSubmit"
    :idcolor="idcolor"
    title="Editar color"
    :edit="true"
  />
</template>

<script>
import FormColor from "../../components/forms/FormColor.vue";
import ColoresService from "../../api/ColoresService.js";
import { ref } from "vue";
import { useRoute } from "vue-router";

export default {
  name: "EditarColor",
  components: {
    FormColor,
  },
  setup(props, context) {
    const route = useRoute();
    const handleSubmit = async (model, callback) => {
      const Response = await ColoresService.putColor(model);
      callback(Response);
    };
    const idcolor = ref(parseInt(route.params["id"]));
    return {
      handleSubmit,
      idcolor,
    };
  },
};
</script>