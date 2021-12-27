import AuthService from '../api/Usuarios.js';
import jwt_decode from "jwt-decode";

const user = JSON.parse(localStorage.getItem('user'));
const initialState = user
  ? { payload: jwt_decode(user.user_token), status: { loggedIn: true }, user }
  : { payload: null, status: { loggedIn: false }, user: null };

export const auth = {
  namespaced: true,
  state: initialState,
  actions: {
    login({ commit }, user) {
      return AuthService.login(user)
        .then(user => {
          commit('loginSuccess', user);
          return Promise.resolve(user);
        },
          reason => {
            commit('loginFailure');
            return Promise.reject(reason);
          });
    },
    logout({ commit }) {
      AuthService.logout();
      commit('logout');
    }
  },
  mutations: {
    loginSuccess(state, user) {
      state.status.loggedIn = true;
      state.user = user;
      state.payload = jwt_decode(user.user_token);
    },
    loginFailure(state) {
      state.status.loggedIn = false;
      state.user = null;
      state.payload = null
    },
    logout(state) {
      state.status.loggedIn = false;
      state.user = null;
      state.payload = null
    }
  },
  getters: {
    isLoginValid: state => {
      if (state.payload != null && state.payload.hasOwnProperty("exp")) {
        if (state.payload.exp > new Date() / 1000) {
          return true;
        }
      }
      return false;
    },
    isLoged: state => {
      return state.status.loggedIn
    }
  }
};