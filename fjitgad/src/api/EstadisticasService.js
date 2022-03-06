import FetchMaster from "./FetchMaster.js";
import { encodeQueryString } from "../util/Utilities.js";

class EstadisticasService {
    #urlApi = "webresources/statisticsgame"

    getEstadisticasAdministrador(page = 1) {
        return new Promise((resolve) => {
            FetchMaster.get(`${this.#urlApi}/getStatisticsgameAdmin${encodeQueryString({ 'page': page })}`,
                (data) => resolve(data), true, true);
        });
    }

    postEstadisticas(Estadisticas) {
        return new Promise((resolve) => {
            FetchMaster.post(`${this.#urlApi}/postStatisticsgame`, Estadisticas, 
                            (data) => resolve(data), FetchMaster.JSONENCODE, false);
        });
    }
}

export default new EstadisticasService(); 