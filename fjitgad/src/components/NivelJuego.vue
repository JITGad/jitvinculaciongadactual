<template>
  <div class="col-sm-3 p-2">
    <div
      class="card"
      @click="Jugar"
      onmouseover="this.style.background='#f2f0f0'"
      onmouseout="this.style.background=''"
      style="cursor: pointer"
    >
      <div class="card-body">
        <div class="col text-center">
          <img :src="GetRamdomImage" class="img-fluid" width="100" height="100" />
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
          Nivel {{ level }}
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
  name: "NivelJuego",
  props: {
    game: {
        type: Object,
        required: true,
    },
    level: {
      type: Number,
      default: 1,
    },
  },
  setup(props, context) {
    const Router = useRouter();
    function Jugar() {
      Router.push({
        name: "JugarJuego",
        params: { id: props.game.idgame, nivel: props.level },
      });
    }

    const GetRamdomImage = computed(() => {
      return setPathFile(props.game.image);
    });

    return {
      Jugar,
      GetRamdomImage,
    };
  },
};
</script>