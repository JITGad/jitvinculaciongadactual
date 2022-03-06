import FetchMaster from "./FetchMaster.js";
import { encodeQueryString } from "../util/Utilities.js";

class JuegosService {

  #urlApi = "webresources/game"

  getJuegoAdministrador(gameid = 0) {
    return new Promise((resolve) => {
      FetchMaster.get(`${this.#urlApi}/getGamebyid${encodeQueryString({ 'idgame': gameid })}`,
        (data) => resolve(data), true);
    });
  }

  getJuego(gameid = 0) {
    return new Promise((resolve) => {
      FetchMaster.get(`${this.#urlApi}/getGamebyidsk${encodeQueryString({ 'idgame': gameid })}`,
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
      FetchMaster.post(`${this.#urlApi}/postGameEncoded`, Juego, (data) => resolve(data),
        FetchMaster.FORMDATAENCODE, true);
    });
  }

  putJuego(Juego) {
    return new Promise((resolve) => {
      FetchMaster.put(`${this.#urlApi}/putGameEncoded`,
        Juego, (data) => resolve(data), FetchMaster.FORMDATAENCODE, true);
    });
  }

  deleteJuego(idjuego = 0) {
    return new Promise(async (resolve) => {
      await FetchMaster.delete(`${this.#urlApi}/deleteGame`, { 'idgame': idjuego }, (data) => resolve(data));
    });
  }
}


export default new JuegosService(); 