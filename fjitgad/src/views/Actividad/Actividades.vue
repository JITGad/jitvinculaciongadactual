<template>
  <main-layout>
    <label
      >Total de Registros: {{ TotalRegistros }} Página: {{ PaginaActual }} -
      {{ PaginasTotales }}</label
    >
    <table class="table table-bordered table-striped table-hover">
      <thead class="thead-light">
        <tr>
          <th scope="col">Id</th>
          <th scope="col">Nombre</th>
          <th scope="col">Estado</th>
          <th scope="col">Editar</th>
          <th scope="col">Eliminar</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(actividad, index) in Actividades" :key="index">
          <th scope="row">{{ actividad.id }}</th>
          <td>{{ actividad.nombre }}</td>
          <td>{{ actividad.estado }}</td>
          <td align="center">
            <my-link-table
              :object="actividad"
              icon="fas fa-pen"
              @click="EditarActividad"
            />
          </td>
          <td align="center">
            <my-link-table
              :object="actividad"
              icon="fas fa-trash"
              @click="EliminarActividad"
            />
          </td>
        </tr>
      </tbody>
    </table>
    <my-paginacion
      :paginaActual="PaginaActual"
      :paginasTotales="PaginasTotales"
      @paginaSeleccionada="PaginaSeleccionada"
    />
  </main-layout>
</template>

<script>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import ActividadesService from "../../api/ActividadesService.js";
import { confirm_action } from "../../util/Messages.js";

export default {
  name: "Actividades",
  setup(props, context) {
    const PaginaActual = ref(1);
    const PaginasTotales = ref(1);
    const TotalRegistros = ref(1);
    const Actividades = ref([]);
    const Router = useRouter();

    onMounted(async () => {
      await FetchData();
    });

    const PaginaSeleccionada = async (pagina) => {
      PaginaActual.value = pagina.Pagina;
      await FetchData();
    };

    const FetchData = async () => {
      const response = await ActividadesService.getActividadesAdministrador(
        PaginaActual.value
      );
      if (!response.status.error) {
        TotalRegistros.value = response.conteo;
        PaginasTotales.value = response.totalPaginas;
        Actividades.value = response.data;
      } else {
        message_error(response.status.message);
      }
    };

    const EditarActividad = (actividad) =>
      Router.push({ name: "EditarActividad", params: { id: actividad["id"] } });

    const EliminarActividad = (actividad) => {
      confirm_action(
        "Confirmación",
        `Esta seguro de eliminar la actividad ${actividad.nombre}`,
        () => {
          ActividadesService.deleteActividad(actividad.id);
        }
      );
    };

    return {
      PaginaActual,
      PaginasTotales,
      TotalRegistros,
      PaginaSeleccionada,
      Actividades,
      EditarActividad,
      EliminarActividad,
    };
  },
};
</script>