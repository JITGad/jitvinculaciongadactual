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
      to="/list/actividades"
      icon="fas fa-border-none"
      text="Actividades"
    />
  </nav>
</template>

<script>
export default {
  name: "Sidebar",
};
</script>