<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<div id="login">

  <form class="form-signin" action="login" method="post">     

      <div class="form-label-group">
        <input type="text" class="form-control"
               name="id_usuario" 
               value="administrador"
               placeholder="Nombre Usuario" 
               required autofocus>
               
        <label for="usuario">Nombre Usuario</label>
      </div>

      <div class="form-label-group">
        <input type="password" 
               name="password"
               value="123456" 
               class="form-control" 
               placeholder="Contraseņa" required>
               
        <label for="password">Contraseņa</label>
      </div>
     
      <button class="btn btn-lg btn-primary btn-block" type="submit">Entrar</button>
      <a href="form.jsp" class="list-group-item">Registrate</a>
    </form>

</div>
<jsp:include page="templates/footer.jsp"></jsp:include>