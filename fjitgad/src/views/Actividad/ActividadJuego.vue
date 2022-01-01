<template>Actividad</template>

<script>
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";

export default {
  name: "MenuActividad",
  props: {
    key: {
      type: Number,
      default: 0,
    },
  },
  setup(props, context) {
    const actividad = ref({});
    const route = useRoute();
    onMounted(() => {
      route.params["id"];
    });

    return {
      actividad,
    };
  },
};
</script>