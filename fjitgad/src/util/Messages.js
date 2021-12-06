/**
 * Funcion que muestra ua ventana de informacion con un objeto que trae la informacion de estos.
 * @param {string|object|array} obj Contiene la informacion del mensaje de error que va a ser mostrado
 * @return {void} No retorna nada
 */
 export const message_error = (obj) => {
     console.log(obj)
    let title = "¡Error!"
    try {
        if (typeof obj === "string") {
            let myObject = (0, eval)('(' + obj + ')');
            if (myObject) obj = myObject;
        }
    } catch (e) {
        //console.log(e);
    }
    let html = '';
    if (typeof (obj) === 'object') {
        title = "¡Errores!"
        html = '<ul style="text-align: left; padding-inline-start: unset;">';
        $.each(obj, function (key, value) {
            html += '<li style="display: block;">' + (key + 1) + ': ' + value + '</li>';
        });
        html += '</ul>';
    } else {
        html = '<p>' + obj + '</p>';
    }
    $.confirm({
        title: title,
        content: html,
        icon: 'fa fa-exclamation-triangle',
        theme: 'material',
        escapeKey: true,
        buttons: {
            ok: {
                text: "OK",
                keys: ['enter', 'esc'],
                btnClass: 'btn-primary',
                action: function () {

                }
            }
        }
    });
};

/**
 * Función que envia un mensaje de informacion al usuario a travez del plugin jqueryconfirm
 * @param {string} message Mensaje que va a ser mostrado al usuario
 * @param {function} callback Funcion de retorno que se activa cuando el usuario acepta el mensaje de información
 * @return {void} No retorna nada
 */
export const message_info = (message, callback) => {
    $.confirm({
        title: '¡Notificación!',
        content: `<p>${message}</p>`,
        icon: 'fa fa-info',
        theme: 'material',
        autoClose: 'ok|5000',
        buttons: {
            ok: {
                text: "OK",
                keys: ['enter', 'esc'],
                btnClass: 'btn-primary',
                action: function () {
                    callback();
                }
            }
        }
    });
};

/**
 * Funcion que activa una ventana de confirmacion para realizar alguna opcion especifica
 * @param {string} title EL titulo de la ventana de confirmacion
 * @param {string} content EL contenido de la ventana de confirmación
 * @param {function} callback Funcion que se activa si el usuario confirma la accion
 * @param {function} cancel Funcion que retorna en caso de cancelar la accion
 * @return {void} No retorna nada
 */
 export const confirm_action = (title, content, callback, cancel) => {
    $.confirm({
        theme: 'material',
        title: title,
        icon: 'fa fa-info',
        content: content,
        columnClass: 'medium',
        typeAnimated: true,
        cancelButtonClass: 'btn-primary',
        draggable: true,
        dragWindowBorder: false,
        buttons: {
            info: {
                text: "Si",
                keys: ['enter'],
                btnClass: 'btn-primary',
                action: function () {
                    callback();
                }
            },
            danger: {
                text: "No",
                keys: ['esc'],
                btnClass: 'btn-red',
                action: function () {
                    cancel();
                }
            },
        }
    });
};