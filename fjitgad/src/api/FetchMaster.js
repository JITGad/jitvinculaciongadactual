import { message_error } from "../util/Messages";

class FetchMaster {

    static JSONENCODE = 1;
    static FORMDATAENCODE = 2;
    GET = "GET";
    POST = "POST";
    PUT = "PUT";
    DELETE = "DELETE";

    constructor() {
        this.baseUrl = process.env.VUE_APP_BASE_URL_API;
    }

    get(url = '', callback, error, authorize = false) {
        this._ajaxJSON(url,
            undefined,
            (response) => callback(response),
            (errorThrown) => error(errorThrown),
            undefined,
            undefined,
            authorize
        );
    }

    _get_token() {
        let user = JSON.parse(localStorage.getItem('user'));

        if (user && user.accessToken) {
            return 'Bearer ' + user.accessToken;
        } else {
            return '';
        }
    }

    _ajaxJSON(url = '', parameters = {}, callback, error,
        type = this.GET, encode = FetchMaster.JSONENCODE, authorize = false) {

        var headers = {};
        var options = {
            'url': this.baseUrl + url,
            'datatype': 'json',
            'type': type,
            success: function (data) {
                callback(JSON.stringify(data));
            },
            error: function (data) {
                message_error(data);
                error(data);
            }
        }
        if (type == this.POST || type == this.PUT) {
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

        if(authorize){
            var token = this._get_token();

            if(token){
                headers['Authorization'] = token;
            }
        }

        options['headers'] = headers;
        
        console.log(options);
        
        $.ajax(options);
    }

    post(url = '', parameters = {}, callback, error, 
        encode = FetchMaster.JSONENCODE, authorize = false) {
        this._ajaxJSON(url,
            parameters,
            (response) => callback(response),
            (errorThrown) => error(errorThrown),
            this.POST,
            encode,
            authorize
        ); 
    }

    put(url = '', parameters = {}, callback, error, 
        encode = FetchMaster.JSONENCODE, authorize = false) {
        this._ajaxJSON(url,
            parameters,
            (response) => callback(response),
            (errorThrown) => error(errorThrown),
            this.PUT,
            encode,
            authorize
        );
    }

    delete(url = '', callback, error, authorize = false) {
        this._ajaxJSON(url,
            undefined,
            (response) => callback(response),
            (errorThrown) => error(errorThrown),
            this.DELETE,
            undefined,
            authorize
        );
    }
}

export default new FetchMaster();