<template>
  <div :class="classContainer">
    <label class="form-label" v-show="type != 'checkbox'">{{ label }}</label>
    <input
      :type="type"
      :value="modelValue"
      :placeholder="placeholder"
      :class="classInput"
      v-model="model"
      @blur="blurEventHandler($event)"
    />
    <label class="form-check-label" v-if="type == 'checkbox'">{{
      label
    }}</label>
    <div v-show="help.length > 0" class="form-text">{{ help }}</div>
    <div v-show="error.state" class="validation-message">{{ error.message }}</div>
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
    type: {
      type: String,
      default: "text",
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
    let _arrValidations =
      props.validations.length > 0
        ? Validate.extractValidations(props.validations)
        : [];

    const error = reactive({
      message: "",
      state: false,
    });
    const modified = ref(false);

    const iserror = computed(() => (error.value ? "invalid" : "valid"));
    const ismodified = computed(() => (modified.value ? "modified" : ""));
    const classInput = computed(
      () =>
        `${myClassInputType(props.type)} ${ismodified.value} ${iserror.value}`
    );
    const classContainer = computed(() =>
      myClassContainerInputType(props.type)
    );
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

    const model = computed({
      get() {
        return this.modelValue;
      },
      set(value) {
        context.emit("update:modelValue", value);
      },
    });

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
      error,
      classInput,
      classContainer,
      model,
    };
  },
};
</script>