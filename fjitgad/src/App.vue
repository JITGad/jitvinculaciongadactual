<template>
<NavBar></NavBar>
  <Index></Index>
  <Footer></Footer>
</template>

<script>
import "./assets/css/estilosinicio.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";
import "./assets/css/footers.css";

import Index from "./components/Index.vue";
import Footer from "./components/Footer.vue";
import NavBar from "./components/NavBar.vue";

export default {
  name: "App",
  components: {
    Index,
    Footer,
    NavBar
  },
};
</script>

<style>
body,
html {
  height: 100%;
  background-repeat: no-repeat;
  background: url(./assets/fondo2.png) no-repeat center center fixed;
  background-size: 100% 100%;
}
</style>