<template>
  <table class="table table-bordered table-striped table-hover table-light">
    <thead class="thead-light">
      <tr>
        <th scope="col">Id</th>
        <th scope="col">Nombre</th>
        <th scope="col">Apellido</th>
        <th scope="col">Correo electronico</th>
        <th scope="col">Rol</th>
        <th scope="col">Estado</th>
        <th scope="col">Imagen</th>
        <th scope="col">Editar</th>
        <th scope="col">Eliminar</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(usuario, index) in Usuarios" :key="index">
        <th scope="row">{{ usuario.iduser }}</th>
        <td>{{ usuario.names }}</td>
        <td>{{ usuario.last_name }}</td>
        <td>{{ usuario.email }}</td>
        <td>{{ usuario.rol }}</td>
        <td>{{ usuario.state ? "Activo" : "Inactivo" }}</td>
        <td align="center">
          <my-prev-file type="image" v-model="usuario.image" />
        </td>
        <td align="center">
          <my-link-table
            :object="usuario"
            icon="fas fa-pen"
            @click="EditarUsuario"
          />
        </td>
        <td align="center">
          <my-link-table
            :object="usuario"
            icon="fas fa-trash"
            @click="EliminarUsuario"
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
import UsuariosService from "../../api/UsuariosService.js";
import {
  confirm_action,
  message_error,
  message_info,
} from "../../util/Messages.js";

export default {
  name: "Usuarios",
  setup(props, context) {
    const instance = getCurrentInstance();
    const Usuarios = ref([]);
    const Router = useRouter();
    const list = inject("layout-list");

    onMounted(async () => {
      list.bind({
        FetchData,
        uid: instance.uid,
        title: "Mis Usuarios",
        url_nuevo: "/create/usuario",
      });
      await FetchData();
    });

    onBeforeUnmount(() => {
      list.unbind(instance.uid);
    });

    const FetchData = async (pActual = 1) => {
      list.setPageActual(pActual);
      const response = await UsuariosService.getUsuariosAdministrador(pActual);
      if (!response.status.error) {
        list.changeData(response.conteo, response.totalPaginas);
        Usuarios.value = response.data;
      } else {
        message_error(response.status.message);
      }
    };

    const EditarUsuario = (_usuario) => {
      Router.push({
        name: "EditarUsuario",
        params: { id: _usuario["iduser"] },
      });
    };

    const EliminarUsuario = (_usuario) => {
      confirm_action(
        "Confirmación",
        `Esta seguro de eliminar la Usuario ${_usuario.names}`,
        async () => {
          const response = await UsuariosService.deleteUsuario(
            _usuario.iduser
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
      Usuarios,
      EditarUsuario,
      EliminarUsuario,
    };
  },
};
</script>