package com.epf.rentmanager.validator;

import com.epf.rentmanager.modele.Reservation;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReservationCreateValidator {
    private static final long serialVersionUID = 1L;

    @Autowired
    ReservationService reservationService;

    public static boolean isVehicleAlreadyReserved(List<Reservation> listReservation, long vehicle_id, LocalDate debut, LocalDate fin) {
        boolean bool = false;
        for (Reservation reservation : listReservation) {
            LocalDate debutR = reservation.getDebut();
            LocalDate finR = reservation.getFin();
            long vehicleIdR = reservation.getVehicle_id();
            if ((vehicleIdR == vehicle_id) && (debut.isEqual(debutR) || fin.isEqual(finR) || (debut.isAfter(debutR) && debut.isBefore(finR)) || (fin.isAfter(debutR) && fin.isBefore(finR)) || (debut.isBefore(debutR) && fin.isAfter(finR)))) {
                bool = true;
                break;
            }
        }
        return bool;
    }

    public static boolean isVehicleReservedMoreThanSevenDaysBySameClient(List<Reservation> reservationByClientVehicle, LocalDate debut, LocalDate fin) {
        boolean bool = false;
        long differenceInDays = ChronoUnit.DAYS.between(debut, fin);
        System.out.println("reservationByClientVehicle : " + reservationByClientVehicle);
        if (differenceInDays > 7) {
            bool = true;
        } else {
            for (Reservation reservation : reservationByClientVehicle) {
                System.out.println("reservation : " + reservation);
                LocalDate debutR = reservation.getDebut();
                System.out.println("debutR : " + debutR);
                LocalDate finR = reservation.getFin();
                System.out.println("finR : " + finR);
                differenceInDays += ChronoUnit.DAYS.between(debutR, finR);
                System.out.println("periode : " + differenceInDays);
                if (debutR.plusDays(-1).isEqual(fin) || debut.plusDays(-1).isEqual(finR)) {
                    differenceInDays += ChronoUnit.DAYS.between(debutR, finR);
                    System.out.println("periode - if : " + differenceInDays);
                }
                if ((differenceInDays > 7)) {
                    bool = true;
                    break;
                }
                System.out.println("periode : " + differenceInDays);
            }
        }
        return bool;
    }

//    public static boolean isVehicleReservedMoreThanThirtyDays(Reservation reservation, long vehicle_id, long client_id, LocalDate debut, LocalDate fin) {
//        boolean bool = false;
//        if ((reservation.reservationTime(debut, fin) > 30) && (reservation.getVehicle_id() == vehicle_id)) {
//            bool = true;
//        }
//        return bool;
//    }


}
