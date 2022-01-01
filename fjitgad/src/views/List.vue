<template>
  <main-layout>
    <label
      >Total de Registros: {{ TotalRegistros }} Página: {{ PaginaActual }} -
      {{ PaginasTotales }}</label
    >
    <router-view></router-view>
    <my-paginacion
      :paginaActual="PaginaActual"
      :paginasTotales="PaginasTotales"
      @paginaSeleccionada="PaginaSeleccionada"
    />
  </main-layout>
</template>

<script>
import { ref, provide, reactive } from "vue";

export default {
  name: "Actividades",
  setup(props, context) {
    const PaginaActual = ref(1);
    const PaginasTotales = ref(1);
    const TotalRegistros = ref(1);
    const state = reactive({
      childs: [],
    });

    provide("list", {
      bind,
      unbind,
      changeData,
      getPaginaActual,
    });

    function bind(component) {
      PaginaActual.value = 1;
      state.childs.push(component);
    }
    function unbind(uid) {
      const index = state.childs.findIndex((c) => c.uid === uid);
      if (index > -1) {
        state.childs.splice(index, 1);
      }
    }

    function changeData(conteo, totalPaginas) {
      TotalRegistros.value = conteo;
      PaginasTotales.value = totalPaginas;
    }

    function getPaginaActual() {
        return PaginaActual.value
    };

    const PaginaSeleccionada = async (pagina) => {
      PaginaActual.value = pagina.Pagina;
      for (const component of state.childs) {
        component.FetchData(PaginaActual.value);
      }
    };

    return {
      PaginaActual,
      PaginasTotales,
      TotalRegistros,
      PaginaSeleccionada,
    };
  },
};
</script>

<style>
body,
html {
  height: 100%;
  background-color: aliceblue;
}
</style>