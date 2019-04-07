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
 * Servlet implementation class BookingSearch
 */
@WebServlet("/BookingSearch")
public class BookingSearch extends HttpServlet {
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
		String custSSN = request.getParameter("custSSN");
		String empSSN = request.getParameter("empSSN");
		PostgreSqlConn con = new PostgreSqlConn();
		ArrayList<Room> bookedRooms = con.getbookedRooms(custSSN,empSSN);
		request.setAttribute("CustName", custSSN);
		request.setAttribute("empSSN", empSSN);
		request.setAttribute("bookedRooms", bookedRooms);
		request.getRequestDispatcher("login_success.jsp").forward(request, response);
		
	}

}
