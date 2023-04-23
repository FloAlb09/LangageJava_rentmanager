package com.epf.rentmanager.validator;

import com.epf.rentmanager.modele.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleValidator {
    public static boolean isConstructorEmpty(Vehicle vehicle) {
        boolean bool = false;
        if (vehicle.getConstructeur()== null){
            bool = true;
        }
        return bool;
    }
    public static boolean isNb_placesBetweenTwoAndNine(Vehicle vehicle) {
        boolean bool = false;
        if ((vehicle.getNb_places()>2) && (vehicle.getNb_places()<9)){
            bool = true;
        }
        return bool;
    }
}
