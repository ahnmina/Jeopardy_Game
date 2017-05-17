<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Start Game</title>
<style>
ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	background-color: #333;
}

li a {
	display: block;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}

.welcome {
	display: block;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}

li a: hover {
	background-color: #111;
}

;
.active {
	background-color: #4CAF50;
}
;
</style>
<link rel='stylesheet'
	href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>
<script
	src='https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js'></script>
<script
	src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>
</head>
<body>
	<!-- navigation bar -->
	<ul>
		<li><a class='non-active' style='float: left;' href='/cs4640work/Browse'>Home</a></li>
		<li><a class='non-active' style='float: left;' href='/cs4640work/Questions'>Create Game</a></li>
		<li><a class='non-active' style='float: right;' href='/cs4640work/Logout'>Logout</a></li>
	    <li><a class='welcome' style='float:right;'>Welcome, <%=session.getAttribute("username") %></a></li>	
	</ul>
	<center>
		<div class='container'>
			<div class='jumbotron'>
							<h2> Starting Game: <b><%= session.getAttribute("gameName") %></b></h2>
				<p>Annette Chun (amc4sq) and Mina Ahn (ma3hd)</p>
			</div>
		</div>
	</center>
<center>
		<h2>Few Simple Rules</h2>
		<p>
			<center>1)Rounds: Jeopardy!</center>
			<center>2)Choose the question - category and point value</center>
			<center>3)Only 1 team member raises hand</center>
			<center>4)The first correct answer receives points</center>
			<center>5)Highest score wins</center>
		</p>
		<br>
		<form action="" method="post">
			Enter number of teams: <input type='text' name="teams" placeholder='max 4 teams'/> <input
				type='submit' name='btn' value='Start' /> 
		</form>
	</center>
</body>
</html>

<%
	//check if a user is not logged in. if so, redirect to login page
	String username = (String) session.getAttribute("username");
	if(username==null){
		response.sendRedirect("/cs4640work/");
	}
	
    //set the initial session object for arraylist of already answered questions
    ArrayList<Integer> answered = new ArrayList<Integer>();
    session.setAttribute("answered", answered);
    		
	String numTeams = request.getParameter("teams");
	if (request.getParameter("btn") != null) {
		if (numTeams.length() == 0){
			out.println("<html><body><script>alert('Please enter a number between 1 and 4!' );</script></body></html>");
			System.out.println("User didn't submit a team number.");
			return;
		}
	}
	if (numTeams != null && numTeams.length() > 0) {
		if (Integer.parseInt(numTeams) <= 0) {
			out.println("<html><body><script>alert('Please enter a number greater than 0!' );</script></body></html>");
			return;
		} else if (Integer.parseInt(numTeams) > 4) {
			out.println("<html><body><script>alert('Please enter a number less than or equal to 4!' );</script></body></html>");
			return;
		} else{
			HttpSession sess = request.getSession();
			sess.setAttribute("teamCount",Integer.parseInt(numTeams));
			String sessAtr = sess.getAttribute("teamCount").toString();
		 	sess.setAttribute("score1a", 0);
			sess.setAttribute("score2a", 0);
			sess.setAttribute("score3a",0);
			sess.setAttribute("score4a",0); 
		
			response.sendRedirect("/cs4640work/PlayGame.jsp");
		}
	}
%>











