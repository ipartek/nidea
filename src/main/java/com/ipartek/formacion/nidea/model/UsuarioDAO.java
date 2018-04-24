package com.ipartek.formacion.nidea.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import com.ipartek.formacion.nidea.pojo.Rol;
import com.ipartek.formacion.nidea.pojo.Usuario;
import com.ipartek.formacion.nidea.util.Utilidades;

public class UsuarioDAO implements Persistible<Usuario> {

	private static UsuarioDAO INSTANCE = null;

	// Private constructor para que no se pueda hacer new y crear N instancias
	private UsuarioDAO() {
	}

	// creador sincronizado para protegerse de posibles problemas multi-hilo
	// otra prueba para evitar instanciación múltiple

	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UsuarioDAO();
		}
	}

	public static UsuarioDAO getInstance() {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	}

	@Override
	public ArrayList<Usuario> getAll() {
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		String sql = "SELECT u.id as 'usuario_id', u.nombre as 'usuario_nombre', u.password, u.email, r.id as 'rol_id', r.nombre as 'rol_nombre'" + 
						"FROM usuario as u, rol as r WHERE u.id_rol = r.id ORDER BY u.`id` DESC LIMIT 500;";
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {
			// Class.forName("com.mysql.jdbc.Driver");
			// final String URL =
			// "jdbc:mysql://192.168.0.42/spoty?user=alumno&password=alumno";
			// con = DriverManager.getConnection(URL);

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
	 * Lista de usuarios solo con id y nombre. Usar solo para la API REST
	 * @param nombre String con el nombre a buscar
	 * @return
	 */
	public ArrayList<Usuario> getAllApiByName(String nombre) {
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		String sql = "SELECT id, nombre FROM usuario WHERE nombre LIKE ? ORDER BY nombre ASC LIMIT 15;";
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
		Usuario u = null;
		String sql = "SELECT u.id as 'usuario_id', u.nombre as 'usuario_nombre', u.password, u.email, r.id as 'rol_id', r.nombre as 'rol_nombre'" + 
						"FROM usuario as u, rol as r WHERE u.id_rol = r.id AND u.`id` = ?;";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {
			pst.setInt(1, id);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					u = mapper(rs);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}

	@Override
	public boolean save(Usuario pojo) throws SQLIntegrityConstraintViolationException {
		boolean resul = false;
		try {

			// sanitizar el nombre
			pojo.setNombre(Utilidades.limpiarEspacios(pojo.getNombre()));

			if (pojo != null) {
				if (pojo.getId() == -1) {
					resul = crear(pojo);
				} else {
					resul = modificar(pojo);
				}
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new SQLIntegrityConstraintViolationException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resul;
	}

	private boolean modificar(Usuario pojo) throws SQLIntegrityConstraintViolationException {
		boolean resul = false;

		String sql = "UPDATE `usuario` SET `nombre`=?, `password`=?, `email`=?, `id_rol'=? WHERE `id`=?;";
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {
			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getPass());
			pst.setString(3, pojo.getEmail());
			pst.setInt(4, pojo.getRol().getId());
			pst.setInt(5, pojo.getId());

			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				resul = true;
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new SQLIntegrityConstraintViolationException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resul;
	}
	
	private boolean modificarByUser(Usuario pojo) throws SQLIntegrityConstraintViolationException {
		boolean resul = false;

		String sql = "UPDATE `usuario` SET `nombre`=?,`password`=?,`email`=? WHERE `id`=?;";
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {
			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getPass());
			pst.setString(3, pojo.getEmail());
			pst.setInt(4, pojo.getId());

			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				resul = true;
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new SQLIntegrityConstraintViolationException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resul;
	}

	private boolean crear(Usuario pojo) throws SQLIntegrityConstraintViolationException {
		boolean resul = false;

		String sql = "INSERT INTO `usuario` (`nombre`, `password`, `email`, `id_rol`) VALUES (?, ?, ?, ?);";
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getPass());
			pst.setString(3, pojo.getEmail());
			pst.setInt(4, pojo.getRol().getId());

			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				try (ResultSet rs = pst.getGeneratedKeys()) {
					while (rs.next()) {
						pojo.setId(rs.getInt(1));
					}
					resul = true;
				}
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new SQLIntegrityConstraintViolationException();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resul;
	}

	@Override
	public boolean delete(int id) {
		boolean resul = false;
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = "DELETE FROM `usuario` WHERE `id`= ?;";
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				resul = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resul;
	}

	public Usuario check(String nombre, String pass) {
		Usuario resul = null;
		String sql = "SELECT u.id as 'usuario_id', u.nombre as 'usuario_nombre', u.password, u.email, r.id as 'rol_id', r.nombre as 'rol_nombre' "
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
	public Usuario mapper(ResultSet rs) throws SQLException {
		Usuario u = new Usuario();
		u.setId(rs.getInt("usuario_id"));
		u.setNombre(rs.getString("usuario_nombre"));
		u.setPass(rs.getString("password"));
		u.setEmail(rs.getString("email"));

		// Rol del usuario
		Rol rol = new Rol();
		rol.setId(rs.getInt("rol_id"));
		rol.setNombre(rs.getString("rol_nombre"));
		u.setRol(rol);

		return u;
	}
	
}

