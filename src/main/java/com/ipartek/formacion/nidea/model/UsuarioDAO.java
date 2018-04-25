package com.ipartek.formacion.nidea.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ipartek.formacion.nidea.pojo.Rol;
import com.ipartek.formacion.nidea.pojo.Usuario;
import com.ipartek.formacion.nidea.util.Utilidades;
import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class UsuarioDAO implements Persistible<Usuario> {

	private static UsuarioDAO INSTANCE = null;

	private UsuarioDAO() {
	}

	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UsuarioDAO();
		}
	}

	public static UsuarioDAO getInstance() {
		if (INSTANCE == null) {
			createInstance();
		}
		return INSTANCE;
	}

	/**
	 * Buscamos un usuario por nombre y password
	 * 
	 * @param nombre
	 *            String nombre del Usuario
	 * @param pass
	 *            String password del Usuario
	 * @return Usuario si existe, null si no lo encuentra
	 */
	public Usuario check(String nombre, String pass) {
		Usuario resul = null;
		String sql = "SELECT u.id as 'usuario_id', u.nombre as 'usuario_nombre', u.password, r.id as 'rol_id', r.nombre as 'rol_nombre' "
				+ "FROM usuario as u, rol as r " + "WHERE u.id_rol = r.id AND u.nombre=? and u.password = ?;";
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {
			pst.setString(1, nombre);
			pst.setString(2, pass);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					resul = mapper(rs);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resul;
	}
	
	/**
	 *  Lista de usuarios solo por id y nombre, usar solo para la API REST
	 * @param nombre
	 * @return
	 */
	public List<Usuario> getAllApiByName(String nombre) {
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		String sql = "SELECT u.`id`,  u.`nombre`  "
				+ "FROM usuario as u  WHERE u.nombre LIKE ? "
				+ "ORDER BY u.`nombre` ASC LIMIT 500";

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setString(1, "%" + nombre + "%");
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {					
					Usuario u = new Usuario();
					u.setId(rs.getInt("id"));
					u.setNombre(rs.getString("nombre"));					
					lista.add(u);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<Usuario> getAll() {
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		String sql = "SELECT u.`id` as usuario_id, u.`nombre` as usuario_nombre, u.password, r.id as rol_id, r.nombre as rol_nombre  "
				+ "FROM usuario as u, rol as r  WHERE u.id_rol = r.id "
				+ "ORDER BY u.`nombre` ASC LIMIT 500";

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(sql);
				ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				lista.add(mapper(rs));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public Usuario getById(int id) {
		Usuario usuario = new Usuario();
		String sql = 
		"SELECT u.`id` as usuario_id, u.`nombre` as usuario_nombre, u.password, r.id as rol_id, r.nombre as rol_nombre  "
		+ "FROM usuario as u, rol as r  WHERE u.id_rol = r.id "
		+ "AND u.`id` = ?;";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setInt(1, id);
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					usuario = mapper(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
	}

	@Override
	public boolean save(Usuario pojo) throws MySQLIntegrityConstraintViolationException, MysqlDataTruncation {
		boolean resultado = false;

		if (pojo != null) {			

			if (pojo.getId() == -1) {
				resultado = crear(pojo);
			} else {
				resultado = modificar(pojo);
			}
		}

		return resultado;
	}

	private boolean modificar(Usuario pojo) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean crear(Usuario pojo) throws MySQLIntegrityConstraintViolationException, MysqlDataTruncation {
		boolean resultado = false;
		String sql = "INSERT INTO `nidea`.usuario (nombre, password, mail, id_rol) VALUES (?, ?, ?, ?);";
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getPass());	
			pst.setString(3, pojo.getMail());	
			pst.setInt(4, Usuario.ROL_USER);	
			
			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				// recuperar ID generado de forma automatica
				try (ResultSet rs = pst.getGeneratedKeys()) {
					while (rs.next()) {
						pojo.setId(rs.getInt(1));
						resultado = true;
					}
				}
			}

		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("Usuario duplicado");
			throw e;
		} catch (MysqlDataTruncation e) {
			System.out.println("Nombre muy largo");
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultado;
	}
	
	

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Usuario mapper(ResultSet rs) throws SQLException {
		Usuario u = new Usuario();
		u.setId(rs.getInt("usuario_id"));
		int idU=u.getId();
		u.setNombre(rs.getString("usuario_nombre"));
		u.setPass(rs.getString("password"));

		// Rol del usuario
		Rol rol = new Rol();
		rol.setId(rs.getInt("rol_id"));
		rol.setNombre(rs.getString("rol_nombre"));
		u.setRol(rol);

		return u;
	}
	
	
}
