export default class DetalleJuegoObject {

    idgameimage = 0;
    idgame = 0;
    idcolortype = 0;
    image = "";
    imagefigure = "";
    paragraph = "";
    audio_parag = "";
    video_parag = "";
    state = true;

    constructor(_colorid = 0, _imagen = "", _imagefigure = "", _paragraph = "", _audio_parag = "", _video_parag = "") {
        this.idcolortype = _colorid;
        this.image = _imagen;
        this.imagefigure = _imagefigure;
        this.paragraph = _paragraph;
        this.audio_parag = _audio_parag;
        this.video_parag = _video_parag;
    }

    static Emparejar(_colorid, _imagen, _imagefigure) {
        return new DetalleJuegoObject(_colorid, _imagen, _imagefigure, undefined, undefined, undefined);
    }

    static Rompecabezas(_imagen) {
        return new DetalleJuegoObject(undefined, _imagen, undefined, undefined, undefined, undefined);
    }

    static Memoria(_imagen) {
        return new DetalleJuegoObject(undefined, _imagen, undefined, undefined, undefined, undefined);
    }

    static Cuento(_imagen, _paragraph, _audio_parag, _video_parag) {
        return new DetalleJuegoObject(undefined, _imagen, undefined, _paragraph, _audio_parag, _video_parag);
    }
}