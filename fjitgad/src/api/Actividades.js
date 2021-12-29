import FetchMaster from "./FetchMaster";
class ActividadesService {
  
  async getActividades() {
    return new Promise(async (resolve) => {
      await FetchMaster.get('activitiestype', (data) => resolve(data), (error) => resolve([]));
    });
  }
  postActividades(formadata) {

  }
}


export default new ActividadesService(); 