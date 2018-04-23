<%@page import="com.ipartek.formacion.nidea.controller.Operable"%>
<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<h1>Materiales</h1>

<div class="row">

	<div class="col-md-6">
		<a class="btn btn-outline-primari" href="backoffice/materiales?op=<%=Operable.OP_MOSTRAR_FORMULARIO%>">Crear Nuevo</a>
	</div> 

	<div class="col-md-6">
		<form action="backoffice/materiales" method="get">
			<input type="hidden" name="op" value="<%=Operable.OP_BUSQUEDA%>">
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
            
	<c:forEach items="${usuarios}" var="usuario">
		<tr>			
			<td>
				<a href="backoffice/usuarios?id=${usuario.id}&op=<%=Operable.OP_MOSTRAR_FORMULARIO%>">${usuario.nombre}</a>
			</td>					
			<td>${usuario.nombre}</td>
		</tr>	
	</c:forEach>
	
	</tbody>
</table>

<jsp:include page="/templates/footer.jsp"></jsp:include>