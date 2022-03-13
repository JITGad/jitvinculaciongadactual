<template>
  <main-layout-admin>
    <video
      :src="require('../assets/ADMINISTRACIÓN _JIT.mp4')"
      style="width: 100%"
      controls="controls"
    >
      <source src="" type="video/*" />
      Your browser does not support HTML5 video.
    </video>
  </main-layout-admin>
</template>

<script>
export default {
  name: "Instrucciones",
  setup(props, context) {},
};
</script>