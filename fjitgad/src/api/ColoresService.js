import FetchMaster from "./FetchMaster.js";
import { encodeQueryString } from "../util/Utilities.js";

class ColoresService {

  #urlApi = "color"

  getColor(colorid = 0) {
    return new Promise((resolve) => {
      FetchMaster.get(`${this.#urlApi}/getcolorbyid${encodeQueryString({ 'colorid': colorid })}`,
        (data) => resolve(data), true);
    });
  }

  getColoresAdministrador(page = 1) {
    return new Promise((resolve) => {
      FetchMaster.get(`${this.#urlApi}/getcolorAdmin${encodeQueryString({ 'page': page })}`,
        (data) => resolve(data), true, true)
    });
  }

  postColor(Color) {
    return new Promise((resolve) => {
      FetchMaster.post(`${this.#urlApi}/postcolor`, Color, (data) => resolve(data), undefined, true);
    });
  }

  putColor(Color) {
    return new Promise((resolve) => {
      FetchMaster.put(`${this.#urlApi}/Putcolor`,
        Color, (data) => resolve(data), undefined, true);
    });
  }

  deleteColor(idColor = 0) {
    return new Promise(async (resolve) => {
      await FetchMaster.delete(`${this.#urlApi}/Deletecolor`, { 'idcolor': idColor }, (data) => resolve(data));
    });
  }
}


export default new ColoresService(); 