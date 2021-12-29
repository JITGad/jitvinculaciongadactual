const validationsArray = [
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
                    if (_value.length > 0) {
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
            if (_value != null && _value != undefined && _value.length <= _maxlength) {
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
            if (_value != null && _value != undefined && _value.length <= _minlength) {
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
    var _arr = validation.split(",");
    var _res = [];
    _arr.forEach(element => {
        var _validation = {};
        if (element.indexOf(":") >= 0) {
            validation['value'] = element.split(":")[1];
            element = element.split(":")[0];
        }
        var _searchValidation = validationsArray.find(el => el.item == element)
        if (_searchValidation) {
            _validation['item'] = _searchValidation;
            _res.push(_validation);
        }
    });
    return _res;
}

function initvalidate(_validatiosArr = [], _value = "") {
    var _res = [true, ""];
    _validatiosArr.forEach(element => {
        _res[0] = (element['item']).validate(_value, element['value']);
        if (!_res[0]) {
            _res[1] = (element['item']).errormessage(element['value']);
        }
    });
    return _res;
}

export {
    validationsArray,
    extractValidations,
    initvalidate
};