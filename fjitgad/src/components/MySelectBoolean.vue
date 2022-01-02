<template>
  <div class="mb-3">
    <label class="form-label">{{ label }}</label>
    <select :class="classSelect" v-model="selected">
      <option value="1">{{ messageTrue }}</option>
      <option value="0">{{ messageFalse }}</option>
    </select>
  </div>
</template>

<script>
import {
  inject,
  getCurrentInstance,
  computed,
  onMounted,
  onBeforeUnmount,
  ref,
} from "vue";
export default {
  name: "MySelectBoolean",
  emits: ["update:modelValue"],
  inheritAttrs: false,
  props: {
    messageTrue: {
      type: String,
      default: "Activo",
    },
    messageFalse: {
      type: String,
      default: "Inactivo",
    },
    modelValue: {
      type: Boolean,
      default: true,
      required: true,
    },
    label: {
      type: String,
      default: "",
      required: true,
    },
  },
  setup(props, context) {
    const form = inject("my-form");
    const uid = getCurrentInstance().uid;
    const modified = ref(false);
    const selected = computed({
      get() {
        return props.modelValue ? "1" : "0";
      },
      set(value) {
        modified.value = true;
        context.emit("update:modelValue", value == "1" ? true : false);
      },
    });
    const classSelect = computed(() => `form-select ${modified.value ? "modified valid" : ""}`);

    onMounted(function () {
      form.bind({ validate, uid });
    });

    onBeforeUnmount(() => {
      form.unbind(uid);
    });

    function validate() {
      return true;
    }

    return {
      selected,
      classSelect,
    };
  },
};
</script>