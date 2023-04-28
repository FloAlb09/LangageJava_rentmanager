package com.epf.rentmanager.validator;

import com.epf.rentmanager.modele.Client;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CientUpdateValidator {
    public static boolean isEmailUsed(long client_id, List<Client> listClients, Client clientCreate) {
        boolean emailUsed = false;
        String emailClientCreate = clientCreate.getEmail();
        for (Client clientExisting : listClients) {
            String emailClientExisting = clientExisting.getEmail();
            if (client_id != clientExisting.getId() && emailClientCreate.equals(emailClientExisting)) {
                emailUsed = true;
                break;
            }
        }
        return emailUsed;
    }
}
