package com.epf.rentmanager.validator;

import com.epf.rentmanager.modele.Client;
import com.epf.rentmanager.modele.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleValidator {
    /**
     * Renvoie true si le vehicule n'est pas reserve ce jour
     *
     * @param vehicle L'instance d'utilisateur à tester
     * @return Resultat du test (>= 18 ans)
     */
    public static boolean isLegal(Vehicle vehicle) {

    }
}
