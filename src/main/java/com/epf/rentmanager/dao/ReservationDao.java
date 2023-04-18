package com.epf.rentmanager.dao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.modele.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;


@Repository
public class ReservationDao {
    private ReservationDao() {
    }

    private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
    private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
    private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT Reservation.id, Client.nom, Client.prenom, Client.email, Reservation.debut, Reservation.fin FROM Reservation INNER JOIN Client ON Reservation.client_id = Client.id WHERE vehicle_id=?;";
    private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
    private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
    private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(id) AS count FROM Reservation;";
    private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?";
    private static final String COUNT_RESERVATIONS_BY_USER_QUERY = "SELECT COUNT(*) FROM Reservation INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id WHERE client_id=?;";
    private static final String COUNT_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT COUNT(*)  FROM Reservation INNER JOIN Client ON Reservation.client_id = Client.id WHERE vehicle_id=?;";
    private static final String FIND_RESERVATION_QUERY = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
    private static final String FIND_RESERVATIONS_INFO_QUERY = "SELECT Reservation.id, Reservation.client_id, Client.nom, Client.prenom, Reservation.vehicle_id, Vehicle.constructeur, Reservation.debut, Reservation.fin FROM ((Reservation INNER JOIN Client ON Reservation.client_id = Client.id) INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id);";

    public List<Reservation> findAll() throws DaoException {
        ArrayList<Reservation> listReservation = new ArrayList<>();
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(FIND_RESERVATIONS_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                long  client_id = rs.getLong("client_id");
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

    public List<Reservation> findResaByClientId(long client_id) throws DaoException {
        try (Connection con = ConnectionManager.getConnection()) {
            ArrayList<Reservation> listReservationByClient = new ArrayList<Reservation>();
            PreparedStatement ps = con.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
            ps.setLong(1, client_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("id");
                Long vehicle_id = rs.getLong("vehicle_id");
                LocalDate debut = rs.getDate("debut").toLocalDate();
                LocalDate fin = rs.getDate("fin").toLocalDate();
                Reservation reservation = new Reservation(id, client_id, vehicle_id, debut, fin);
                listReservationByClient.add(reservation);
            }
            return listReservationByClient;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Reservation> findResaByVehicleId(long vehicle_id) throws DaoException {
        try (Connection con = ConnectionManager.getConnection()) {
            ArrayList<Reservation> listReservationByVehicle = new ArrayList<Reservation>();
            PreparedStatement ps = con.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
            ps.setLong(1, vehicle_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long reservation_id = rs.getLong("Reservation.id");
                String client = rs.getString("Client.nom") + " " + rs.getString("Client.prenom");
                String client_email = rs.getString("Client.email");
                LocalDate reservation_debut = rs.getDate("Reservation.debut").toLocalDate();
                LocalDate reservation_fin = rs.getDate("Reservation.fin").toLocalDate();
                Reservation reservation = new Reservation(reservation_id, client, client_email, reservation_debut, reservation_fin);
                listReservationByVehicle.add(reservation);
            }
            return listReservationByVehicle;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long create(Reservation reservation) throws DaoException {
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

    public long delete(Reservation reservation) throws DaoException {
        Long id = reservation.getId();
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
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(COUNT_RESERVATIONS_QUERY);
            ResultSet rs = ps.executeQuery();
            rs.last();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long update(Reservation reservation, Long id) throws DaoException {
        long client_id = reservation.getClient_id();
        long vehicle_id = reservation.getVehicle_id();
        LocalDate debut = reservation.getDebut();
        LocalDate fin = reservation.getFin();
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(UPDATE_RESERVATION_QUERY);
            ps.setLong(1, client_id);
            ps.setLong(2, vehicle_id);
            ps.setDate(3, Date.valueOf(debut));
            ps.setDate(4, Date.valueOf(fin));
            ps.setLong(5, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long countResaByUser(long client_id) throws DaoException {
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(COUNT_RESERVATIONS_BY_USER_QUERY);
            ps.setLong(1,client_id);
            ResultSet rs = ps.executeQuery();
            rs.last();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long countResaByVehicle(long vehicle_id) throws DaoException {
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(COUNT_RESERVATIONS_BY_VEHICLE_QUERY);
            ps.setLong(1,vehicle_id);
            ResultSet rs = ps.executeQuery();
            rs.last();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Optional<Reservation> findById(long id) throws DaoException {
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(FIND_RESERVATION_QUERY);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            long clientId = rs.getLong("client_id");
            long vehicleId = rs.getLong("vehicle_id");
            LocalDate debut = rs.getDate("debut").toLocalDate();
            LocalDate fin = rs.getDate("fin").toLocalDate();
            Reservation reservation = new Reservation(id, clientId, vehicleId, debut, fin);
            return Optional.of(reservation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Reservation> findInfoAll() throws DaoException {
        ArrayList<Reservation> listReservation = new ArrayList<>();
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(FIND_RESERVATIONS_INFO_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("Reservation.id");
                long client_id = rs.getLong("Reservation.client_id");
                String client_info = rs.getString("Client.nom") + " " + rs.getString("Client.prenom");
                long vehicle_id = rs.getLong("Reservation.vehicle_id");
                String vehicule_info = rs.getString("Vehicle.constructeur");
                LocalDate debut = rs.getDate("Reservation.debut").toLocalDate();
                LocalDate fin = rs.getDate("Reservation.fin").toLocalDate();
                Reservation reservation = new Reservation(id, client_id, vehicle_id, client_info, vehicule_info, debut,
                        fin);
                listReservation.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listReservation;
    }

    public long update(Reservation reservation, long id) throws DaoException {
        long client_id = reservation.getClient_id();
        long vehicle_id = reservation.getVehicle_id();
        LocalDate debut = reservation.getDebut();
        LocalDate fin = reservation.getFin();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(UPDATE_RESERVATION_QUERY);
            pstmt.setLong(1, client_id);
            pstmt.setLong(2, vehicle_id);
            pstmt.setDate(3, Date.valueOf(debut));
            pstmt.setDate(4, Date.valueOf(fin));
            pstmt.setLong(5, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
