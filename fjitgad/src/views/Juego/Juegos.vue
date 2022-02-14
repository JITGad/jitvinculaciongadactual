<template>
  <table class="table table-bordered table-striped table-hover table-light">
    <thead class="thead-light">
      <tr>
        <th scope="col">Id</th>
        <th scope="col">Nombre</th>
        <th scope="col">Estado</th>
        <th scope="col">Portada</th>
        <th scope="col">Tipo de actividad</th>
        <th scope="col">Tipo de juego</th>
        <th scope="col">Editar</th>
        <my-autorization roles="Administrador">
          <th scope="col">Eliminar</th>
        </my-autorization>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(juego, index) in Juegos" :key="index">
        <th scope="row">{{ juego.idgame }}</th>
        <td>{{ juego.name }}</td>
        <td>{{ juego.state ? "Activo" : "Inactivo" }}</td>
        <td align="center">
          <my-prev-file type="image" v-model="juego.image" />
        </td>
        <td align="center">{{ juego.nameactivities }}</td>
        <td align="center">{{ juego.namegametype }}</td>
        <td align="center">
          <my-link-table
            :object="juego"
            icon="fas fa-pen"
            @click="Editarjuego"
          />
        </td>
        <my-autorization roles="Administrador">
          <td align="center">
            <my-link-table
              :object="juego"
              icon="fas fa-trash"
              @click="Eliminarjuego"
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
import JuegosService from "../../api/JuegosService.js";
import {
  confirm_action,
  message_error,
  message_info,
} from "../../util/Messages.js";

export default {
  name: "Juegos",
  setup(props, context) {
    const instance = getCurrentInstance();
    const Juegos = ref([]);
    const Router = useRouter();
    const list = inject("layout-list");

    onMounted(async () => {
      list.bind({
        FetchData,
        uid: instance.uid,
        title: "Mis Juegos",
        url_nuevo: "/create/juego",
      });
      await FetchData();
    });

    onBeforeUnmount(() => {
      list.unbind(instance.uid);
    });

    const FetchData = async (pActual = 1) => {
      list.setPageActual(pActual);
      const response = await JuegosService.getJuegosAdministrador(pActual);
      if (!response.status.error) {
        list.changeData(response.conteo, response.totalPaginas);
        Juegos.value = response.data;
      } else {
        message_error(response.status.message);
      }
    };

    function Editarjuego(juego) {
      Router.push({
        name: "EditarJuego",
        params: { id: juego["idgame"] },
      });
    }

    function Eliminarjuego(juego) {
      confirm_action(
        "Confirmación",
        `Esta seguro de eliminar el juego ${juego.name}`,
        async () => {
          const response = await JuegosService.deleteJuego(
            juego.idgame
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
    }

    return {
      Juegos,
      Editarjuego,
      Eliminarjuego,
    };
  },
};
</script>

