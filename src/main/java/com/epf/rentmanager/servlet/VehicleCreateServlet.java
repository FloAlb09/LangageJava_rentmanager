package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;

import com.epf.rentmanager.modele.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.validator.VehicleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer.parseInt(request.getParameter("action"));
            String constructeur = request.getParameter("manufacturer");
            String nb_place_string = request.getParameter("seats");
            int nb_place = Integer.parseInt(nb_place_string);
            Vehicle vehicle = new Vehicle(constructeur, nb_place);

            boolean testConstructorEmpty = VehicleValidator.isConstructorEmpty(vehicle);
            boolean testNb_placesBetweenTwoAndNine = VehicleValidator.isNb_placesBetweenTwoAndNine(vehicle);
            if (!testConstructorEmpty && testNb_placesBetweenTwoAndNine) {
                try {
                    request.setAttribute("vehicles", vehicleService.create(vehicle));
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
