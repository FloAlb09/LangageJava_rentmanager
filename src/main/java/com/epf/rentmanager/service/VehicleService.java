package com.epf.rentmanager.service;

//Importation
import java.util.List;
import java.util.Optional;

//exception
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;

//modele
import com.epf.rentmanager.modele.Vehicle;

//dao
import com.epf.rentmanager.dao.VehicleDao;

//Classe
public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService vehicleInstance;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getVehicleInstance();
	}
	
	public static VehicleService getVehicleInstance() {
		if (vehicleInstance == null) {
			vehicleInstance = new VehicleService();
		}
		
		return vehicleInstance;
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return VehicleDao.getVehicleInstance().findAll(); //cree un nouveau vehicule
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération du vehicule");
		}
	}

	public Optional<Vehicle> findById(long id) throws ServiceException {
		try {
			Optional<Vehicle> vehicle = vehicleDao.findById(id);
			if (vehicle != null){
				return vehicle;
			}
			throw new ServiceException("Le vehicule n°"+ id + "n'a pas été trouvé dans la base de données");
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération du vehicule");
		}
	}

	public long create(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la création du véhicule");
		}
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
		try {
			return this.vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long count() throws ServiceException {
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
