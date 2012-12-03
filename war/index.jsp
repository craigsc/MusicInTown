<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script type="text/javascript" src="javascript/jquery-1.4.2.js"></script>
<script type="text/javascript" src="javascript/jquery-ui-1.8rc3.custom.min.js"></script>
<script type="text/javascript" src="javascript/jquery.combobox.js"></script>
<script type="text/javascript" src="javascript/jquery.placeholder-1.0.1.js"></script>
<script type="text/javascript" src="javascript/index.js"></script>

<html>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  	<link rel="stylesheet" type="text/css" href="css/ui-lightness/jquery-ui-1.8rc3.custom.css">
  	<link rel="stylesheet" type="text/css" href="css/main.css">
  	
    <title>MusicInTown</title>
    
    
  </head>

  <body>
  	
  	<div id="beta"></div>
  	<div id="repeatHeader">
            <!--<div id="logo"><a href="/" title="Home"><h1>MusicInTown</h1></a></div>-->
            
    </div>
    <div id="centerImage"><a href="/"><img id="logo" src="css/img/logo.png"/></a></div>
    
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
    
    
    
    <div id="clickme">
  			<div id="actualText">What is this and how do I use it?</div>
	</div>
		<p id="tagLine">Ever miss a concert you would have gone to if you had known about it? MusicInTown aims to eliminate that problem by notifying you with a text message when you favorite artists are in town. You will never miss a concert again.</p>
    <form id="form" action="verify" method="post" >
   
    <div class="bl">
    	<div class="br">
    		<div class="tl">
    			<div class="tr">
					<!--<div id="container1">
    					<div id="edgeLeft"></div>-->
    					<div id="text">Step 1: Have a Last.fm username?<br /> or just put in an artist</div> 
    					<div class="fieldHolder">
  						<input type="text" class="textInput" id="artist" placeholder="Username/Artist..." name="artist" />

  						</div>
  						<!--<div id="edgeRight"></div>
    				</div>-->
				</div>
			</div>
		</div>
	</div>
	<div class="clear">&nbsp;</div>
    
    <div class="bl">
    	<div class="br">
    		<div class="tl">
    			<div class="tr">
					<!--<div id="container1">
    					<div id="edgeLeft"></div>-->
    					<div id="text">Step 2: What is your cell number?</div>
    					<div class="fieldHolder">
  						<input type="text" class="textInput" id="phone" placeholder="Phone Number..." name="phone"/>
  						</div>
  						<!--<div id="edgeRight"></div>
    				</div>-->
				</div>
			</div>
		</div>
	</div>
	<div class="clear">&nbsp;</div>
    

    <!--<div id="container2">
    	<div id="edgeLeft"></div>
    	
  		<div id="edgeRight"></div>
    </div>-->
 <div id="bottom">
 <div class="bl">
    	<div class="br">
    		<div class="tl">
    			<div class="tr">
					<!--<div id="container1">
    					<div id="edgeLeft"></div>-->
    					<div id="autoLoc" >
    						<div id="text">Step 3: Verify location</div>
    						<p id="town"></p>
    						<div id="smallText">That's not where I live!</div>
    					</div>
    					<div id="manualLoc">
	    					<div id="text">Step 3: Enter your zipcode</div>
	    					<div class="fieldHolder">
							<input type="text" class="textInput" id="zipcode" placeholder="Zipcode..."/>
	  						</div>
	  					</div>
  						<input type="hidden" id="lat" name="lat" value=""/>
  						<input type="hidden" id="long" name="long" value=""/>

  						<input type="hidden" id="isUsername" name="isUsername" value="0"/>
  						<div id="submit"><input type="image" src="css/img/submit.png" value="Submit button" /></div>

  						<!--<div id="edgeRight"></div>
    				</div>-->
				</div>
			</div>
		</div>
	</div>
	<div class="clear">&nbsp;</div>
 	</div>
  	<!--<div id="container3">
		
  		
	</div>-->
	
	</form>
    
    
    
    
    <div id="footer"></div>
	
 
    
  </body>
</html>
