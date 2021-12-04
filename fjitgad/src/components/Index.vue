<template>
  <div>
    <div class="temas" id="divtemas">
      <div
        class="row"
        id="divcargando"
        name="divcargando"
        v-if="actividades.length == 0"
      >
        <div class="row">
          <div class="col">
            <div class="mx-auto w-75 px-5 text-white text-center">
              <img src="../assets/spinner.gif" width="400" class="img-fluid" />
              <br />
            </div>
          </div>
        </div>
      </div>
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

export default {
  name: "Index",
  components: {
    Actividad,
  },
  data() {
    return {
      actividades: [],
      error: ""
    };
  },
  mounted() {
    fetch("https://api.openbrewerydb.org/breweries/", {
      headers: { "Content-type": "application/json" },
    })
      .then((res) => res.json())
      .then((response) => {
        this.actividades = response;
      })
      .catch((error) => {
        this.error = error;
      });
  },
};
</script>