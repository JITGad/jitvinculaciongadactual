import FetchMaster from "./FetchMaster.js";

class FileUploadService {

  #urlApi = "webresources/fileupload"

  UploadFile(FormData) {
    return new Promise((resolve) => {
      FetchMaster.post(`${this.#urlApi}/upload`, FormData, (data) => resolve(data), FetchMaster.FORMDATAENCODE, true);
    });
  }

}


export default new FileUploadService(); 