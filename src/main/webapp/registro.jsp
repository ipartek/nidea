<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>
<h2 class="text-center">Registro</h2>

<form action="registro" method="post">
<label for="name">Nombre</label>

<input type="text" name="nombre" value="manolo">
<label for="email">Email</label>

<input type="text" name="email" value="manolo@gmail.com">
<label for="name">Password</label>
<input type="password" name="password" value="manolo">
<label for="password2">Repetir Password</label>
<input type="password" name="password2" value="manolo">
 <button id="boton" class="btn btn-lg btn-primary btn-block" type="submit" onblur="">Enviar</button>
</form>
  
     
<script type="text/javascript">

function desactivar(){
	
	var boton=document.getElementById("boton").disabled=false;

}
</script>


<jsp:include page="templates/footer.jsp"></jsp:include>