/* Libreria para llamadas Ajax XMLHGttpRequest en Vanilla JavaScript
Retornando Promise*/

function ajax(method,url,data = null){
    return new Promise(function(resolve,reject){
        let request;

        //soporte para navegadores
        if (window.XMLHttpRequest){ //Firefox, Chrome, Safari, etc
            request= new XMLHttpRequest();
            
        } else if (window.ActiveXObject){ //IE
            request = new ActiveXObject("Msxml2.XMLHTTP");
        } else{
            reject ('Tu navegador no soporta llamadas Ajax')
        }

        //comprobar cambios de estado
        request.onreadystatechange = function(){
            if (request.readyState == 4) {
                if (request.status >= 200 && request.status <= 203) {
                    resolve(JSON.parse(request.responseText));
                } else{
                    reject (Error(request.statusText));
                }
            }

        };
        
        request.open(method, url, true);
        request.setRequestHeader("Content-Type", "application/json");
        request.send(JSON.stringify(data));
    });
}


