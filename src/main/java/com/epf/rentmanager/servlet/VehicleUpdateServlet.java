package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;

import com.epf.rentmanager.modele.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/cars/update")
public class VehicleUpdateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public static long vehicle_id = -1;

    protected static void vehicleIdRecup(long vehicle_id_update) {
        vehicle_id = vehicle_id_update;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int action = Integer.parseInt(request.getParameter("action"));

            String constructeur = request.getParameter("constructeurU");
            String nb_places_string = request.getParameter("nb_placesU");
            int nb_places = Integer.parseInt(nb_places_string);

            Vehicle vehicle = new Vehicle(constructeur, nb_places);
            try {
                vehicleService.update(vehicle, vehicle_id);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            response.sendRedirect("/rentmanager/cars");

        } catch (NumberFormatException e) {
            response.sendRedirect("/rentmanager/cars");
        }
    }

}
