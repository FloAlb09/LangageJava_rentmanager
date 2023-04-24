package com.epf.rentmanager.validator;

import com.epf.rentmanager.modele.Reservation;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class ReservationValidator {
    private static final long serialVersionUID = 1L;

    @Autowired
    ReservationService reservationService;

    public static boolean isVehicleReservedTwoTimesSamesDay(long reservation_id, List<Reservation> listReservation, LocalDate debut, LocalDate fin) {
        boolean bool = true;
        for (Reservation reservation : listReservation) {
            LocalDate debutR = reservation.getDebut();
            LocalDate finR = reservation.getFin();
            if ((reservation.getId()!=reservation_id) && (debut.isEqual(debutR) || fin.isEqual(finR) || (debut.isAfter(debutR) && debut.isBefore(finR)))
                    || (fin.isAfter(debutR) && fin.isBefore(finR)) || (debut.isBefore(debutR) && fin.isAfter(finR))) {
                bool = false;
                break;
            }
        }
        return bool;
    }

    public static boolean isVehicleReservedMoreThanSevenDaysBySameClient(Reservation reservation, long vehicle_id, long client_id, LocalDate debut, LocalDate fin) {
        boolean bool = false;
            if ((reservation.reservationTime(debut, fin) > 7) && (reservation.getVehicle_id() == vehicle_id) && (reservation.getClient_id() == client_id)){
                bool = true;
            }
        return bool;
    }

    public static boolean isVehicleReservedMoreThanThirtyDays(Reservation reservation, long vehicle_id, long client_id, LocalDate debut, LocalDate fin) {
        boolean bool = false;
        if ((reservation.reservationTime(debut, fin) > 30) && (reservation.getVehicle_id() == vehicle_id)){
            bool = true;
        }
        return bool;
    }


}
