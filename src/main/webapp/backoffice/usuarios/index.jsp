<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>
<%@page import="com.ipartek.formacion.nidea.controller.frontoffice.MaterialesController"%>
    
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css" >
    
<h1>Backofiice usuarios</h1>



<table class="tabla table table-striped table-bordered" style="width:100%">
   <thead>
       <tr>
           <th>Nombre</th>
           <th>Rol</th>             
       </tr>
   </thead>
   <tbody> 
	<c:forEach items="${usuarios}" var="usuario">
			<tr>		
				<td>
					<a href="backoffice/usuarios?op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>&id=${usuario.id }">${usuario.nombre.toUpperCase()}</a>
				</td>		
				<td>${usuario.rol.nombre} </td>	
			</tr>	
	</c:forEach>
	
	</tbody>
</table>

<jsp:include page="/templates/footer.jsp"></jsp:include>