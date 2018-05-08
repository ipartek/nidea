<%@page import="com.ipartek.formacion.nidea.controller.Operable"%>
<%@page
	import="com.ipartek.formacion.nidea.controller.frontoffice.FrontofficeUsuariosController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/templates/head.jsp"%>
<%@include file="/templates/navbar.jsp"%>

<h1>Frontoffice Usuario Form</h1>

<%@include file="/templates/alert.jsp"%>

<form action="frontoffice/usuarios" method="get">

	<div class="form-group">
		<label for="id">Id Usuario:</label> 
		<input type="number" class="form-control" id="id" name="id" placeholder="Id Usuario" readonly value="${usuario.id }">
	</div>
	<div class="form-group">
		<label for="nombre">Nombre Usuario:</label> <input type="text"
			class="form-control" id="nombre" name="nombre"
			placeholder="Nombre Usuario" required value="${usuario.nombre }">
	</div>
	<div class="form-group">
		<label for="password">Password:</label> <input type="password"
			class="form-control" id="password" name="password"
			placeholder="Password" required value="${usuario.pass }">
	</div>
	<div class="form-group">
		<label for="nombre">Rol Usuario:</label> <input type="text"
			class="form-control" id="rol" name="nombre" placeholder="Rol Usuario"
			required value="${usuario.rol.nombre }" readonly>
	</div>


	<input type="hidden" name="op" value="<%=Operable.OP_GUARDAR%>">
	<button type="submit" class="btn btn-primary btn-block">Modificar</button>

</form>


<%@include file="/templates/footer.jsp"%>