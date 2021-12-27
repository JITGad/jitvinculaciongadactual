<template>
  <div>
    <div class="temas">
      <Loading v-if="loading"/>
      <div v-else>
          <Actividad
            v-for="value in actividades"
            :tema="value.id"
            :urlimagen="value.name"
            :key="value.id"
          ></Actividad>
      </div>
    </div>
  </div>
</template>

<script>
import Actividad from "./Actividad.vue";
import Loading from "./Loading.vue";
import ActividadesService from "../api/Actividades";
import { ref, onMounted } from "vue";

export default {
  name: "Index",
  components: {
    Actividad,
    Loading
  },
  setup(props,context){
    const actividades = ref([]);
    const loading = ref(true);

    onMounted(async() => {
        actividades.value = await ActividadesService.getActividades();
        loading.value = false;
    });
    return {
      actividades,
      loading
    }
  }
};
</script>