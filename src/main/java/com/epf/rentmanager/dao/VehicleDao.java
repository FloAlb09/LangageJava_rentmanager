package com.epf.rentmanager.dao;
import com.epf.rentmanager.modele.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Repository;




@Repository
public class VehicleDao {

	private VehicleDao() {

	}

	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicule SET constructeur = ?, nb_places = ? WHERE\n" + "id = ?;";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(id) AS count FROM Vehicle;";

	public List<Vehicle> findAll() throws DaoException {
		//"SELECT id, constructeur, nb_places FROM Vehicle;"
		ArrayList<Vehicle> listVehicle = new ArrayList<>();
		try (Connection con = ConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement(FIND_VEHICLES_QUERY);
			//ResultSet : execution d'une requete → objet creer pour recuperer la donnee d'un database grace a jdbc → tableau de donnees avec un curseur (quelle ligne de donnees on lit)
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				//on recupere la donnee du ResultSet avec les methodes getInt, getString, ...
				long id = rs.getLong("id");
				String constructeur = rs.getString("constructeur");
				int nb_places = rs.getInt("nb_places");
				Vehicle vehicle = new Vehicle(id, constructeur, nb_places);
				listVehicle.add(vehicle);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listVehicle;
	}

	public Optional<Vehicle> findById(long id) throws DaoException {
		try (Connection con = ConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement(FIND_VEHICLE_QUERY);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String constructeur = rs.getString("constructeur");
			int nb_places = rs.getInt("nb_places");
			Vehicle vehicle =new Vehicle(constructeur, nb_places);
			return Optional.of(vehicle);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public long create(Vehicle vehicle) throws DaoException {
		//"INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);"
		String constructeur = vehicle.getConstructeur();
		int nb_places = vehicle.getNb_places();
		try (Connection con = ConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, constructeur);
			ps.setInt(2, nb_places);
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

	public long update(Vehicle vehicle, Long id) throws DaoException {
		//"UPDATE Vehicule SET constructeur = ?, nb_places = ? WHERE\n" + "id = ?;"
		String constructeur = vehicle.getConstructeur();
		int nb_places = vehicle.getNb_places();
		//renvoie 1 si le update a ete effectue, 0 sinon
		try (Connection con = ConnectionManager.getConnection()) { //connexion a un SGBD - creer l'objet de connexion
			//creer l'objet preparedStatement
			PreparedStatement ps = con.prepareStatement(UPDATE_VEHICLE_QUERY);
			//specifie le premier parametre de la requete - definit la valeur String du nom du client sur l'index 1 du parametre donne
			ps.setString(1,constructeur);
			ps.setInt(2, nb_places);
			ps.setLong(3, id);
			//executer la requete
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long delete(Vehicle vehicle) throws DaoException {
		//"DELETE FROM Vehicle WHERE id=?;"
		//renvoie 1 si le delete a ete effectue, 0 sinon
		Long id = vehicle.getId();
		try (Connection con = ConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement(DELETE_VEHICLE_QUERY);
			ps.setLong(1, id);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long count() throws DaoException {
		//"SELECT COUNT(id) AS count FROM Vehicle;"
		try (Connection con = ConnectionManager.getConnection()) {
			//statement permettant d'injecter une val au lieu des ? de la query securite envers les injections SQL
			PreparedStatement ps = con.prepareStatement(COUNT_VEHICLES_QUERY);
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
