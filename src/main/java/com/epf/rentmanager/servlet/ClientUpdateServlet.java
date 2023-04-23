package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.epf.rentmanager.modele.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import com.epf.rentmanager.validator.ClientValidator;

@WebServlet("/users/update")
public class ClientUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static long client_id = -1;

    @Autowired
    ClientService clientService;

    protected static void recupIdClient(int idClientRecup) {
        client_id = idClientRecup;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Client client = new Client(client_id, null, null, null, null);
        try {
            client = clientService.findById(client_id);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        request.setAttribute("nomU", client.getNom());
        request.setAttribute("prenomU", client.getPrenom());
        request.setAttribute("emailU", client.getEmail());
        request.setAttribute("naissanceU", client.getNaissance());
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/update.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Integer.parseInt(request.getParameter("action"));
            String nom = request.getParameter("nomU");
            String prenom = request.getParameter("prenomU");
            String email = request.getParameter("emailU");
            String naissance_string = request.getParameter("naissanceU");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate naissance = LocalDate.parse(naissance_string, formatter);
            Client client = new Client(nom, prenom, email, naissance);
            boolean testLegal = ClientValidator.isLegal(client);
            if (testLegal) {
                try {
                    clientService.update(client, client_id);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                response.sendRedirect("/rentmanager/users");
            } else {
                JOptionPane.showMessageDialog(null, "Les clients doivent être majeurs", "Age", JOptionPane.ERROR_MESSAGE);
                response.sendRedirect("/rentmanager/users/update");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("/rentmanager/users");
        }
    }
}
