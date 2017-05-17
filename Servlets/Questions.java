package cs4640work;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Questions")
public class Questions extends HttpServlet {

	private static final long serialVersionUID = 2138091187841832097L;

	// input text file (from CSLAB server)
	public static java.lang.String input_data = "http://plato.cs.virginia.edu/~amc4sq/cs4640-assignment4/myfile.txt";
	
	String user = "";
	String str_data = "";

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		//check if someone is logged in - if so, show navigation bar
		HttpSession session=req.getSession();
		String username = (String) session.getAttribute(Login.USERNAME);

		out.println("<html>");
		out.println("   <head>");
		out.println("      <title>Jeopardy</title>");
		//styling/css for navigation bar
		out.println("       <style>");
		out.println("           ul { list-style-type: none; margin: 0; padding: 0; overflow: hidden; background-color: #333; }");
		out.println("           li a { display: block; color: white; text-align: center; padding: 14px 16px; text-decoration: none; }");
		out.println("           .welcome { display: block; color: white; text-align: center; padding: 14px 16px; text-decoration: none; }");
		out.println("           li a: hover { background-color: #111; }");
		out.println("           .active { background-color: #4CAF50;");
		out.println("       </style>");
		out.println( "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>");
		out.println(" <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js'></script>");
		out.println("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>");
		out.println("   </head>");
		out.println("   <body>");
		//navigation bar
		out.println("       <ul>");
		if(username!=null){
			out.println("           <li><a class='non-active' style='float:left;' href='/cs4640work/Browse'>Home</a></li>");
			out.println("           <li><a class='active' style='float:left;' href='/cs4640work/Questions'>Create Game</a></li>");
			out.println("           <li><a class='non-active' style='float:right;' href='/cs4640work/Logout'>Logout</a></li>");
			out.println("           <li class='welcome' style='float:right;'>Welcome, " + username + "</li>");
		}else{
			out.println("           <li><a class='non-active' style='float:left;' href='/cs4640work/'>Login</a></li>");
			out.println("           <li><a class='active' style='float:left;' href='/cs4640work/Questions'>Create Game</a></li>");
		}
		out.println("       </ul>");
		out.println("  <center>");
		out.println("<div class ='container'>");
		out.println("<div class='jumbotron'>");
		out.println("     <h2>Question & Answer Selector<g;/h2><br></br>");
		out.println(" <p>Annette Chun (amc4sq) and Mina Ahn (ma3hd)</p>");
		out.println("</div>");
		out.println("</div>");
		// write instruction on html page
		out.println(
				"<p>Select the questions that you want on the Jeopardy board, assigning its row and column position as well as its point value. </p><br>");
		out.println("  </center>");
		readFile(res);
		
