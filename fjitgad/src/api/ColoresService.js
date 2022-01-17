import FetchMaster from "./FetchMaster.js";
import { encodeQueryString } from "../util/Utilities.js";

class ColoresService {

  #urlApi = "webresources/colortype"

  getColor(colorid = 0) {
    return new Promise((resolve) => {
      FetchMaster.get(`${this.#urlApi}/getcolortypebyid${encodeQueryString({ 'idcolortype': colorid })}`,
        (data) => resolve(data), true);
    });
  }

  getColoresAdministrador(page = 1) {
    return new Promise((resolve) => {
      FetchMaster.get(`${this.#urlApi}/getcolortypeAdmin${encodeQueryString({ 'page': page })}`,
        (data) => resolve(data), true, true)
    });
  }

  postColor(Color) {
    return new Promise((resolve) => {
      FetchMaster.post(`${this.#urlApi}/postColortype`, Color, (data) => resolve(data), undefined, true);
    });
  }

  putColor(Color) {
    return new Promise((resolve) => {
      FetchMaster.put(`${this.#urlApi}/putColortype`,
        Color, (data) => resolve(data), undefined, true);
    });
  }

  deleteColor(idColor = 0) {
    return new Promise(async (resolve) => {
      await FetchMaster.delete(`${this.#urlApi}/deleteColortype`, { 'idcolortype': idColor }, (data) => resolve(data));
    });
  }
}


export default new ColoresService(); 