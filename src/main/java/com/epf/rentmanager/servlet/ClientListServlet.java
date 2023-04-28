package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.modele.Client;
import com.epf.rentmanager.modele.Reservation;
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
import java.util.List;


@WebServlet("/users")
public class ClientListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    ClientService clientService;
    @Autowired
    ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("listUsers", clientService.findAll());
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(request,
                    response);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String client_id_detail_string = request.getParameter("detailClient");
        if (client_id_detail_string != null) {
            long client_id_detail = Long.parseLong(client_id_detail_string);
            ClientDetailServlet.clientIdRecup(client_id_detail);
            response.sendRedirect("/rentmanager/users/details");
        }
        String client_id_update_string = request.getParameter("updateClient");
        if (client_id_update_string != null) {
            long client_id_update = Long.parseLong(client_id_update_string);
            ClientUpdateServlet.clientIdRecup(client_id_update);
            response.sendRedirect("/rentmanager/users/update");
        }
        String client_id_delete_string = request.getParameter("deleteClient");
        if (client_id_delete_string != null) {
            long client_id_delete = Long.parseLong(client_id_delete_string);
            Client clientDelete = new Client(client_id_delete, null, null, null, null);

            List<Reservation> reservationByClient = null;
            try {
                reservationByClient = reservationService.findResaByClientId(client_id_delete);
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }
            for (Reservation reservation_delete : reservationByClient){
                try {
                    reservationService.delete(reservation_delete);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
            try {
                clientService.delete(clientDelete);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            response.sendRedirect("/rentmanager/users");
        }
    }

}
