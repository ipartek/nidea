/* Libreria para realizar llamadas ajax XMLHTTPRequest en vanilla js retornando una promise */

console.log('Libreria AJAX');

function ajax(method, url, data) {
    return new Promise(function (resolve, reject) {

        //let body = JSON.parse(data);
        //console.log('data en server %o', data);
        var request = false;

        
        //Crear la request dependiendo del navegador
        if (window.XMLHttpRequest) { // Mozilla, Safari,...
            request = new XMLHttpRequest();
            if (request.overrideMimeType) {
                request.overrideMimeType('text/xml');
            }
        } else if (window.ActiveXObject) { // IE
            try {
                request = new ActiveXObject("Msxml2.XMLHTTP");
            } catch (e) {
                try {
                    request = new ActiveXObject("Microsoft.XMLHTTP");
                } catch (e) {}
            }
        } else {
            console.log('Su navegador no soporta ajax');
        }

        //Hacer la llamada
        request.onreadystatechange = function () {
            if (request.readyState == 4) {
                if (request.status >= 200 && request.status <= 299) {
                    resolve(request);
                } else {
                    reject(Error(request.statusText));
                }
            }
        }

        request.open(method, url, true);
        request.setRequestHeader( 'Content-Type', 'application/json' );
        request.send(JSON.stringify(data));

    });


}
