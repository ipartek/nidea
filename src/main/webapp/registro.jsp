<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/templates/head.jsp"%>
<%@include file="/templates/navbar.jsp"%>
<%@include file="/templates/alert.jsp"%>


 <script src="js/registro.js"></script>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="well well-sm">
                <form class="form-signin" action="registro" method="post">
                    <fieldset>
                       

                        <div class="form-group">
                            <span class="col-md-1 col-md-offset-2 text-center"><i class="fa fa-user bigicon"></i></span>
                            <div class="col-md-8">
                                <input id="fname" name="nombre" type="text" placeholder="Nombre" class="form-control" onblur="confusuario()">
                            </div>
                        </div>
                     
						<script src="js/registro.js"></script>
                        <div class="form-group">
                            <span class="col-md-1 col-md-offset-2 text-center"><i class="fa fa-lock"></i></span>
                            <div class="col-md-8">
                                <input id="fcontraseña" name="password" type="password" placeholder="Contraseña" class="form-control" >
                            </div>
                        </div>

                        <div class="form-group">
                            <span class="col-md-1 col-md-offset-2 text-center"><i class="fa fa-unlock-alt"></i></span>
                            <div class="col-md-8">
                                <input id="fcontraseña2" name="contraseñarepe" type="password" placeholder="Repetir Contraseña" class="form-control" onblur="confpassword()">
                            </div>
                             
                        </div>

                      

                        <div class="form-group">
                            <div class="col-md-12 block">
                              <button class="btn btn-primary btn-block" type="submit" >Registrarse</button>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>















<jsp:include page="/templates/footer.jsp"></jsp:include>