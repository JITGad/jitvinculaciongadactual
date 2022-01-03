<template>
  <form-usuario @submit="handleSubmit" />
</template>

<script>
import UsuariosService from '../../api/UsuariosService';
import FormUsuario from "../../components/forms/FormUsuario.vue";
export default {
  name: "Crearusuario",
  components: {
    FormUsuario,
  },
  setup(props, context) {
    const handleSubmit = async (model, callback) => {
      const Response = await UsuariosService.postUsuario(model);
      callback(Response);
    };

    return {
      handleSubmit,
    };
  },
};
</script>