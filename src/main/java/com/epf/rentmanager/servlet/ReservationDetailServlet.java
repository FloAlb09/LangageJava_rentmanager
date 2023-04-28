package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.modele.Reservation;
import com.epf.rentmanager.modele.Client;
import com.epf.rentmanager.modele.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rents/details")
public class ReservationDetailServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public static long reservation_id = -1;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    ClientService clientService;
    @Autowired
    ReservationService reservationService;

    protected static void recupIdReservation(long reseravation_id_recup) {
        reservation_id = reseravation_id_recup;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Reservation reservation = reservationService.findById(reservation_id);
            Vehicle vehicle = vehicleService.findById(reservation.getVehicle_id());
            Client client = clientService.findById(reservation.getClient_id());

            request.setAttribute("reservation_id", reservation.getId());
            request.setAttribute("reservation_debut", reservation.getDebut());
            request.setAttribute("reservation_fin", reservation.getFin());

            request.setAttribute("client_id", reservation.getClient_id());
            request.setAttribute("client_nom", client.getNom());
            request.setAttribute("client_prenom", client.getPrenom());
            request.setAttribute("client_email", client.getEmail());
            request.setAttribute("client_naissance", client.getNaissance());

            request.setAttribute("vehicle_id", reservation.getVehicle_id());
            request.setAttribute("vehicle_constructeur", vehicle.getConstructeur());
            request.setAttribute("vehicle_nb_places", vehicle.getNb_places());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/details.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}
