<template>
  <table class="table table-success table-striped">
    <thead>
      <tr>
        <th scope="col">Imagen</th>
        <th scope="col">Color</th>
        <th scope="col">Nuevo</th>
        <th scope="col">Eliminar</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(object, index) in list" :key="index">
        <td>
          <my-input-file v-model="object.image" type="image" />
        </td>
        <td>
          <my-select-color
            placeholder="Seleccione un color"
            v-model="object.idcolortype"
            :labelshow="false"
            type="int"
            :data="Colores"
          />
        </td>
        <td>
          <my-link-table
            :object="index"
            icon="fas fa-plus-circle"
            @click="AgregarItem"
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
  name: "DetalleEmparejar",
  emits: ["nuevoItem", "borrarItem"],
  components: {
    MySelectColor,
  },
  props: {
    list: {
      type: Array,
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

    function AgregarItem() {
      context.emit("nuevoItem");
    }

    function EliminarItem(index) {
      context.emit("borrarItem", index);
    }

    onBeforeUnmount(() => {
      form.unbind(instance.uid);
    });

    function validate() {
      var repetido = props.list
        .groupBy((t) => t.idcolortype)
        .find((t) => t.values.length > 1 && t.key != "0");

      if (repetido) {
        message_error(
          `El color ${
            Colores.value.find((c) => c.id === parseInt(repetido.key))
              .text
          } esta repetido varias veces`
        );
        return false;
      }

      return true;
    }

    return {
      Colores,
      AgregarItem,
      EliminarItem,
    };
  },
};
</script>