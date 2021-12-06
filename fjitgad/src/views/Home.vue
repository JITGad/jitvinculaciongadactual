<template>
  <div>
    <NavBar></NavBar>
    <Index></Index>
  </div>
</template>

<script>
// @ is an alias to /src
import Index from "@/components/Index.vue";
import NavBar from "@/components/NavBar.vue";

export default {
  name: "Home",
  components: {
    Index,
    NavBar,
  },
};
</script>
