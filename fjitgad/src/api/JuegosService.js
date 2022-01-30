import FetchMaster from "./FetchMaster.js";
import { encodeQueryString } from "../util/Utilities.js";

class JuegosService {

  #urlApi = "webresources/game"

  getJuego(gameid = 0) {
    return new Promise((resolve) => {
      FetchMaster.get(`${this.#urlApi}/getGamebyid${encodeQueryString({ 'idgame': gameid })}`,
        (data) => resolve(data), true);
    });
  }

  getJuegosAdministrador(page = 1) {
    return new Promise((resolve) => {
      FetchMaster.get(`${this.#urlApi}/getGameAdmin${encodeQueryString({ 'page': page })}`,
        (data) => resolve(data), true, true)
    });
  }

  postJuego(Juego) {
    return new Promise((resolve) => {
      FetchMaster.post(`${this.#urlApi}/postGame`, Juego, (data) => resolve(data), undefined, true);
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
      await FetchMaster.delete(`${this.#urlApi}/deleteGame`, { 'idgame': idjuego }, (data) => resolve(data));
    });
  }
}


export default new JuegosService(); 