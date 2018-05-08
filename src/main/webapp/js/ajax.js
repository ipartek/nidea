/**
**Esto es una libreria para realizar llamadas Ajax de tipo XMLHttpRequest en vanilla script
**Retornando una Promise
**/
console.log ("Libreria cargada");
function ajax(method, url, data=null){
    return new Promise(function(resolve, reject){
        let request;
        
        //Soporte para navegadores
        if(window.XMLHttpRequest){ //Chrome, safari,...
            request = new XMLHttpRequest();
           
        }else if(window.ActiveXObject){ //IE
            request = new ActiveXObject("Msxml2.XMLHTTP");
                 
        }else{
            reject("Tu navegador no soporta llamadas Ajax");   
        }
        
        //Comprobar cambios de estado
        request.onreadystatechange = function(){
             if (request.readyState == 4){
                 if(request.status >= 200 && request.status <=299) {
                     resolve(JSON.parse(request.responseText));
                     
                 }else{
                      reject(Error(request.statusText));
                 }
             } 
        };
        request.open(method, url, true);
        request.setRequestHeader("Content-Type", "application/json");
        request.send(data);
       
   });
}

