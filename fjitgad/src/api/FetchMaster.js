import { message_error } from "../util/Messages";

class FetchMaster {

    static JSONENCODE = 1;
    static FORMDATAENCODE = 2;
    GET = "GET";
    POST = "POST";
    PUT = "PUT";
    DELETE = "DELETE";

    constructor() {
        this.baseUrl = process.env.VUE_APP_BASE_URL_API
        this.mode = process.env.VUE_APP_MODE_API_FETCH,
            this.credentials = process.env.VUE_APP_CREDENTIALS_API_FETCH
    }

    get(url = '', callback, error) {
        this._ajaxJSON(url, 
            null, 
            (response) => callback(response), 
            (errorThrown) => error(errorThrown),
            this.GET,
            null
        );
    }

    _ajaxJSON(url = '', parameters = {}, callback, error,
        type = this.GET,
        encode = FetchMaster.JSONENCODE) {

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
                options['data'] = JSON.stringify(parameters);
            } else if (encode == FetchMaster.FORMDATAENCODE) {
                console.log("dd")
                options['data'] = parameters;
                options['processData'] = false;
                options['contentType'] = false;
            }    
        }
        console.log(options);
        $.ajax(options);
    }

    post(url = '', parameters = {}, callback, error, encode = FetchMaster.JSONENCODE) {
        var credenciales = JSON.stringify(parameters);
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: this.baseUrl + "users/logIn",
            data: credenciales,
            success: function (data) {
                var json = JSON.stringify(data);
                console.log(data);
                console.log(data.flag);
                if(data.flag == true)
                {
                    navegationpageprincipal();
                }
//                console.log(json);
            },
            error: function (data) {
                alert("Error");
            }
        });
    }

    put(url = '', parameters = {}, callback, error, encode = FetchMaster.JSONENCODE) {
        this._ajaxJSON(url, 
            parameters, 
            (response) => callback(response), 
            (errorThrown) => error(errorThrown),
            this.PUT,
            encode
        );
    }

    delete(url = '', callback, error) {
        this._ajaxJSON(url, 
            null, 
            (response) => callback(response), 
            (errorThrown) => error(errorThrown),
            this.DELETE,
            null
        );
    }
}

export default new FetchMaster();