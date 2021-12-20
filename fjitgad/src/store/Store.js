import { createStore } from "vuex";
import {auth} from "../store/Store.Auth";

const store = createStore({
  modules: {
    auth,
  },
});

export default store;