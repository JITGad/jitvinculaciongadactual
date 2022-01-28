<template>
  <div class="mb-3">
    <label v-if="labelshow" class="form-label">{{ label }}</label>
    <select
      :class="classSelect"
      v-model="selected"
      @blur="blurEventHandler($event)"
    >
      <option value="0" selected hidden disabled>--{{ placeholder }}--</option>
      <my-option
        v-for="(option, index) in data"
        :value="option.id"
        :text="option.text"
        :actual="selected"
        :key="index"
      />
    </select>
    <div v-if="labelshow" v-show="error.state" class="validation-message">
      {{ error.message }}
    </div>
  </div>
</template>

<script>
import MyOption from "./MyOption.vue";
import * as Validate from "../util/ValidationTypes.js";
import {
  inject,
  getCurrentInstance,
  computed,
  onMounted,
  onBeforeUnmount,
  ref,
  reactive,
} from "vue";
export default {
  name: "MySelect",
  emits: ["update:modelValue"],
  inheritAttrs: false,
  components: {
    MyOption,
  },
  props: {
    placeholder: {
      type: String,
      default: "Seleccione",
    },
    modelValue: {
      type: [String, Number],
      default: "0",
      required: true,
    },
    type: {
      type: String,
      default: "string",
    },
    label: {
      type: String,
      default: "",
      required: true,
    },
    labelshow: {
      type: Boolean,
      default: true,
    },
    data: {
      type: Array,
      default: [],
      required: true,
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
    const selected = computed({
      get() {
        return props.modelValue ? props.modelValue : "0";
      },
      set(value) {
        modified.value = true;
        context.emit("update:modelValue", props.type == "int" ? parseInt(value) : value );
      },
    });
    const classSelect = computed(
      () =>
        `form-select ${modified.value ? "modified" : ""} ${
          error.state ? "invalid" : "valid"
        }`
    );

    const executevalidation = function (_value) {
      error.state = false;
      error.message = "";
      modified.value = true;
      let [_error, _error_msg] = Validate.initvalidate(
        _arrValidations,
        _value == "0" ? "" : _value
      );

      error.state = !_error;
      error.message = _error_msg;

      return !error.state;
    };

    onMounted(function () {
      form.bind({ validate, uid: instance.uid });
    });

    onBeforeUnmount(() => {
      form.unbind(instance.uid);
    });

    function validate() {
      return executevalidation(props.modelValue);
    }
    const blurEventHandler = function (e) {
      executevalidation(e.target.value);
    };
    return {
      selected,
      classSelect,
      blurEventHandler,
      error,
    };
  },
};
</script>