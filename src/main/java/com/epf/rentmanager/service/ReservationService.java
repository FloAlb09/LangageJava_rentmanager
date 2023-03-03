package com.epf.rentmanager.service;

//Importation
import java.util.ArrayList;
import java.util.List;

//dao
import com.epf.rentmanager.dao.ReservationDao;
//exception
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
//modele
import com.epf.rentmanager.modele.Reservation;

//Classe
public class ReservationService {

    private ReservationDao reservationDao;
    public static ReservationService reservationInstance;

    private ReservationService() {
        this.reservationDao = ReservationDao.getResrevationInstance();
    }

    public static ReservationService getReservationInstance() {
        if (reservationInstance == null) {
            reservationInstance = new ReservationService();
        }

        return reservationInstance;
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            ArrayList<Reservation> allReservation = (ArrayList<Reservation>) reservationDao.findAll();
            return allReservation;
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération des reservations");
        }
    }

    public List<Reservation> findResaByClientId(long client_id) throws ServiceException {
        if (client_id <=0) {
            throw new ServiceException("Ce client n'existe pas ou n'a pas de reservation");
        }
        try {
            ArrayList<Reservation> allResaByClient = (ArrayList<Reservation>) reservationDao.findResaByClientId(client_id);
            if (allResaByClient != null){
                return allResaByClient;
            }
            throw new ServiceException("L'utilisateur n°"+ client_id + "n'est pas associé à des reservations dans la base de données");
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération de l'utilisateur");
        }
    }

    public List<Reservation> findResaByVehicle(long vehicle_id) throws ServiceException {
        if (vehicle_id <=0) {
            throw new ServiceException("Ce vehicule n'existe pas ou n'a pas de reservation");
        }
        try {
            ArrayList<Reservation> allResaByVehicle = (ArrayList<Reservation>) reservationDao.findResaByVehicleId(vehicle_id);
            if (allResaByVehicle != null){
                return allResaByVehicle;
            }
            throw new ServiceException("Le vehicule n°"+ vehicle_id + "n'est pas associé à des reservations dans la base de données");
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la récupération du vehicule");
        }
    }

    public long create(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur a eu lieu lors de la création de la reservation");
        }
    }

    public long delete(Reservation reservation) throws ServiceException {
        try {
            return this.reservationDao.delete(reservation);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long count() throws ServiceException {
        try {
            return reservationDao.count();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
