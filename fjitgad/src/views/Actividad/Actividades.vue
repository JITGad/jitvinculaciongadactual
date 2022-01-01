<template>
  <table class="table table-bordered table-striped table-hover table-light">
    <thead class="thead-light">
      <tr>
        <th scope="col">Id</th>
        <th scope="col">Nombre</th>
        <th scope="col">Estado</th>
        <th scope="col">Imagen</th>
        <th scope="col">Editar</th>
        <th scope="col">Eliminar</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(actividad, index) in Actividades" :key="index">
        <th scope="row">{{ actividad.idactivitiestype }}</th>
        <td>{{ actividad.name }}</td>
        <td>{{ actividad.state ? "Activo" : "Inactivo" }}</td>
        <td align="center">
          <img
            class="brand-image"
            width="30"
            height="24"
            :src="actividad.image"
          />
        </td>
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
</template>

<script>
import {
  ref,
  onMounted,
  inject,
  getCurrentInstance,
  onBeforeUnmount,
} from "vue";
import { useRouter } from "vue-router";
import ActividadesService from "../../api/ActividadesService.js";
import { confirm_action, message_error } from "../../util/Messages.js";

export default {
  name: "Actividades",
  setup(props, context) {
    const Actividades = ref([]);
    const Router = useRouter();

    const list = inject("list");
    const uid = getCurrentInstance().uid;

    onMounted(async () => {
      list.bind({ FetchData, uid });
      await FetchData(list.getPaginaActual());
    });

    onBeforeUnmount(() => {
      list.unbind(uid);
    });

    const FetchData = async (pActual) => {
      const response = await ActividadesService.getActividadesAdministrador(
        pActual
      );
      if (!response.status.error) {
        list.changeData(response.conteo, response.totalPaginas);
        Actividades.value = response.data;
      } else {
        message_error(response.status.message);
      }
    };

    const EditarActividad = (actividad) => {
      Router.push({
        name: "EditarActividad",
        params: { id: actividad["idactivitiestype"] },
      });
    };

    const EliminarActividad = (actividad) => {
      confirm_action(
        "Confirmación",
        `Esta seguro de eliminar la actividad ${actividad.name}`,
        () => {
          ActividadesService.deleteActividad(actividad.idactivitiestype);
        }
      );
    };

    return {
      Actividades,
      EditarActividad,
      EliminarActividad,
    };
  },
};
</script>

