export default class DetalleJuegoObject {

    idgameimage = 0;
    idgame = 0;
    idcolortype = 0;
    image = "";
    paragraph = "";
    audio_parag = "";
    video_parag = "";
    state = true;

    constructor(_colorid = 0, _imagen = "", _paragraph = "", _audio_parag = "", _video_parag = "") {
        this.idcolortype = _colorid;
        this.image = _imagen;
        this.paragraph = _paragraph;
        this.audio_parag = _audio_parag;
        this.video_parag = _video_parag;
    }

    static Emparejar(_colorid, _imagen){
        return new DetalleJuegoObject(_colorid, _imagen, undefined,undefined,undefined);
    }

    static Rompecabezas(_imagen){
        return new DetalleJuegoObject(undefined, _imagen, undefined,undefined,undefined);
    }

    static Memoria(_imagen){
        return new DetalleJuegoObject(undefined, _imagen, undefined,undefined,undefined);
    }
    
    static Cuento(_imagen, _paragraph, _audio_parag, _video_parag){
        return new DetalleJuegoObject(undefined,_imagen,_paragraph, _audio_parag, _video_parag);
    }
}