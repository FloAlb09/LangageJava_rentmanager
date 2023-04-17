package com.epf.rentmanager.dao;

import com.epf.rentmanager.modele.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.DaoException;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Repository;


@Repository
public class VehicleDao {

    private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";
    private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
    private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
    private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicule SET constructeur = ?, nb_places = ? WHERE\n" + "id = ?;";
    private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
    private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(id) AS count FROM Vehicle;";
    private static final String FIND_VEHICLE_BY_USER_QUERY = "SELECT DISTINCT Vehicle.id, Vehicle.constructeur, Vehicle.nb_places FROM Vehicle INNER JOIN Reservation ON Reservation.vehicle_id = Vehicle.id WHERE client_id=?;";
    private static final String COUNT_VEHICLE_BY_USER_QUERY = "SELECT COUNT(DISTINCT Reservation.vehicle_id) FROM Vehicle INNER JOIN Reservation ON Reservation.vehicle_id = Vehicle.id WHERE client_id=?;";

    private VehicleDao() {

    }

    public List<Vehicle> findAll() throws DaoException {
        ArrayList<Vehicle> listVehicle = new ArrayList<>();
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(FIND_VEHICLES_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
            Vehicle vehicle = new Vehicle(constructeur, nb_places);
            return Optional.of(vehicle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public long create(Vehicle vehicle) throws DaoException {
        String constructeur = vehicle.getConstructeur();
        int nb_places = vehicle.getNb_places();
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, constructeur);
            ps.setInt(2, nb_places);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            long id = 0;
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
        String constructeur = vehicle.getConstructeur();
        int nb_places = vehicle.getNb_places();
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(UPDATE_VEHICLE_QUERY);
            ps.setString(1, constructeur);
            ps.setInt(2, nb_places);
            ps.setLong(3, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long delete(Vehicle vehicle) throws DaoException {
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
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(COUNT_VEHICLES_QUERY);
            ResultSet rs = ps.executeQuery();
            rs.last();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Vehicle> findVehicleByUser(long client_id) throws DaoException {
        List<Vehicle> listVehicle = new ArrayList<>();
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(FIND_VEHICLE_BY_USER_QUERY);
            ps.setLong(1, client_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                long vehicle_id = rs.getLong("Vehicle.id");
                String constructeur = rs.getString("Vehicle.constructeur");
                int nb_places = rs.getInt("Vehicle.nb_places");
                Vehicle vehicle = new Vehicle(vehicle_id, constructeur, nb_places);
                listVehicle.add(vehicle);
            }
            return listVehicle;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long countVehicleByUser(long client_id) throws DaoException {
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(COUNT_VEHICLE_BY_USER_QUERY);
            ps.setLong(1, client_id);
            ResultSet rs = ps.executeQuery();
            rs.last();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
