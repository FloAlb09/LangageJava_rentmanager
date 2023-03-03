package com.epf.rentmanager.service;

//Importation
import java.util.List;

//exception
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;

//modele
import com.epf.rentmanager.modele.Client;
import com.epf.rentmanager.modele.Vehicle;

//dao
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;

//Classe
public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}

	public long update(Vehicle vehicle, Long id) throws ServiceException {
		try {
			return this.vehicleDao.update(vehicle, id);
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

	public long create(Vehicle vehicle) throws ServiceException {
		// TODO: créer un véhicule
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la création du véhicule");
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		// TODO: récupérer un véhicule par son id
		try {
			Vehicle vehicle = vehicleDao.findById(id);
			if (vehicle != null){
				return vehicle;
			}
			throw new ServiceException("Le vehicule n°"+ id + "n'a pas été trouvé dans la base de données");
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération du vehicule");
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return VehicleDao.getInstance().findAll(); //cree un nouveau vehicule
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération du vehicule");
		}
	}
	
}
