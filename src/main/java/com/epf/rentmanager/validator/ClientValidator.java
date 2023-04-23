package com.epf.rentmanager.validator;

import com.epf.rentmanager.modele.Client;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientValidator {
    public static boolean isLegal(Client client) {
        return client.getAge(client.getNaissance()) >= 18;
    }

    public static boolean isEmailUsed(List<Client> listClients, Client clientCreate) {
        boolean emailUsed = false;
        String emailClientCreate = clientCreate.getEmail();
        for (Client clientExisting : listClients) {
            String emailClientExisting = clientExisting.getEmail();
            if (emailClientCreate.equals(emailClientExisting)) {
                emailUsed = true;
                break;
            }
        }
        return emailUsed;
    }

    public static boolean isLenghtNameAtLeastThree(Client client) {
        return client.getNom().length() >= 3;
    }

    public static boolean isLenghtFirstnameAtLeastThree(Client client) {
        return client.getPrenom().length() >= 3;
    }
}
