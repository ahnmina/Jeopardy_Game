package cs4640work;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String owner = (String)session.getAttribute(Login.USERNAME);
		String gameName = req.getParameter("gameName");
		
		String dataFileName = owner + ("$") + gameName;
		File userGameFile =new File("WebContent/WEB-INF/data/" + dataFileName +".txt");

		Scanner inputFile = null;
		
		try {
			inputFile = new Scanner(userGameFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<QAObject> list = new ArrayList<QAObject>();
		while (inputFile.hasNext()){
			String[] qa = inputFile.nextLine().split(",");
			QAObject qaObj = new QAObject();
			qaObj.setQuestion(qa[0]);
			qaObj.setAnswer(qa[1]);
			qaObj.setRow(Integer.parseInt(qa[2]));
			qaObj.setColumn(Integer.parseInt(qa[3]));
			qaObj.setScore(Integer.parseInt(qa[4]));
			// add question type later
			list.add(qaObj);
		}
		
		inputFile.close();
		
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		out.println("<html>");
		out.println("   <head>");
		out.println("      <title>Jeopardy</title>");
		//styling/css for navigation bar
		out.println("      <style>");
		out.println("         ul { list-style-type: none; margin: 0; padding: 0; overflow: hidden; background-color: #333; }");
		out.println("         li a { display: block; color: white; text-align: center; padding: 14px 16px; text-decoration: none; }");
		out.println("         .welcome { display: block; color: white; text-align: center; padding: 14px 16px; text-decoration: none; }");
		out.println("         li a: hover { background-color: #111; }");
		out.println("         .active { background-color: #4CAF50;");
		out.println("      </style>");
		out.println(
				"<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>");
		out.println(" <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js'></script>");
		out.println("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>");
		out.println("   </head>");
		out.println("   <body>");
		//navigation bar
		out.println("       <ul>");
		out.println("           <li><a class='non-active' style='float:left;' href='/cs4640work/Browse'>Home</a></li>");
		out.println("           <li><a class='non-active' style='float:left;' href='/cs4640work/Questions'>Create Game</a></li>");
		out.println("           <li><a class='non-active' style='float:right;' href='/cs4640work/Logout'>Logout</a></li>");
		out.println("           <li class='welcome' style='float:right;'>Welcome, " + owner + "</li>");
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
		out.println("  </center>");		out.println("  <center>");
	    
	    out.println("      <form action=\"Questions\" method=\"post\" >");
	    out.println("<strong><p>Please enter the game name: </p></strong>");
		out.println("<strong>Owner:</strong> <input type='text' name='owner' value='" + owner + "'>");
		out.println("<strong>Game Name:</strong> <input type='text' name='gameName' value='" + gameName + "'><br><br>");

		
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

		// load QA from saved game file
		for (QAObject tmp : list) {
			out.println("			<tr>");
			out.println("			<td >");
			out.println("<textarea name='question'>"+tmp.getQuestion()+"</textarea>");
			out.println("			</td>");
			out.println("			<td>");
			out.println("<textarea name='answer'>"+tmp.getAnswer()+"</textarea>");
			out.println("			</td>");
			out.println("			<td>");
			out.println("<input type = 'text' name='row' value='" + tmp.getRow() + "'>");
			out.println("			</td>");
			out.println("			<td>");
			out.println("<input type = 'text' name='column' value='" + tmp.getColumn() + "'>");
			out.println("			</td>");
			out.println("			<td>");
			out.println("<input type = 'text' name='score' value='" + tmp.getScore() + "'>");
			out.println("			</td>");
			out.println("			</tr>");
		}
		
		// load QA from the server excluding the QA from the saved data file
		for (QAObject qa : getAllQAs(list)) {
			out.println("			<tr>");
			out.println("			<td >");
			out.println("<textarea name='question'>"+qa.getQuestion()+"</textarea>");
			out.println("			</td>");
			out.println("			<td>");
			out.println("<textarea name='answer'>"+qa.getAnswer()+"</textarea>");
			out.println("			</td>");
			out.println("			<td>");
			out.println("<input type = 'text' name='row' value=''>");
			out.println("			</td>");
			out.println("			<td>");
			out.println("<input type = 'text' name='column' value=''>");
			out.println("			</td>");
			out.println("			<td>");
			out.println("<input type = 'text' name='score' value=''>");
			out.println("			</td>");
			out.println("			</tr>");
		}
		out.println("		</table><br>");
		out.println("   <input type=\"submit\" name=\"AddQA\" value=\"Add Question\"/>");
		out.println("   <input type=\"submit\" name=\"CreateGame\" value=\"Update Game\"/>");
		out.println("</form>");
		out.println("  </center>");
		
		out.println("   </body>");
		out.println("</html>");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected List<QAObject> getAllQAs(List<QAObject> qaFromDataFile) {
		List<QAObject> list = new ArrayList<QAObject>();
		try {
			URL url = new URL(Questions.input_data);
			URLConnection urlcon = url.openConnection();
			BufferedReader input_file = new BufferedReader(
					new InputStreamReader(urlcon.getInputStream()));
			String line = new java.lang.String();
			
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		list.removeAll(qaFromDataFile);
		// create List of just questions from data file
		List<String> questionsFromDataFile = new ArrayList<String>();
		for (QAObject question : qaFromDataFile) {
			questionsFromDataFile.add(question.getQuestion());
		}
		// Loop through QAObjects from the big list comparing it's questions against
		// the questions from the data file
		// and if question from big list is not contained in questions from data file
		// add to List, updated.
		List<QAObject> updated = new ArrayList<QAObject>();
		for (QAObject qaObj : list) {
			if (!questionsFromDataFile.contains(qaObj.getQuestion().trim())) {
				updated.add(qaObj);
			}
		}
		return updated;
	}
}


