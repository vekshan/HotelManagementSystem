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
 * Servlet implementation class RegisterEmployee
 */
@WebServlet("/RegisterEmployee")
public class RegisterEmployee extends HttpServlet {
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String ssn = req.getParameter("SSN");
		String fName = req.getParameter("FName");
		String lName = req.getParameter("LName");
		String pwd = req.getParameter("Pwd");
		String[] hotelinfo = req.getParameter("hotelinfo").split(",");
		
		String[] param = new String[] {ssn,fName, lName, hotelinfo[0],hotelinfo[1],pwd};
		PostgreSqlConn con = new PostgreSqlConn();
		boolean pwdfromdb = con.insertNewEmployee(param);
		if (pwdfromdb) {			
			System.out.println("success");
			ArrayList<String> hotels = con.getAllHotels();
			req.setAttribute("hotels", hotels);
			req.getRequestDispatcher("RegisterEmployee.jsp").forward(req, resp);
			return;			
	}
	resp.sendRedirect("register_failure.jsp");
	return;
		
		
	}

}
