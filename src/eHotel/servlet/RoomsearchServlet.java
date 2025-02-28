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
		
		String area ="";
		
		String custSSN = request.getParameter("custName");
		area = request.getParameter("area");
		int capacity = Integer.parseInt(request.getParameter("capacity"));
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		
		PostgreSqlConn con = new PostgreSqlConn();
		ArrayList<Room> bookedRooms = con.getbookedRooms(custSSN);
		ArrayList<Room> matchedRooms = con.searchRooms(capacity, area,startdate,enddate);
		ArrayList<String> view1 =con.getView1();
		
		request.setAttribute("CustName", custSSN);
		request.setAttribute("bookedRooms", bookedRooms);
		request.setAttribute("allRooms", matchedRooms);
		request.setAttribute("startdate", startdate);
		request.setAttribute("enddate", enddate);
		request.setAttribute("view1", view1);
		request.getRequestDispatcher("booking.jsp").forward(request, response);
		return;		
		
		
	}

}
