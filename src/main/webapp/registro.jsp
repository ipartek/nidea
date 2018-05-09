<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/templates/head.jsp"%>
<%@include file="/templates/navbar.jsp"%>
<%@include file="/templates/alert.jsp"%>


<<<<<<< HEAD
=======

 <script src="js/registro.js"></script>
>>>>>>> branch 'master_jorge' of https://github.com/ipartek/nidea.git
 
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="well well-sm">
                <form class="form-signin" action="registro" method="post">
                    <fieldset>
                       

                        <div class="form-group">
<<<<<<< HEAD
                            <span class="col-md-1 col-md-offset-2 text-center"><i class="fa fa-user bigicon"></i></span>
                            <div class="col-md-8">
                                <input id="nombre" name="nombre" type="text" placeholder="Nombre" class="form-control">
=======
                            <span class="col-md-1 col-md-offset-2"><i class="fa fa-user bigicon"></i></span>
                            <div class="col-md-8 ">
                                <input id="fname" name="nombre" type="text" placeholder="Nombre" class="form-control" onblur="confusuario()">
>>>>>>> branch 'master_jorge' of https://github.com/ipartek/nidea.git
                            </div>
                        </div>
                     
						
                        <div class="form-group">
                            <span class="col-md-1 col-md-offset-2 text-center"><i class="fa fa-lock"></i></span>
                            <div class="col-md-8">
                                <input id="password" name="password" type="password" placeholder="Contraseña" class="form-control" >
                            </div>
                        </div>
                               <div class="col-md-3">
                <div class="form-control-feedback">
                        <span id="error_pass" class="text-danger align-middle">
                            <i class="fa fa-close"> Contraseña no coincide</i>
                        </span>
                </div>
            </div>
        </div>

                        <div class="form-group">
                            <span class="col-md-1 col-md-offset-2 text-center"><i class="fa fa-unlock-alt"></i></span>
                            <div class="col-md-8">
                                <input id="rePassword" name="rePassword" type="password" placeholder="Repetir Contraseña" class="form-control" onblur="confpassword()">
                            </div>
                             
                        </div>

                      

                        <div class="form-group">
                            <div class="col-md-12 block">
                                <button type="submit" id="boton" disabled class="btn btn-success"><i class="fa fa-user-plus"></i>Registrarse</button>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>










<script src="js/registro.js?v2"></script>




<jsp:include page="/templates/footer.jsp"></jsp:include>