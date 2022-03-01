/**
 * Funcion que se encarga de trasformar un objeto en un string con formato de url
 * por ejemplo: {a: 1, b: 2} = ?a=1&b=2
 * @param {Object} params Corresponde a los parametros que va a formatear
 * @return {string} Retorna un string con la informacion formateada
 */
export const encodeQueryString = (params = {}) => {
    const keys = Object.keys(params);
    return keys.length
        ? "?" + keys
            .map(key => encodeURIComponent(key)
                + "=" + encodeURIComponent(params[key]))
            .join("&")
        : "";
};

/**
 * Funcion que recorre y muestra en consola la informacion de un formadata
 * @param {FormData} form_data Objeto formadata que sera recorrido
 * @return {void} No retorna nada
 */
export const recorrer_formdata = form_data => {
    form_data.forEach(function (value, key, parent) {
        console.log(`${key}: ${value}`);
    });
};

/**
 * Funcion que se encarga de buscar la cookie que ingresa como parametro y retorna su valor en caso
 * de encontrarla
 * @param {string} name Nombre de la cookie que va a ser buscada
 * @return {string} Retorna el valor de la cookie que fue buscada
 */
export function getCookie(name) {
    let cookieValue = "";
    if (document.cookie && document.cookie !== '') {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            // Does this cookie string begin with the name we want?
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}

export function myClassContainerInputType(type = "") {
    let myClass = "";
    switch (type) {
        case "checkbox":
            myClass = "mb-3 form-check";
            break;
        default:
            myClass = "mb-3";
            break;
    }
    return myClass;
}

export function myClassInputType(type = "") {
    let myClass = "";
    switch (type) {
        case "checkbox":
            myClass = "form-check-input";
            break;
        case "color":
            myClass = "form-control form-control-color"
            break;
        default:
            myClass = "form-control";
            break;
    }
    return myClass;
}

export function acceptTypeInputFile(type = "") {
    let _accept = "";
    switch (type) {
        case "image":
            _accept = "image/*";
            break;
        case "pdf":
            _accept = "application/pdf";
            break;
        case "audio":
            _accept = "audio/*";
            break;
        case "video":
            _accept = "video/*";
            break;
        default:
            break;
    }
    return _accept;
}

export const Role = {
    Admin: 'Administrador',
    Docente: 'Docente'
}

export function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

function getFileBase64(file) {
    return new Promise((resolve) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => {
            console.error(error);
            resolve("");
        };
    });
}

export async function readFilesToBase64(files, multiple = false) {
    if (!multiple) {
        return await getFileBase64(files);
    }
    const _arrFiles = [];
    for (const file of files) {
        await _arrFiles.push(await getFileBase64(file));
    }
    return _arrFiles;
}

export function formatDateInput(date = new Date()) {
    return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
}

export function isBase64(str = "") {
    if (str === null || str === '' || str.trim() === '') { return false; }
    try {
        const extension = str.split(',')[0];
        if (extension == null || extension.length == 0) {
            return false;
        }

        return extension.includes("base64");
    } catch (err) {
        console.log(err);
        return false;
    }
}

export function setPathFile(_value) {
    if (_value == null || _value.length == 0) {
        return "";
    }
    if (isBase64(_value)) {
        return _value;
    }

    return `${process.env.VUE_APP_BASE_URL}${_value}`;
}

// Convertir cadena Unicode a cadena donde cada
// elemento 16-bit ocupe solo un byte
export function toBinary(string) {
    const codeUnits = new Uint16Array(string.length);
    for (let i = 0; i < codeUnits.length; i++) {
        codeUnits[i] = string.charCodeAt(i);
    }
    return String.fromCharCode(...new Uint8Array(codeUnits.buffer));
}

// Recodificar cadena original
export function fromBinary(binary) {
    const bytes = new Uint8Array(binary.length);
    for (let i = 0; i < bytes.length; i++) {
        bytes[i] = binary.charCodeAt(i);
    }
    return String.fromCharCode(...new Uint16Array(bytes.buffer));
}

export function shuffle(array) {
    let currentIndex = array.length;
    while (currentIndex !== 0) {
        let randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex -= 1;
        let temporaryValue = array[currentIndex];
        array[currentIndex] = array[randomIndex];
        array[randomIndex] = temporaryValue;
    }
    return array;
}

// Auxiliary functions
export function generateRandomItemsArray(n, originalArray) {
    let res = [];
    let clonedArray = [...originalArray];
    if (n > clonedArray.length) n = clonedArray.length;
    for (let i = 1; i <= n; i++) {
        const randomIndex = Math.floor(Math.random() * clonedArray.length);
        res.push(clonedArray[randomIndex]);
        clonedArray.splice(randomIndex, 1);
    }
    return res;
}

export function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
}

export function idiomaVozDisponible() {
    const IDIOMAS_PREFERIDOS = ["es-MX", "es-US", "es-ES", "es_US", "es_ES"];
    const vocesDisponibles = speechSynthesis.getVoices();
    let posibleIndice = vocesDisponibles.findIndex(voz => IDIOMAS_PREFERIDOS.includes(voz.lang));
    if (posibleIndice === -1) posibleIndice = 0;
    return vocesDisponibles[posibleIndice];
}

export function reproducirTextoAVoz(textoAEscuchar = "", idioma) {
    let mensaje = new SpeechSynthesisUtterance();
    mensaje.voice = idioma;
    mensaje.volume = 1;
    mensaje.rate = 0.8;
    mensaje.text = textoAEscuchar;
    mensaje.pitch = 1;
    speechSynthesis.speak(mensaje);
}