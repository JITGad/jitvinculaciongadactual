<template>
  <div class="mb-3" style="text-align: center">
    <h3>{{ title }}</h3>
  </div>
  <div v-if="loading">Cargando...</div>
  <div v-else>
    <my-form @submit="handleSubmit">
      <my-input
        v-model="model.nombre"
        type="text"
        label="Nombre"
        placeholder="Escriba el nombre de la actividad"
        validations="requerido"
      />
    </my-form>
  </div>
</template>

<script>
import { ref, reactive } from "vue";
import ActividadesService from "../../api/ActividadesService.js";

export default {
  name: "FormActividad",
  props: {
    key: {
      type: Number,
      default: 0,
    },
    title: {
      type: String,
      default: "Registro",
      required: true,
    },
  },
  emits: ["submit"],
  setup(props, context) {
    const model = reactive({
      id: 0,
      nombre: "",
      estado: true,
    });

    if (props.key > 0) {
      model.id = props.key;
      const response = ActividadesService.getActividad(model.id);
      if (!response.status.error) {
        model.nombre = response.data.nombre;
        model.estado = response.data.estado;
      } else {
        message_error(response.status.message);
      }
    }

    const handleSubmit = () => {
      context.emit("submit", model);
    };

    return {
      model,
      handleSubmit,
    };
  },
};
</script>