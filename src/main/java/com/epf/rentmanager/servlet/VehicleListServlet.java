package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.epf.rentmanager.service.VehicleService;


@WebServlet("/cars")
public class VehicleListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private VehicleService vehicleService;
    public static VehicleService vehicleInstance;

    public VehicleListServlet() {
        this.vehicleService = VehicleService.getVehicleInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("vehicles", VehicleService.getVehicleInstance().findAll());
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request,
                    response);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}
