<template>
  <table class="table table-bordered table-striped table-hover table-light">
    <thead class="thead-light">
      <tr>
        <th scope="col">Id</th>
        <th scope="col">Nombre</th>
        <th scope="col">Estado</th>
        <th scope="col">Imagen</th>
        <th scope="col">Editar</th>
        <my-autorization roles="Administrador">
          <th scope="col">Eliminar</th>
        </my-autorization>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(actividad, index) in Actividades" :key="index">
        <th scope="row">{{ actividad.idactivitiestype }}</th>
        <td>{{ actividad.name }}</td>
        <td>{{ actividad.state ? "Activo" : "Inactivo" }}</td>
        <td align="center">
          <my-prev-file type="image" v-model="actividad.image" />
        </td>
        <td align="center">
          <my-link-table
            :object="actividad"
            icon="fas fa-pen"
            @click="EditarActividad"
          />
        </td>
        <my-autorization roles="Administrador">
          <td align="center">
            <my-link-table
              :object="actividad"
              icon="fas fa-trash"
              @click="EliminarActividad"
            />
          </td>
        </my-autorization>
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
import {
  confirm_action,
  message_error,
  message_info,
} from "../../util/Messages.js";

export default {
  name: "Actividades",
  setup(props, context) {
    const instance = getCurrentInstance();
    const Actividades = ref([]);
    const Router = useRouter();
    const list = inject("layout-list");

    onMounted(async () => {
      list.bind({
        FetchData,
        uid: instance.uid,
        title: "Mis Actividades",
        url_nuevo: "/create/actividad",
      });
      await FetchData();
    });

    onBeforeUnmount(() => {
      list.unbind(instance.uid);
    });

    const FetchData = async (pActual = 1) => {
      list.setPageActual(pActual);
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
        async () => {
          const response = await ActividadesService.deleteActividad(
            actividad.idactivitiestype
          );
          if (!response.status.error) {
            message_info("Registro eliminado correctamente", async () => {
              await FetchData();
            });
          } else {
            message_error(response.status.message);
          }
        },
        () => {}
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

