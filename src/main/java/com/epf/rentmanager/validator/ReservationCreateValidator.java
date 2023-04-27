package com.epf.rentmanager.validator;

import com.epf.rentmanager.modele.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.servlet.ReservationCreateServlet;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Array;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReservationCreateValidator {
    private static final long serialVersionUID = 1L;

    @Autowired
    ReservationService reservationService;

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
            System.out.println("reservation : " + reservation);
            LocalDate date_debut = reservation.getDebut();
            LocalDate date_fin = reservation.getFin();
            if (!date_debut.plusDays(-1).isEqual(save_date_fin)) {
                System.out.println("je suis dedans save_delta=0");
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
            System.out.println("reservation : " + reservation);
            LocalDate date_debut = reservation.getDebut();
            LocalDate date_fin = reservation.getFin();
            if (!date_debut.plusDays(-1).isEqual(save_date_fin)) {
                System.out.println("je suis dedans save_delta=0");
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
