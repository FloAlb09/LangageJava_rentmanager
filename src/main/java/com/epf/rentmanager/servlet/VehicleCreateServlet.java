package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.epf.rentmanager.modele.Vehicle;
import com.epf.rentmanager.service.VehicleService;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public static VehicleService vehicleInstance;
    private VehicleService vehicleService;

    public VehicleCreateServlet() {
        this.vehicleService = VehicleService.getVehicleInstance();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // affichage du formulaire
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // traitement du formulaire (appel à la méthode de sauvegarde)
        String constructeur = request.getParameter("manufacturer");
        String nbPlaceString = request.getParameter("seats");
        int nbPlace = Integer.parseInt(nbPlaceString);
        Vehicle vehicle = new Vehicle(constructeur, nbPlace);
        try {
            request.setAttribute("vehicles", VehicleService.getVehicleInstance().create(vehicle));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/rentmanager/cars");
    }
}
