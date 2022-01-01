<template>
  <header>
    <a class="menu-toggle btn-link"><i class="fas fa-bars"></i></a>
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
    <router-link
      to="/dashboard"
      class="dashboard-nav-item dashboard-nav-link active"
    >
      <i class="fas fa-home"></i>
      Inicio
    </router-link>
    <router-link
      class="dashboard-nav-item dashboard-nav-link"
      to="/actividades"
    >
      <i class="fas fa-user-alt"></i> Actividades
    </router-link>
  </nav>
</template>

<script>
export default {
    name: 'Sidebar'
}
</script>