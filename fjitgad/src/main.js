import {createApp} from 'vue'
import {createRouter, createWebHashHistory} from "vue-router";
import App from './App.vue'
import Dashboard from './Dashboard.vue'
import AcercaDe from "./AcercaDe.vue"

const routes = [
    { path: '/', component: App },
    { path: '/about', component: AcercaDe },
    { path: '/dashboard', component: Dashboard },
  ]

  const router = createRouter({
    // 4. Provide the history implementation to use. We are using the hash history for simplicity here.
    history: createWebHashHistory(),
    routes, // short for `routes: routes`
  })

  const app = createApp({})
// Make sure to _use_ the router instance to make the
// whole app router-aware.
app.use(router)

app.mount('#app')
