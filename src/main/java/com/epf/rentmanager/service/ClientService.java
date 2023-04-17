package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ClientDao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;

import com.epf.rentmanager.modele.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private ClientDao clientDao;

    private ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public List<Client> findAll() throws ServiceException {
        try {
            return this.clientDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public Client findById(long id) throws ServiceException {
        try {
            return this.clientDao.findById(id).get();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long create(Client client) throws ServiceException {
        try {
            return this.clientDao.create(client);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return 0;
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
            return this.clientDao.count();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Client> findClientsByVehicle(Long id_vehicle) throws ServiceException {
        try {
            return this.clientDao.findClientsByVehicle(id_vehicle);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public long countClientsByVehicle(Long id_vehicle) throws ServiceException {
        try {
            return this.clientDao.countClientsByVehicle(id_vehicle);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
