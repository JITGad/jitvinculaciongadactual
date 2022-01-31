<template>
  <img v-if="type == 'image'" ref="filePrev" class="responsive-image" />
  <audio v-if="type == 'audio'" ref="filePrev" controls="controls">
    <source src="" type="audio/*" />
  </audio>
  <video v-if="type == 'video'" style="width: 100%;" ref="filePrev" controls="controls">
    <source src="" type="video/*" />
    Your browser does not support HTML5 video.
  </video>
</template>

<script>
import { ref, watch, onMounted } from "vue";
import { isBase64 } from "../util/Utilities.js";

export default {
  name: "MyPrevFile",
  inheritAttrs: false,
  props: {
    modelValue: {
      type: [String, null],
      required: true,
    },
    type: {
      type: String,
      required: true,
    },
  },
  setup(props, context) {
    const filePrev = ref(null);

    watch(
      () => props.modelValue,
      (value, prevValue) => {
        setPathFile(value);
      }
    );

    onMounted(function () {
      setPathFile(props.modelValue);
    });

    function setPathFile(_value) {
      if (_value == null || _value.length == 0) {
        filePrev.value.src = "";
        return;
      }
      if (isBase64(_value)) {
        filePrev.value.src = _value;
        return;
      }

      filePrev.value.src = `${process.env.VUE_APP_BASE_URL}${_value}`;
    }

    return {
      filePrev,
    };
  },
};
</script>