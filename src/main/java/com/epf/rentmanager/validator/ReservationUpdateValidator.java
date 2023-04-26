package com.epf.rentmanager.validator;

import com.epf.rentmanager.modele.Reservation;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class ReservationUpdateValidator {
    private static final long serialVersionUID = 1L;

    @Autowired
    ReservationService reservationService;

    public static boolean dateValidator(long id, List<Reservation> listReservation, LocalDate debut, LocalDate fin) {
        boolean bool = true;
        for (Reservation reservation : listReservation) {
            long idR = reservation.getId();
            LocalDate debutR = reservation.getDebut();
            LocalDate finR = reservation.getFin();
            if (id == idR || (debut.isEqual(debutR) || fin.isEqual(finR) || (debut.isAfter(debutR) && debut.isBefore(finR)))
                    || (fin.isAfter(debutR) && fin.isBefore(finR)) || (debut.isBefore(debutR) && fin.isAfter(finR))) {
                bool = false;
                break;
            }
        }
        return bool;
    }
}
