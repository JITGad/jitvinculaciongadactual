import { createApp } from 'vue';
import App from './App.vue';
import store from "./store/Store.js";
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
import "./assets/css/NavMenu.css";

import MyForm from "./components/MyForm";
import MyInput from "./components/MyInput";
import MyButton from "./components/MyButton";
import MainLayout from "./components/MainLayout";
import Paginacion from "./components/Paginacion";
import MyLinkTable from "./components/MyLinkTable";
import MyRouterLink from "./components/MyRouterLink";
import MyButtonToggleMenu from "./components/MyButtonToggleMenu";
import MySelectBoolean from "./components/MySelectBoolean";
import MyInputFile from "./components/MyInputFile";
import MySelect from "./components/MySelect";
import MyAutorization from "./components/MyAutorization";

const app = createApp(App);

app.use(store);
app.use(router);
router.store = store;

app.component("my-input", MyInput);
app.component("my-form", MyForm);
app.component("my-button", MyButton);
app.component("main-layout", MainLayout);
app.component("my-paginacion", Paginacion);
app.component("my-link-table", MyLinkTable);
app.component("my-router-link", MyRouterLink);
app.component("my-button-toggle-menu", MyButtonToggleMenu);
app.component("my-select-boolean", MySelectBoolean);
app.component("my-input-file", MyInputFile);
app.component("my-select", MySelect);
app.component("my-autorization", MyAutorization);

app.mount('#app');
