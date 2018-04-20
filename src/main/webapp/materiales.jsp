<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.nidea.pojo.Material"%>
<%@page import="com.ipartek.formacion.nidea.pojo.Mesa"%>

<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

${applicationScope.usuarios_conectados}

<!-- Usar ${variable} = EL: Expresion Lenguage -->
<!-- Se puede usar cualquier expresión, en caso de no existir, pinta la variable -->
<h1>Materiales</h1> 
<p> <b>Rango de precio:</b> ROJO > 25&euro; | AZUL > 6&euro;</p>
<ol>
<c:forEach items="${materiales}" var="material"> 
	<c:choose>
         
         <c:when test = "${material.precio > 6.0 && material.precio <= 25.0}">
            <li style="color:blue">${material.nombre} - ${material.precio} &euro;</li>
         </c:when>
         
         <c:when test = "${material.precio > 25.0}">
            <li style="color:red">${material.nombre} - ${material.precio} &euro;</li>
         </c:when>
         
         <c:otherwise>
            <li>${material.nombre} - ${material.precio} &euro;</li>
         </c:otherwise>
      </c:choose>
</c:forEach>
</ol>


<%@include file="/templates/footer.jsp" %>