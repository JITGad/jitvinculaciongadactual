<template>
  <div class="mb-3">
    <label v-if="labelshow" class="form-label">{{ label }}</label>
    <select
      ref="Model"
      class="form-control form-select select-menu-color-jquery"
    >
      <option value="0" data-hexcode="#ffffff" hidden disabled selected>
        Seleccione un color
      </option>
      <my-option-color
        v-for="(option, index) in data"
        :id="option.id"
        :value="option.value"
        :text="option.text"
        :actual="selected"
        :key="index"
        @mounted="MountedChilds"
      />
    </select>
    <div v-if="labelshow" v-show="error.state" class="validation-message">
      {{ error.message }}
    </div>
  </div>
</template>

<script>
import MyOptionColor from "./MyOptionColor.vue";
import * as Validate from "../util/ValidationTypes.js";
import {
  inject,
  getCurrentInstance,
  computed,
  onMounted,
  onBeforeUnmount,
  ref,
  reactive,
  nextTick,
} from "vue";
export default {
  name: "MySelectColor",
  emits: ["update:modelValue"],
  inheritAttrs: false,
  components: {
    MyOptionColor,
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
    const Model = ref(null);
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
        context.emit(
          "update:modelValue",
          props.type == "int" ? parseInt(value) : value
        );
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

    onMounted(async function () {
      form.bind({ validate, uid: instance.uid });
      $(Model.value).selectmenucolor({
          width: "100%",
          select: function (event, ui) {
            selected.value = ui.item.value;
          },
        });
        console.log(instance);
    });

    function MountedChilds() {
      if($(Model.value).selectmenucolor("instance")){
        $(Model.value).selectmenucolor("refresh");
      }
    }

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
      Model,
      MountedChilds,
    };
  },
};
</script>