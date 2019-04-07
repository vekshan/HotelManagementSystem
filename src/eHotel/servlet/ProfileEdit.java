package eHotel.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eHotel.connections.PostgreSqlConn;
import eHotel.entities.Room;

/**
 * Servlet implementation class ProfileEdit
 */
@WebServlet("/ProfileEdit")
public class ProfileEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String value = request.getParameter("action");
		String custSSN = request.getParameter("custSSN");
		PostgreSqlConn con = new PostgreSqlConn();
		if(value.equals("email")) {
			boolean isUpdated = con.updateEmail(request.getParameter("custEmail"), custSSN);
			if(isUpdated) {
				ArrayList<Room> bookedRooms = con.getbookedRooms(custSSN);
				
				ArrayList<Room> allRooms = con.getAllAvailRooms();
				
				System.out.println(allRooms);
				
				request.setAttribute("CustName", custSSN);
				request.setAttribute("bookedRooms", bookedRooms);
				request.setAttribute("allRooms", allRooms);

				request.getRequestDispatcher("booking.jsp").forward(request, response);
				return;			
			}
		}else {
			System.out.println(value);
		}
		
	}

}
