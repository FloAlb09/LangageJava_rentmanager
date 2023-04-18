package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
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

@WebServlet("/cars/details")
public class VehicleDetailServlet extends HttpServlet {

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

    public static long vehicle_id = -1;

    protected static void recupIdVehicle(long vehicle_id_recup) {
        vehicle_id = vehicle_id_recup;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Vehicle vehicle = new Vehicle(vehicle_id, null, 0);
        try {
            vehicle = vehicleService.findById(vehicle_id);
            request.setAttribute("idD", vehicle.getId());
            request.setAttribute("constructeurD", vehicle.getConstructeur());
            request.setAttribute("nb_placesD", vehicle.getNb_places());
            request.setAttribute("listResaV", reservationService.findResaByVehicle(vehicle_id));
            request.setAttribute("listClientV", clientService.findClientsByVehicle(vehicle_id));
            request.setAttribute("countResaByVehicle", reservationService.countResaByVehicle(vehicle_id));
                request.setAttribute("countClientsByVehicle", clientService.countClientsByVehicle(vehicle_id));
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
