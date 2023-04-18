package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;

import com.epf.rentmanager.modele.Reservation;
import com.epf.rentmanager.modele.Vehicle;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.validator.ReservationValidator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.modele.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/rents/update")
public class ReservationUpdateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    VehicleService vehicleService;
    @Autowired
    ClientService clientService;
    @Autowired
    ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    private static boolean sauvU;
    private static long client_id_sauvU;
    private static long vehicle_id_sauvU;
    private static LocalDate debut_sauvU;
    private static LocalDate fin_sauvU;

    private static void sauvegarde(long client_id, long vehicle_id, LocalDate debut, LocalDate fin) throws ServletException {
        client_id_sauvU = client_id;
        vehicle_id_sauvU = vehicle_id;
        debut_sauvU = debut;
        fin_sauvU = fin;
        sauvU = true;
    }

    public static long reservation_id = -1;

    protected static void recupIdReservation(long reservation_id_recup) {
        reservation_id = reservation_id_recup;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (sauvU == true) {
            try {
                List<Vehicle> listVehiclesR = new ArrayList<>();
                List<Vehicle> listVehicles = vehicleService.findAll();
                for (Vehicle vehicle : listVehicles) {
                    if (vehicle.getId() == vehicle_id_sauvU) {
                        listVehiclesR.add(vehicle);
                        break;
                    }
                }
                for (Vehicle vehicle : listVehicles) {
                    if (vehicle.getId() != vehicle_id_sauvU) {
                        listVehiclesR.add(vehicle);
                    }
                }
                request.setAttribute("listVehiclesR", listVehiclesR);

                List<Client> listClientsR = new ArrayList<>();
                List<Client> listClients = clientService.findAll();
                for (Client C : listClients) {
                    if (C.getId() == client_id_sauvU) {
                        listClientsR.add(C);
                        break;
                    }
                }
                for (Client C : listClients) {
                    if (C.getId() != client_id_sauvU) {
                        listClientsR.add(C);
                    }
                }
                request.setAttribute("listClientsR", listClientsR);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            request.setAttribute("debutU", debut_sauvU.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            request.setAttribute("finU", fin_sauvU.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            Reservation reservation = new Reservation(reservation_id);
            try {
                reservation = reservationService.findById(reservation_id);
                List<Vehicle> listVehiclesR = new ArrayList<>();
                List<Vehicle> listVehicles = vehicleService.findAll();
                for (Vehicle vehicle : listVehicles) {
                    if (vehicle.getId() == reservation.getVehicle_id()) {
                        listVehiclesR.add(vehicle);
                        break;
                    }
                }
                for (Vehicle vehicle : listVehicles) {
                    if (vehicle.getId() != reservation.getVehicle_id()) {
                        listVehiclesR.add(vehicle);
                    }
                }
                request.setAttribute("listVehiclesR", listVehiclesR);

                List<Client> listClientsR = new ArrayList<>();
                List<Client> listClients = clientService.findAll();
                for (Client C : listClients) {
                    if (C.getId() == reservation.getClient_id()) {
                        listClientsR.add(C);
                        break;
                    }
                }
                for (Client C : listClients) {
                    if (C.getId() != reservation.getClient_id()) {
                        listClientsR.add(C);
                    }
                }
                request.setAttribute("listClientsR", listClientsR);
            } catch (ServiceException e) {
                e.printStackTrace();
            }

            request.setAttribute("debutU", reservation.getDebut().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            request.setAttribute("finU", reservation.getFin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/update.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int action = Integer.parseInt(request.getParameter("action"));

            String client_id_string = request.getParameter("clientU");
            String vehicle_id_string = request.getParameter("vehicleU");
            String debut_string = request.getParameter("debutU");
            String fin_string = request.getParameter("finU");
            long client_id = Long.parseLong(client_id_string);
            long vehicle_id = Long.parseLong(vehicle_id_string);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate debut = LocalDate.parse(debut_string, formatter);
            LocalDate fin = LocalDate.parse(fin_string, formatter);

            List<Reservation> listReservationV = new ArrayList<>();
            try {
                listReservationV = reservationService.findResaByVehicle(vehicle_id);
            } catch (ServiceException e1) {
                e1.printStackTrace();
            }
            boolean test = ReservationValidator.dateValidator(reservation_id, listReservationV, debut, fin);
            if (test) {
                Reservation reservation = new Reservation(client_id, vehicle_id, debut, fin);
                try {
                    reservationService.update(reservation, reservation_id);
                    sauvU = false;
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                response.sendRedirect("/rentmanager/rents");
            } else {
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null,
                        "La voiture n'est pas disponible sur cette période. Elle est réservée par un autre client.",
                        "Disponibilite", JOptionPane.ERROR_MESSAGE);
                ReservationUpdateServlet.sauvegarde(client_id, vehicle_id, debut, fin);
                response.sendRedirect("/rentmanager/rents/update");
            }

        } catch (NumberFormatException e) {
            sauvU = false;
            response.sendRedirect("/rentmanager/rents");
        }

    }

}