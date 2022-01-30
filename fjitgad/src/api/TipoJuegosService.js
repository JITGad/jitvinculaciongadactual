import FetchMaster from "./FetchMaster.js";
import { encodeQueryString } from "../util/Utilities.js";
import ObjectSelect from "../util/ObjectSelect.js";

class TipoJuegosService {

    #urlApi = "webresources/gametype"

    getTipoJuego(idtipo = 0) {
        return new Promise((resolve) => {
            FetchMaster.get(`${this.#urlApi}/getgametypebyid${encodeQueryString({ 'idgametype': idtipo })}`,
                (data) => resolve(data), true);
        });
    }

    getTipoJuegosSelectMenu() {
        return new Promise((resolve) => {
            FetchMaster.get(`${this.#urlApi}/getgametypecv`, (data) => {
                const result = [];
                if (Array.isArray(data.data)) {
                    for (const tipojuego of data.data) {
                        result.push(new ObjectSelect(tipojuego.id, tipojuego.text, tipojuego.value));
                    }
                }
                resolve(result);
            }, true, false);
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