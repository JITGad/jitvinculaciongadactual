import { encodeQueryString } from "../util/Utilities";
import FetchMaster from "./FetchMaster";

class AuthService {
    #urlApi = "users"

    getUsuario(id = 0) {
        return new Promise((resolve) => {
          FetchMaster.get(`${this.#urlApi}/getUsersAdminbyid${encodeQueryString({ 'id': id })}`,
            (data) => resolve(data), true);
        });
      }

    getUsuariosAdministrador(page = 1) {
        return new Promise((resolve) => {
            FetchMaster.get(`${this.#urlApi}/getUsersAdmin${encodeQueryString({ 'page': page })}`,
                (response) => resolve(response), true, true);
        });
    }

    postUsuario(usuario) {
        return new Promise((resolve) => {
            FetchMaster.post(`${this.#urlApi}/PostUserRegistration`, usuario, (data) => resolve(data), undefined, true);
        });
    }

    putUsuario(Usuario) {
        return new Promise((resolve) => {
            FetchMaster.put(`${this.#urlApi}/putuser`,
                Usuario, (data) => resolve(data), undefined, true);
        });
    }

    deleteUsuario(iduser = 0) {
        return new Promise(async (resolve) => {
            await FetchMaster.delete(`${this.#urlApi}/deleteuser`, { 'iduser': iduser }, (data) => resolve(data));
        });
    }

    login(user) {
        return new Promise((resolve, reject) => {
            FetchMaster.post(`${this.#urlApi}/logIn`, user, (response) => {
                if (response.status.error) {
                    reject(response.status.message);
                    return;
                }

                if (!response.status.error && response.data.user_token) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                resolve(response.data);
            });
        });
    }

    logout() {
        localStorage.removeItem('user');
    }
}

export default new AuthService();