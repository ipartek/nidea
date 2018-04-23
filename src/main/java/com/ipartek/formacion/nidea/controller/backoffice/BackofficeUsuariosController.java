package com.ipartek.formacion.nidea.controller.backoffice;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.nidea.model.RolDAO;
import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Alert;

/**
 * Servlet implementation class BackofficeUsuariosController
 */
@WebServlet("/backoffice/usuarios")
public class BackofficeUsuariosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	private static final String VIEW_FORM = "/backoffice/usuarios/form.jsp";
	private static final String VIEW_INDEX = "/backoffice/usuarios/index.jsp";

	public static final int OP_MOSTRAR_FORMULARIO = 1;
	public static final int OP_BUSQUEDA = 2;
	public static final int OP_ELIMINAR = 3;
	public static final int OP_GUARDAR = 4;

	private RequestDispatcher dispatcher;
	private Alert alert;
	private UsuarioDAO dao;

	private int id;
	private String nombre;

	private String search;
	private int op;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
