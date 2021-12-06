<template>
  <nav
    class="navbar fixed-top navbar-dark bg-primary p-1"
    id="nav"
    role="navigation"
  >
    <div class="ms-auto" style="display: flex">
      <router-link class="nav-link" style="color: black" to="/"
        >Inicio</router-link
      >
      <router-link class="nav-link" style="color: black" to="/about">
        A<span style="text-transform: lowercase">cerca de</span>
      </router-link>
      <router-link class="nav-link" style="color: black" to="/login">
        Iniciar<span style="text-transform: lowercase"> session</span>
      </router-link>
    </div>
    <!--/.container -->
  </nav>
</template>

<script>
export default {
  name: "NavBar",
};
</script>