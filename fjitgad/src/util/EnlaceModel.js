export default class EnlaceModel {

    Activa = false;
    Habilitada = true;
    Pagina = 1;
    Texto = "";

    constructor(Pagina = 1, Habilitada = true, Texto = "", Activa = false) {
        this.Pagina = Pagina;
        this.Texto = Texto;
        this.Habilitada = Habilitada;
        this.Activa = Activa;
    }
}