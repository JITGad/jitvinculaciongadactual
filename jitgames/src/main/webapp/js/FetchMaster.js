class FetchMaster {
    JSONENCODE = 1;
    FORMDATAENCODE = 2;
    #GET = "GET";
    #POST = "POST";
    #PUT = "PUT";
    #DELETE = "DELETE";
    #RESPONSESTATUSSUCCESS = "success";
    #RESPONSESTATUSPARSEERROR = "parsererror";
    #RESPONSESTATUSNOTMODIFIED = "notmodified";
    #RESPONSESTATUSTIMEOUT = "timeout";
    #RESPONSESTATUSERROR = "error";
    #baseUrl = "http://192.241.152.203:8080/bjitgad/";

    constructor() {
    }

    get(url = '', callback, authorize = false, paginacion = false) {
        this.#ajaxJSON(url,
            undefined,
            (response) => callback(response),
            undefined,
            undefined,
            authorize,
            paginacion
        );
    }

    #get_token() {
//        let user = JSON.parse(localStorage.getItem('user'));
//
//        if (user && user.user_token) {
            return 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjQ1MzI5ODIxLCJleHAiOjE2NDYxOTM4MjEsImVtYWlsIjoiam9yZ2UtbW9saW5hMTJAaG90bWFpbC5jb20iLCJyb2wiOiJBZG1pbmlzdHJhZG9yIn0.w_OAwYPRfH0y2vXFQMMt4sdxPFFDOS0rLWcBFt3w9GU';
//        } else {
//            return '';
//        }
    }

    #manage_status_response(statusText = "", errorThrown, data) {
        const status = { error: true, message: "" };
        switch (statusText) {
            case this.#RESPONSESTATUSERROR:
                status.message = errorThrown || "A ocurrido un error en el servidor";
            case this.#RESPONSESTATUSNOTMODIFIED:
                status.message = errorThrown || "No se realizaron modificaciones de datos, por favor intentelo de nuevo";
            case this.#RESPONSESTATUSPARSEERROR:
                status.message = errorThrown || "Ha ocurrido un error con los datos enviados al servidor";
                break;
            case this.#RESPONSESTATUSTIMEOUT:
                status.message = errorThrown || "El servidor ha tardado en responder"
                break;
            case this.#RESPONSESTATUSSUCCESS:
                status.error = !data.flag;
                status.message = data.message;
            default:
                break;
        }
        return status;
    }

    #sendAjax(url = '', parameters = {}, callback, type = this.#GET,
        encode = FetchMaster.JSONENCODE, authorize = false, paginacion = false) {
        const _this = this;
        const headers = {};
        const options = {
            'url': this.#baseUrl + url,
            'datatype': 'jsonp',
            jsonp: "$jsonp",
            'type': type,
            success: function (data, textStatus, request) {
                console.log(data);
                callback({
                    'data': data.data,
                    'conteo': paginacion ? data.CountingPage : undefined,
                    'totalPaginas': paginacion ? data.TotalPages : undefined,
                    'status': _this.#manage_status_response(textStatus, undefined, data)
                });
            },
            error: function (request, textStatus, errorThrown) {
                callback({
                    'status': _this.#manage_status_response(textStatus, errorThrown, undefined)
                });
            }
        }
        if (type === this.#POST || type === this.#PUT || type === this.#DELETE) {
            if (encode == FetchMaster.JSONENCODE) {
                headers['Content-Type'] = "application/json; charset=utf-8";
                options['data'] = JSON.stringify(parameters);
            } else if (encode == FetchMaster.FORMDATAENCODE) {
                headers['Content-Type'] = "application/x-www-form-urlencoded";
                options['data'] = parameters;
                options['processData'] = false;
                options['contentType'] = false;
            }
        }

        if (authorize) {
            var token = this.#get_token();

            if (token) {
                headers['Authorization'] = token;
            }
        }

        options['headers'] = headers;
        $.ajax(options);
    }

    #sendFetch(url = '', parameters = {}, callback, type = this.#GET,
        encode = FetchMaster.JSONENCODE, authorize = false, paginacion = false) {
        const myHeaders = {'Content-Type': 'application/json'};
        const options = {
            method: type,
            headers: myHeaders,
            cache: 'default',
            mode: 'cors',
            credentials: 'omit'
        };

        if (authorize) {
            var token = this.#get_token();
            if (token) {
                myHeaders['Authorization'] = token;
            }

        }

        if (type === this.#POST || type === this.#PUT || type === this.#DELETE) {
            if (encode == FetchMaster.JSONENCODE) {
                options['body'] = JSON.stringify(parameters);
            } else if (encode == FetchMaster.FORMDATAENCODE) {
                myHeaders['Content-Type'] = 'application/x-www-form-urlencoded';
                options['body'] = parameters;
            }
        }

        fetch(this.#baseUrl + url, options)
            .then(response => response.json())
            .then(data => {
                callback({
                    'data': data.data,
                    'conteo': paginacion ? data.countingpage : undefined,
                    'totalPaginas': paginacion ? data.totalpages : undefined,
                    'status': {
                        error: !data.flag,
                        message: data.message
                    }
                });
            })
            .catch(function (error) {
                callback({
                    'status': {
                        error: true,
                        message: error
                    }
                });
            });
    }

    #ajaxJSON(url = '', parameters = {}, callback, type = this.#GET,
        encode = FetchMaster.JSONENCODE, authorize = false, paginacion = false) {

        //this.#sendAjax(url,parameters,callback,type,encode,authorize,paginacion);
        this.#sendFetch(url, parameters, callback, type, encode, authorize, paginacion);
    }

    post(url = '', parameters = {}, callback, encode = FetchMaster.JSONENCODE, authorize = true) {
        this.#ajaxJSON(url,
            parameters,
            (response) => callback(response),
            this.#POST,
            encode,
            authorize,
            undefined
        );
    }

    put(url = '', parameters = {}, callback, encode = FetchMaster.JSONENCODE, authorize = true) {
        this.#ajaxJSON(url,
            parameters,
            (response) => callback(response),
            this.#PUT,
            encode,
            authorize,
            undefined
        );
    }

    delete(url = '', parameters = {}, callback, authorize = true) {
        this.#ajaxJSON(url,
            parameters,
            (response) => callback(response),
            this.#DELETE,
            undefined,
            authorize,
            undefined
        );
    }
}
//export default new FetchMaster();