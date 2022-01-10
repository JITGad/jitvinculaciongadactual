<template>
  <header>
    <my-button-toggle-menu />
    <router-link class="brand-logo" to="/">
      <img
        class="brand-image"
        width="30"
        height="24"
        src="../assets/image/escudouteq.png"
      />
      <span>JIT</span>
    </router-link>
  </header>
  <nav class="dashboard-nav-list">
    <my-router-link to="/dashboard" icon="fas fa-home" text="Inicio" />
    <my-autorization roles="Administrador">
      <my-router-link
        to="/list/usuarios"
        icon="fas fa-user-alt"
        text="Usuarios"
      />
    </my-autorization>
    <my-router-link
        to="/list/colors"
        icon="fas fa-palette"
        text="Colores"
      />
    <my-router-link
        to="/list/tipo-juegos"
        icon="fas fa-layer-group"
        text="Tipos de juegos"
      />
    <my-router-link
      to="/list/actividades"
      icon="fas fa-border-none"
      text="Actividades"
    />
    <my-router-link
      to="/list/juegos"
      icon="fas fa-vr-cardboard"
      text="Juegos"
    />
  </nav>
</template>

<script>
export default {
  name: "Sidebar",
};
</script>