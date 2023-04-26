package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;

import com.epf.rentmanager.modele.Reservation;

import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.epf.rentmanager.validator.ReservationCreateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        try {
            request.setAttribute("listVehiclesReservation", vehicleService.findAll());
            request.setAttribute("listClientsReservation", clientService.findAll());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer.parseInt(request.getParameter("action"));
            String client_id_string = request.getParameter("client");
            String vehicle_id_string = request.getParameter("car");
            String debut_string = request.getParameter("begin");
            String fin_string = request.getParameter("end");
            long client_id = Long.parseLong(client_id_string);
            long vehicle_id = Long.parseLong(vehicle_id_string);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate debut = LocalDate.parse(debut_string, formatter);
            LocalDate fin = LocalDate.parse(fin_string, formatter);
            Reservation reservation = new Reservation(client_id, vehicle_id, debut, fin);

            List<Reservation> reservationByClientVehicle = reservationService.findResaByClientVehicleId(client_id, vehicle_id);

            List<Reservation> allResrvation = reservationService.findAll();
            boolean testVehicleAlreadyReserved = ReservationCreateValidator.isVehicleAlreadyReserved(allResrvation, vehicle_id, debut, fin);
            boolean testVehicleReservedMoreThanSevenDaysBySameClient = ReservationCreateValidator.isVehicleReservedMoreThanSevenDaysBySameClient(reservationByClientVehicle, debut, fin);
            if (!testVehicleAlreadyReserved & !testVehicleReservedMoreThanSevenDaysBySameClient) {
                try {
                    request.setAttribute("rents", reservationService.create(reservation));
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                response.sendRedirect("/rentmanager/rents");
            } else if (testVehicleAlreadyReserved){
                List<Reservation> allReservation = reservationService.findAll();
                for (Reservation reservations : allReservation) {
                    LocalDate debutR = reservations.getDebut();
                    LocalDate finR = reservations.getFin();
                    long vehicleIdR = reservations.getVehicle_id();
                    if ((vehicleIdR == vehicle_id) && (debut.isEqual(debutR) || fin.isEqual(finR) || (debut.isAfter(debutR) && debut.isBefore(finR)) || (fin.isAfter(debutR) && fin.isBefore(finR)) || (debut.isBefore(debutR) && fin.isAfter(finR)))) {
                        JOptionPane.showMessageDialog(null, "Le véhicule est déjà réservé du " + debutR + " au " + finR + ".", "vehicleAlreadyReserved", JOptionPane.ERROR_MESSAGE);
                        response.sendRedirect("/rentmanager/rents/create");
                        break;
                    }
                }
            } else if (testVehicleReservedMoreThanSevenDaysBySameClient){
                JOptionPane.showMessageDialog(null, "Un véhicule ne peut pas être réservé plus de sept jours par un même client.", "vehicleReservedMoreThanSevenDays", JOptionPane.ERROR_MESSAGE);
                response.sendRedirect("/rentmanager/rents/create");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("/rentmanager/rents");
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}