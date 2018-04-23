
<%@page import="com.ipartek.formacion.nidea.controller.backoffice.BackofficeUsuariosController"%>
<%@page import="com.ipartek.formacion.nidea.controller.backoffice.BackofficeMaterialesController"%>
<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>




<div class="container">
	<div class="form-group row">
		<a class="btn btn-outline-dark btn-lg" href="backoffice/usuarios">Volver</a>
	</div>
	
	<form action="backoffice/usuarios" method="post">
	  <div class="form-group row">
	    <label for="id_usuario" class="col-sm-2 col-form-label">ID:</label>
	    <div class="col-sm-2">
	      <input type="text" class="form-control" name="id_usuario" readonly value="${usu.id}">
	    </div>
	  </div>
	  <div class="form-group row">
	    <label for="nombre_usuario" class="col-sm-2 col-form-label">Nombre Usuario</label>
	    <div class="col-sm-5">
	      <input type="text" value="${usu.nombre}" class="form-control" name="nombre_usuario" placeholder="Introduce el nombre del usuario" >
	    </div>
	  </div>
	  <div class="input-group ">
	    <label for="pass" class="col-sm-2 col-form-label">Password</label>
	    <div class="input-group-append">
	      <input type="text" class="form-control" value="${usu.pass}" name="password" placeholder="Introduce la contraseña">
	    </div>
	  </div>
	  <br>
	 
	</div>
	<br> 
	<br>
	  <div class="input-group ">
	    <label for="rol" class="col-sm-2 col-form-label">Rol del usuario</label>
	    <div class="input-group-append">
	    	<select name="rol_id">
		      <c:forEach items="${roles}" var="r">
		      	<option value="${r.id}">${r.nombre}</option>
		      </c:forEach>
	      </select>
	    </div>
	  </div> 
	<c:if test="${usuarios.id_usuarios == -1}">
		<div class="form-group row">
			<div class="col-sm-12">
			 	<input type="hidden" name="op" value="<%=BackofficeUsuariosController.OP_GUARDAR%>"> 	
			    <button type="submit" class="btn btn-primary btn-lg btn-block">Crear</button>
			</div>
		 </div>
	</c:if>
	<c:if test="${usuarios.id_usuarios > -1}">  
		<div class="form-group row">
			<div class="col-sm-6">
			    <input type="hidden" name="op" value="<%=BackofficeUsuariosController.OP_GUARDAR%>"> 	
			     <button type="submit" class="btn btn-success btn-lg btn-block">Modificar</button>
			 </div>
		<div class="col-sm-6">		      
			<!-- Button trigger modal -->
			<button type="button" class="btn btn-danger btn-lg btn-block" data-toggle="modal" data-target="#exampleModal">Eliminar
			</button>
						
						<!-- Modal -->
						<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
						  <div class="modal-dialog" role="document">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="exampleModalLabel">Eliminar</h5>
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          <span aria-hidden="true">&times;</span>
						        </button>
						      </div>
						      <div class="modal-body">
						         ¿ Estas seguro que deseas Eliminar ?
						      </div>
						      <div class="modal-footer">
						        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
						        <a href="backoffice/usuarios?id=${usuarios.id}&op=<%=BackofficeUsuariosController.OP_ELIMINAR%>">
						        	<button type="button" class="btn btn-primary">Aceptar</button>
						        </a>
						      </div>
						    </div>
						  </div>
						</div>
			    </div>
			    <!-- "col-sm-6"  -->
			    
			  </div>
		</c:if>	  
	</form>
</div>


<jsp:include page="/templates/footer.jsp"></jsp:include>