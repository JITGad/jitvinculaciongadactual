<template>
  <div>
    <NavBar></NavBar>
    <Index></Index>
    <Footer></Footer>
  </div>
</template>

<script>
// @ is an alias to /src
import Index from "@/components/Index.vue";
import Footer from "@/components/Footer.vue";
import NavBar from "@/components/NavBar.vue";

export default {
  name: "Login",
  components: {
    Index,
    Footer,
    NavBar,
  },
};
</script>
