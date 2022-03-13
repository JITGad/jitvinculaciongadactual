import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import { Role } from "../util/Utilities.js";

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/About.vue')
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('../views/Dashboard.vue'),
    meta: { authorize: [] }
  },
  {
    path: '/list',
    name: 'List',
    component: () => import('../views/List.vue'),
    meta: { authorize: [] },
    children: [
      {
        path: 'actividades',
        name: 'Actividades',
        component: () => import('../views/Actividad/Actividades.vue'),
        meta: { authorize: [] },
      },
      {
        path: 'usuarios',
        name: 'Usuarios',
        component: () => import('../views/Usuario/Usuarios.vue'),
        meta: { authorize: [Role.Admin] },
      },
      {
        path: 'tipo-juegos',
        name: 'TipoJuegos',
        component: () => import('../views/TipoJuego/TipoJuegos.vue'),
        meta: { authorize: [] },
      },
      {
        path: 'colors',
        name: 'Colores',
        component: () => import('../views/Color/Colores.vue'),
        meta: { authorize: [] },
      },
      {
        path: 'juegos',
        name: 'Juegos',
        component: () => import('../views/Juego/Juegos.vue'),
        meta: { authorize: [] },
      },
    ]
  },
  {
    path: '/create',
    name: 'Create',
    component: () => import('../views/Create.vue'),
    meta: { authorize: [] },
    children: [
      {
        path: 'actividad',
        name: 'CrearActividad',
        component: () => import('../views/Actividad/CrearActividad.vue'),
        meta: { authorize: [] },
      },
      {
        path: 'usuario',
        name: 'CrearUsuario',
        component: () => import('../views/Usuario/CrearUsuario.vue'),
        meta: { authorize: [Role.Admin] },
      },
      {
        path: 'tipojuego',
        name: 'CrearTipoJuego',
        component: () => import('../views/TipoJuego/CrearTipoJuego.vue'),
        meta: { authorize: [] },
      },
      {
        path: 'color',
        name: 'CrearColor',
        component: () => import('../views/Color/CrearColor.vue'),
        meta: { authorize: [] },
      },
      {
        path: 'juego',
        name: 'CrearJuego',
        component: () => import('../views/Juego/CrearJuego.vue'),
        meta: { authorize: [] },
      },
    ]
  },
  {
    path: '/edit',
    name: 'Edit',
    component: () => import('../views/Edit.vue'),
    meta: { authorize: [] },
    children: [
      {
        path: 'actividad/:id',
        name: 'EditarActividad',
        component: () => import('../views/Actividad/EditarActividad.vue'),
        meta: { authorize: [] },
      },
      {
        path: 'usuario/:id',
        name: 'EditarUsuario',
        component: () => import('../views/Usuario/EditarUsuario.vue'),
        meta: { authorize: [Role.Admin] },
      },
      {
        path: 'tipo-juego/:id',
        name: 'EditarTipoJuego',
        component: () => import('../views/TipoJuego/EditarTipoJuego.vue'),
        meta: { authorize: [] },
      },
      {
        path: 'color/:id',
        name: 'EditarColor',
        component: () => import('../views/Color/EditarColor.vue'),
        meta: { authorize: [] },
      },
      {
        path: 'juego/:id',
        name: 'EditarJuego',
        component: () => import('../views/Juego/EditarJuego.vue'),
        meta: { authorize: [] },
      },
    ]
  },
  {
    path: '/jugar/:id/:nivel',
    name: 'JugarJuego',
    component: () => import('../views/Jugar.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/logout',
    name: 'Logout',
    component: () => import('../views/Logout.vue')
  },
  {
    path: '/juegos-por-actividad/:id',
    name: "JuegosPorActividad",
    component: () => import('../views/JuegosPorActividad.vue')
  },
  {
    path: '/niveles-Juego/:id',
    name: "NivelesJuego",
    component: () => import('../views/NivelesJuego.vue')
  },
  {
    path: '/instrucciones',
    name: "Instrucciones",
    component: () => import('../views/Instrucciones.vue')
  },
  // otherwise redirect to home
  { path: "/:catchAll(.*)", redirect: '/' }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  const { authorize } = to.meta;
  const isLoged = router.store.getters["auth/isLoged"];
  const isLoginValid = router.store.getters["auth/isLoginValid"];;

  if (to.name === 'Login') {
    if (isLoged && isLoginValid) {
      return next({ path: '/dashboard' });
    }
  }

  if (authorize) {
    if (!isLoged) {
      return next({ path: '/login', query: { returnUrl: to.path } });
    }

    if (!isLoginValid) {
      return next({ path: '/logout' });
    }

    // check if route is restricted by role
    if (authorize.length && !authorize.includes(router.store.state.auth.user.rol)) {
      // role not authorised so redirect to home page
      return next({ path: '/dashboard' });
    }
  }

  next();
})

export default router;
