<template>
  <div class="col-sm-3 p-2">
    <div
      class="card"
      @click="Jugar"
      onmouseover="this.style.background='#f2f0f0'"
      onmouseout="this.style.background=''"
      style="cursor: pointer;"
    >
      <div class="card-body">
        <div class="col text-center">
          <img :src="MyImage" class="img-fluid" width="100" height="100" />
        </div>
        <hr
          style="width: 100%; height: 2px; color: gray; background-color: gray"
          class="m-1"
        />
        <hr
          style="width: 100%; height: 1px; color: gray; background-color: gray"
          class="m-1"
        />
        <h4 class="card-title my-3" style="text-align: center">
          {{ label }}
        </h4>
      </div>
    </div>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import { computed } from "vue";
import { setPathFile } from "../util/Utilities";

export default {
  name: "JuegoTipoJuegoActividad",
  props: {
    image: {
      type: String,
      default: "",
    },
    label: {
      type: String,
      default: "",
    },
    idgame: {
      type: Number,
      default: 0,
    },
    levels: {
      type: Number,
      default: 1,
    }
  },
  setup(props, context) {
    const Router = useRouter();
    const MyImage = computed(() => setPathFile(props.image));

    function Jugar() {
      if (props.levels > 1) {
        Router.push({ name: "NivelesJuego", params: { id: props.idgame } });
        return;
      }
      Router.push({ name: "JugarJuego", params: { id: props.idgame, nivel: 1 } });
    }
    return {
      MyImage,
      Jugar,
    };
  },
};
</script>