<template>
  <div v-if="Loading">Cargando...</div>
  <div v-else>
    <my-input
      v-model="model.names"
      type="text"
      label="Nombres"
      placeholder="Escriba los nombres del usuario"
      validations="requerido"
    />
    <my-input
      v-model="model.last_name"
      type="text"
      label="Apellidos"
      placeholder="Escriba los apellidos del usuario"
      validations="requerido"
    />
    <my-input
      v-model="model.email"
      type="email"
      label="Correo electronico"
      placeholder="Escriba el correo electronico del usuario"
      validations="requerido"
    />
    <my-input
      v-model="model.password"
      type="password"
      label="Contraseña"
      placeholder="Escriba la contraseña del usuario"
      :help="HelpPassword"
      :validations="ValidationPassword"
    />
    <my-input
      v-model="model.confirm_password"
      :equal="model.password"
      type="password"
      label="Repita la Contraseña"
      placeholder="Repita la contraseña del usuario"
      :help="HelpPassword"
      :validations="ValidationConfirmPassword"
    />
    <my-input-file label="Imagen" v-model="model.image" type="image" />
    <my-input
      v-model="model.birthdate"
      type="date"
      label="Fecha de nacimiento"
      placeholder="Escriba la fecha de nacimiento"
    />
    <my-select
      placeholder="Seleccione un rol de usuario"
      v-model="model.rol"
      label="Rol de usuario"
      :data="Roles"
      validations="requerido"
    />
    <my-select-boolean label="Estado" v-model="model.state" />
  </div>
</template>

<script>
import {
  inject,
  getCurrentInstance,
  reactive,
  onMounted,
  onBeforeUnmount,
  computed,
  ref,
} from "vue";
import { message_error } from "../../util/Messages.js";
import UsuariosService from "../../api/UsuariosService.js";
import ObjectSelect from "../../util/ObjectSelect.js";
import { formatDateInput, Role } from "../../util/Utilities.js";

export default {
  name: "FormUsuario",
  emits: ["submit"],
  props: {
    iduser: {
      type: Number,
      default: 0,
    },
    title: {
      type: String,
      default: "Crear usuario",
    },
    edit: {
      type: Boolean,
      default: false,
    },
  },
  setup(props, context) {
    const InitialState = {
      iduser: 0,
      names: "",
      last_name: "",
      email: "",
      password: "",
      confirm_password: "",
      image: null,
      birthdate: formatDateInput(),
      rol: "",
      state: true,
    };
    const HelpPassword = computed(() => {
      return props.edit
        ? "Si envia este campo vacio, se mantendra la contraseña anterior"
        : "";
    });
    const ValidationPassword = computed(() => {
      return `${props.edit ? "" : "requerido,"}minlength:6`;
    });
    const ValidationConfirmPassword = computed(() => {
      return `${
        props.edit ? "" : "requerido,"
      }equalprop:Contraseña;Repetir Contraseña,minlength:6`;
    });
    const Roles = ref([]);
    Roles.value.push(new ObjectSelect(Role.Admin, Role.Admin));
    Roles.value.push(new ObjectSelect(Role.Docente, Role.Docente));
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
        "url-next": "/list/usuarios",
        "is-edit": props.edit,
      });
      if (props.iduser > 0) {
        setLoading(true);
        const response = await UsuariosService.getUsuario(props.iduser);
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
      Roles,
      HelpPassword,
      ValidationConfirmPassword,
      ValidationPassword,
    };
  },
};
</script>