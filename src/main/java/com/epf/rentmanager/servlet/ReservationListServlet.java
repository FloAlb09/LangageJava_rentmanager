package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.modele.Client;
import com.epf.rentmanager.modele.Reservation;
import com.epf.rentmanager.modele.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


@WebServlet("/rents")
public class ReservationListServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    @Autowired
    ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("listReservations", reservationService.findInfoAll());
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reservation_id_string_delete = request.getParameter("deleteReservation");
        if (reservation_id_string_delete != null) {
            long reservation_id_delete = Long.parseLong(reservation_id_string_delete);
            Reservation reservationDelete = new Reservation(reservation_id_delete);
            try {
                reservationService.delete(reservationDelete);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            response.sendRedirect("/rentmanager/rents");
        }

        String reservation_id_string_update = request.getParameter("updateReservation");
        if (reservation_id_string_update != null) {
            long reservation_id_update = Long.parseLong(reservation_id_string_update);
            ReservationUpdateServlet.recupIdReservation(reservation_id_update);
            response.sendRedirect("/rentmanager/rents/update");
        }

        String reservation_id_string_detail = request.getParameter("detailReservation") ;
        if (reservation_id_string_detail != null) {
            long reservation_id_detail = Long.parseLong(reservation_id_string_detail);
            ReservationDetailServlet.recupIdReservation(reservation_id_detail);
            response.sendRedirect("/rentmanager/rents/details");
        }
    }
}

