package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;

import com.epf.rentmanager.modele.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.validator.VehicleValidator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/cars/update")
public class VehicleUpdateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public static long vehicle_id = -1;
    @Autowired
    VehicleService vehicleService;

    protected static void vehicleIdRecup(long vehicle_id_update) {
        vehicle_id = vehicle_id_update;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Vehicle vehicle = new Vehicle(vehicle_id, null, 0);
        try {
            vehicle = vehicleService.findById(vehicle_id);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        request.setAttribute("constructeurU", vehicle.getConstructeur());
        request.setAttribute("nb_placesU", vehicle.getNb_places());
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Integer.parseInt(request.getParameter("action"));

            String constructeur = request.getParameter("constructeurU");
            String nb_places_string = request.getParameter("nb_placesU");
            int nb_places = Integer.parseInt(nb_places_string);

            Vehicle vehicle = new Vehicle(constructeur, nb_places);

            boolean testConstructorEmpty = VehicleValidator.isConstructorEmpty(vehicle);
            boolean testNb_placesBetweenTwoAndNine = VehicleValidator.isNb_placesBetweenTwoAndNine(vehicle);

            if (!testConstructorEmpty && testNb_placesBetweenTwoAndNine) {
                try {
                    vehicleService.update(vehicle, vehicle_id);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                response.sendRedirect("/rentmanager/cars");
            } else if (testConstructorEmpty) {
                JOptionPane.showMessageDialog(null, "Le véhicule doit avoir un constructeur", "constructorEmpty", JOptionPane.ERROR_MESSAGE);
                response.sendRedirect("/rentmanager/cars/create");
            } else if (!testNb_placesBetweenTwoAndNine) {
                JOptionPane.showMessageDialog(null, "Le nombre de places du véhicule doit être compris entre 2 et 9", "nbPlace", JOptionPane.ERROR_MESSAGE);
                response.sendRedirect("/rentmanager/cars/create");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("/rentmanager/cars");
        }
    }

}
