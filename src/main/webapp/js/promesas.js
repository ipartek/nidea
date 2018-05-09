
/**
 * Libreria para realizar  llamada ajas xmlhttpreques en vanilla javascript
 * retornando un promise
 * @param method: GET, PUT, POST, DELETE
 * @param url donde escucha el servicio Rest
 * @param data: valor opcional, es lo que esta en el body
 * formato 'application/json', valor por defecto null
 * @returns Promise => resolve(request), reject (Error
 */
function ajax(method, url, data=null){
    return new Promise( function(resolve, reject){
        let request;
      //soporte para diferentes navegadores
        if(window.XMLHttpRequest){// chrome, safari, etc...
            request= new XMLHttpRequest();
            request.responseType = 'text';
        } else if (window.ActiveXObject){//IE
            request = new ActiveXObject ("Msxml2.XMLHTTP")
        } else {
            reject("Tu navegador no soporta llamadas ajax");
        }

        //comprobar cambios de estado
        request.onreadystatechange =function(){
            if(request.readyState == 4){
                if (request.status == 200 && request.statu <=299){
                    /* como sabemos que es un json, en vez de devolver un texto, parseamos el json
                    resolve(request.responseText);
                    resolve(JSON.parse(request.responseText));*/
                	resolve(request);// devolvemos todo
                }else{
                	reject(Error(request.statusText));
                }
            }
        };
      
        
        //resolve(datos);
        //reject(error);
        request.open(method, url, true);
        //request.send(data);
        request.setRequestHeader("Content-Type", "application/json");
        request.send(JSON.stringify(data));
  });
}