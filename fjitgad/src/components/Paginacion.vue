<template>
  <nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
      <enlace-paginacion
        v-for="(item, index) in Enlaces"
        :pagina="item"
        :key="index"
        :paginaActual="paginaActual"
        @click="PaginaSeleccionada"
      />
    </ul>
  </nav>
</template>

<script>
import { ref, watch } from "vue";
import EnlacePaginacion from "./EnlacePaginacion.vue";
import EnlaceModel from "../util/EnlaceModel.js";

export default {
  name: "Paginacion",
  components: {
    EnlacePaginacion,
  },
  props: {
    paginaActual: {
      type: Number,
      default: 1,
      required: true,
    },
    paginasTotales: {
      type: Number,
      required: true,
    },
    radio: {
      type: Number,
      default: 3,
    },
  },
  emits: ["paginaSeleccionada"],
  setup(props, context) {
    const Enlaces = ref([]);

    const PaginaSeleccionada = (pagina) => {
      context.emit("paginaSeleccionada", pagina);
    };

    const ConstruirPaginacion = (pActual, pTotales, pRadio) => {
      Enlaces.value = [];
      Enlaces.value.push(
        new EnlaceModel(pActual - 1, pActual != 1, "Anterior")
      );

      for (let i = 1; i <= pTotales; i++) {
        if (i >= pActual - pRadio && i <= pActual + pRadio) {
          Enlaces.value.push(
            new EnlaceModel(i, true, i.toString(), pActual == i)
          );
        }
      }

      Enlaces.value.push(
        new EnlaceModel(pActual + 1, pActual != pTotales, "Siguiente")
      );
    };

    ConstruirPaginacion(props.paginaActual, props.paginasTotales, props.radio);

    watch(
      () => [props.paginaActual, props.paginasTotales, props.radio],
      ([pActual, pTotales, pRadio], [pActualOld, pTotalesOld, pRadioOld]) => {
        ConstruirPaginacion(pActual, pTotales, pRadio);
      }
    );

    return {
      Enlaces,
      PaginaSeleccionada,
    };
  },
};
</script>