package com.ipartek.formacion.nidea.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ipartek.formacion.nidea.pojo.Rol;
import com.ipartek.formacion.nidea.pojo.Usuario;

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

		@Override
		public ArrayList<Usuario> getAll() {

			ArrayList<Usuario> lista = new ArrayList<Usuario>();
			String sql = "SELECT u.id as 'usuario_id', u.nombre as 'usuario_nombre', u.password, r.id as 'rol_id', r.nombre as 'rol_nombre'"
						+"FROM usuario as u, rol as r " 
						+ "WHERE u.id_rol = r.id;";

			try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {
				try (ResultSet rs = pst.executeQuery();) {
					Usuario m = null;
					while (rs.next()) {
						m = mapper(rs);
						lista.add(m);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return lista;

		}
		/**
		 * Lista de usuarios solo con id Nombre, usar solo para la API REST
		 * @param nombre String con el nombre a buscar
		 * @return
		 */
		public List<Usuario> getAllApiByName(String nombre) {

			ArrayList<Usuario> lista = new ArrayList<Usuario>();
			String sql = "SELECT id, nombre FROM usuario WHERE nombre like ? ORDER BY nombre ASC LIMIT 500;";

			try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {
				pst.setString(1, "%"+nombre+"%");
				try (ResultSet rs = pst.executeQuery();) {
					Usuario usuario = null;
					while (rs.next()) {
						usuario = new Usuario();
						usuario.setNombre(rs.getString("nombre"));
						usuario.setId(rs.getInt("id"));
						
						lista.add(usuario);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return lista;

		}
	@Override
	public Usuario getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(Usuario pojo) {
		// TODO Auto-generated method stub
		return false;
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
