<template>
  <div v-if="Loading">Cargando...</div>
  <div v-else>
    <my-input
      v-model="model.name"
      type="text"
      label="Nombre"
      placeholder="Escriba el nombre de la actividad"
      validations="requerido"
    />
    <my-select-boolean label="Estado" v-model="model.state" />
    <my-input-file
      label="Imagen"
      v-model="model.image"
      validations="requerido"
    />
  </div>
</template>

<script>
import ActividadesService from "../../api/ActividadesService.js";
import {
  inject,
  getCurrentInstance,
  reactive,
  onMounted,
  onBeforeUnmount,
  ref,
  watch,
} from "vue";
import { message_error } from "../../util/Messages.js";

export default {
  name: "FormActividad",
  emits: ["submit"],
  props: {
    idactividad: {
      type: Number,
      default: 0,
    },
    title: {
      type: String,
      default: "Crear actividad"
    },
    edit: {
      type: Boolean,
      default: false
    },
  },
  setup(props, context) {
    const Loading = ref(false);
    const layout = inject("layout");
    const instance = getCurrentInstance();
    const model = reactive({
      idactivitiestype: 0,
      name: "",
      image: null,
      state: true,
    });
    onMounted(async function () {
      layout.bind({
        submit,
        clear,
        uid: instance.uid,
        title: props.title,
        "url-next": "/list/actividades",
        "is-edit": props.edit,
      });
      if (props.idactividad > 0) {
        setLoading(true);
        model.idactivitiestype = props.idactividad;
        const response = await ActividadesService.getActividad(model.id);
        if (!response.status.error) {
          model.nombre = response.data.nombre;
          model.estado = response.data.estado;
          model.image = response.data.image;
          setLoading(false);
        } else {
          message_error(response.status.message);
        }
      }
    });
    onBeforeUnmount(() => {
      layout.unbind(instance.uid);
    });
    function submit() {
      return new Promise((resolve) => {
        context.emit("submit", model, (response) => resolve(response));
      });
    }
    function setLoading(val){
      Loading.value = val;
      layout.loading(val);
    }
    function clear() {
      model.idactivitiestype = 0;
      model.name = "";
      model.state = true;
      model.image = null;
    }
    return {
      model,
      Loading,
    };
  },
};
</script>