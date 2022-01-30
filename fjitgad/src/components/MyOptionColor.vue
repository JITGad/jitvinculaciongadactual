<template>
  <option v-if="IsActual" :value="id" :data-value="value" selected>
    {{ text }}
  </option>
  <option v-else :value="id" :data-value="value">
    {{ text }}
  </option>
</template>

<script>
import { computed } from "vue";
export default {
  name: "MyOptionColor",
  props: {
    value: {
      type: [String,Number],
      required: true,
    },
    text: {
      type: String,
      required: true,
    },
    id: {
        type: [String,Number],
      required: true,
    },
    actual: {
      type: [String,Number],
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