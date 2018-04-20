<%@page import="com.ipartek.formacion.nidea.controller.backoffice.MaterialesController"%>
<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<h1>Materiales</h1>

<div class="row">

	<div class="col-md-12">
		<a class="btn btn-outline-dark float-right" href="backoffice/materiales?op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>">Crear Nuevo</a>
	</div>
</div> 

<!-- BUSCADOR -->
<div class="row">
	<div class=" input-group">
		<form action="backoffice/materiales" method="get">
			<div class=" input-group">
				<input type="hidden" name="op" value="<%=MaterialesController.OP_BUSQUEDA%>">
				<input  type="text"class="form-control " name="search" required placeholder="Nombre del material">
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
	           <th>Precio</th>
	           <th>Usuario</th>                
	       </tr>
	   </thead>
	   <tbody>
	            
		<c:forEach items="${materiales}" var="material">
			<tr>			
				<td>
					<a href="backoffice/materiales?id=${material.id}&op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>">${material.nombre}</a>
				</td>		
				<td>${material.precio} &euro;</td>			
				<td>${material.usuario.nombre}</td>
			</tr>	
		</c:forEach>
		
		</tbody>
	</table>
</div>


<jsp:include page="/templates/footer.jsp"></jsp:include>