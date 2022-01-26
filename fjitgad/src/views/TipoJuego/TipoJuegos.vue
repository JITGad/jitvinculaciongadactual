<template>
  <table class="table table-bordered table-striped table-hover table-light">
    <thead class="thead-light">
      <tr>
        <th scope="col">Id</th>
        <th scope="col">Texto</th>
        <th scope="col">Nombre corto</th>
        <th scope="col">Imagen</th>
        <th scope="col">Instrucciones</th>
        <th scope="col">Audio de instrucciones</th>
        <th scope="col">Video de instrucciones</th>
        <th scope="col">Estado</th>
        <th scope="col">Editar</th>
        <my-autorization roles="Administrador">
          <th scope="col">Eliminar</th>
        </my-autorization>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(tipojuego, index) in TipoJuegos" :key="index">
        <th scope="row">{{ tipojuego.idgametype }}</th>
        <td>{{ tipojuego.name }}</td>
        <td>{{ tipojuego.shortname }}</td>
        <td align="center">
          <my-prev-file type="image" v-model="tipojuego.image" />
        </td>
        <td>
          <textarea
            class="form-control"
            rows="3"
            readonly
            v-model="tipojuego.text_instructions"
          >
          </textarea>
        </td>
        <td align="center">
          <my-prev-file type="audio" v-model="tipojuego.audio_instructions" />
        </td>
        <td align="center">
          <my-prev-file type="video" v-model="tipojuego.video_instructions" />
        </td>
        <td>{{ tipojuego.state ? "Activo" : "Inactivo" }}</td>
        <td align="center">
          <my-link-table
            :object="tipojuego"
            icon="fas fa-pen"
            @click="EditarTipoJuego"
          />
        </td>
        <my-autorization roles="Administrador">
          <td align="center">
            <my-link-table
              :object="tipojuego"
              icon="fas fa-trash"
              @click="EliminarTipoJuego"
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
import TipoJuegosService from "../../api/TipoJuegosService.js";
import {
  confirm_action,
  message_error,
  message_info,
} from "../../util/Messages.js";

export default {
  name: "TipoJuegos",
  setup(props, context) {
    const instance = getCurrentInstance();
    const TipoJuegos = ref([]);
    const Router = useRouter();
    const list = inject("layout-list");

    onMounted(async () => {
      list.bind({
        FetchData,
        uid: instance.uid,
        title: "Mis Tipos de juegos",
        url_nuevo: "/create/tipojuego",
      });
      await FetchData();
    });

    onBeforeUnmount(() => {
      list.unbind(instance.uid);
    });

    const FetchData = async (pActual = 1) => {
      list.setPageActual(pActual);
      const response = await TipoJuegosService.getTipoJuegosAdministrador(
        pActual
      );
      if (!response.status.error) {
        list.changeData(response.conteo, response.totalPaginas);
        TipoJuegos.value = response.data;
      } else {
        message_error(response.status.message);
      }
    };

    const EditarTipoJuego = (TipoJuego) => {
      Router.push({
        name: "EditarTipoJuego",
        params: { id: TipoJuego["idgametype"] },
      });
    };

    const EliminarTipoJuego = (TipoJuego) => {
      confirm_action(
        "Confirmación",
        `Esta seguro de eliminar la TipoJuego ${TipoJuego.name}`,
        async () => {
          const response = await TipoJuegosService.deleteTipoJuego(
            TipoJuego.idgametype
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
      TipoJuegos,
      EditarTipoJuego,
      EliminarTipoJuego,
    };
  },
};
</script>

