<%@page import="com.ipartek.formacion.nidea.controller.backoffice.MaterialesController"%>
<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<h1>Materiales</h1>

<div class="row">

	<div class="col-md-6">
		<a class="btn btn-outline-primary" href="backoffice/materiales?op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>">Crear Nuevo</a>
	</div> 

	<div class="col-md-6">
		<form action="backoffice/materiales" method="get">
			<input type="hidden" name="op" value="<%=MaterialesController.OP_BUSQUEDA%>">
			
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
			<td>
				<a href="backoffice/materiales?id=${material.id}&op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>">${material.nombre}</a>
			</td>		
			<td>${material.precio} &euro;</td>			
			<td>${material.usuario.nombre}</td>
		</tr>	
	</c:forEach>
	
	</tbody>
</table>

<jsp:include page="/templates/footer.jsp"></jsp:include>