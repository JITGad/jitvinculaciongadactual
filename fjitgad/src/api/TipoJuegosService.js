import FetchMaster from "./FetchMaster.js";
import { encodeQueryString } from "../util/Utilities.js";

class TipoJuegosService {

    #urlApi = "gametype"

    getTipoJuego(idtipo = 0) {
        return new Promise((resolve) => {
            FetchMaster.get(`${this.#urlApi}/getgametypeAdminbyid${encodeQueryString({ 'id': idtipo })}`,
                (data) => resolve(data), true);
        });
    }

    getTipoJuegosJuegos() {
        return new Promise((resolve) => {
            FetchMaster.get(this.#urlApi, (data) => resolve(data), true, true);
        });
    }

    getTipoJuegosAdministrador(page = 1) {
        return new Promise((resolve) => {
            FetchMaster.get(`${this.#urlApi}/getgametypeAdmin${encodeQueryString({ 'page': page })}`,
                (data) => resolve(data), true, true)
        });
    }

    postTipoJuego(TipoJuego) {
        return new Promise((resolve) => {
            FetchMaster.post(`${this.#urlApi}/Postgametype`, TipoJuego, (data) => resolve(data),
                undefined, true);
        });
    }

    putTipoJuego(TipoJuego) {
        return new Promise((resolve) => {
            FetchMaster.put(`${this.#urlApi}/Putgametype`,
                TipoJuego, (data) => resolve(data), undefined, true);
        });
    }

    deleteTipoJuego(idgametype = 0) {
        return new Promise(async (resolve) => {
            await FetchMaster.delete(`${this.#urlApi}/Deletegametype`, { 'idgametype': idgametype }, (data) => resolve(data));
        });
    }
}


export default new TipoJuegosService(); 