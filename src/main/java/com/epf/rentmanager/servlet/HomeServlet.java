package com.epf.rentmanager.servlet;

import java.io.IOException;
//servlet
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//service
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ReservationService;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private ClientService clientService;
	private VehicleService vehicleService;

	private ReservationService reservationService;

	public static ClientService instance;
	public static VehicleService vehicleInstance;
	public static ReservationService reservationInstance;


	public HomeServlet() {
		this.clientService = ClientService.getInstance();
		this.vehicleService = VehicleService.getVehicleInstance();
		this.reservationService = ReservationService.getReservationInstance();
	}



	//verbe doGet
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println(clientService.count());
			request.setAttribute("nbClients", clientService.count());
			request.setAttribute("nbVehicles", vehicleService.count());
			request.setAttribute("nbReservations", reservationService.count());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		//on pousse la ressource de home.jsp: on recupere le ServletContext+chemin jusquau hom.jsp + forward la ressource avec une requete et reponse
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

}
