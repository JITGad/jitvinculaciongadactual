<template>
  <main-layout>
    <div style="text-align: center">
      <h3>{{ Title }}</h3>
    </div>
    <div style="margin: 0px 0px 10px 0px" class="form-group">
      <router-link class="btn btn-primary" :to="UrlNuevo" autofocus>
        <i class="fas fa-plus"></i> Agregar Nuevo
      </router-link>
    </div>
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
  name: "List",
  setup(props, context) {
    const PaginaActual = ref(1);
    const PaginasTotales = ref(1);
    const TotalRegistros = ref(1);
    const Title = ref("");
    const UrlNuevo = ref("");
    const state = reactive({
      child: {},
    });

    provide("layout-list", {
      bind,
      unbind,
      changeData,
      setPageActual,
    });

    function bind(component) {
      PaginaActual.value = component.page;
      Title.value = component.title;
      UrlNuevo.value = component["url-nuevo"];
      state.child = component;
    }
    function unbind(uid) {
      const index = state.child.uid;
      if (index == uid) {
        state.childs = {};
      }
    }
    function setPageActual(page = 1) {
      PaginaActual.value = page;
    }
    function changeData(conteo, totalPaginas) {
      TotalRegistros.value = conteo;
      PaginasTotales.value = totalPaginas;
    }

    const PaginaSeleccionada = async (pagina) => {
      PaginaActual.value = pagina.Pagina;
      state.child.FetchData(PaginaActual.value);
    };

    return {
      PaginaActual,
      PaginasTotales,
      TotalRegistros,
      PaginaSeleccionada,
      UrlNuevo,
      Title,
    };
  },
};
</script>