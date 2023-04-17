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

    protected static void recupIdClient(int idClientRecup) {
        client_id = idClientRecup;
    }

    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    private static boolean sauvU;
    private static String nomSauvU;
    private static String prenomSauvU;
    private static String emailSauvU;
    private static LocalDate naissanceSauvU;

    private static void sauvegarde(String nom, String prenom, String email, LocalDate naissance) {
        nomSauvU = nom;
        prenomSauvU = prenom;
        emailSauvU = email;
        naissanceSauvU = naissance;
        sauvU = true;
    }

    public static long client_id = -1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        if (sauvU) {
            request.setAttribute("nomU", nomSauvU);
            request.setAttribute("prenomU", prenomSauvU);
            request.setAttribute("emailU", emailSauvU);
            request.setAttribute("naissanceU", naissanceSauvU);
        } else {
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
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/update.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int action = Integer.parseInt(request.getParameter("action"));
            String nom = request.getParameter("nomU");
            String prenom = request.getParameter("prenomU");
            String email = request.getParameter("emailU");
            String naissance_string = request.getParameter("naissanceU");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate naissance = LocalDate.parse(naissance_string, formatter);
            Client client = new Client(nom, prenom, email, naissance);
            boolean test = ClientValidator.isLegal(client);
            if (test) {
                Client clientU = new Client(prenom, nom, email, naissance);
                try {
                    clientService.update(clientU, client_id);
                    sauvU = false;
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                response.sendRedirect("/rentmanager/users");
            } else {
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null, "Les clients doivent être majeurs et âgés de moins de 150 ans.", "Age",
                        JOptionPane.ERROR_MESSAGE);
                ClientUpdateServlet.sauvegarde(nom, prenom, email, naissance);
                response.sendRedirect("/rentmanager/users/update");
            }
        } catch (NumberFormatException e) {
            sauvU = false;
            response.sendRedirect("/rentmanager/users");
        }
    }
}
