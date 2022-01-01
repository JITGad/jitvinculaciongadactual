import FetchMaster from "./FetchMaster.js";
import { encodeQueryString } from "../util/Utilities.js";

class ActividadesService {

  async getActividadesJuegos() {
    return new Promise(async (resolve) => {
      await FetchMaster.get('activitiestype', (data) => resolve(data));
    });
  }

  async getActividadesAdministrador(page = 0) {
    return new Promise(async (resolve) => {
      await FetchMaster.get(`activitiestypeadmin${encodeQueryString({ 'page': page })}`,
        (data) => resolve(data), true, true)
    });
  }

  postActividad(actividad) {

  }

  putActividad() {

  }

  deleteActividad(idActividad = 0){
    return new Promise(async (resolve) => {
      await FetchMaster.delete(`deleteactividad/${idActividad}`, (data) => resolve(data));
    });
  }
}


export default new ActividadesService(); 