		out.println("   </body>");
		out.println("</html>");
		out.close();
	}
	
	//reads the file created by hw 3
	private void readFile(HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		try {
			res.setContentType("text/html");
			
			java.net.URL url = new java.net.URL(input_data);
			java.net.URLConnection urlcon = url.openConnection();
			java.io.BufferedReader input_file = new java.io.BufferedReader(
					new java.io.InputStreamReader(urlcon.getInputStream()));
			java.lang.String line = new java.lang.String();
			List<QAObject> list = new ArrayList<QAObject>();
			while ((line = input_file.readLine()) != null) {
				if (line.length() > 0) {
					String[] qa = line.split(":");
					QAObject qaObj = new QAObject();
						qaObj.setQuestion(qa[0]);
						qaObj.setAnswer(qa[1]);
						//add question type later
						list.add(qaObj);
	
				}
			}
				out.println("  <center>");
			    out.println("      <form action=\"Questions\" method=\"post\" >");
			    
			    //need to specify owner and game name
				out.println("<strong><p>Please enter the game name: </p></strong>");
				
				out.println("<strong>Owner:</strong> <input type='text' name='owner' value='' placeholder='Enter owner of game'>");
				out.println("<strong>Game Name:</strong> <input type='text' name='gameName' value='' placeholder = 'Enter game name'><br><br>");
			    
				out.println("<strong><p>Please enter the size of the grid: </p></strong>");
				
				out.println("<strong>Row:</strong> <input type='text' name='rowSize' value='' placeholder='Enter number of rows'>");
				out.println("<strong>Column:</strong> <input type='text' name='columnSize' value='' placeholder = 'Enter number of columns'><br><br>");
				out.println("		<table border=1>");
				
				// row1 (column labels)
				out.println("			<tr>");
				out.println("			<th bgcolor=lavender>");
				out.println("Question");
				out.println("			</th>");
				out.println("			<th bgcolor=lavender>");
				out.println("Answer");
				out.println("			</th>");
				out.println("			<th bgcolor=lavender>");
				out.println("Row");
				out.println("			</th>");
				out.println("			<th bgcolor=lavender>");
				out.println("Column");
				out.println("			</th>");
				out.println("			<th bgcolor=lavender>");
				out.println("Score");
				out.println("			</th>");
				out.println("			</tr>");

				
				for (QAObject tmp : list) {
					out.println("			<tr>");
					out.println("			<td >");
					out.println("<textarea name='question'>"+tmp.getQuestion()+"</textarea>");
					out.println("			</td>");
					out.println("			<td>");
					out.println("<textarea name='answer'>"+tmp.getAnswer()+"</textarea>");
					out.println("			</td>");
					out.println("			<td>");
					out.println("<input type = 'text' name='row' value='' placeholder='type row position'>");
					out.println("			</td>");
					out.println("			<td>");
					out.println("<input type = 'text' name='column' value='' placeholder='type column position'>");
					out.println("			</td>");
					out.println("			<td>");
					out.println("<input type = 'text' name='score' value='' placeholder='type score value'>");
					out.println("			</td>");
					out.println("			</tr>");
				}
				out.println("		</table><br>");
				out.println("   <input type=\"submit\" name=\"AddQA\" value=\"Add Question\"/>");
				out.println("   <input type=\"submit\" name=\"BrowseGames\" value=\"Browse Games\"/>");
				out.println("   <input type=\"submit\" name=\"CreateGame\" value=\"Create Game\"/>");
				out.println("</form>");
				out.println("  </center>");
			
			input_file.close();
		} catch (java.lang.Exception e) {
			out.println("<center>");
			out.println("No questions available! Please submit questions or browse other games! <br>");
			out.println("   <input type=\"submit\" name=\"AddQA\" value=\"AddQA\" onclick=\"window.location.href='http://plato.cs.virginia.edu/~amc4sq/cs4640-assignment4/home.php'\"</button>");
			out.println("   <input type=\"submit\" name=\"BrowseGames\" value=\"BrowseGames\"/>");
			out.println("</center>");
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		if(req.getParameter("BrowseGames")!=null) {
		res.sendRedirect("/cs4640work/Browse");
		return;
	}
		
		//if user clicked the "AddQA" button, redirect the user to hw 3's home page
		if(req.getParameter("AddQA")!=null) {
			res.sendRedirect("http://plato.cs.virginia.edu/~amc4sq/cs4640-assignment4/home.php");
			return;
		}
		//if user clicked the "BrowseGames" button, redirect to browse page
		if(req.getParameter("BrowseGames")!=null) {
			res.sendRedirect("/cs4640/Browse");
			return;
		}
		//if the user did not input a row or column size, then send an error
		if(req.getParameter("rowSize")=="" || req.getParameter("columnSize")=="" ) {
			out.println("<html><body><script>alert('Please enter a row size and column size');window.location= 'javascript:history.back()';</script></body></html>");
			return;
		}
		//if the user input a row or column size less than 0, the min value, then send an error
		else if(Integer.parseInt(req.getParameter("rowSize")) <= 0 || Integer.parseInt(req.getParameter("columnSize")) <=0) {
			out.println("<html><body><script>alert('Row size and column size have to be greater than 0');window.location= 'javascript:history.back()';;</script></body></html>");
			return;
		
		}
		else if(req.getParameter("CreateGame")!=null) {
		//need to store game owner, name, questions, answers, row, column, score
			String owner = req.getParameter("owner");
			String gameName = req.getParameter("gameName");
			String[] question = req.getParameterValues("question");
			String[] answer = req.getParameterValues("answer");
			String[] row = req.getParameterValues("row");
			String[] column = req.getParameterValues("column");
			String[] score = req.getParameterValues("score");
			
			//session object to get name of game and owner of game
			HttpSession session = req.getSession();
			//check if someone is logged in - if so, show navigation bar
			String username = (String) session.getAttribute(Login.USERNAME);
			Games games = new Games();
			GameBoard board = new GameBoard();
			board.setGameName(gameName);
			//session.setAttribute("gameName", board);
			board.setUsername((String)session.getAttribute(Login.USERNAME));
			List<GameBoard> gameBoards = new ArrayList<GameBoard>();
			//System.out.println(board.getGameName());

			//String dataFileName = owner + ("$") + gameName;
			File file = new File("C:\\apache-tomcat-8.5.11\\apache-tomcat-8.5.11\\webapps\\cs4640\\WEB-INF\\data\\data.txt");
			
			//creates file for each user
			String dataFileName = owner + ("$") + gameName;
			File userGameFile =new File("WebContent/WEB-INF/data/" + dataFileName +".txt");
			userGameFile.createNewFile();
			FileWriter writer = new FileWriter(userGameFile);

			//			boolean d = file.delete();
			file.delete();
			
			//write to file the owner of the game and game name 
			writeToFile(owner + "$");
			writeToFile(gameName + "$");
			
			List<QAObject> qaObjects = new ArrayList<QAObject>();
			//write to file the questions, answers, row, column, score
			for(int i =0; i < question.length; i++) {
				if(row[i]!="" && column[i]!="" && score[i]!=""
						&& Integer.parseInt(row[i]) > 0 
						&& Integer.parseInt(column[i]) > 0 
						&& Integer.parseInt(score[i]) > 0) {
					//instantiating attributes for QAObject
					QAObject qaObj = new QAObject();
					qaObj.setQuestion(question[i]);
					qaObj.setAnswer(answer[i]);
					qaObj.setRow(Integer.parseInt(row[i]));
					qaObj.setColumn(Integer.parseInt(column[i]));
					qaObj.setScore(Integer.parseInt(score[i]));
					qaObjects.add(qaObj);
				writeToFile(question[i] + "," + answer[i] + "," +  row[i] + "," + column[i] + "," + score[i]);
				writer.write(question[i] + "," + answer[i] + "," +  row[i] + "," + column[i] + "," + score[i]);
				writeToFile("\n");
				writer.write("\n");
				}
			}writer.close();
			
			int numQuestions = qaObjects.size();
			board.setQAObjects(qaObjects);
			gameBoards.add(board);
			games.setGames(gameBoards);
			session.setAttribute(Login.GAMES, games);


			//verify number of questions
			int rowSize = Integer.parseInt(req.getParameter("rowSize"));
			int columnSize= Integer.parseInt(req.getParameter("columnSize"));
			int totalQuestions = rowSize * columnSize; 
	
			
			if(numQuestions < totalQuestions) {
				out.println("<html><body><script>alert('Not enough questions. Please select ' + "+totalQuestions+" + ' question(s). Please make sure you fill out all fields.');window.location= 'javascript:history.back()';</script></body></html>");
				return;
			}else if(numQuestions > totalQuestions){
				out.println("<html><body><script>alert('Too many questions. Please select ' + "+totalQuestions+" + ' question(s). Please make sure you fill out all fields.');window.location= 'javascript:history.back()';</script></body></html>");
				return;
			}
			
			ArrayList<String[]> text = new ArrayList<String[]>();
			
			file = new File("WebContent/WEB-INF/data/data.txt");
			
			//reads the file created by selecting questions for jeopardy board
			Scanner inputFile = null;
			
			try {
				inputFile = new Scanner(userGameFile);
//				inputFile = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			while (inputFile.hasNext()){
				String[] inputs = inputFile.nextLine().split(",");
				text.add(inputs);
			}
			inputFile.close();
			for (int i = 1; i <= rowSize; i++){
				for (int j = 1; j <= columnSize; j++){
					int count = 0;
					for (int k = 0; k < text.size(); k++){
						String[] test = text.get(k);
						//if it's a match then add to number of questions
						if (Integer.parseInt(test[2]) == i && Integer.parseInt(test[3]) == j){
							count++;
						}
						//if the row value or column value is greater than the max row/column size then send out an error
						if(Integer.parseInt(test[2]) > rowSize || Integer.parseInt(test[3]) > columnSize) {
							out.println("<html><body><script>alert('Cannot have row or column value greater than the row or column size.' );window.location='javascript:history.back()';</script></body></html>");
							return;
						}
						//if the row value or column value is less than zero, the min size value, then send out an error
						if(Integer.parseInt(test[2]) < 1 || Integer.parseInt(test[3]) < 1) {
							out.println("<html><body><script>alert('Cannot have a row or column value less than one.' );window.location='javascript:history.back()';</script></body></html>");
							return;
						}
					}
					//if there are two or more questions with the same row/column value, then send out an error
					if(count > 1) {
						out.println("<html><body><script>alert('Cannot have questions with the same row and column value.');window.location='javascript:history.back()';</script></body></html>");
						return;
					}
				}
			}
	
			
			
			//create the jeopardy board game
			out.println("<html>");
			out.println("  <head>");
			out.println("    <title>Jeopardy</title>");
			//styling/css for navigation bar
			out.println("       <style>");
			out.println("           ul { list-style-type: none; margin: 0; padding: 0; overflow: hidden; background-color: #333; }");
			out.println("           li a { display: block; color: white; text-align: center; padding: 14px 16px; text-decoration: none; }");
			out.println("           .welcome { display: block; color: white; text-align: center; padding: 14px 16px; text-decoration: none; }");
			out.println("           li a: hover { background-color: #111; }");
			out.println("           .active { background-color: #4CAF50;");
			out.println("       </style>");
			out.println( "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>");
			out.println(" <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js'></script>");
			out.println("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>");
			out.println("   </head>");
			out.println("   <body>");
			//navigation bar
			out.println("       <ul>");
			if(username!=null){
				out.println("           <li><a class='non-active' style='float:left;' href='/cs4640work/Browse'>Home</a></li>");
				out.println("           <li><a class='non-active' style='float:left;' href='/cs4640work/Questions'>Create Game</a></li>");
				out.println("           <li><a class='non-active' style='float:right;' href='/cs4640work/Logout'>Logout</a></li>");
				out.println("           <li class='welcome' style='float:right;'>Welcome, " + username + "</li>");
			}else{
				out.println("           <li><a class='non-active' style='float:left;' href='/cs4640work/'>Login</a></li>");
				out.println("           <li><a class='non-active' style='float:left;' href='/cs4640work/Questions'>Create Game</a></li>");
			}
			out.println("       </ul>");
			out.println("    <center><h1>Jeopardy Game<br /></h1></center>");
			out.println("<center><b>Game Owner:</b> "+session.getAttribute(Login.USERNAME)+"  <b>Game Name:</b> "+req.getParameter("gameName")+"</center><br>");
			out.println("<center>");
			out.println("		<table border=1 cellPadding=1 width=\"75%\" border=1 bgColor=darkblue>");
			//look for the score value for each grid value
			for (int i = 1; i <= rowSize; i++){
				out.println("<tr>");
				for (int j = 1; j <= columnSize; j++){
					for (int k = 0; k < text.size(); k++){
						String[] test = text.get(k);
						//if it's a match
						if (Integer.parseInt(test[2]) == i && Integer.parseInt(test[3]) == j){
							out.println("<td style=color:gold>");
							out.println("<strong>");
							//out.println(test[4]);
							out.println("<input type='submit' disabled='disabled' value='"+test[4]+"' style='width:100%; background-color:darkblue'>");
							out.println("</strong>");
							out.println("</td>");
						}
					}
				}out.println("</tr>");
			}
			out.println("</table><br>");
			out.println("   <input type=\"submit\" name=\"EditBoard\" value=\"Edit Board\" onclick= 'javascript:history.back()'/>");
			out.println("   <input type=\"submit\" name=\"BrowseGames\" value=\"Back to Home\" onclick=\"window.location.href='http://localhost:8080/cs4640work/Browse'\"/>");
			out.println("</center>");
			out.close();
		}
	}

	private void writeToFile(java.lang.String str) {
		java.lang.String temp_str = str;
		java.lang.String out_str = new java.lang.String();
		int n = 0;
		while ((n = temp_str.indexOf('\n')) > 0) {
			java.lang.String line_str = temp_str.substring(0, n);
			temp_str = temp_str.substring(n + 1, temp_str.length());
			int i = line_str.indexOf(":");
			if (i > 0) {
				out_str = out_str + line_str.substring(i + 1, line_str.length()) + '\n';
			}
		}
		try {
			File file = new File("C:\\apache-tomcat-8.5.11\\apache-tomcat-8.5.11\\webapps\\cs4640\\WEB-INF\\data\\data.txt");
			
			FileWriter fout = new FileWriter(file, true);
			fout.write(str);
			fout.flush();
			fout.close();
		} catch (java.io.IOException e) {
			System.out.println("Error: cannot write to file" + e.toString());
			e.printStackTrace();
		}
	}
}


