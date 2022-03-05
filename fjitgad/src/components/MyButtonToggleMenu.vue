<template>
  <a class="menu-toggle btn-link" @click="ToggleMenu"
    ><i class="fas fa-bars"></i
  ></a>
</template>

<script>
import { nextTick, onMounted } from "vue";
export default {
  name: "MyButtonToggleMenu",
  setup(props, context) {
    const mobileScreen = window.matchMedia("(max-width: 990px )");
    const ToggleMenu = () => {
      if (mobileScreen.matches) {
        $(".dashboard-nav").toggleClass("mobile-show");
      } else {
        $(".dashboard").toggleClass("dashboard-compact");
      }
    };
    onMounted(async () => {
      await nextTick();
      ToggleMenu();
    });
    return {
      ToggleMenu,
    };
  },
};
</script>