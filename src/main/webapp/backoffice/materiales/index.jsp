<%@page import="com.ipartek.formacion.nidea.controller.backoffice.BackOfficeMaterialesController"%>
<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<h1>Materiales</h1>

<div class="row">

	<div class="col-md-6">
		<a class="btn btn-outline-primary" href="backoffice/materiales?op=<%=BackOfficeMaterialesController.OP_MOSTRAR_FORMULARIO%>">Crear Nuevo</a>
	</div> 

	<div class="col-md-6">
		<form action="backoffice/materiales" method="get">
			<input type="hidden" name="op" value="<%=BackOfficeMaterialesController.OP_BUSQUEDA%>">
			<input type="text" name="search" required placeholder="Nombre del Material">
			<input type="submit" value="Buscar" class="btn btn-outline-primari">	
		</form>
	</div>	

</div>


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
					<c:choose>
						<c:when test = "${material.precio>=6.0 && material.precio<25.0}">
	           	 			<td class="text-primary"> ${material.id}</td>
	           	 			<td> <a class="text-primary" href="backoffice/materiales?id=${material.id}&op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>">${material.nombre}</a></td>
	           	 			<td class="text-primary"> ${material.precio} &euro;</td>	
	        			</c:when>
	        			<c:when test = "${material.precio>=25.0}">
	        				<td class="text-danger"> ${material.id}</td>
	           	 			<td> <a class="text-danger" href="backoffice/materiales?id=${material.id}&op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>">${material.nombre}</a></td>
	           	 			<td class="text-danger"> ${material.precio} &euro;</td>
	        			</c:when>	
						<c:otherwise>
	            			<td class='bajo'>${material.id}</td>
	            			<td class='bajo'><a class='bajo' href="backoffice/materiales?id=${material.id}&op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>">${material.nombre}</a></td>
	            			<td class='bajo'>${material.precio} &euro;</td>
	         			</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>	
        </tbody>
</table>

<%@include file="/templates/footer.jsp" %>