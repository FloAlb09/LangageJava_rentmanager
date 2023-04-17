package com.epf.rentmanager.dao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.modele.Client;
import com.epf.rentmanager.persistence.ConnectionManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	
	private ClientDao(){

	}

	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom = ?, prenom = ?, email = ?, naissance = ? WHERE id\n" + "= ?;";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(id) AS count FROM Client;";

	private static final String FIND_CLIENTS_BY_VEHICLE_QUERY = "SELECT DISTINCT Client.id, Client.nom, Client.prenom, Client.email FROM Client INNER JOIN Reservation ON Reservation.client_id = Client.id WHERE vehicle_id=?;";
	private static final String COUNT_CLIENTS_BY_VEHICLE_QUERY = "SELECT COUNT(DISTINCT Reservation.client_id) FROM Client INNER JOIN Reservation ON Reservation.client_id = Client.id WHERE vehicle_id=?;";



	public ArrayList<Client> findAll() throws DaoException {
		ArrayList<Client> listClient = new ArrayList<Client>();
		try (Connection con = ConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement(FIND_CLIENTS_QUERY);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				long id = rs.getLong("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				LocalDate naissance = rs.getDate("naissance").toLocalDate();
				Client client = new Client(id, nom, prenom, email, naissance);
				listClient.add(client);
			}
			return listClient;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Optional<Client> findById(long id) throws DaoException {
		try (Connection con = ConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement(FIND_CLIENT_QUERY);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String email = rs.getString("email");
			LocalDate naissance = rs.getDate("naissance").toLocalDate();
			Client client = new Client(nom, prenom, email, naissance);
			return Optional.of(client);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public long create(Client client) throws DaoException {
		String nom = client.getNom();
		String prenom = client.getPrenom();
		String email = client.getEmail();
		Date naissance = Date.valueOf(client.getNaissance());
		try (Connection con = ConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, nom);
			ps.setString(2, prenom);
			ps.setString(3, email);
			ps.setDate(4, naissance);
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			long id=0;
			if (rs.next()) {
				id = rs.getLong(1);
			}
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long update(Client client, Long id) throws DaoException {
		String nom = client.getNom();
		String prenom = client.getPrenom();
		String email = client.getEmail();
		LocalDate naissance = client.getNaissance();
		try (Connection con = ConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement(UPDATE_CLIENT_QUERY);
			ps.setString(1,nom);
			ps.setString(2, prenom);
			ps.setString(3, email);
			ps.setDate(4, Date.valueOf(naissance));
			ps.setLong(5, id);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long delete(Client client) throws DaoException {
		Long id = client.getId();
		try (Connection con = ConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement(DELETE_CLIENT_QUERY);
			ps.setLong(1, id);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long count() throws DaoException {
		try (Connection con = ConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement(COUNT_CLIENTS_QUERY);
			ResultSet rs = ps.executeQuery();
			rs.last();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public ArrayList<Client> findClientsByVehicle(long id_vehicle) throws DaoException {
		ArrayList<Client> listClient = new ArrayList<Client>();
		try {
			Connection con = ConnectionManager.getConnection();
			PreparedStatement ps = con.prepareStatement(FIND_CLIENTS_BY_VEHICLE_QUERY);
			ps.setLong(1, id_vehicle);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				long id_client = rs.getInt("Client.id");
				String nom = rs.getString("Client.nom");
				String prenom = rs.getString("Client.prenom");
				String email = rs.getString("Client.email");
				Client client = new Client(id_client, prenom, nom, email);
				listClient.add(client);
			}
			return listClient;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public long countClientsByVehicle(long id_vehicle) throws DaoException {
		try (Connection con = ConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement(COUNT_CLIENTS_BY_VEHICLE_QUERY);
			ps.setLong(1,id_vehicle);
			ResultSet rs = ps.executeQuery();
			rs.last();
			return rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
