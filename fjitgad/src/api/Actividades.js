import FetchMaster  from "./FetchMaster";

export async function getActividades() {
    return new Promise(async (resolve) => {
        await FetchMaster.get('activitiestype', (data) => resolve(data), (error) => resolve([]));
      });
}

export function postActividades(formadata) {
    
}