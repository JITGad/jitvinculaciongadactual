<template>
  <table class="table table-bordered table-striped table-hover table-light">
    <thead class="thead-light">
      <tr>
        <th scope="col">Id</th>
        <th scope="col">Nombre</th>
        <th scope="col">Color</th>
        <th scope="col">Estado</th>
        <th scope="col">Editar</th>
        <my-autorization roles="Administrador">
          <th scope="col">Eliminar</th>
        </my-autorization>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(Color, index) in Colores" :key="index">
        <th scope="row">{{ Color.idcolortype }}</th>
        <td>{{ Color.name }}</td>
        <td>
            <input type="color" :value="Color.html" class="form-control form-control-color" disabled />
        </td>
        <td>{{ Color.state ? "Activo" : "Inactivo" }}</td>
        <td align="center">
          <my-link-table
            :object="Color"
            icon="fas fa-pen"
            @click="EditarColor"
          />
        </td>
        <my-autorization roles="Administrador">
          <td align="center">
            <my-link-table
              :object="Color"
              icon="fas fa-trash"
              @click="EliminarColor"
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
import ColoresService from "../../api/ColoresService.js";
import {
  confirm_action,
  message_error,
  message_info,
} from "../../util/Messages.js";

export default {
  name: "Colores",
  setup(props, context) {
    const instance = getCurrentInstance();
    const Colores = ref([]);
    const Router = useRouter();
    const list = inject("layout-list");

    onMounted(async () => {
      list.bind({
        FetchData,
        uid: instance.uid,
        title: "Mis Colores",
        url_nuevo: "/create/color",
      });
      await FetchData();
    });

    onBeforeUnmount(() => {
      list.unbind(instance.uid);
    });

    const FetchData = async (pActual = 1) => {
      list.setPageActual(pActual);
      const response = await ColoresService.getColoresAdministrador(
        pActual
      );
      if (!response.status.error) {
        list.changeData(response.conteo, response.totalPaginas);
        Colores.value = response.data;
      } else {
        message_error(response.status.message);
      }
    };

    const EditarColor = (Color) => {
      Router.push({
        name: "EditarColor",
        params: { id: Color["idcolortype"] },
      });
    };

    const EliminarColor = (Color) => {
      confirm_action(
        "Confirmación",
        `Esta seguro de eliminar la Color ${Color.name}`,
        async () => {
          const response = await ColoresService.deleteColor(
            Color.idcolortype
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
      Colores,
      EditarColor,
      EliminarColor,
    };
  },
};
</script>

