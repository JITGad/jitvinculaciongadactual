<template>
  <div v-if="Loading">Cargando...</div>
  <div v-else>
    <my-input
      v-model="model.name"
      type="text"
      label="Nombre"
      placeholder="Escriba el nombre del color"
      validations="requerido"
    />
    <my-input-color
      v-model="model.html"
      label="Color en hexadecimal"
      placeholder="Escriba el nombre del color"
      validations="requerido"
    />
    <my-select-boolean label="Estado" v-model="model.state" />
  </div>
</template>

<script>
import ColoresService from "../../api/ColoresService.js";
import {
  inject,
  getCurrentInstance,
  reactive,
  onMounted,
  onBeforeUnmount,
  ref,
} from "vue";
import { message_error } from "../../util/Messages.js";

export default {
  name: "FormColor",
  emits: ["submit"],
  props: {
    idcolortype: {
      type: Number,
      default: 0,
    },
    title: {
      type: String,
      default: "Crear color",
    },
    edit: {
      type: Boolean,
      default: false,
    },
  },
  setup(props, context) {
    const InitialState = {
      idcolortype: 0,
      rgb: "",
      name: "",
      html: "#000000",
      state: true,
    };
    const Loading = ref(false);
    const layout = inject("layout");
    const instance = getCurrentInstance();
    const model = reactive({ ...InitialState });
    onMounted(async function () {
      layout.bind({
        submit,
        clear,
        uid: instance.uid,
        title: props.title,
        "url-next": "/list/colors",
        "is-edit": props.edit,
      });
      if (props.idcolortype > 0) {
        setLoading(true);
        const response = await ColoresService.getColor(props.idcolortype);
        if (!response.status.error) {
          Object.assign(model, response.data);
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
    function setLoading(val) {
      Loading.value = val;
      layout.loading(val);
    }
    function clear() {
      Object.assign(model, InitialState);
    }
    return {
      model,
      Loading,
    };
  },
};
</script>