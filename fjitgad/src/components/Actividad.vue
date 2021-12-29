<template>
  <div
    class="ctrtemas"
    @click="verActividad"
    onmouseover="this.style.background='#f2f0f0'"
    onmouseout="this.style.background=''"
  >
    <div class="tema">
      <img
        src="{{urlimagen}}"
        class="img-fluid"
        width="100"
        height="100"
      />════════<br /><span>{{ tema }}</span>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";

export default {
  name: "Actividad",
  props: {
    tema: {
      type: String,
      default: ""
    },
    urlimagen: {
      type: String,
      default: ""
    },
    key: {
      type: Number,
      default: 0
    }
  },
  setup(props, context) {
    const router = useRouter();
    const verActividad = function () {
      router.push("/dashboard");
    };

    return {
      verActividad,
    };
  },
};
</script>