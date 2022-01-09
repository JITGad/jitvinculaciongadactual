const validationsArray = [
    {
        "item": "equalprop",
        "errormessage": function (_campos) {
            const _arr = _campos.split(";");
            return `El campo ${_arr[0]} debe ser igual al campo ${_arr[1]}`;
        },
        "validate": function (_value, _prop, equal) {
            return _value === equal ? true : false;
        }
    },
    {
        "item": "solonumeros",
        "errormessage": function () {
            return "Este campo admite solo numeros"
        },
        "validate": function (_value) {
            const regex = /^[0-9]*$/;
            return regex.test(_value);
        }
    },
    {
        "item": "sololetras",
        "errormessage": function () {
            return "Este campo admite solo letras"
        },
        "validate": function (_value) {
            const pattern = new RegExp('^[A-Z]+$', 'i');
            return pattern.test(_value);
        }
    },
    {
        "item": "requerido",
        "errormessage": function () {
            return "Este campo es requerido"
        },
        "validate": function (_value = "") {
            if (_value != null) {
                if (_value != undefined) {
                    if (_value.length > 0 || typeof (_value) == 'object') {
                        return true;
                    }
                }
            }
            return false;
        }
    },
    {
        "item": "maxlength",
        "errormessage": function (_maxlength = 0) {
            return 'Este campo no puede superar los ' + _maxlength + ' caracteres';
        },
        "validate": function (_value = "", _maxlength = 0) {
            if (_value != null && _value != undefined && _value.length <= parseInt(_maxlength)) {
                return true;
            }
            return false;
        }
    },
    {
        "item": "minlength",
        "errormessage": function (_minlength = 0) {
            return 'Este campo tiene que tener un minimo de  ' + _minlength + ' caracteres';
        },
        "validate": function (_value = "", _minlength = 0) {
            if (_value == null || _value == undefined || _value.length == 0 || _value.length >= parseInt(_minlength)) {
                return true;
            }
            return false;
        }
    },
]
/**
 * Extrae las validaciones de una cadena y los retorna en array
 * @param {String} validation contiene todas las validaciones separadas por comas
 * @returns {Array} Retorna un objeto con todas las validaciones a realizar
 */
function extractValidations(validation = "") {
    const _arr = validation.split(",");
    const _res = [];
    _arr.forEach(element => {
        const _validation = {};
        if (element.indexOf(":") >= 0) {
            _validation['value'] = element.split(":")[1];
            element = element.split(":")[0];
        }
        const _searchValidation = validationsArray.find(el => el.item == element)
        if (_searchValidation) {
            _validation['item'] = _searchValidation;
            _res.push(_validation);
        }
    });
    return _res;
}

function initvalidate(_validatiosArr = [], _value = "", _equal) {
    var _res = [true, ""];
    for (const element of _validatiosArr) {
        _res[0] = (element['item']).validate(_value, element['value'], _equal);
        if (!_res[0]) {
            _res[1] = (element['item']).errormessage(element['value']);
            break;
        }
    }
    return _res;
}

export {
    validationsArray,
    extractValidations,
    initvalidate
};