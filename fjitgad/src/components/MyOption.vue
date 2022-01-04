<template>
  <option v-if="IsActual" :value="value">
    {{ text }}
  </option>
  <option v-else :value="value">
    {{ text }}
  </option>
</template>

<script>
import { computed } from "vue";
export default {
  name: "MyOption",
  props: {
    value: {
      type: String,
      required: true,
    },
    text: {
      type: String,
      required: true,
    },
    actual: {
      type: String,
      required: true,
    },
  },
  setup(props, context) {
    const IsActual = computed(() => {
      props.value == props.actual ? true : false;
    });

    return {
        IsActual
    }
  },
};
</script>