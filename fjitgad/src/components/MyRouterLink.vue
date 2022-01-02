<template>
  <router-link :to="to" :class="ClassRoute">
    <i :class="icon"></i>
    {{ text }}
  </router-link>
</template>

<script>
import { useRoute } from "vue-router";
import { computed } from "vue";
export default {
  name: "MyRouterLink",
  props: {
    to: {
      type: String,
      required: true,
    },
    text: {
      type: String,
      required: true,
    },
    icon: {
      type: String,
      default: "",
    },
  },
  setup(props, context) {
    const Route = useRoute();
    const ClassRoute = computed(() => {
      return `dashboard-nav-item dashboard-nav-link ${
        Route.path == props.to ? "active" : ""
      }`;
    });
    return {
      ClassRoute,
    };
  },
};
</script>