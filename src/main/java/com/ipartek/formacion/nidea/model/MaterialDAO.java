package com.ipartek.formacion.nidea.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ipartek.formacion.nidea.pojo.Material;
import com.ipartek.formacion.nidea.pojo.Usuario;
import com.ipartek.formacion.nidea.util.Utilidades;
import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class MaterialDAO implements Persistible<Material> {

	private static MaterialDAO INSTANCE = null;

	// No se puede hacer news de esta clase pq se bloquea con este metodo al ser
	// private
	private MaterialDAO() {
	}

	// creador synchronyzed para protegerse de posibles problemas multi-hilo
	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MaterialDAO();
		}
	}

	// A este metodo pueden acceder multiples usuarios a la vez y ejecutarlo pero al
	// creador solo uno por estar synchronyzed
	public static MaterialDAO getInstance() {
		if (INSTANCE == null) {
			createInstance();
		}
		return INSTANCE;
	}

	/**
	 * Recupera todos los materiales de la BBDD ordenados por id descendente
	 * 
	 * @return ArrayList<Material> si no existen registros new ArrayList<Material>()
	 */
	@Override
	public ArrayList<Material> getAll() {

		ArrayList<Material> lista = new ArrayList<Material>();
		String sql = "SELECT m.id, m.nombre, precio, u.id as id_usuario, u.nombre as nombre_usuario FROM `material` as m, usuario as u WHERE u.id = m.id_usuario ORDER BY m.id DESC LIMIT 500;";

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

	public ArrayList<Material> getByName(String search) {

		ArrayList<Material> lista = new ArrayList<Material>();
		String sql = "SELECT m.id, m.nombre, precio, u.id as id_usuario, u.nombre as nombre_usuario FROM material as m, usuario as u WHERE u.id = m.id_usuario AND m.nombre LIKE ? ORDER BY m.id DESC LIMIT 500;";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {

			pst.setString(1, "%" + search + "%");

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					lista.add(mapper(rs));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public ArrayList<Material> getByNameAndUser(String search, int id_usuario) {

		ArrayList<Material> lista = new ArrayList<Material>();
		String sql = "SELECT m.id, m.nombre, precio, u.id as id_usuario, u.nombre as nombre_usuario FROM material as m, usuario as u WHERE u.id = m.id_usuario AND m.nombre LIKE ? AND m.id_usuario = ? ORDER BY m.id DESC LIMIT 500;";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {

			pst.setString(1, "%" + search + "%");
			pst.setInt(2, id_usuario);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					lista.add(mapper(rs));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public Material getById(int id) {

		Material m = null;
		String sql = "SELECT m.id, m.nombre, precio, u.id as id_usuario, u.nombre as nombre_usuario FROM material as m, usuario as u WHERE u.id = m.id_usuario AND m.id = ? ;";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {

			pst.setInt(1, id);

			try (ResultSet rs = pst.executeQuery()) {

				while (rs.next()) {
					m = mapper(rs);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return m;

	}

	public ArrayList<Material> getByUser(int id_usuario) {
		ArrayList<Material> lista = new ArrayList<Material>();
		String sql = "SELECT m.id as id, m.nombre as nombre, m.precio as precio, u.id as id_usuario, u.nombre as nombre_usuario FROM material as m, usuario as u WHERE m.id_usuario = u.id AND m.id_usuario = ?;";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {

			pst.setInt(1, id_usuario);
			try (ResultSet rs = pst.executeQuery()) {
				Material m = null;
				while (rs.next()) {
					m = new Material();
					m = mapper(rs);
					lista.add(m);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public boolean save(Material pojo) {
		boolean resultado = false;

		if (pojo != null) {

			// Sanitize nombre
			pojo.setNombre(Utilidades.limpiarEspacios(pojo.getNombre()));

			if (pojo.getId() == -1) {
				resultado = crear(pojo);
			} else {
				resultado = modificar(pojo);
			}
		}

		return resultado;
	}

	private boolean modificar(Material pojo) {

		boolean resultado = false;

		String sql = "UPDATE `material` SET `nombre`=?, `precio`=?, `id_usuario`=? WHERE  `id`=?;";

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {

			pst.setString(1, pojo.getNombre());
			pst.setFloat(2, pojo.getPrecio());
			pst.setInt(3, pojo.getUsuario().getId());
			pst.setInt(4, pojo.getId());

			int affectedRows = pst.executeUpdate();

			if (affectedRows == 1) {
				resultado = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultado;
	}

	private boolean crear(Material pojo) {
		boolean resultado = false;
		String sql = "INSERT INTO `material` (`nombre`, `precio`, `id_usuario`) VALUES (?, ?, ?);";

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			pst.setString(1, pojo.getNombre());
			pst.setFloat(2, pojo.getPrecio());
			pst.setInt(3, pojo.getUsuario().getId());

			int affetedRows = pst.executeUpdate();

			if (affetedRows == 1) {
				try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
					while (generatedKeys.next()) {
						pojo.setId(generatedKeys.getInt(1));
					}
				}
				resultado = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultado;
	}

	@Override
	public boolean delete(int id) {

		boolean resultado = false;
		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = ConnectionManager.getConnection();
			String sql = "DELETE FROM `material` WHERE  `id`= ?;";

			pst = con.prepareStatement(sql);
			pst.setInt(1, id);

			int affetedRows = pst.executeUpdate();

			if (affetedRows == 1) {
				resultado = true;
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

		return resultado;
	}

	@Override
	public Material mapper(ResultSet rs) throws SQLException {

		Material m = null;
		Usuario u = null;

		if (rs != null) {
			m = new Material();
			m.setId(rs.getInt("id"));
			m.setNombre(rs.getString("nombre"));
			m.setPrecio(rs.getFloat("precio"));

			u = new Usuario();
			u.setId(rs.getInt("id_usuario"));
			u.setNombre(rs.getString("nombre_usuario"));
			m.setUsuario(u);
		}

		return m;
	}

}
