<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Your Profile</title>
<script>

	function validateEmail() {
		var custPwdagain = document.getElementById("custEmail");
		if(custEmail.value == "" ){
			alert("You need to fill all the information");
			return false;
		}
		else
			return true;
	}
	

	function validatePassword() {
		var custPwd = document.getElementById("custPwd");
		var custPwdagain = document.getElementById("custPwdagain");
		if(custPwd.value == "" || custPwdagain.value == "" ){
			alert("You need to fill all the information");
			return false;
		}
		else if(custPwd.value != custPwdagain.value){
			alert("passwords need to be same");
			return false;
		}
		else
			return true;
	}

</script>

</head>
<body>
<%
		String custEmail = (String) request.getAttribute("custEmail");
		String custSSN = request.getParameter("custSSN");
		
	%>
	
	<h4>You will be logged out to apply changes</h4>
<form method = "post" action="ProfileEdit">
Please input your new email:<input type="text" id="custEmail" name="custEmail"><br><br>
<input type="hidden" name="custSSN" value="<%=custSSN%>" />
<button name = "action" type="submit" value="email" onclick="return validateEmail();">submit</button>
<button type="reset" value="reset">Update</button>
</form>

<form method = "post" action="ProfileEdit">
Please input your new password:<input type="password" id="custPwd" name="custPwd"><br><br>
Please input your new password again:<input type="password" id="custPwdagain" name="custPwdagain"><br><br>
<input type="hidden" name="custSSN" value="<%=custSSN%>" />
<button name = "action" "type="submit" value="password" onclick="return validatePassword();">submit</button>
<button type="reset" value="reset">Update</button>
</form>

</body>
</html>