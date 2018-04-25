package com.ipartek.formacion.nidea.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.ipartek.formacion.nidea.model.UsuarioDAO;
import com.ipartek.formacion.nidea.pojo.Alert;
import com.ipartek.formacion.nidea.pojo.Material;
import com.ipartek.formacion.nidea.pojo.RegistroForm;
import com.ipartek.formacion.nidea.pojo.Rol;
import com.ipartek.formacion.nidea.pojo.Usuario;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * Servlet implementation class RegistroController
 */
@WebServlet("/registro")
public class RegistroController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int USUARIO = 2;
	
	private String view = "";
	private static final String VIEW_REGISTRO = "registro.jsp";
	private static final String VIEW_LOGIN = "login.jsp";
	
	
	private UsuarioDAO daoUsuario;
	
	ValidatorFactory factory;
	Validator validator;
	
       
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		daoUsuario = UsuarioDAO.getInstance();
	}

	@Override
	public void destroy() {
		super.destroy();
		factory=null;
		validator =null;
		daoUsuario = null;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Alert alert =null;
		try {
			alert = new Alert();

			// recoger parametros
			String nombre = request.getParameter("nombre");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String rePassword = request.getParameter("rePassword");
			RegistroForm form = new RegistroForm (nombre, email, password, rePassword);
			
			//validar parametros
			if (rePassword !=null && password.equals(rePassword)) {
				
				
				Set<ConstraintViolation<RegistroForm>> violations = validator.validate(form);
				if (violations.size() == 0) {
					//validacion ok => insert y VIEW_LOGIN
					Usuario usuario = new Usuario();
					usuario.setNombre(nombre);
					usuario.setMail(email);
					usuario.setPass(password);
					if (daoUsuario.save(usuario)) {
						alert = new Alert("Registro correcto, introduce credencias", Alert.TIPO_PRIMARY);
						view =VIEW_LOGIN;
					} else {
						alert = new Alert("Lo sentimos pero ya existe el usuario o mail", Alert.TIPO_WARNING);
						view =VIEW_REGISTRO;
					}					
					
				} else {				
					//validacion error => VIEW_REGISTRO
					String mensajes = "";
					for (ConstraintViolation<RegistroForm> violation : violations) {
						mensajes +=violation.getPropertyPath() +": "+  violation.getMessage() + "<br>";						
					}
					alert = new Alert(mensajes, Alert.TIPO_WARNING);
					view =VIEW_REGISTRO;
				}				
			}			
			
		}catch (MySQLIntegrityConstraintViolationException e) {			
			view = VIEW_REGISTRO;
			alert = new Alert("Lo sentimos usuario ya existe", Alert.TIPO_WARNING);
		} catch (Exception e) {
			e.printStackTrace();
			view = VIEW_REGISTRO;
			alert = new Alert();
		} finally {
			request.setAttribute("alert", alert);
			request.getRequestDispatcher(view).forward(request, response);
		}		
	}
}
