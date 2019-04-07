<%@page import="java.util.ArrayList"%>
<%@page import="eHotel.entities.Room"%>
<%@page import="java.text.SimpleDateFormat"%>;  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Employee Controls</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="ch-cn">
</head>
<body>

<%
		String empSSN = (String)request.getParameter("empSSN");
		String custSSN = (String) request.getAttribute("CustName");
		String chain_id ="";
		String h_name= "";
		String roomno= "";
		String roominfoChoice = "";
		String startdate = "";
		String enddate = "";
%>
	<h4>Welcome,<%=empSSN%><h4>
	
<form method = "post" action="BookingSearch"><center>
Enter customer SSN:<input type="text" id="custSSN" name="custSSN">
<input type="hidden" name="empSSN" value="<%=empSSN%>" />
<button type="submit" value="search">search</button>
</center></form>

<form method="post" action="BookingConvert" >
					
				<h4>Convert Renting</h4>

				<select name ="roominfo">
					<%
						Object obj = request.getAttribute("bookedRooms");
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
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
								startdate = dateFormat.format(room.getStartdate());
								enddate = dateFormat.format(room.getEnddate());
								
								roominfoChoice = "Room " + roomno +" at " + h_name +" hotel from " + startdate + " to "+ enddate;		
								String val = chain_id+","+ h_name+","+ roomno +","+ startdate + ","+enddate;

					%>					
						<option value="<%=val%>"><%=roominfoChoice%></option>
						
					<%
						}
						
						}
					%>
						<input type="hidden" name="empSSN" value="<%=empSSN%>" />
						<input type="hidden" name="custSSN" value="<%=custSSN%>" />
					
				</select>
				Amount Paid:<input type="number" step="0.01" id="payment" name="payment">
				
				
				
				<button type="submit" name="action" value="convert" onclick="return confirm('Confirm Renting?');">Confirm</button>
	</form>

<form method="post" action="BookingConvert">
<h4>Create New Renting</h4>
Customer SSN:<input type="number" id="custSSN" name="custSSN">
Room no:<input type="number" id="roomno" name="roomno">
Start date:<input type="date" id="startdate" name="startdate">
End date:<input type="date" id="enddate" name="enddate">
Payment:<input type="number" step="0.01" id="payment" name="payment"><br>
<input type="hidden" name="empSSN" value="<%=empSSN%>" />
<button type="submit" name="action" value="create" onclick="return confirm('Confirm Renting?');">Confirm</button>
</form>

</body>
</html>
	
</body>
</html>
