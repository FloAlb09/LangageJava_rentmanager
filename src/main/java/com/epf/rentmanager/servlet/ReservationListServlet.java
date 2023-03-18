package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.modele.Client;
import com.epf.rentmanager.modele.Reservation;
import com.epf.rentmanager.modele.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


@WebServlet("/rents")
public class ReservationListServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    @Autowired
    ReservationService reservationService;
    @Autowired
    ClientService clientService;
    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Reservation> listReservations = reservationService.findAll();
            System.out.println("listReservations: " + listReservations);
            List<Reservation> listReservationsFormated = new ArrayList<>();
            for (Reservation reservation : listReservations){
                System.out.println("Reservation: " + reservation);
                System.out.println("reservation.getClient_id: "+ reservation.getClient_id());
                System.out.println("reservation.getClient_id: "+ reservation.getVehicle_id());
                System.out.println(reservation.getVehicle_id());
                Client client = clientService.findById(reservation.getClient_id());
                System.out.println("Client: "+client);
                Vehicle vehicle = vehicleService.findById(reservation.getVehicle_id());
                System.out.println("Vehicle:" + vehicle);
                long id = reservation.getId();
                LocalDate debut = reservation.getDebut();
                LocalDate fin = reservation.getFin();
                listReservationsFormated.add(new Reservation(id, client, vehicle, debut, fin));
                System.out.println("listReservationFormated" + listReservationsFormated);
            }
            request.setAttribute("rents", listReservationsFormated);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request,response);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
