<template>
  <a class="menu-toggle btn-link" @click="ToggleMenu"
    ><i class="fas fa-bars"></i
  ></a>
</template>

<script>
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

    return {
      ToggleMenu,
    };
  },
};
</script>