<%@page import="com.ipartek.formacion.nidea.controller.Operable"%>


<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<h1>Mis Materiales</h1>

<div class="row">

	<div class="col-md-12">
		<a class="btn btn-outline-dark float-right" href="frontoffice/materiales?op=<%=Operable.OP_MOSTRAR_FORMULARIO%>">Crear Nuevo</a>
	</div>
</div> 

<!-- BUSCADOR -->
<div class="row">
	<div class=" input-group">
		<form action="frontoffice/materiales" method="get">
			<div class=" input-group">
				<input type="hidden" name="op" value="<%=Operable.OP_BUSQUEDA%>">
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
	                      
	       </tr>
	   </thead>
	   <tbody>
	            
		<c:forEach items="${materiales}" var="material">
			<tr>			
				<td>
					<a href="frontoffice/materiales?id=${material.id}&op=<%=Operable.OP_MOSTRAR_FORMULARIO%>">${material.nombre}</a>
				</td>		
				<td>${material.precio} &euro;</td>			
			</tr>	
		</c:forEach>
		
		</tbody>
	</table>
</div>


<jsp:include page="/templates/footer.jsp"></jsp:include>