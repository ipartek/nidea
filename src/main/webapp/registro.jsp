<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>
<h2 class="text-center">Registro</h2>

<form action="registro" method="post">
<label for="name">Nombre</label>

<input id="nombre" type="text" name="nombre" >
<label for="email">Email</label>

<input id="email" type="text" name="email" >
<label for="name">Password</label>
<input id="password"  type="password" name="password" >
<label for="password2">Repetir Password</label>
<input id="password2"  type="password" name="password2" >
 <button id="boton" class="btn btn-lg btn-primary btn-block" disabled type="submit" onblur="validar()">Enviar</button>
</form>
  
     
<script src="js/registro.js"></script>


<jsp:include page="templates/footer.jsp"></jsp:include>