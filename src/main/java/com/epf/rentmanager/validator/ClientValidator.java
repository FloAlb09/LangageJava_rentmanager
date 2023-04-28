package com.epf.rentmanager.validator;

import com.epf.rentmanager.modele.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientValidator {
    public static boolean isLegal(Client client) {
        return Client.getAge(client.getNaissance()) >= 18;
    }

    public static boolean isLenghtNameAtLeastThree(Client client) {
        return client.getNom().length() >= 3;
    }

    public static boolean isLenghtFirstnameAtLeastThree(Client client) {
        return client.getPrenom().length() >= 3;
    }
}
