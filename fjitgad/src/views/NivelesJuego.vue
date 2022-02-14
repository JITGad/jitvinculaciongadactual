<template>
  <main-layout-juego :backgroundBlanc="true">
    <my-loading v-if="Loading" />
    <div v-else class="container" style="margin-top: 10vh">
      <encabezado-juego :routes="routes" />
      <template v-for="(tipojuego, i) in JuegosPorActividad" :key="i">
      </template>
    </div>
  </main-layout-juego>
</template>

<script>
export default {
    name: "NivelesJuego",
    setup(props, context) {
        
    },
}
</script>