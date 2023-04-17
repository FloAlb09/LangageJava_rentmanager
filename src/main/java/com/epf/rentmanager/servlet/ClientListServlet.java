package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.modele.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/users")
public class ClientListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    ClientService clientService;

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
        String clientIdStringDetail = request.getParameter("detailClient");
        if (clientIdStringDetail != null) {
            int clientIdDetail = Integer.parseInt(clientIdStringDetail);
            ClientDetailServlet.recupIdClient(clientIdDetail);
            response.sendRedirect("/rentmanager/users/details");
        }
        String clientIdStringUpdate = request.getParameter("updateClient");
        if (clientIdStringUpdate != null) {
            int clientIdUpdate = Integer.parseInt(clientIdStringUpdate);
            ClientUpdateServlet.recupIdClient(clientIdUpdate);
            response.sendRedirect("/rentmanager/users/update");
        }
        String clientIdStringDelete = request.getParameter("deleteClient");
        if (clientIdStringDelete != null) {
            long clientIdDelete = Long.parseLong(clientIdStringDelete);
            Client clientDelete = new Client(clientIdDelete, null, null, null, null);
            try {
                clientService.delete(clientDelete);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            response.sendRedirect("/rentmanager/users");
        }
    }

}
