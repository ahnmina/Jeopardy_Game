<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.io.File"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.Scanner"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cs4640work.*"%>
<%@page import="java.util.*"%>

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
		};
		.active {
			background-color: #4CAF50;
		};
	</style>
	<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>
	<script>
		src='https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js'
		src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'
	</script>
	<script>
		function showAnswer(ans){
			var xhttp;
			if (window.XMLHttpRequest) {
				// code for modern browsers
				xhttp = new XMLHttpRequest();
			} else {
				// code for IE6, IE5
				xhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					document.getElementById("answer").innerHTML = ans;
				}
			};
			xhttp.open("GET", "ajax_info.txt", true);
			xhttp.send();
		}
	</script>
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
				<h2> Currently Playing Game: <b><%= session.getAttribute("gameName") %></b></h2>
				<p>Annette Chun (amc4sq) and Mina Ahn (ma3hd)</p>
			</div>
		</div>
	</center>
	<% 
		//check if a user is not logged in. if so, redirect to login page
		String username2 = (String) session.getAttribute("username");
		if(username2==null){
			response.sendRedirect("/cs4640work/");
		}
	
		//grab selected question's row and column values from POST data
		int row = Integer.parseInt(request.getParameter("row"));
		int column = Integer.parseInt(request.getParameter("column"));
		
		//grab username and game name from session object
		String username = (String) session.getAttribute("currentGameUsername");
		String gameName = (String) session.getAttribute("gameName");
		
		//get complete game file path
		String fileName = username + "$" + gameName + ".txt";
		File gameFile = new File("WebContent/WEB-INF/data/" + fileName);
		System.out.println("Current file game being played is: " + fileName);
		
		//go through file and get question and answer related to the selected row and column
		Scanner inputFile = new Scanner(gameFile);

		while (inputFile.hasNext()) {
			String input = inputFile.nextLine();
			String[] split = input.split(",");
			//if the row and columns values match then get the question and answer values
			if(Integer.parseInt(split[2])==row && Integer.parseInt(split[3]) == column){
				out.println("<center>");
				out.println("<h2><b>" + split[0] + "</b></h2>");
				out.println("<h4 id='answer'></h4>");
				out.println("<form name='myForm' id='myForm' action='/cs4640work/PlayGame.jsp' method='post'>");
				out.println("<button type=\"button\" onclick=\"showAnswer('"+split[1]+"')\">Show Answer</button>");
				out.println("<input type='submit' value='Continue'/>");
				out.println("</center>");
				break;
			}
		}
		ArrayList<Integer> tmp;
		tmp = (ArrayList<Integer>)session.getAttribute("answered");
		tmp.add(row);
		tmp.add(column);
		inputFile.close();		
	%>
</body>
</html>

