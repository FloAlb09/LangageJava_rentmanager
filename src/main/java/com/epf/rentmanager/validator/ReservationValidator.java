package com.epf.rentmanager.validator;

import com.epf.rentmanager.modele.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class ReservationValidator {
    public static boolean isVehicleReservedMoreThanSevenDaysBySameClient(List<Reservation> reservationByClientVehicle, Reservation reservationCreate) {
        boolean bool = false;
        LocalDate save_date_fin = LocalDate.of(1900, Month.JANUARY, 01);
        Reservation reservationCreateSauv = new Reservation(0L, reservationCreate.getClient_id(), reservationCreate.getVehicle_id(), reservationCreate.getDebut(), reservationCreate.getFin());
        long save_delta = 0;
        long delta1 = 0;
        Comparator<Reservation> comparatorAsc = (resa1, resa2) -> resa1.getDebut().compareTo(resa2.getFin());
        reservationByClientVehicle.add(reservationCreateSauv);
        Collections.sort(reservationByClientVehicle, comparatorAsc);
        for (Reservation reservation : reservationByClientVehicle) {
            LocalDate date_debut = reservation.getDebut();
            LocalDate date_fin = reservation.getFin();
            if (!date_debut.plusDays(-1).isEqual(save_date_fin)) {
                save_delta = 0;
            }
            delta1 = ChronoUnit.DAYS.between(date_debut, date_fin) + 1;
            save_delta = save_delta + delta1;
            if (save_delta > 7) {
                bool = true;
                break;
            }
            save_date_fin = date_fin;
        }
        return bool;
    }

    public static boolean isVehicleReservedMoreThanThirtyDaysInARow(List<Reservation> reservationByVehicle, Reservation reservationCreate) {
        boolean bool = false;
        LocalDate save_date_fin = LocalDate.of(1900, Month.JANUARY, 01);
        Reservation reservationCreateSauv = new Reservation(0L, reservationCreate.getClient_id(), reservationCreate.getVehicle_id(), reservationCreate.getDebut(), reservationCreate.getFin());
        long save_delta = 0;
        long delta1 = 0;
        Comparator<Reservation> comparatorAsc = (resa1, resa2) -> resa1.getDebut().compareTo(resa2.getFin());
        reservationByVehicle.add(reservationCreateSauv);
        Collections.sort(reservationByVehicle, comparatorAsc);
        for (Reservation reservation : reservationByVehicle) {
            LocalDate date_debut = reservation.getDebut();
            LocalDate date_fin = reservation.getFin();
            if (!date_debut.plusDays(-1).isEqual(save_date_fin)) {
                save_delta = 0;
            }
            delta1 = ChronoUnit.DAYS.between(date_debut, date_fin) + 1;
            save_delta = save_delta + delta1;
            if (save_delta > 30) {
                bool = true;
                break;
            }
            save_date_fin = date_fin;
        }
        return bool;
    }
}
