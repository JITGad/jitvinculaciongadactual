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
import "jquery-ui";
import "jquery-ui/ui/widgets/selectmenu.js";
import "jquery-ui/themes/base/selectmenu.css";
import "jquery-confirm/dist/jquery-confirm.min.css";
import "./util/ExtendJquery.js";

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

import MyForm from "./components/MyForm.vue";
import MyInput from "./components/MyInput.vue";
import MyInputColor from "./components/MyInputColor.vue";
import MyInputFile from "./components/MyInputFile.vue";
import MyPrevFile from "./components/MyPrevFile.vue";
import MySelect from "./components/MySelect.vue";
import MySelectBoolean from "./components/MySelectBoolean.vue";
import MyButton from "./components/MyButton.vue";
import MyButtonToggleMenu from "./components/MyButtonToggleMenu.vue";
import MyRouterLink from "./components/MyRouterLink.vue";
import MyLinkTable from "./components/MyLinkTable.vue";
import MainLayoutAdmin from "./components/MainLayoutAdmin.vue";
import MainLayoutJuego from "./components/MainLayoutJuego.vue";
import MyFooter from "./components/MyFooter.vue";
import Paginacion from "./components/Paginacion.vue";
import MyAutorization from "./components/MyAutorization.vue";

const app = createApp(App);

app.use(store);
app.use(router);
router.store = store;

app.component("my-form", MyForm);
app.component("my-input", MyInput);
app.component("my-input-color", MyInputColor);
app.component("my-input-file", MyInputFile);
app.component("my-prev-file", MyPrevFile);
app.component("my-button", MyButton);
app.component("my-button-toggle-menu", MyButtonToggleMenu);
app.component("my-router-link", MyRouterLink);
app.component("my-link-table", MyLinkTable);
app.component("my-select", MySelect);
app.component("my-select-boolean", MySelectBoolean);
app.component("main-layout-admin", MainLayoutAdmin);
app.component("main-layout-juego", MainLayoutJuego);
app.component("my-footer", MyFooter);
app.component("my-paginacion", Paginacion);
app.component("my-autorization", MyAutorization);

app.mount('#app');
