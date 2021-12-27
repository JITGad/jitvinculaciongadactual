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
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('../views/Dashboard.vue'),
    meta: { authorize: [] }
  },
  {
    path: '/play-activity/:id',
    name: 'MenuActividad',
    component: () => import('../views/MenuActividad.vue')
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
