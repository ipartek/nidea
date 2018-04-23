
<%@page import="com.ipartek.formacion.nidea.controller.frontoffice.MaterialesController"%>
<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<h1>Materiales</h1>

<div class="row">

	<div class="col-md-6">
		<a class="btn btn-outline-primary" href="frontoffice/materiales?op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>">Crear Nuevo</a>
	</div> 

	<div class="col-md-6">
		<form action="frontoffice/materiales" method="get">
			<input type="hidden" name="op" value="<%=MaterialesController.OP_BUSQUEDA%>">
			<input type="text" name="search" required placeholder="Nombre del Material">
			<input type="submit" value="Buscar" class="btn btn-outline-primari">	
		</form>
	</div>	

</div>

<table class="tabla table table-striped table-bordered" style="width:100%">
   <thead>
       <tr>
       		<th>id</th>
			<th>Nombre</th>
			<th>Precio</th>
			<th>Usuario</th>                
       </tr>
   </thead>
   <tbody>
        	<c:forEach items="${materiales}" var="material">
				<tr>
					<c:choose>
						<c:when test = "${material.precio>=6.0 && material.precio<25.0}">
	           	 			<td class="text-primary"> ${material.id}</td>
	           	 			<td> <a class="text-primary" href="frontoffice/materiales?id=${material.id}&op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>">${material.nombre}</a></td>
	           	 			<td class="text-primary"> ${material.precio} &euro;</td>
	           	 			<td class="text-primary"> ${usuario.nombre}</td>	
	        			</c:when>
	        			<c:when test = "${material.precio>=25.0}">
	        				<td class="text-danger"> ${material.id}</td>
	           	 			<td> <a class="text-danger" href="frontoffice/materiales?id=${material.id}&op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>">${material.nombre}</a></td>
	           	 			<td class="text-danger"> ${material.precio} &euro;</td>
	           	 			<td class="text-danger"> ${usuario.nombre}</td>
	        			</c:when>	
						<c:otherwise>
	            			<td class="bajo">${material.id}</td>
	            			<td class="bajo"><a class='bajo' href="frontoffice/materiales?id=${material.id}&op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>">${material.nombre}</a></td>
	            			<td class="bajo">${material.precio} &euro;</td>
	            			<td class="bajo"> ${usuario.nombre}</td>
	         			</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>	
        </tbody>
</table>

<%@include file="/templates/footer.jsp" %>