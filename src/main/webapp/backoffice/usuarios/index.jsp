<%@page import="com.ipartek.formacion.nidea.controller.Operable"%>
<%@page import="com.ipartek.formacion.nidea.controller.backoffice.BackofficeUsuariosController"%>

<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<h1>Usuarios</h1>

<div class="row">

	<div class="col-md-12">
		<a class="btn btn-outline-dark float-right" href="backoffice/usuarios?op=<%=Operable.OP_MOSTRAR_FORMULARIO%>">Crear Nuevo</a>
	</div>
</div> 

<!-- BUSCADOR -->
<div class="row">
	<div class=" input-group">
		<form action="backoffice/usuarios" method="get">
			<div class=" input-group">
				<input type="hidden" name="op" value="<%=Operable.OP_BUSQUEDA%>">
				<input  type="text"class="form-control " name="search" required placeholder="Nombre del usuario">
				<input type="submit"  value="Buscar" >
			</div>
		</form>
	</div>	
</div>
<br><br>
<div class="row">
	<table class="tabla table table-striped table-bordered" style="width:100%">
	   <thead>
	       <tr>
	           <th>Nombre</th>
	           <th>Password</th>
	           <th>Rol</th>                
	       </tr>
	   </thead>
	   <tbody>
	            
		<c:forEach items="${usuarios}" var="usu">
			<tr>			
				<td>
					<a href="backoffice/usuarios?id=${usu.id}&op=<%=Operable.OP_MOSTRAR_FORMULARIO%>">${usu.nombre}</a>
				</td>		
				<td>${usu.pass}</td>			
				<td>${usu.rol.nombre}</td>
			</tr>	
		</c:forEach>
		
		</tbody>
	</table>
</div>


<jsp:include page="/templates/footer.jsp"></jsp:include>