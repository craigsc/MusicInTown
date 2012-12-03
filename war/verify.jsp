<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  	<link rel="stylesheet" type="text/css" href="css/main.css">
  	
    <title>MusicInTown</title>
  </head>

  <body>
  	<div id="beta"></div>
  	<div id="repeatHeader">
    </div>
    <div id="centerImage"><a href="/"><img id="logo" src="css/img/logo.png"/></a></div>
    <p id="tagLine">Get notified about your favorite artists' upcoming concerts!</p>

	<div id="verify">
		<div id="text">A verification code has been sent to your phone. Please verify your phone to begin receiving notifications (we promise we don't spam and unsubscribing is a single text away).
		</div>
	</div>
	
	
	<% String error = (String) session.getAttribute("error");
	   if (error != null && !error.equals("")) {
		   session.removeAttribute("error");
	%>
	<div id="error"><%= error %></div>
	<%} %>
	
		<div id="spotlightLT"></div>
    	<div id="spotlightLB"></div>
    <div id="spotlightContainer">
   		<div id="spotlightRT"></div>
    	<div id="spotlightRB"></div>
    </div>
    <div id="verify">
	<form id="verification" action="welcome" method="post">
	<div class="fieldHolder">
		<input type="text" class="textInput" name="verificationCode" placeholder="Verification Code"/>
	</div>	
		<input type="hidden" name="phone" value="<%= session.getAttribute("phone") %>" />
		<input type="image" src="css/img/verify.png" id="verifyButton" value="Verify!"/>
	</form>
	</div>
	<div id="footer"></div>
	
  </body>
</html>