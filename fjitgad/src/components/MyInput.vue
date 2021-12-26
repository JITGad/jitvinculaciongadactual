<template>
  <div class="mb-3">
    <label class="form-label">{{ label }}</label>
    <input
      :type="type"
      :modelValue="modelValue"
      :placeholder="placeholder"
      :class="classInput"
      @input="onInput"
      @blur="blurEventHandler($event)"
    />
    <div v-if="help.length > 0" class="form-text">{{ help }}</div>
    <div v-if="error.state" class="validation-message">{{ error.message }}</div>
  </div>
</template>

<script>
import * as Validate from "../util/ValidationTypes.js";
import {
  onBeforeUnmount,
  onMounted,
  inject,
  getCurrentInstance,
  ref,
  reactive,
  computed,
} from "vue";

export default {
  inheritAttrs: false,
  props: {
    modelValue: {
      type: String,
      default: "",
    },
    type: {
      type: String,
      default: "text",
    },
    label: {
      type: String,
      default: "",
    },
    help: {
      type: String,
      default: "",
    },
    placeholder: {
      type: String,
      default: "",
    },
    validations: {
      type: String,
      default: "",
    },
  },
  setup(props, context) {
    let _arrValidations =
      props.validations.length > 0
        ? Validate.extractValidations(props.validations)
        : [];

    const error = reactive({
      message: "",
      state: false
    });
    const modified = ref(false);

    const iserror = computed(() => (error.value ? "invalid" : "valid"));
    const ismodified = computed(() => (modified.value ? "modified" : ""));
    const classInput = computed(() => `form-control ${ismodified} ${iserror}`);

    const validationsArray = reactive(_arrValidations);

    const executevalidation = function (_value) {
      error.state = false;
      error.message = "";
      if (_value.length > 0) {
        modified.value = true;
      }
      let [_error, _error_msg] = Validate.initvalidate(
        validationsArray,
        _value
      );

      error.state = !_error;
      error.message = _error_msg;

      return !error.state;
    };

    const blurEventHandler = function (e) {
      executevalidation(e.target.value);
    };

    const onInput = function (event) {
      context.emit("update:modelValue", event.target.value);
    };

    const form = inject("my-form");
    const uid = getCurrentInstance().uid;

    onMounted(function () {
      form.bind({ validate, uid });
    });

    onBeforeUnmount(() => {
      form.unbind(uid);
    });

    function validate() {
      return executevalidation(props.modelValue);
    }

    return {
      blurEventHandler,
      validate,
      onInput,
      error,
      classInput
    };
  },
};
</script>