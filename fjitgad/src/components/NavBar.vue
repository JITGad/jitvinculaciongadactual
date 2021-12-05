<template>
  <nav
    class="navbar navbar-static-top p-1"
    id="nav"
    role="navigation"
    style="
      background-color: #329163;
      box-shadow: 0px 1px 10px 0px rgba(0, 0, 0, 0.8);
    "
  >
    <div class="container">
      <div class="navbar-header"></div>
      <ul class="nav navbar-nav navbar-right">
        <router-link to="/">Inicio</router-link> |
        <router-link to="/about">A<span style="text-transform: lowercase">cerca de</span></router-link>
        <router-link to="/login">Iniciar<span style="text-transform: lowercase">session</span></router-link>
      </ul>
    </div>
    <!--/.container -->
  </nav>
</template>

<script>
export default {
  name: "NavBar",
};
</script>