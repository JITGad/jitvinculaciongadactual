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

import "./assets/css/animate.min.css";
import "./assets/css/estilosactividades.css";
import "./assets/css/estiloscuentos.css";
import "./assets/css/estilosemparejar.css";
import "./assets/css/estilosinicio.css";
import "./assets/css/floatingbutton.css";
import "./assets/css/footers.css";
import "./assets/css/fuegosartificiales.css";
import "./assets/css/list.css";
import "./assets/css/list.css";
import "./assets/css/style.css";
import "./assets/css/styles.css";

import store from "./store/Store.js";

import MyForm from "./components/MyForm";
import MyInput from "./components/MyInput";

const app = createApp(App);

app.use(router);
app.use(store);

app.component("my-input", MyInput);
app.component("my-form", MyForm);

app.mount('#app');
