<template>
  <div class="mb-3">
    <label class="form-label">{{ label }}</label>
    <div style="display: flex">
      <input
        type="color"
        :title="placeholder"
        :class="classInputColor"
        v-model="model"
        @blur="blurEventHandler($event)"
      />
      <input
        type="text"
        :placeholder="placeholder"
        :class="classInputText"
        v-model="model"
        @blur="blurEventHandler($event)"
      />
    </div>
    <div v-show="help.length > 0" class="form-text">{{ help }}</div>
    <div v-show="error.state" class="validation-message">
      {{ error.message }}
    </div>
  </div>
</template>

<script>
import * as Validate from "../util/ValidationTypes.js";
import {
  myClassContainerInputType,
  myClassInputType,
} from "../util/Utilities.js";
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
  name: "MyInput",
  inheritAttrs: false,
  emits: ["update:modelValue"],
  props: {
    modelValue: {
      type: [String, Boolean],
      default: "",
      required: true,
    },
    label: {
      type: String,
      default: "",
      required: true,
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
    const form = inject("my-form");
    const instance = getCurrentInstance();
    const _arrValidations =
      props.validations.length > 0
        ? Validate.extractValidations(props.validations)
        : [];
    const error = reactive({
      message: "",
      state: false,
    });
    const modified = ref(false);
    const classInputColor = computed(
      () =>
        `form-control form-control-color ${modified.value ? "modified" : ""} ${
          error.state ? "invalid" : "valid"
        }`
    );
    const classInputText = computed(
      () =>
        `form-control ${modified.value ? "modified" : ""} ${
          error.state ? "invalid" : "valid"
        }`
    );
    const executevalidation = function (_value) {
      error.state = false;
      error.message = "";
      let [_error, _error_msg] = Validate.initvalidate(_arrValidations, _value);

      error.state = !_error;
      error.message = _error_msg;

      return !error.state;
    };

    const blurEventHandler = function (e) {
      executevalidation(e.target.value);
    };

    const model = computed({
      get() {
        return props.modelValue;
      },
      set(value) {
        modified.value = true;
        context.emit("update:modelValue", value);
      },
    });
    onMounted(function () {
      form.bind({ validate, uid: instance.uid });
    });
    onBeforeUnmount(() => {
      form.unbind(instance.uid);
    });
    function validate() {
      return executevalidation(props.modelValue);
    }
    return {
      blurEventHandler,
      error,
      classInputColor,
      classInputText,
      model,
    };
  },
};
</script>