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
        default:
            myClass = "form-control";
            break;
    }
    return myClass;
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