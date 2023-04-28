package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.modele.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/details")
public class ClientDetailServlet extends HttpServlet {

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

    public static long client_id = -1;

    protected static void clientIdRecup(long client_id_recup) {
        client_id = client_id_recup;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Client client = new Client(client_id, null, null, null, null);
        try {
            client = clientService.findById(client_id);
            request.setAttribute("idDetail", client.getId());
            request.setAttribute("nomDetail", client.getNom());
            request.setAttribute("prenomDetail", client.getPrenom());
            request.setAttribute("emailDetail", client.getEmail());
            request.setAttribute("birthDetail", client.getNaissance());
            request.setAttribute("listResa", reservationService.findResaByClientId(client_id));
            request.setAttribute("listVehicle", vehicleService.findVehicleByUser(client_id));
            request.setAttribute("countResaByUser", reservationService.countResaByUser(client_id));
            request.setAttribute("countVehicleByUser", vehicleService.countVehicleByUser(client_id));
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String client_id_string = request.getParameter("id");
        long client_id = Long.parseLong(client_id_string);
        try {
            reservationService.findResaByClientId(client_id);
            String data = request.getParameter("user.id");
            System.out.println(data);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/rentmanager/users");
    }
}
