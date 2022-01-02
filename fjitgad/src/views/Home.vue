<template>
  <NavBar></NavBar>
  <div class="temas">
    <Loading v-if="loading" />
    <div v-else>
      <Actividad
        v-for="(value, index) in actividades"
        :tema="value.name"
        :urlimagen="value.image"
        :key="index"
        :idactividad="value.id"
      ></Actividad>
    </div>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
import Actividad from "@/components/Actividad.vue";
import Loading from "@/components/Loading.vue";
import ActividadesService from "../api/ActividadesService";
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