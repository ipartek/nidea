<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.ipartek.formacion.nidea.pojo.Material"%>
<%@page import="java.util.ArrayList"%>

<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>



<h1>Backoffice</h1>

<a href="backoffice/materiales" type="button" class="btn btn-primary">Materiales</a>
<a href="backoffice/usuarios" type="button" class="btn btn-primary">Usuarios</a>
<a href="backoffice/roles" type="button" class="btn btn-primary">Roles</a>



<%@include file="/templates/footer.jsp" %>