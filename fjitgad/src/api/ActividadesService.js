import FetchMaster from "./FetchMaster.js";
import { encodeQueryString } from "../util/Utilities.js";

class ActividadesService {

  #urlApi = "activitiestype"

  getActividad(activityid = 0) {
    return new Promise((resolve) => {
      FetchMaster.get(`${this.#urlApi}/getactivitiesbyid${encodeQueryString({ 'activityid': activityid })}`,
        (data) => resolve(data), true);
    });
  }

  getActividadesJuegos() {
    return new Promise((resolve) => {
      FetchMaster.get(this.#urlApi, (data) => resolve(data));
    });
  }

  getActividadesAdministrador(page = 1) {
    return new Promise((resolve) => {
      FetchMaster.get(`${this.#urlApi}/getActivitiestypeAdmin${encodeQueryString({ 'page': page })}`,
        (data) => resolve(data), true, true)
    });
  }

  postActividad(actividad) {
    return new Promise((resolve) => {
      FetchMaster.post(`${this.#urlApi}/postActivitiesType`, actividad, (data) => resolve(data), undefined, true);
    });
  }

  putActividad(actividad) {
    return new Promise((resolve) => {
      FetchMaster.put(`${this.#urlApi}/PutActivitiesType`,
        actividad, (data) => resolve(data), undefined, true);
    });
  }

  deleteActividad(idActividad = 0) {
    return new Promise(async (resolve) => {
      await FetchMaster.delete(`${this.#urlApi}/DeleteActivitiesType`, { 'idactivitiestype': idActividad }, (data) => resolve(data));
    });
  }
}


export default new ActividadesService(); 