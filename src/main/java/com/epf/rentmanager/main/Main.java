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

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ClientService clientService = context.getBean(ClientService.class);
        VehicleService vehicleService = context.getBean(VehicleService.class);
        ReservationService reservationService = context.getBean(ReservationService.class);

        try {
            System.out.println(clientService.findById(18));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
