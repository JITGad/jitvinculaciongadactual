import FetchMaster from "./FetchMaster.js";
import { encodeQueryString } from "../util/Utilities.js";

class ActividadesService {

  #urlApi = "activitiestype"

  async getActividad(idActividad = 0) {
    return new Promise(async (resolve) => {
      await FetchMaster.get(`getactividad/${idActividad}`, (data) => resolve(data),true);
    });
  }

  async getActividadesJuegos() {
    return new Promise(async (resolve) => {
      await FetchMaster.get(this.#urlApi, (data) => resolve(data));
    });
  }

  async getActividadesAdministrador(page = 0) {
    return new Promise(async (resolve) => {
      await FetchMaster.get(`${this.#urlApi}/getActivitiestypeAdmin${encodeQueryString({ 'page': page })}`,
        (data) => resolve(data), true, true)
    });
  }

  postActividad(actividad) {

  }

  putActividad() {

  }

  deleteActividad(idActividad = 0){
    return new Promise(async (resolve) => {
      await FetchMaster.delete(`${this.#urlApi}/${idActividad}`, (data) => resolve(data));
    });
  }
}


export default new ActividadesService(); 