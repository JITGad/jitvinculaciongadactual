<template>
  <div class="page-header-juego">
    <button
      type="button"
      onclick="location.href='index.jsp'"
      class="btn btn-info btn-lg"
    >
      <i class="fa fa-home" aria-hidden="true">&nbsp;</i>
    </button>
    <span class="h4" id="spanNombreTema" name="spanNombreTema"
      >Espacialidad</span
    ><span class="h4 m-0" style="font-family: Times New Roman, Times, serif">
      / Actividades</span
    >
    <span class="h4 m-2" style="font-family: Times New Roman, Times, serif"
      >/ Rompecabeza:</span
    ><span class="h4" id="spanNombreActividad" name="spanNombreActividad"
      >Derecha</span
    >
    <hr
      style="width: 100%; height: 2px; color: black; background-color: black"
      class="m-1"
    />
    <hr
      style="width: 100%; height: 1px; color: gray; background-color: black"
      class="m-1"
    />
    <a @click="HandleClickBack" class="float">
      <i class="fas fa-arrow-left my-float"></i>
    </a>
  </div>
</template>

<script>
import { useRouter } from "vue-router";

export default {
  name: "EncabezadoJuego",
  setup(props, context) {
    const router = useRouter();
    const HandleClickBack = () => {
      router.go(-1);
    };

    return {
      HandleClickBack,
    };
  },
};
</script>