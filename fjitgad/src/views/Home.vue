<template>
  <main-layout-juego>
  <div class="temas">
    <my-loading v-if="loading" />
    <div v-else>
      <Actividad
        v-for="(value, index) in actividades"
        :tema="value.name"
        :urlimagen="value.image"
        :key="index"
        :idactividad="value.idactivitiestype"
      />
    </div>
  </div>
  </main-layout-juego>
</template>

<script>
import Actividad from "@/components/Actividad.vue";
import ActividadesService from "../api/ActividadesService";
import { ref, onMounted } from "vue";
import { message_error } from "@/util/Messages";

export default {
  name: "Home",
  components: {
    Actividad,
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