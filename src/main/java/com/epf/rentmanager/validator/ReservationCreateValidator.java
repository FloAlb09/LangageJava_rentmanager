package com.epf.rentmanager.validator;

import com.epf.rentmanager.modele.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReservationCreateValidator {
    public static boolean isVehicleAlreadyReserved(List<Reservation> reservationByVehicle, LocalDate debut, LocalDate fin) {
        boolean bool = false;
        for (Reservation reservation : reservationByVehicle) {
            LocalDate debutR = reservation.getDebut();
            LocalDate finR = reservation.getFin();
            if (debut.isEqual(debutR) || fin.isEqual(finR) || debut.isEqual(finR) || fin.isEqual(debutR) || (debut.isAfter(debutR) && debut.isBefore(finR)) || (fin.isAfter(debutR) && fin.isBefore(finR)) || (debut.isBefore(debutR) && fin.isAfter(finR))) {
                bool = true;
                break;
            }
        }
        return bool;
    }
}
