<template>
  <NavBar></NavBar>
  <div class="temas">
    <Loading v-if="loading" />
    <div v-else>
      <Actividad
        v-for="value in actividades"
        :tema="value.id"
        :urlimagen="value.name"
        :key="value.id"
      ></Actividad>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import NavBar from "@/components/NavBar.vue";
import Actividad from "@/components/Actividad.vue";
import Loading from "@/components/Loading.vue";
import ActividadesService from "@/api/ActividadesService";
import { ref, onMounted } from "vue";
import { message_error } from "@/util/Messages";

export default {
  name: "Home",
  components: {
    NavBar,
    Actividad,
    Loading
  },
  setup(props,context){
    const actividades = ref([]);
    const loading = ref(true);

    onMounted(async() => {
        const response = await ActividadesService.getActividadesJuegos();
        loading.value = false;
        console.log(response);
        if (!response.status.error) {
          actividades.value = response.data;
        } else {
          message_error(response.status.message);
        }
    });
    return {
      actividades,
      loading
    }
  }
};
</script>

<style>
body,
html {
  height: 100%;
  background-repeat: no-repeat;
  background: url(../assets/image/fondo2.png) no-repeat center center fixed;
  background-size: 100% 100%;
}
</style>