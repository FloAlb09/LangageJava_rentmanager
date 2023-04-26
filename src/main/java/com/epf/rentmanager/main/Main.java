package com.epf.rentmanager.main;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.modele.Client;
import com.epf.rentmanager.modele.Reservation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import com.epf.rentmanager.configuration.AppConfiguration;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ClientService clientService = context.getBean(ClientService.class);
        VehicleService vehicleService = context.getBean(VehicleService.class);
        ReservationService reservationService = context.getBean(ReservationService.class);
        try {
            List<Reservation> resaByClientVehicleId = reservationService.findResaByClientVehicleId(1, 1);
            System.out.println("reservationClientVehicle : " + resaByClientVehicleId);
            long periode = 0l;
            LocalDate debutSauvU = null;
            System.out.println("debutSauvU : " + debutSauvU);
            for (Reservation reservation : resaByClientVehicleId){
                LocalDate debutR = reservation.getDebut();
                System.out.println("debutR : " + debutR);
                LocalDate finR = reservation.getFin();
                System.out.println("finR : " + finR);
                periode += ChronoUnit.DAYS.between(debutR, finR);
                System.out.println("periode : " + periode);
                LocalDate debutSauv = debutR;
                System.out.println("------------");
            }
            System.out.println("periode : " + periode);

        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
