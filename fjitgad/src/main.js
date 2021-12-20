import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

import '@fortawesome/fontawesome-free/css/all.css';
import '@fortawesome/fontawesome-free/js/all.js';

import "jquery";
import "jquery-confirm";
import "jquery-confirm/dist/jquery-confirm.min.css";

import "./assets/css/estilosinicio.css";
import "./assets/css/footers.css";

const app = createApp(App);

app.use(router);

app.mount('#app');
