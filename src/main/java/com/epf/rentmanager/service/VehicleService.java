package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;

import com.epf.rentmanager.modele.Vehicle;

import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;

	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	public List<Vehicle> findAll() throws ServiceException {
		List<Vehicle> vehicleList = new ArrayList<>();
		try {
			vehicleList = this.vehicleDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return vehicleList;
	}

	public Vehicle findById(long id) throws ServiceException {
		try {
			return this.vehicleDao.findById(id).get();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	public long create(Vehicle vehicle) throws ServiceException {
		try {
			return this.vehicleDao.create(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long update(Vehicle vehicle, Long id) throws ServiceException {
		try {
			return this.vehicleDao.update(vehicle, id);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long delete(Vehicle vehicle) throws ServiceException {
		long numberDeleted = 0;

		try {
			numberDeleted = this.vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return numberDeleted;
	}

	public long count() throws ServiceException {
		try {
			return this.vehicleDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Vehicle> findVehicleByUser(long client_id) throws ServiceException {
		try{
			return this.vehicleDao.findVehicleByUser(client_id);
		}catch (DaoException e){
			e.printStackTrace();
		}
		return null;
	}

	public long countVehicleByUser(long client_id) throws ServiceException {
		try {
			return this.vehicleDao.countVehicleByUser(client_id);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
