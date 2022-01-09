<template>
  <div v-if="Loading">Cargando...</div>
  <div v-else>
    <my-input
      v-model="model.name"
      type="text"
      label="Nombre Largo"
      placeholder="Escriba el nombre de la actividad"
      validations="requerido"
    />
    <my-input
      v-model="model.shortname"
      type="text"
      label="Nombre corto"
      :help="HelpShortName"
      :disabled="edit"
      placeholder="Escriba el nombre de la actividad"
      validations="requerido"
    />
    <my-input-file
      label="Imagen"
      v-model="model.image"
      type="image"
    />
    <my-input-file
      label="Audio de instrucciones"
      v-model="model.audio_instructions"
      type="audio"
    />
    <my-select-boolean label="Estado" v-model="model.state" />
  </div>
</template>

<script>
import TipoJuegosService from "../../api/TipoJuegosService.js";
import {
  inject,
  getCurrentInstance,
  reactive,
  onMounted,
  onBeforeUnmount,
  ref,
  computed,
} from "vue";
import { message_error } from "../../util/Messages.js";

export default {
  name: "FormTipoJuego",
  emits: ["submit"],
  props: {
    idgametype: {
      type: Number,
      default: 0,
    },
    title: {
      type: String,
      default: "Crear tipo de juego",
    },
    edit: {
      type: Boolean,
      default: false,
    },
  },
  setup(props, context) {
    const InitialState = {
      idgametype: 0,
      name: "",
      shortname: "",
      image: null,
      audio_instructions: null,
      state: true,
    };
    const HelpShortName = computed(() => {
      return props.edit
        ? "Este campo no es editable"
        : "Ese campo no se podra editar una vez se guarde";
    });
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
        "url-next": "/list/tipo-juegos",
        "is-edit": props.edit,
      });
      if (props.idgametype > 0) {
        setLoading(true);
        const response = await TipoJuegosService.getTipoJuego(props.idgametype);
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
      HelpShortName,
    };
  },
};
</script>