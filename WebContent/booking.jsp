<%@page import="java.util.ArrayList"%>
<%@page import="eHotel.entities.Room"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Booking Page</title>
</head>
<body>

	<%
		String CustName = (String) request.getAttribute("CustName");
		String chain_id ="";
		String h_name= "";
		String roomno= "";
		String roominfoChoice = "";
		String startdate = (String)request.getAttribute("startdate");
		String enddate = (String)request.getAttribute("enddate");
		
	%>
	
	<h4>
			Welcome,
			<%=CustName%><h4>
				
	<a href="profile_edit.jsp?custSSN=<%=CustName%>">Edit Profile</a>
	<form method="post" action="roomsearch">
	<h4>Please enter your criteria before booking</h4>
					  <label for="area">Area:</label>
					  <input type="text" id="area" placeholder="Enter Area" name="area">
					  <label for="capacity">Capacity(Mandatory):</label>
					  <input type="number" id="capacity" placeholder="Enter capacity" name="capacity">
					  <label for="startdate">Starting on(Mandatory):</label>
					  <input type="date" id="startdate" name="startdate">
					  <label for="enddate">Ending on(Mandatory):</label>
					  <input type="date" id="enddate" name="enddate">
					  <input type="hidden" name="custName" value="<%=CustName%>" />
					
					  <button type="submit">Search</button>
	</form>

	<form method="post" action="roombook">
	<h4>Here are the room(s) you booked</h4>
				<ul>
					<%
						Object obj1 = request.getAttribute("bookedRooms");
						ArrayList<Room> broomList = null;
						if (obj1 instanceof ArrayList) {
							broomList = (ArrayList<Room>) obj1;
						}
						if (broomList != null) {
							for (Room room : broomList) {
							
								String roominfo = "Room " + Integer.toString(room.getRoom_no())+" at " + room.getH_name() +" hotel from " + room.getStartdate() + " to " + room.getEnddate();
					%>
					<li><%=roominfo%></li>
					<%
						}
						}
					%>
				</ul>
				<input type="hidden" name="custName" value="<%=CustName%>" />
					
				<h4>Here are all available rooms</h4>

				<select name ="roominfo">
					<%
						Object obj = request.getAttribute("allRooms");
						ArrayList<Room> roomList = null;
						if (obj instanceof ArrayList) {
							roomList = (ArrayList<Room>) obj;
						}
						if (roomList != null) {
							for (Room room : roomList) {
								/* String roominfo = room.getRoom_no() + "---" + room.getRoom_status(); */
								chain_id = Integer.toString(room.getChain_id());
								h_name = room.getH_name();
								roomno = Integer.toString(room.getRoom_no());
								roominfoChoice = "Room " + roomno +" at " + h_name +" hotel";		
								String val = chain_id+","+ h_name+","+ roomno +","+ startdate + ","+enddate;

					%>					
						<option value="<%=val%>"><%=roominfoChoice%></option>
						
					<%
						}
						
						}
					%>
						<input type="hidden" name="chain_id" value="<%=chain_id%>" />
						<input type="hidden" name="h_name" value="<%=h_name%>" />
						<input type="hidden" name="roomno" value="<%=roomno%>" />
					
				</select>
				
				
				
				<button type="submit" onclick="return confirm('book?');">book</button>
				
				<h4>Number of Rooms per Area</h4>
				<ul>
					<%
						Object obj2 = request.getAttribute("view1");
						ArrayList<String> view1 = null;
						if (obj2 instanceof ArrayList) {
							view1 = (ArrayList<String>) obj2;
						}
						if (view1 != null) {
							for (String v1 : view1) {
							
						%>
					<li><%=v1%></li>
					<%
						}
						}
					%>
				</ul>
	</form>


</body>
</html>