<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register Employee</title>
</head>
<body>
<form method = "post" action="RegisterEmployee">
please input SSN:<input type="text" id="SSN" name="SSN"><br><br>
please input first name:<input type="text" id="FName" name="FName"><br><br>
please input last name:<input type="text" id="LName" name="LName"><br><br>
please input password:<input type="password" id="Pwd" name="Pwd"><br><br>
please input password again:<input type="password" id="Pwdagain" name="Pwdagain"><br><br>

<select name ="hotelinfo">
					<%
						Object obj = request.getAttribute("hotels");
						ArrayList<String> hotels = null;
						if (obj instanceof ArrayList) {
							hotels = (ArrayList<String>) obj;
						}
						if (hotels != null) {
							for (String hotel : hotels) {
								

					%>					
						<option value="<%=hotel%>"><%=hotel%></option>
						
					<%
						}
						
						}
					%>
</select>
<button type="submit" value="submit" onclick="return validate();">submit</button>
<button type="reset" value="reset">reset</button>
</body>
</html>