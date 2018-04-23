<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="index.jsp"><img src="img/logo.png" class="logo" alt="Logo Nidea"></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
  	<ul class="navbar-nav mr-auto">
      	<li class="nav-item">
      		<a class="nav-link" href="generar-mesa">Mesa</a> 
      	</li>
      	<li class="nav-item">
			<a class="nav-link" href="calculadora">Calculadora</a> 
		</li>
		<li class="nav-item">
				<a class="nav-link" href="materiales">Materiales</a>
			</li>	
		<c:if test="${usuario.rol.id == 2 }">
			<li class="nav-item">
				<a class="nav-link" href="frontoffice/materiales">Mis Materiales</a>
			</li>		
		</c:if>     
	    <c:if test="${usuario.rol.id == 1 }">  
		    <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          Backoffice
		        </a>
		        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
		        	<a class="dropdown-item" href="backoffice/materiales">Materiales</a>
					<a class="dropdown-item" href="backoffice/usuarios">Usuarios</a>
					<a class="dropdown-item" href="backoffice/roles">Roles</a>
		        </div>
		    </li>
		</c:if> 
	    
    </ul>
    <ul class="navbar-nav"> 
    	<c:if test="${usuario != null }">	    
	    	<li class="nav-item nav-link">
		    	<a href="frontoffice/usuarios" class="nav-link">
		    		<i class="fa fa-user" aria-hidden="true"></i> ${usuario.nombre } 
		    	</a>
	    	</li>	    
    	</c:if>
    	<c:if test="${empty usuario }">
    		<li class="nav-item">
				<a class="btn btn-outline-primary" href="login">Login</a>
			</li>
		</c:if>

		<c:if test="${!empty usuario }">
			<li class="nav-item">
				<a class="btn btn-outline-danger" href="logout">Logout</a>
			</li>
		</c:if>
	</ul>
  </div>
</nav>


<div class="container">