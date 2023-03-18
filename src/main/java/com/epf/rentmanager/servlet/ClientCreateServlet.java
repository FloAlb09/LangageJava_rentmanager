package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.epf.rentmanager.modele.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import com.epf.rentmanager.validator.ClientValidator;

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
        // affichage du formulaire
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // traitement du formulaire (appel à la méthode de sauvegarde)
        String last_name = request.getParameter("last_name");
        String first_name = request.getParameter("first_name");
        String email = request.getParameter("email");
        String birth_date_string = request.getParameter("birth_date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birth_date = LocalDate.parse(birth_date_string, formatter);
        Client client = new Client(last_name, first_name, email, birth_date);
        boolean test = ClientValidator.isLegal(client);
        if (test) {
            try {

                request.setAttribute("users", clientService.create(client));
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            response.sendRedirect("/rentmanager/users");
        }
    }
}
