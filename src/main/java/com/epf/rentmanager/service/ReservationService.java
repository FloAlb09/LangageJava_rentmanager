package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;

import com.epf.rentmanager.modele.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private ReservationDao reservationDao;
    private ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            return this.reservationDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Reservation> findResaByClientId(long client_id) throws ServiceException {
        try {
            return this.reservationDao.findResaByClientId(client_id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Reservation> findResaByVehicle(long vehicle_id) throws ServiceException {
        try {
            return this.reservationDao.findResaByVehicleId(vehicle_id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }
    public long create(Reservation reservation) throws ServiceException {
        try {
            return this.reservationDao.create(reservation);
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
            return this.reservationDao.count();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
