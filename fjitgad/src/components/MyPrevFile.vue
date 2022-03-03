<template>
  <img v-if="type == 'image'" ref="filePrev" class="responsive-image" />
  <audio  style="width: 100%;" v-if="type == 'audio'" ref="filePrev" controls="controls"
  :controlsList="download ? '': 'nodownload'">
    <source src="" type="audio/*" />
  </audio>
  <video v-if="type == 'video'" style="width: 100%;" ref="filePrev" controls="controls">
    <source src="" type="video/*" />
    Your browser does not support HTML5 video.
  </video>
</template>

<script>
import { ref, watch, onMounted } from "vue";
import { setPathFile } from "../util/Utilities.js";

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
    download: {
      type: Boolean,
      default: true
    }
  },
  setup(props, context) {
    const filePrev = ref(null);

    watch(
      () => props.modelValue,
      (value, prevValue) => {
        filePrev.value.src = setPathFile(value);
      }
    );

    onMounted(function () {
      filePrev.value.src = setPathFile(props.modelValue);
    });

    return {
      filePrev,
    };
  },
};
</script>