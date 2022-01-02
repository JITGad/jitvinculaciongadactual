<template>
  <button :class="class" :type="type" @click="handleClick" :disabled="loading">
    <span v-show="loading" class="spinner-border spinner-border-sm"></span>
    <span>{{ label }}</span>
  </button>
</template>

<script>
export default {
  name: "MyButton",
  emits: ["click"],
  props: {
    loading: {
      type: Boolean,
      default: false,
    },
    label: {
      type: String,
      default: "Guardar",
    },
    class: {
      type: String,
      required: true,
    },
    type: {
      type: String,
      default: "submit",
    },
  },
  setup(props, context) {
    const handleClick = () => {
      context.emit("click");
    };

    return {
      handleClick,
    };
  },
};
</script>