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


public class RoomsearchServlet extends HttpServlet {
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
		
		String custSSN = request.getParameter("custName");
		String area = request.getParameter("area");
		int capacity = Integer.parseInt(request.getParameter("capacity"));
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		
		PostgreSqlConn con = new PostgreSqlConn();
		ArrayList<Room> bookedRooms = con.getAllAvailRooms();
		ArrayList<Room> matchedRooms = con.searchRooms(capacity, area,startdate,enddate);
		
		request.setAttribute("CustName", custSSN);
		request.setAttribute("bookedRooms", bookedRooms);
		request.setAttribute("allRooms", matchedRooms);
		request.getRequestDispatcher("booking.jsp").forward(request, response);
		return;		
		
		
	}

}
