package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;

import com.epf.rentmanager.modele.Client;
import com.epf.rentmanager.modele.Reservation;
import com.epf.rentmanager.modele.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.validator.ReservationUpdateValidator;
import com.epf.rentmanager.validator.ReservationValidator;
import com.epf.rentmanager.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/rents/update")
public class ReservationUpdateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public static long reservation_id = -1;
    private static boolean sauvU;
    private static long client_id_sauvU;
    private static long vehicle_id_sauvU;
    private static LocalDate debut_sauvU;
    private static LocalDate fin_sauvU;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    ClientService clientService;
    @Autowired
    ReservationService reservationService;

    private static void sauvegarde(long client_id, long vehicle_id, LocalDate debut, LocalDate fin) {
        client_id_sauvU = client_id;
        vehicle_id_sauvU = vehicle_id;
        debut_sauvU = debut;
        fin_sauvU = fin;
        sauvU = true;
    }

    protected static void reservationIdRecup(long reservation_id_recup) {
        reservation_id = reservation_id_recup;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (sauvU) {
            try {
                List<Vehicle> listVehicleR = new ArrayList<>();
                List<Vehicle> listVehicleAll = vehicleService.findAll();
                for (Vehicle v : listVehicleAll) {
                    if (v.getId() == vehicle_id_sauvU) {
                        listVehicleR.add(v);
                        break;
                    }
                }
                for (Vehicle v : listVehicleAll) {
                    if (v.getId() != vehicle_id_sauvU) {
                        listVehicleR.add(v);
                    }
                }
                request.setAttribute("listVehiclesR", listVehicleR);

                List<Client> listClientR = new ArrayList<>();
                List<Client> listClientAll = clientService.findAll();
                for (Client c : listClientAll) {
                    if (c.getId() == client_id_sauvU) {
                        listClientR.add(c);
                        break;
                    }
                }
                for (Client c : listClientAll) {
                    if (c.getId() != client_id_sauvU) {
                        listClientR.add(c);
                    }
                }
                request.setAttribute("listClientsR", listClientR);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            request.setAttribute("debutU", debut_sauvU.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            request.setAttribute("finU", fin_sauvU.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            Reservation reservation = new Reservation(reservation_id);
            try {
                reservation = reservationService.findById(reservation_id);

                List<Vehicle> listVehicleR = new ArrayList<>();
                List<Vehicle> listVehicleAll = vehicleService.findAll();
                for (Vehicle v : listVehicleAll) {
                    if (v.getId().equals(reservation.getVehicle_id())) {
                        listVehicleR.add(v);
                        break;
                    }
                }
                for (Vehicle v : listVehicleAll) {
                    if (!v.getId().equals(reservation.getVehicle_id())) {
                        listVehicleR.add(v);
                    }
                }
                request.setAttribute("listVehiclesR", listVehicleR);

                List<Client> listClientR = new ArrayList<>();
                List<Client> listClientAll = clientService.findAll();
                for (Client c : listClientAll) {
                    if (c.getId().equals(reservation.getClient_id())) {
                        listClientR.add(c);
                        break;
                    }
                }
                for (Client c : listClientAll) {
                    if (!c.getId().equals(reservation.getClient_id())) {
                        listClientR.add(c);
                    }
                }
                request.setAttribute("listClientsR", listClientR);
            } catch (ServiceException e) {
                e.printStackTrace();
            }

            request.setAttribute("debutU", reservation.getDebut().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            request.setAttribute("finU", reservation.getFin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/update.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Integer.parseInt(request.getParameter("action"));

            String client_id_string = request.getParameter("clientU");
            String vehicle_id_string = request.getParameter("vehicleU");
            String debut_string = request.getParameter("debutU");
            String fin_string = request.getParameter("finU");
            long client_id = Long.parseLong(client_id_string);
            long vehicle_id = Long.parseLong(vehicle_id_string);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate debut = LocalDate.parse(debut_string, formatter);
            LocalDate fin = LocalDate.parse(fin_string, formatter);

            Reservation reservationUpdate = new Reservation(client_id, vehicle_id, debut, fin);

            List<Reservation> reservationByVehicle = new ArrayList<>();
            List<Reservation> reservationByClientVehicle = new ArrayList<>();
            try {
                reservationByVehicle = reservationService.findResaByVehicle(vehicle_id);
                reservationByClientVehicle = reservationService.findResaByClientVehicleId(client_id, vehicle_id);
            } catch (ServiceException e1) {
                e1.printStackTrace();
            }
            boolean testVehicleAlreadyReserved = ReservationUpdateValidator.isVehicleAlreadyReserved(reservation_id, reservationByVehicle, debut, fin);
            boolean testVehicleReservedMoreThanSevenDaysBySameClient = ReservationValidator.isVehicleReservedMoreThanSevenDaysBySameClient(reservationByClientVehicle, reservationUpdate);
            boolean testVehicleReservedMoreThanThirtyDaysInARow = ReservationValidator.isVehicleReservedMoreThanThirtyDaysInARow(reservationByVehicle, reservationUpdate);

            if (!testVehicleAlreadyReserved && !testVehicleReservedMoreThanSevenDaysBySameClient && !testVehicleReservedMoreThanThirtyDaysInARow) {
                try {
                    reservationService.update(reservationUpdate, reservation_id);
                    sauvU = false;
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                response.sendRedirect("/rentmanager/rents");
            } else if (testVehicleAlreadyReserved) {
                Validator.showMessageDialog("Le véhicule est déjà réservé.");
                response.sendRedirect("/rentmanager/rents/update");
            } else if (testVehicleReservedMoreThanSevenDaysBySameClient) {
                Validator.showMessageDialog("Un véhicule ne peut pas être réservé plus de sept jours par un même client.");
                response.sendRedirect("/rentmanager/rents/update");
            } else if (testVehicleReservedMoreThanThirtyDaysInARow) {
                Validator.showMessageDialog("Un véhicule ne peut pas être réservé plus de 30 jours de suite.");
                response.sendRedirect("/rentmanager/rents/update");
            }
        } catch (NumberFormatException e) {
            sauvU = false;
            response.sendRedirect("/rentmanager/rents");
        }

    }

}