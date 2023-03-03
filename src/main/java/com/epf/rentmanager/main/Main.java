package com.epf.rentmanager.main;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.modele.Client;
import com.epf.rentmanager.modele.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

public class Main {
    public static void main (String[] args){
        try {
            Client client = new Client();
            //System.out.println(ClientService.getInstance().findById(1));
            //System.out.println(ClientService.getInstance().count());
            //System.out.println(ClientService.getInstance().update(client,0L));
            //System.out.println(VehicleService.getInstance().findById(2));
            System.out.println(ReservationService.getReservationInstance().count());
            //System.out.println(ReservationService.getReservationInstance().findResaByClientId());

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
