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
import java.util.List;

import com.epf.rentmanager.modele.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import com.epf.rentmanager.validator.ClientValidator;

import static com.epf.rentmanager.utils.IOUtils.print;

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer.parseInt(request.getParameter("action"));
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            String naissance_string = request.getParameter("naissance");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate naissance = LocalDate.parse(naissance_string, formatter);
            Client client = new Client(nom, prenom, email, naissance);
            boolean testAgeLegal = ClientValidator.isLegal(client);
            List<Client> allClients = clientService.findAll();
            boolean testMailUsed = ClientValidator.isEmailUsed(allClients, client);
            boolean testNameLength = ClientValidator.isLenghtNameAtLeastThree(client);
            boolean testFirstNameLength = ClientValidator.isLenghtFirstnameAtLeastThree(client);
            if (testAgeLegal & testMailUsed == false & testNameLength & testFirstNameLength) {
                try {
                    request.setAttribute("users", clientService.create(client));
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                response.sendRedirect("/rentmanager/users");
            } else if (testAgeLegal == false) {
                JOptionPane.showMessageDialog(null, "Le client doit être majeur", "Age", JOptionPane.ERROR_MESSAGE);
                response.sendRedirect("/rentmanager/users/create");
            } else if (testMailUsed){
                JOptionPane.showMessageDialog(null, "Ce mail est déjà utilisé", "Mail", JOptionPane.ERROR_MESSAGE);
                response.sendRedirect("/rentmanager/users/create");
            } else if (testNameLength == false){
                JOptionPane.showMessageDialog(null, "Le nom d'un client doit comporter plus de 3 charactères", "nameLength", JOptionPane.ERROR_MESSAGE);
                response.sendRedirect("/rentmanager/users/create");
            } else if (testFirstNameLength == false){
                JOptionPane.showMessageDialog(null, "Le prénom d'un client doit comporter plus de 3 charactères", "firstnameLength", JOptionPane.ERROR_MESSAGE);
                response.sendRedirect("/rentmanager/users/create");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("/rentmanager/users");
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
