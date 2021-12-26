<template>
  <form @submit.prevent="onSubmit">
    <slot></slot>
  </form>
</template>

<script>
import { reactive, provide } from "vue";

export default {
  name: "MyForm",
  emits: ["submit"],
  setup(props, { emit }) {
    const state = reactive({
      validateComponents: [],
    });
    provide("my-form", {
      bind,
      unbind,
    });

    function bind(component) {
      state.validateComponents.push(component);
    }
    function unbind(uid) {
      const index = state.validateComponents.findIndex((c) => c.uid === uid);
      if (index > -1) {
        state.validateComponents.splice(index, 1);
      }
    }

    function validate() {
      let valid = true;
      for (const component of state.validateComponents) {
        if (!component.validate()) {
          valid = false;
          break;
        }
      }
      return valid;
    }
    function onSubmit(evt) {
        evt.preventDefault();
      if (validate()) {
        emit("submit");
      }
    }

    return {
        onSubmit
    }
  },
};
</script>