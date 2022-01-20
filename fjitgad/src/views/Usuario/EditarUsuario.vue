<template>
  <form-usuario
    @submit="handleSubmit"
    :iduser="iduser"
    title="Editar usuario"
    :edit="true"
  />
</template>

<script>
import UsuariosService from "../../api/UsuariosService";
import FormUsuario from "../../components/forms/FormUsuario.vue";
import { ref } from "vue";
import { useRoute } from "vue-router";
export default {
  name: "Crearusuario",
  components: {
    FormUsuario,
  },
  setup(props, context) {
    const route = useRoute();
    const handleSubmit = async (model, callback) => {
      console.log(model);
      const Response = await UsuariosService.putUsuario(model);
      console.log(Response);
      callback(Response);
    };
    const iduser = ref(parseInt(route.params["id"]));
    return {
      handleSubmit,
      iduser,
    };
  },
};
</script>