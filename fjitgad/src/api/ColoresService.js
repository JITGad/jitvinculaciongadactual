import FetchMaster from "./FetchMaster.js";
import { encodeQueryString } from "../util/Utilities.js";
import ObjectSelect from "../util/ObjectSelect.js";

class ColoresService {

  #urlApi = "webresources/colortype"

  getColor(colorid = 0) {
    return new Promise((resolve) => {
      FetchMaster.get(`${this.#urlApi}/getcolortypebyid${encodeQueryString({ 'idcolortype': colorid })}`,
        (data) => resolve(data), true);
    });
  }

  getColoresSelectMenu() {
    return new Promise((resolve) => {
      FetchMaster.get(`${this.#urlApi}/getcolortypecv`, (data) => {
        const result = [];
        if (Array.isArray(data.data)) {
          for (const color of data.data) {
            result.push(new ObjectSelect(color.id, color.text, color.value));
          }
        }
        resolve(result);
      }, true, false);
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
      FetchMaster.post(`${this.#urlApi}/postColortype`, Color, 
                      (data) => resolve(data), FetchMaster.JSONENCODE, true);
    });
  }

  putColor(Color) {
    return new Promise((resolve) => {
      FetchMaster.put(`${this.#urlApi}/putColortype`,
        Color, (data) => resolve(data), FetchMaster.JSONENCODE, true);
    });
  }

  deleteColor(idColor = 0) {
    return new Promise(async (resolve) => {
      await FetchMaster.delete(`${this.#urlApi}/deleteColortype`, { 'idcolortype': idColor }, (data) => resolve(data));
    });
  }
}


export default new ColoresService(); 