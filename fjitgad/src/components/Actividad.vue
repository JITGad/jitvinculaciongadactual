<template>
  <div
    class="ctrtemas"
    onclick="location.href='Actividades.jsp?id=21';"
    onmouseover="this.style.background='#f2f0f0'"
    onmouseout="this.style.background=''"
  >
    <div class="tema">
      <img
        src="{{urlimagen}}"
        class="img-fluid"
        width="100"
        height="100"
      />════════<br /><span id="titulostemas">{{tema}}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "Actividad",
  props: {
    tema: String,
    urlimagen: String
  }
};
</script>