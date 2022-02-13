<template>
  <div class="page-header-juego">
    <button type="button" @click="IrInicio" class="btn btn-info btn-lg">
      <i class="fa fa-home" aria-hidden="true">&nbsp;</i>
    </button>
    <template v-for="(value, index) in routes" :key="index">
      <span class="h4 m-0" style="font-family: Times New Roman, Times, serif">
        {{ value }}
      </span>
      <template v-if="index < routes.length"> / </template>
    </template>
    <hr
      style="width: 100%; height: 2px; color: black; background-color: black"
      class="m-1"
    />
    <hr
      style="width: 100%; height: 1px; color: gray; background-color: black"
      class="m-1"
    />
    <a @click="HandleClickBack" class="float" style="cursor: pointer;">
      <i class="fas fa-arrow-left my-float"></i>
    </a>
  </div>
</template>

<script>
import { useRouter } from "vue-router";

export default {
  name: "EncabezadoJuego",
  props: {
    routes: {
      type: Array,
      required: true,
    },
  },
  setup(props, context) {
    const router = useRouter();
    const HandleClickBack = () => {
      router.go(-1);
    };
    function IrInicio() {
      router.push("/");
    }
    return {
      HandleClickBack,
      IrInicio,
    };
  },
};
</script>