package com.epf.rentmanager.dao;

//Importation
import java.sql.*;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//exception
import com.epf.rentmanager.exception.DaoException;
//modele
import com.epf.rentmanager.modele.Client;
//persistence
import com.epf.rentmanager.persistence.ConnectionManager;

//Classe
public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";

	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(id) AS count FROM Client;";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom = ?, prenom = ?, email = ?, naissance = ? WHERE id\n" + "= ?;";

	public long update(Client client, Long id) throws DaoException {
		//"UPDATE Client SET nom = ?, prenom = ?, email = ?, naissance = ? WHERE id\n" + "= ?;"
		String nom = client.getNom();
		String prenom = client.getPrenom();
		String email = client.getEmail();
		LocalDate naissance = client.getNaissance();
		//renvoie 1 si le update a ete effectue, 0 sinon
		try (Connection con = ConnectionManager.getConnection()) { //connexion a un SGBD - creer l'objet de connexion
			//creer l'objet preparedStatement
			PreparedStatement ps = con.prepareStatement(UPDATE_CLIENT_QUERY);
			//specifie le premier parametre de la requete - definit la valeur String du nom du client sur l'index 1 du parametre donne
			ps.setString(1,nom);
			ps.setString(2, prenom);
			ps.setString(3, email);
			ps.setDate(4, Date.valueOf(naissance));
			ps.setLong(5, id);
			//executer la requete
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long count() throws DaoException {
		//"SELECT COUNT(id) AS count FROM Client;"
		try (Connection con = ConnectionManager.getConnection()) {
			//statement permettant d'injecter une val au lieu des ? de la query securite envers les injections SQL
			PreparedStatement ps = con.prepareStatement(COUNT_CLIENTS_QUERY);
			ResultSet rs = ps.executeQuery();
			//moves the cursor to the last row in this ResultSet object.
			rs.last();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long create(Client client) throws DaoException {
		//"INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);"
		String nom = client.getNom();
		String prenom = client.getPrenom();
		String email = client.getEmail();
		Date naissance = Date.valueOf(client.getNaissance());
		try (Connection con = ConnectionManager.getConnection()) { //connection à un SGBD - creation de l'objet connexion
			//auto-generated keys feature provides a way to retrieve values from columns that are part of an index or have a default value assigned
			PreparedStatement ps = con.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS); //creation de l'objet prepared statement
			//Récupèrer les éléments
			ResultSet rs = ps.getGeneratedKeys();
			//Extraction des donnees
			ps.setString(1, nom); //specifie le premier parametre de la requete - definit la valeur String du nom du client sur l'index 1 du parametre donne
			ps.setString(2, prenom); //specifie le deuxieme parametre de la requete - definit la valeur String du prenom du client sur l'index 2 du parametre donne
			ps.setString(3, email); //specifie le troisieme parametre de la requete - definit la valeur String de l'email du client sur l'index 3 du parametre donne
			//convertir un objet de type LocalDate en un objet de type Date
			ps.setDate(4, naissance); //specifie le quatrieme parametre de la requete -  - definit la valeur LocalDate de la date de naissance du client sur l'index 4 du parametre donne
			//Recupere toutes les cles generees automatiquement du fait de l'execution de cet objet SQLServerStatement
			long id=0;
			//parcourt des enregistrements que la table ResultSet
			if (rs.next()) { //deplace le curseur sur la ligne suivante a partir de la position actuelle
				id = rs.getLong(1); //renvoie les donnees de la colonne specifiee de la ligne actuelle sous forme d'un long
			}
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public long delete(Client client) throws DaoException {
		//"DELETE FROM Client WHERE id=?;"
		Long id = client.getId();
		//renvoie 1 si le delete a ete effectue, 0 sinon
		try (Connection con = ConnectionManager.getConnection()) { //connexion a un SGBD - creer l'objet de connexion
			//creer l'objet preparedStatement
			PreparedStatement ps = con.prepareStatement(DELETE_CLIENT_QUERY);
			//specifie le premier parametre de la requete - definit la valeur String du nom du client sur l'index 1 du parametre donne
			ps.setLong(1, id);
			//executer la requete
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Client findById(long id) throws DaoException {
		//"SELECT nom, prenom, email, naissance FROM Client WHERE id=?;"
		try (Connection con = ConnectionManager.getConnection()) {
			//statement permettant d'injecter une val au lieu des ? de la query securite envers les injections SQL
			PreparedStatement ps = con.prepareStatement(FIND_CLIENT_QUERY);
			//injection l'id à l'index  1 au premier ? rencontrer dans la requete
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String email = rs.getString("email");
			//convertir un objet de type Date en objet de type LocalDate
			LocalDate naissance = rs.getDate("naissance").toLocalDate();
			//ceration du client a partir du resultset
			Client client = new Client(nom, prenom, email, naissance);
			return client;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Client> findAll() throws DaoException {
		ArrayList<Client> listClient = new ArrayList<Client>();
		try (Connection con = ConnectionManager.getConnection()) {
			PreparedStatement ps = con.prepareStatement(FIND_CLIENTS_QUERY);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				long id = rs.getLong("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				LocalDate naissance = rs.getDate("naissance").toLocalDate();
				Client client = new Client(id, nom, prenom, email, naissance);
				listClient.add(client);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listClient;
	}

}
