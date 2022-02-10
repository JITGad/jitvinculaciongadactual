<template>
  <div :class="ClassAlert" role="alert">
    <div class="row">
      <div class="col-md-12 p-0">
        <img :src="MyImage" width="40" />
        <span class="h4 px-2 my-2">
          {{ label }}
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import { computed } from "vue";
import { setPathFile } from "../util/Utilities";

export default {
  name: "TipoJuegoActividad",
  props: {
    image: {
      type: String,
      default: "",
    },
    label: {
      type: String,
      default: "",
    },
    index: {
      type: Number,
      default: 0,
    },
  },
  setup(props, context) {
    const MyImage = computed(() => setPathFile(props.image));
    const TypeAlert = [
      "primary",
      "secondary",
      "success",
      "danger",
      "warning",
      "info",
      "light",
      "dark",
    ];

    const ClassAlert = computed(() => {
      return `alert alert-${TypeAlert[props.index % TypeAlert.length]} m-0 p-1`;
    });

    return {
      MyImage,
      ClassAlert,
    };
  },
};
</script>