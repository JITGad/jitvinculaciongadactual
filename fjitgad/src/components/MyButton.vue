<template>
  <button
    class="w-100 btn btn-lg btn-primary"
    type="submit"
    :disabled="loading"
  >
    <span v-show="loading" class="spinner-border spinner-border-sm"></span>
    <span>{{label}}</span>
  </button>
</template>

<script>
export default {
  name: "MyButton",
  props: {
    loading: {
      type: Boolean,
      default: false,
    },
    label: {
      type: String,
      default: "Guardar",
    },
  },
  setup(props, context) {},
};
</script>