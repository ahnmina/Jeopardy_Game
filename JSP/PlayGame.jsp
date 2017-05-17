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
}

;
.active {
	background-color: #4CAF50;
}
;
</style>
<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>
<script
	src='https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js'
	src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'>
</script>
<!--JAVASCRIPT -->
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script>
    //get grid's row and column values and save into hidden input fields for POST
	$(document).ready(
		function() {
			$("#myTable td").hover(
				function() {
					var column_num = parseInt($(this).index()) + 1;
					var row_num = parseInt($(this).parent().index()) + 1;
					//$("#result").html("Row_num =" + row_num+ "  ,  Column_num =" + column_num);
					$("input[id=r]").val(row_num);
					$("input[id=c]").val(column_num);
				});
		});
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
				<h2>
					Currently Playing Game: <b><%=session.getAttribute("gameName")%></b>
				</h2>
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
		
		String gameName = (String) session.getAttribute("gameName");
		File file = new File("WebContent/WEB-INF/data");
		String fileNames[] = file.list();
		String username = "";
		for (int i = 0; i < fileNames.length; i++) {
			if (fileNames[i].contains("$")) {
				//remove .txt from file name
				String tmp = fileNames[i].replace(".txt", "");
				//split the name
				String split[] = tmp.split(Pattern.quote("$"));
				//if correct game is found
				if (split[1].equals(gameName)) {
					username = split[0];
					break;
				}
			}
		}
		
		//add the game's username to the session
		session.setAttribute("currentGameUsername",username);
		
		//get complete game file path
		String fileName = username + "$" + gameName + ".txt";
		File gameFile = new File("WebContent/WEB-INF/data/" + fileName);
		//System.out.println("File name is: " + fileName);

		//go through file and get max row and column size
		Scanner inputFile = new Scanner(gameFile);

		//initialize row and column size
		int rowSize = 0;
		int columnSize = 0;

		//store all lines into an array
		ArrayList<String> lines = new ArrayList<String>();

		while (inputFile.hasNext()) {
			String input = inputFile.nextLine();
			//add line to arraylist
			lines.add(input);
			String[] parts = input.split(",");
			int tmpRow = Integer.parseInt(parts[2]);
			int tmpColumn = Integer.parseInt(parts[3]);
			if (tmpRow > rowSize) {
				rowSize = tmpRow;
			}
			if (tmpColumn > columnSize) {
				columnSize = tmpColumn;
			}
		}
		inputFile.close();
		
		//read arraylist with questions answered information
		ArrayList<Integer> answered;
		answered = (ArrayList<Integer>)session.getAttribute("answered");

		//read file and make board
		out.println("<center>");
		out.println(" <div id=\"result\"></div>");
		out.println(
				"		<table id=\"myTable\" border=\"1\" cellPadding=1 width=\"75%\" border=1 bgColor=darkblue>");
		int num_questions_answered = 0;
		int total_questions = rowSize * columnSize;
		//look for the score value for each grid value
		for (int i = 1; i <= rowSize; i++) {
			out.println("<tr>");
			for (int j = 1; j <= columnSize; j++) {
				for (int k = 0; k < lines.size(); k++) {
					String[] split = lines.get(k).split(",");
					//if it's a match
					int row = Integer.parseInt(split[2]);
					int column = Integer.parseInt(split[3]);
					if (row == i && column == j) {
						//check if the question has already been answered
						int disable = 0;
						if(answered.size() != 0){
							for(int a = 0; a < answered.size()-1; a = a+2){
								//question has been answered already
								if(answered.get(a) == row && answered.get(a+1) == column){
									disable = 1;
									num_questions_answered++;
								}
							}
						}
						out.println("<td style=color:gold>");
						//out.println("<td>");
						out.println("<strong>");
						//out.println(split[4]);
						out.println("<form name='myForm' id='myForm' action='/cs4640work/QuestionInfo.jsp' method='post'>");
					 	out.println("<input type='hidden' id='r' name='row' value='default'>");
						out.println("<input type='hidden' id='c' name='column' value='default'>");
						if(disable == 0){
							out.println("<input type='submit' value='"+ split[4] +"' style='width:100%; background-color:darkblue'>");
						}
						else{
							out.println("<input type='submit' disabled='disabled' value='' style='width:100%; background-color:darkblue'>");
						}
						out.println("</form>");
						out.println("</strong>");
						out.println("</td>");
					}
				}
			}
			out.println("</tr>");
		}
		out.println("</table>");
		
		//get number of teams
		int teamCount = (int)session.getAttribute("teamCount");	
		System.out.println("Number of teams: " + teamCount);
		//ArrayList<Team> teamList = new ArrayList<Team>();
		
		String add1 = request.getParameter("+1");
		String add2 = request.getParameter("+2");
		String add3 = request.getParameter("+3");
		String add4 = request.getParameter("+4");
		int addBy = 100; 
		if(add1!=null){
			System.out.println("TEAM 1");
			String score1a = session.getAttribute("score1a").toString();
			int currentScore = Integer.parseInt(score1a);
			session.setAttribute("score1a",currentScore+addBy);
		}else if(add2!=null){
			System.out.println("TEAM 2");
			String score2a = session.getAttribute("score2a").toString();
			int currentScore = Integer.parseInt(score2a);
			session.setAttribute("score2a",currentScore+addBy);
		}else if(add3 != null){
			System.out.println("TEAM 3");
			String score3a = session.getAttribute("score3a").toString();
			int currentScore = Integer.parseInt(score3a);
			session.setAttribute("score3a",currentScore+addBy);
		}else if(add4 !=null){
			System.out.println("TEAM 4");
			String score4a = session.getAttribute("score4a").toString();
			int currentScore = Integer.parseInt(score4a);
			session.setAttribute("score4a",currentScore+addBy);
		}
		
		out.println("<p>");
		out.println("<form name='teamForm' action='/cs4640work/PlayGame.jsp'  method='post'");
		out.println("<table border=\"1\">");
		//for(int i = 1; i<=teamCount;i++){
		//	Team team = new Team();
		//	team.setId(i);
		//	teamList.add(team);
		//}
		for(int i = 0; i<teamCount;i++){
		int teamNum = i+1;
			if(i==0){
				out.println("<tr>");
				out.println("<th><b> Team "+teamNum+":</b></th>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td name='score'>"+session.getAttribute("score1a")+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td><input type='submit' name='+"+teamNum+"' value='+'></input>");
				out.println("</td>");
				out.println("</tr>");
			}else if(i==1){
				out.println("<tr>");
				out.println("<th><b> Team "+teamNum+":</b></th>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td name='score'>"+session.getAttribute("score2a")+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td><input type='submit' name='+"+teamNum+"' value='+'></input>");
				out.println("</td>");
				out.println("</tr>");
			}else if(i==2){
				out.println("<tr>");
				out.println("<th><b> Team "+teamNum+":</b></th>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td name='score'>"+session.getAttribute("score3a")+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td><input type='submit' name='+"+teamNum+"' value='+'></input>");
				out.println("</td>");
				out.println("</tr>");
			}else if(i==3){
				out.println("<tr>");
				out.println("<th><b> Team "+teamNum+":</b></th>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td name='score'>"+session.getAttribute("score4a")+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td><input type='submit' name='+"+teamNum+"' value='+'></input>");
				out.println("</td>");
				out.println("</tr>");
			}
		}
		out.println("<br>");
		out.println("</table>");
		out.println("</form>");
		out.println("</p>");
		
		
		if(num_questions_answered == total_questions){
			out.println("<h2>Congratulations on completing the Jeopardy Board Game <b> " + gameName + "</b>!</h2>");
			out.println("<input type=\"submit\" name=\"BrowseGames\" value=\"Back to Home\" onclick=\"window.location.href='http://localhost:8080/cs4640work/Browse'\"/>");
		}
		out.println("</center>");
	%>
	
	<%

	%>
</body>
</html>