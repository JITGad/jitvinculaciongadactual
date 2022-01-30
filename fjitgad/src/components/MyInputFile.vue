<template>
  <div class="mb-3">
    <div class="row">
      <div class="col-8">
        <label v-if="labelshow" class="form-label">{{ label }}</label>
        <input
          v-if="!multiple"
          @change="changeFiles"
          :class="classInput"
          type="file"
          @blur="blurEventHandler"
          ref="fileInput"
          :accept="AcceptTypeInput"
        />
        <input
          v-else
          @change="changeFiles"
          :class="classInput"
          type="file"
          multiple
          @blur="blurEventHandler"
          ref="fileInput"
          :accept="AcceptTypeInput"
        />
      </div>
      <div class="col-4">
        <my-prev-file :type="type" v-model="filePrev" />
      </div>
    </div>
    <div v-if="labelshow" v-show="error.state" class="validation-message">
      {{ error.message }}
      <span v-show="loading" class="spinner-border spinner-border-sm"></span>
    </div>
  </div>
</template>
<script>
import * as Validate from "../util/ValidationTypes.js";
import { readFilesToBase64 } from "../util/Utilities.js";
import { acceptTypeInputFile } from "../util/Utilities.js";
import {
  onBeforeUnmount,
  onMounted,
  inject,
  getCurrentInstance,
  ref,
  reactive,
  computed,
  watch,
} from "vue";

export default {
  name: "MyInputFile",
  inheritAttrs: false,
  emits: ["update:modelValue"],
  props: {
    modelValue: {
      type: [Array, String, null],
      required: true,
    },
    label: {
      type: String,
      default: ""
    },
    labelshow: {
      type: Boolean,
      default: true,
    },
    validations: {
      type: String,
      default: "",
    },
    multiple: {
      type: Boolean,
      default: false,
    },
    type: {
      type: String,
      default: "image",
    },
  },
  setup(props, context) {
    const form = inject("my-form");
    const instance = getCurrentInstance();
    const filePrev = ref(null);
    const fileInput = ref(null);
    const _arrValidations =
      props.validations.length > 0
        ? Validate.extractValidations(props.validations)
        : [];
    const AcceptTypeInput = computed(() => acceptTypeInputFile(props.type));
    const error = reactive({
      message: "",
      state: false,
    });
    const modified = ref(false);
    const loading = ref(false);
    const classInput = computed(
      () =>
        `form-control ${modified.value ? "modified" : ""} ${
          error.state ? "invalid" : "valid"
        }`
    );
    watch(
      () => props.modelValue,
      (value, prevValue) => {
        if (value == null || value.length == 0) {
          fileInput.value.value = "";
          filePrev.value = "";
          return;
        }
        filePrev.value = value;
      }
    );
    const executevalidation = function (_value) {
      if (loading.value) {
        error.state = true;
        error.message = "Procesando archivo";
        return;
      }
      error.state = false;
      error.message = "";
      if (_value != undefined && _value != null && _value.length > 0) {
        modified.value = true;
      }
      let [_error, _error_msg] = Validate.initvalidate(_arrValidations, _value);
      error.state = !_error;
      error.message = _error_msg;
      return !error.state;
    };
    const blurEventHandler = function (e) {
      executevalidation(e.target.files);
    };
    async function changeFiles(event) {
      loading.value = true;
      const archivos = event.target.files;
      if (!archivos || !archivos.length) {
        filePrev.value = "";
        return;
      }
      const primerArchivo = archivos[0];
      const objectURL = URL.createObjectURL(primerArchivo);
      filePrev.value = objectURL;
      const fileBase64 = await readFilesToBase64(
        props.multiple ? archivos : primerArchivo,
        props.multiple
      );
      context.emit("update:modelValue", fileBase64);
      loading.value = false;
    }
    onMounted(function () {
      filePrev.value = props.modelValue;
      form.bind({ validate, uid: instance.uid });
    });
    onBeforeUnmount(() => {
      form.unbind(instance.uid);
    });
    function validate() {
      return executevalidation(props.modelValue);
    }
    return {
      changeFiles,
      classInput,
      blurEventHandler,
      filePrev,
      error,
      loading,
      fileInput,
      AcceptTypeInput,
    };
  },
};
</script>