package com.epf.rentmanager.service;

//Importation
import java.util.ArrayList;
import java.util.List;

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
	public static ClientService instance;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}

	public long update(Client client, Long id) throws ServiceException {
		try {
			return this.clientDao.update(client, id);
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
	
	
	public long create(Client client) throws ServiceException {
		// TODO: créer un client
		try {
			return clientDao.create(client);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la création de l'utilisateur");
		}
	}

	public Client findById(long id) throws ServiceException {
		// TODO: récupérer un client par son id
		if (id <=0) {
			throw new ServiceException("Le client n'existe pas");
		}
		try {
			Client client = clientDao.findById(id);
			if (client != null){
				return client;
			}
			throw new ServiceException("L'utilisateur n°"+ id + "n'a pas été trouvé dans la base de données");
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération de l'utilisateur");
		}
	}

	public List<Client> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			ArrayList<Client> allClient = (ArrayList<Client>) clientDao.findAll();
			return allClient;
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération des utilisateurs");
		}
	}
	
}
