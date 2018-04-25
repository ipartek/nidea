<%@page import="com.ipartek.formacion.nidea.controller.Operable"%>
<%@page
	import="com.ipartek.formacion.nidea.controller.frontoffice.FrontofficeUsuariosController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/templates/head.jsp"%>
<%@include file="/templates/navbar.jsp"%>

<h1>Registro Usuario</h1>

<%@include file="/templates/alert.jsp"%>

<form action="registro" method="get">

	<div class="form-group">
		<label for="nombre">Nombre Usuario:</label> <input type="text"
			class="form-control" id="nombre" name="nombre"
			placeholder="Nombre Usuario" required value="${registro.nombre }">
	</div>
	<div class="form-group">
		<label for="password">Password:</label> 
		<input type="password" class="form-control" id="password" name="password"
			placeholder="Password" required value="${registro.pass }">
	</div>
	<div class="form-group">
		<label for="confirm_password">Repetir Password:</label> 
		<input type="password" class="form-control" id="confirm_password" name="confirm_password" 
		placeholder="Repetir Password" required>
	</div>
		<div class="form-group">
		<label for="email">Email:</label> <input type="email"
			class="form-control" id="email" name="email"
			placeholder="Email" value="${registro.email }">
	</div>

	<input type="hidden" name="op" value="<%=Operable.OP_GUARDAR%>">
	<button type="submit" class="btn btn-success btn-block" id="boton" disabled>Crear</button>

</form>
<script src="js/registroUsuarios.js"></script>

<%@include file="/templates/footer.jsp"%>
