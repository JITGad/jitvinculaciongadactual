import FetchMaster from "./FetchMaster.js";
import { encodeQueryString } from "../util/Utilities.js";

class JuegosService {

  #urlApi = "webresources/activitiestype"

  getJuego(gameid = 0) {
    return new Promise((resolve) => {
      FetchMaster.get(`${this.#urlApi}/get${encodeQueryString({ 'gameid': gameid })}`,
        (data) => resolve(data), true);
    });
  }

  getJuegosAdministrador(page = 1) {
    return new Promise((resolve) => {
      FetchMaster.get(`${this.#urlApi}/getAdmin${encodeQueryString({ 'page': page })}`,
        (data) => resolve(data), true, true)
    });
  }

  postJuego(Juego) {
    return new Promise((resolve) => {
      FetchMaster.post(`${this.#urlApi}/postType`, Juego, (data) => resolve(data), undefined, true);
    });
  }

  putJuego(Juego) {
    return new Promise((resolve) => {
      FetchMaster.put(`${this.#urlApi}/PutType`,
        Juego, (data) => resolve(data), undefined, true);
    });
  }

  deleteJuego(idjuego = 0) {
    return new Promise(async (resolve) => {
      await FetchMaster.delete(`${this.#urlApi}/DeleteType`, { 'idgame': idjuego }, (data) => resolve(data));
    });
  }
}


export default new JuegosService(); 