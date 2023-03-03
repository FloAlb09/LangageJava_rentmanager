package com.epf.rentmanager.dao;

//Importation
import java.sql.*;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

//exception
import com.epf.rentmanager.exception.DaoException;
//modele
import com.epf.rentmanager.modele.Reservation;
//persistence
import com.epf.rentmanager.persistence.ConnectionManager;

//Classe
public class ReservationDao {

	private static ReservationDao resrevationInstance = null;
	private ReservationDao() {}
	public static ReservationDao getResrevationInstance() {
		if(resrevationInstance == null) {
			resrevationInstance = new ReservationDao();
		}
		return resrevationInstance;
	}

	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(id) AS count FROM Reservation;";

	public List<Reservation> findAll() throws DaoException {
		//"SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;"
		ArrayList<Reservation> listReservation = new ArrayList<Reservation>();
		try (Connection con = ConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement(FIND_RESERVATIONS_QUERY);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				long id = rs.getLong("id");
				long client_id = rs.getLong("client_id");
				long vehicle_Id = rs.getLong("vehicle_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				Reservation reservation = new Reservation(id, client_id, vehicle_Id, debut, fin);
				listReservation.add(reservation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listReservation;
	}

	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		//"SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;"
		try (Connection con = ConnectionManager.getConnection()) {
			ArrayList<Reservation> listReservationByClient = new ArrayList<Reservation>();
			PreparedStatement ps = con.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			ps.setLong(1, clientId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				Long id = rs.getLong("id");
				Long vehicle_id = rs.getLong("vehicle_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				Reservation reservation = new Reservation(id, vehicle_id, debut, fin);
				listReservationByClient.add(reservation);
			}
			return listReservationByClient;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		//"SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;"
		try (Connection con = ConnectionManager.getConnection()) {
			ArrayList<Reservation> listReservationByVehicle = new ArrayList<Reservation>();
			PreparedStatement ps = con.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			ps.setLong(1, vehicleId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				long id = rs.getLong("id");
				long client_id = rs.getLong("client_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				Reservation reservation = new Reservation(id, client_id, debut, fin);
				listReservationByVehicle.add(reservation);
			}
			return listReservationByVehicle;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public long create(Reservation reservation) throws DaoException {
		//"INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);"
		Long client_id = reservation.getClient_id();
		Long vehicle_id = reservation.getVehicle_id();
		Date debut = Date.valueOf(reservation.getDebut());
		Date fin = Date.valueOf(reservation.getFin());
		try (Connection con = ConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, client_id);
			ps.setLong(2, vehicle_id);
			ps.setDate(3, debut);
			ps.setDate(4, fin);
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

	public long delete(Reservation reservation) throws DaoException {
		//"DELETE FROM Reservation WHERE id=?;"
		Long id = reservation.getId();
		//renvoie 1 si le delete a ete effectue, 0 sinon
		try (Connection con = ConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement(DELETE_RESERVATION_QUERY);
			ps.setLong(1, id);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long count() throws DaoException {
		//"SELECT COUNT(id) AS count FROM Reservation;"
		try (Connection con = ConnectionManager.getConnection()) {
			//statement permettant d'injecter une val au lieu des ? de la query securite envers les injections SQL
			PreparedStatement ps = con.prepareStatement(COUNT_RESERVATIONS_QUERY);
			ResultSet rs = ps.executeQuery();
			//moves the cursor to the last row in this ResultSet object.
			rs.last();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
