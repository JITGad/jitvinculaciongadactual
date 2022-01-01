<template>
  <li @click="handleEvent" style="cursor: pointer" :class="classEnlace">
    <span class="page-link">{{ pagina.Texto }}</span>
  </li>
</template>

<script>
import { computed } from "vue";
import EnlaceModel from "../util/EnlaceModel.js";

export default {
  name: "EnlacePaginacion",
  props: {
    pagina: {
      type: EnlaceModel,
      required: true,
    },
    paginaActual: {
      type: Number,
      default: 1,
      required: true,
    },
  },
  emits: ["click"],
  setup(props, context) {
    const handleEvent = () => {
      if (props.pagina.Pagina == props.paginaActual) {
        return;
      }
      if (!props.pagina.Habilitada) {
        return;
      }
      context.emit("click", props.pagina);
    };
    const classEnlace = computed(() => {
      return `page-item ${props.pagina.Habilitada ? "" : "disabled"} ${
        props.pagina.Activa ? "active" : ""
      }`;
    });

    return {
      handleEvent,
      classEnlace,
    };
  },
};
</script>