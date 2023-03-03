package com.epf.rentmanager.service;

//Importation
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//dao
import com.epf.rentmanager.dao.ClientDao;
//exception
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
//modele
import com.epf.rentmanager.modele.Client;

//Classe
public class ClientService {

	private ClientDao clientDao;
	public static ClientService clientInstance;
	
	private ClientService() {
		this.clientDao = ClientDao.getClientInstance();
	}
	
	public static ClientService getClientInstance() {
		if (clientInstance == null) {
			clientInstance = new ClientService();
		}
		
		return clientInstance;
	}

	public List<Client> findAll() throws ServiceException {
		try {
			ArrayList<Client> allClient = (ArrayList<Client>) clientDao.findAll();
			return allClient;
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération des utilisateurs");
		}
	}

	public Optional<Client> findById(long id) throws ServiceException {
		if (id <=0) {
			throw new ServiceException("Le client n'existe pas");
		}
		try {
			Optional<Client> client = clientDao.findById(id);
			if (client != null){
				return client;
			}
			throw new ServiceException("L'utilisateur n°"+ id + "n'a pas été trouvé dans la base de données");
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération de l'utilisateur");
		}
	}

	public long create(Client client) throws ServiceException {
		try {
			return clientDao.create(client);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la création de l'utilisateur");
		}
	}

	public long update(Client client, Long id) throws ServiceException {
		try {
			return this.clientDao.update(client, id);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long delete(Client client) throws ServiceException {
		try {
			return this.clientDao.delete(client);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long count() throws ServiceException {
		try {
			return clientDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
