<%@page import="com.ipartek.formacion.nidea.controller.frontoffice.MaterialesController"%>
<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<h1>Mis Materiales</h1>

<div class="row">

	<div class="col-md-6">
		<a class="btn btn-outline-primari" href="frontoffice/materiales?op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>">Crear Nuevo</a>
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
           <th>Nombre</th>
           <th>Precio</th>             
       </tr>
   </thead>
   <tbody>
            
	<c:forEach items="${materiales}" var="material">
	<c:if test="${material.usuario.id==usuario.id}">
			<tr>		
				<td>
					<a href="frontoffice/materiales?id=${material.id}&op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>">${material.nombre}</a>
				</td>		
				<td>${material.precio} &euro;</td>	
			</tr>	
		</c:if>
	</c:forEach>
	
	</tbody>
</table>

<jsp:include page="/templates/footer.jsp"></jsp:include>