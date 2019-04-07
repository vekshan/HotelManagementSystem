package eHotel.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eHotel.connections.PostgreSqlConn;
import eHotel.entities.Room;
import eHotel.entities.employee;

public class CustomerloginServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
//		employee account = new employee();
		String userSSN = req.getParameter("userSSN");
		String pwd = req.getParameter("pwd");
		
		PostgreSqlConn con = new PostgreSqlConn();
//		[0]:name,[1]:pwd
		String pwdfromdb = con.getuserinforbycustSSN(Integer.parseInt(userSSN.trim()));
		
		
		
		if (pwd.equals(pwdfromdb)) {	
			req.setAttribute("userSSN", userSSN);
			//resp.sendRedirect("login_success.jsp");
			//resp.sendRedirect("login_success.jsp?userSSN="+userSSN);
			/*ArrayList<Room> bookedRooms = con.getbookedRooms(userSSN);
			
			ArrayList<Room> allRooms = con.getAllAvailRooms();
			
			
			req.setAttribute("CustName", "tom");
			req.setAttribute("bookedRooms", bookedRooms);
			req.setAttribute("allRooms", allRooms);

			req.getRequestDispatcher("booking.jsp").forward(req, resp);
			return;	*/
			
			ArrayList<String> view1 =con.getView1();
			ArrayList<Room> bookedRooms = con.getbookedRooms(userSSN);
			
			ArrayList<Room> allRooms = con.getAllAvailRooms();
			
			
			System.out.println(allRooms);
			
			req.setAttribute("CustName", userSSN);
			req.setAttribute("view1", view1);
			req.setAttribute("bookedRooms", bookedRooms);
			req.setAttribute("allRooms", allRooms);

			req.getRequestDispatcher("booking.jsp").forward(req, resp);
			return;			
		}
		resp.sendRedirect("login_failure.jsp");
		return;
	}
}