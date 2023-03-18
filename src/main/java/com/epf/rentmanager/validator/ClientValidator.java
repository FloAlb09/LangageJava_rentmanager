package com.epf.rentmanager.validator;

import com.epf.rentmanager.modele.Client;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientValidator {
    /**
     * Renvoie true si l'utilisateur passe en parametre a un age >= 18 ans
     *
     * @param client L'instance d'utilisateur à tester
     * @return Resultat du test (>= 18 ans)
     */
    public static boolean isLegal(Client client) {
        return client.getAge(client.getNaissance()) >= 18;
    }

    /**
     * Renvoie true si l'utilisateur passe en parametre un mail deja existant
     *
     * @param clientCreate L'instance d'utilisateur à tester
     * @param listClients  La liste d'utilisateur pour comparer
     * @return boolean emailUsed → true si email est deja pris → false si email n'est pas deja pris
     */
    public static boolean isEmailUsed(List<Client> listClients, Client clientCreate) {
        boolean emailUsed = false;
        String emailClientCreate = clientCreate.getEmail();
        for (Client clientExisting : listClients) {
            String emailClientExisting = clientExisting.getEmail();
            if (emailClientCreate == emailClientExisting) {
                emailUsed = true;
                break;
            }
        }
        return emailUsed;
    }

    /**
     * Renvoie true si l'utilisateur passe en parametre a un nom >= 3 caracteres
     *
     * @param client L'instance d'utilisateur à tester
     * @return Resultat du test (>= 3 caracteres)
     */
    public static boolean isLenghtNameAtLeastThree(Client client) {
        return client.getNom().length() >= 3;
    }

    /**
     * Renvoie true si l'utilisateur passe en parametre a un prenom >= 3 caracteres
     *
     * @param client L'instance d'utilisateur à tester
     * @return Resultat du test (>= 3 caracteres)
     */
    public static boolean isLenghtFirstnameAtLeastThree(Client client) {
        return client.getPrenom().length() >= 3;
    }
}
