<template>
  <table class="table table-success table-striped">
    <thead>
      <tr>
        <th scope="col">Imagen</th>
        <th scope="col">Color</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(object, index) in list" :key="index">
        <td>
          <my-input-file v-model="object.imagen" type="image" />
        </td>
        <td>
          <my-select
            placeholder="Seleccione un color"
            v-model="object.colorid"
            :labelshow="false"
            type="int"
            :data="Colores"
          />
        </td>
      </tr>
    </tbody>
  </table>
</template>

<script>
import { ref, onMounted } from "vue";
import ColoresService from "../api/ColoresService.js";

export default {
  name: "DetalleEmparejar",
  props: {
    list: {
      type: Array,
      required: true,
    },
  },
  setup(props, context) {
    const Colores = ref([]);
    onMounted(async function () {
      Colores.value = await ColoresService.getColoresSelectMenu();
    });

    return {
      Colores,
    };
  },
};
</script>