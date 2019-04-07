package eHotel.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eHotel.connections.PostgreSqlConn;

/**
 * Servlet implementation class BookingConvert
 */
@WebServlet("/BookingConvert")
public class BookingConvert extends HttpServlet {
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
		String action = request.getParameter("action");
		String custSSN = request.getParameter("custSSN");
		String empSSN = request.getParameter("empSSN");
		PostgreSqlConn con = new PostgreSqlConn();
		if(action.equals("convert")) {

			String[] values =request.getParameter("roominfo").split(",");
			boolean isConverted = con.createRenting(empSSN, custSSN, values[0], values[1], values[2], values[3], values[4], 60.20);
			if(isConverted) {
				request.setAttribute("empSSN", empSSN);
				response.sendRedirect("login_success.jsp?empSSN="+empSSN);
				return;
			}else {
				response.sendRedirect("login_failure.jsp");
				return;
			}
		}else {
			
			String roomno = request.getParameter("roomno");
			String startdate = request.getParameter("startdate");
			String enddate= request.getParameter("enddate");
			String payment = request.getParameter("payment");
			boolean isConverted = con.createRenting(empSSN, custSSN, roomno, startdate, enddate, Double.parseDouble(payment));
			if(isConverted) {
				request.setAttribute("empSSN", empSSN);
				response.sendRedirect("login_success.jsp?empSSN="+empSSN);
				return;
			}else {
				response.sendRedirect("login_failure.jsp");
				return;
			}
		}

	}

}
