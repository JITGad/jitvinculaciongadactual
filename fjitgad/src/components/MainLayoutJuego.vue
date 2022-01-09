<template>
  <div class="dashboard">
    <NavBar />
    <slot></slot>
    <my-footer />
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";

export default {
  name: "MainLayoutJuego",
  components: {
    NavBar,
  },
  props: {
    backgroundBlanc: {
      type: Boolean,
      default: false,
    },
  },
  setup(props, context) {
    if (props.backgroundBlanc) {
      document.body.style.backgroundColor = "#f8f9fa";
      document.body.style.background = "";
    } else {
      document.body.style.height = "100%";
      document.body.style.backgroundRepeat = "no-repeat";
      document.body.style.background = `url(${require("../assets/image/fondo2.png")}) no-repeat center center fixed`;
      document.body.style.backgroundSize = "100% 100%";
    }
  },
};
</script>