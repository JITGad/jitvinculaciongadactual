<template>
  <table class="table table-success table-striped">
    <thead>
      <tr>
        <th scope="col">Imagen</th>
        <th v-if="type == 'cuento'" scope="col">Parrafo</th>
        <th v-if="type == 'cuento'" scope="col">Audio Parrafo</th>
        <th v-if="type == 'cuento'" scope="col">Video Parrafo</th>
        <th v-if="type == 'emparejar'" scope="col">Color</th>
        <th scope="col">Eliminar</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(object, index) in list" :key="index">
        <td>
          <my-input-file
            v-model="object.image"
            type="image"
            :labelshow="false"
          />
        </td>
        <td v-if="type == 'emparejar'">
          <my-select-color
            placeholder="Seleccione un color"
            v-model="object.idcolortype"
            :labelshow="false"
            type="int"
            :data="Colores"
          />
        </td>
        <td v-if="type == 'cuento'">
          <my-input
            v-model="object.paragraph"
            type="text"
            :multiple="true"
            :labelshow="false"
            placeholder="Escriba el parrafo"
          />
        </td>
        <td v-if="type == 'cuento'">
          <my-input-file
            v-model="object.audio_parag"
            type="audio"
            :labelshow="false"
          />
        </td>
        <td v-if="type == 'cuento'">
          <my-input-file
            v-model="object.video_parag"
            type="video"
            :labelshow="false"
          />
        </td>
        <td>
          <my-link-table
            :object="index"
            icon="fas fa-trash"
            @click="EliminarItem"
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
import ColoresService from "../api/ColoresService.js";
import MySelectColor from "./MySelectColor.vue";
import { message_error } from "../util/Messages.js";

export default {
  name: "DetalleJuego",
  emits: ["borrarItem"],
  components: {
    MySelectColor,
  },
  props: {
    list: {
      type: Array,
      required: true,
    },
    type: {
      type: String,
      required: true,
    },
  },
  setup(props, context) {
    const form = inject("my-form");
    const instance = getCurrentInstance();
    const Colores = ref([]);
    onMounted(async function () {
      Colores.value = await ColoresService.getColoresSelectMenu();
      form.bind({ validate, uid: instance.uid });
    });

    function EliminarItem(index) {
      context.emit("borrarItem", index);
    }

    onBeforeUnmount(() => {
      form.unbind(instance.uid);
    });

    function validate() {
      var valid = true;
      switch (props.type) {
        case "emparejar":
          valid = validateColor();
          break;
        case "rompecabezas":
        case "memoria":
          valid = validateImagen();
          break;
        case "cuento":
          valid = validateCuento();
        default:
          break;
      }
      return valid;
    }

    function validateCuento() {
      var repetido = props.list.find(
        (t) => t.paragraph || t.audio_parag || t.video_parag || t.image
      );
      if (!repetido) {
        message_error("Cada detalle debe tener al menos un tipo de entrada asignado");
        return false;
      }
      return true;
    }

    function validateImagen() {
      var repetido = props.list.find(
        (t) => t.image != null || t.image != undefined || t.image.length > 0
      );
      if (!repetido) {
        message_error("Debe agregar una imagen como minimo al detalle");
        return false;
      }
      return true;
    }

    function validateColor() {
      var repetido = props.list
        .groupBy((t) => t.idcolortype)
        .find((t) => t.values.length > 1 && t.key != "0");

      if (repetido) {
        message_error(
          `El color ${
            Colores.value.find((c) => c.id === parseInt(repetido.key)).text
          } esta repetido varias veces`
        );
        return false;
      }

      return true;
    }

    return {
      Colores,
      EliminarItem,
    };
  },
};
</script>