<template>
  <div>
    <div class="temas">
      <Loading v-if="actividades.length == 0"/>
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
import { ref } from "vue";

export default {
  name: "Index",
  components: {
    Actividad,
    Loading
  },
  async setup(props,context){
    const actividades = ref([]);
    actividades.value = await ActividadesService.getActividades();  

    return {
      actividades
    }
  }
};
</script>