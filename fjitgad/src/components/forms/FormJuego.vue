<template>
  <div v-if="Loading">Cargando...</div>
  <div v-else>
    <my-input
      v-model="model.name"
      type="text"
      label="Nombre"
      placeholder="Escriba el nombre del Juego"
      validations="requerido"
    />
    <my-input
      v-model="model.level"
      type="number"
      label="Niveles"
      placeholder="Escriba los niveles del Juego"
      validations="requerido"
    />
    <my-select
      placeholder="Seleccione un tipo de actividad"
      v-model="model.idactivitiestype"
      label="Tipo de actividad"
      type="int"
      :data="TipoActividades"
      validations="requerido"
    />
    <my-select
      placeholder="Seleccione un tipo de juego"
      v-model="model.idgametype"
      label="Tipo de juego"
      type="int"
      :data="TipoJuegos"
      validations="requerido"
    />
    <my-select-boolean label="Estado" v-model="model.state" />

    <div class="mb-3">
      <label class="form-label">Detalle de juego</label>
      <detalle-emparejar :list="model.detalles" v-if="TipoJuegoSelected == 'emparejar'"/>
    </div>

  </div>
</template>

<script>
import JuegosService from "../../api/JuegosService.js";
import ActividadesService from "../../api/ActividadesService.js";
import TipoJuegosService from "../../api/TipoJuegosService.js";
import {
  inject,
  getCurrentInstance,
  reactive,
  onMounted,
  onBeforeUnmount,
  ref,
  watch,
  computed,
} from "vue";
import { message_error } from "../../util/Messages.js";
import DetalleEmparejarObject from "../../util/DetalleEmparejarObject.js";
import DetalleEmparejar from "../DetalleEmparejar.vue";

export default {
  name: "FormJuego",
  emits: ["submit"],
  props: {
    idgame: {
      type: Number,
      default: 0,
    },
    title: {
      type: String,
      default: "Crear Juego",
    },
    edit: {
      type: Boolean,
      default: false,
    },
  },
  components: {
    DetalleEmparejar
  },
  setup(props, context) {
    const InitialState = {
      idgame: 0,
      idactivitiestype: 0,
      idgametype: 0,
      name: "",
      state: true,
      level: 1,
      detalles: [

      ]
    };
    const TipoActividades = ref([]);
    const TipoJuegos = ref([]);
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
        "url-next": "/list/Juegos",
        "is-edit": props.edit,
      });
      if (props.idgame > 0) {
        setLoading(true);
        const response = await JuegosService.getJuego(props.idgame);
        if (!response.status.error) {
          Object.assign(model, response.data);
          setLoading(false);
        } else {
          message_error(response.status.message);
        }
      }
      TipoActividades.value =
        await ActividadesService.getActividadesSelectMenu();
      TipoJuegos.value = await TipoJuegosService.getTipoJuegosSelectMenu();
    });
    watch(
      () => model.idgametype,
      (value, prevValue) => {
        const tipojuego = TipoJuegos.value.find(el => el.id == value);
        if(tipojuego && tipojuego.value == "emparejar"){
          model.detalles.clear();
          model.detalles.push(new DetalleEmparejarObject());
        }
      }
    );
    const TipoJuegoSelected = computed(() => {
      const tipojuego = TipoJuegos.value.find(el => el.id == model.idgametype);
        if(tipojuego && tipojuego.value == "emparejar"){
          return "emparejar";
        }
        return "No se";
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
      TipoActividades,
      TipoJuegos,
      TipoJuegoSelected,
    };
  },
};
</script>