package cs4640work;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Browse")
public class Browse extends HttpServlet {
	
	private static final long serialVersionUID = -1064059883610425417L;

	// doGet method
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
	
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		//check if a user is not logged in. if so, redirect to login page
		HttpSession session=req.getSession();
		String username = (String) session.getAttribute(Login.USERNAME);
		if(username==null){
			res.sendRedirect("/cs4640work/");
		 }
		
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
		out.println(
				"<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>");
		out.println(" <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js'></script>");
		out.println("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>");
		out.println("   </head>");
		out.println("   <body>");
		//navigation bar
		out.println("       <ul>");
		out.println("           <li><a class='active' style='float:left;' href='/cs4640work/Browse'>Home</a></li>");
		out.println("           <li><a class='non-active' style='float:left;' href='/cs4640work/Questions'>Create Game</a></li>");
		out.println("           <li><a class='non-active' style='float:right;' href='/cs4640work/Logout'>Logout</a></li>");
		out.println("           <li class='welcome' style='float:right;'>Welcome, " + username + "</li>");
		out.println("       </ul>");
		out.println("  <center>");
		out.println("<div class ='container'>");
		out.println("<div class='jumbotron'>");
		out.println("     <h2>List of Available Games<g;/h2><br></br>");
		out.println(" <p>Annette Chun (amc4sq) and Mina Ahn (ma3hd)</p>");
		out.println("</div>");
		out.println("</div>");
		
		readFile(res, username);
		
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	// doPost method
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		res.setContentType("text/html");
		//PrintWriter out = res.getWriter();

		// if user clicked the "Play" button, has no functionality for hw5
		 if(req.getParameter("Play")!=null) {
			 String gameName = (String)req.getParameter("Play");
			 gameName = gameName.replace("Play ",  "");
			 session.setAttribute("gameName", gameName);
			 res.sendRedirect("/cs4640work/StartGame.jsp");
			 return;
		 }

		// if user clicked on "Update" button, allows user to modify selected game
		if (req.getParameter("Update") != null) {
			String gameName = (String)req.getParameter("Update");
			gameName = gameName.replace("Update ", "");
			res.sendRedirect("/cs4640work/Update?gameName=" + gameName);
			return;
		}
		// if user clicked on "Delete" button, deletes selected game
		if (req.getParameter("Delete") != null) {
			String user = (String)session.getAttribute(Login.USERNAME);
			String gameName = (String)req.getParameter("Delete");
			gameName = gameName.replaceAll("Delete ", "");
			String dataFileName = user + "$" + gameName;
			File file = new File("WebContent/WEB-INF/data/" + dataFileName +".txt");
			removeFile(res, user, file);
			System.out.println("File has been deleted");
			res.sendRedirect(req.getRequestURI());
			return;
		}
		// if user clicked on "Create Game" button, redirects to creating board game page
		if (req.getParameter("createGame") != null) {
			res.sendRedirect("/cs4640work/Questions");
			return;
		}
		
		// if user clicked on "BrowseGames" button, redirects to Browse page
		if (req.getParameter("BrowseGames") != null) {
			res.sendRedirect("/cs4640work/Browse");
			return;
		}
	}

	private void removeFile(HttpServletResponse res, String username, File filename) throws IOException{
		PrintWriter out = res.getWriter();
		try {
			res.setContentType("text/html");
			
			Games games = getAllGames();
			List<GameBoard> gameBoards = games.getGames();
			//add game name as argument too
			for(GameBoard gameBoard: gameBoards){
				if(gameBoard.getUsername().equals(username)){
					filename.delete();
				}
			}
		} catch (java.lang.Exception e) {
			out.println("<center>");
			out.println("File does not exist!<br>");
			out.println(
					"   <input type=\"submit\" name=\"CreateGame\" value=\"Create Game\" onclick=\"window.location.href='http://plato.cs.virginia.edu/~amc4sq/cs4640-assignment4/home.php'\"</input>");
			out.println("</center>");
			out.close();
		}
	}
	
	// reads the file created by hw 3 to get game info
	private void readFile(HttpServletResponse res, String username) throws IOException {
		PrintWriter out = res.getWriter();
		try {
			res.setContentType("text/html");
			
			Games games = getAllGames();
			List<GameBoard> gameBoards = games.getGames();
			
			if (gameBoards.isEmpty()){
				out.println("No games are available! Please create a game.");
			}
			
			for (GameBoard gameBoard : gameBoards) {
				//testing purpose
				//System.out.println("Username: " + gameBoard.getUsername() + " - " + 
				//		"Gamename: " + gameBoard.getGameName());
			
				// the table that lists all of the games (owner and name) and three buttons
				out.println(" <form action=\"Browse\" method=\"post\" >");
				out.println("<table border=1>");
				
				// row 1 (labels)
				out.println("			<tr>");
				out.println("			<th bgcolor=lavender>");
				out.println("Game Owner");
				out.println("			</th>");
				out.println("			<th bgcolor=lavender>");
				out.println("Game Name");
				out.println("			</th>");
				out.println("			<th bgcolor=lavender>");
				out.println("<center>Choose an Option</center>");
				out.println("			</th>");
				out.println("</tr>");
	
				out.println("<tr>");
				out.println("<td>");
				out.println("" + gameBoard.getUsername() + "");
				out.println("</td>");
				out.println("<td>");
				out.println("" + gameBoard.getGameName() + "");
				out.println("</td>");
				out.println("<td>");
				//out.println("  <button type=\"button\" disabled>Play</button>");
				out.println(" <input type = 'submit' name='Play' value=\"Play "+ gameBoard.getGameName() + "\"/>");
				if (gameBoard.getUsername().equalsIgnoreCase(username)) {
					//to specify which game should be updated/deleted assigned button values with game name
					out.println("   <input type=\"submit\" name=\"Update\" value=\"Update " + gameBoard.getGameName() + "\"/>");
					out.println("   <input type=\"submit\" name=\"Delete\" value=\"Delete " + gameBoard.getGameName() + "\"/>");
					//out.println("<input type='hidden' name='gameName' value='"+gameBoard.getGameName()+"");
				}
				out.println("</td>");
				out.println("</tr>");
				out.println("</table><br>");
	
			}			
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
			
		} catch (java.lang.Exception e) {
			out.println("<center>");
			out.println("No games available! Please create a game or browse available games! <br>");
			out.println(
					"   <input type=\"submit\" name=\"CreateGame\" value=\"Create Game\" onclick=\"window.location.href='http://localhost:8080/cs4640work/Questions'\"</input>");
			out.println("   <input type=\"submit\" name=\"BrowseGames\" value=\"Browse Games\"/>");
			out.println("</center>");
			out.close();
		}
	}
	
//method to get all the games by all users
	private Games getAllGames() {
		
		List<String> usernames = getAllUsers();
		
		// read all txt files in data folder and look for files 
		// starting with username
		File file = new File("WebContent/WEB-INF/data");
		File[] gameFiles = file.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				String[] fileName = name.split(Pattern.quote("$"));
				String username = fileName[0];
				if (usernames.contains(username) && name.endsWith(".txt")) {
					return true;
				} else {
					return false;
				}
			}
		});

		Games games = new Games();
		List<GameBoard> gameBoards = new ArrayList<GameBoard>();
		for (File gameFile : gameFiles) {
			GameBoard gameBoard = new GameBoard();
			String fName = gameFile.getName().replaceAll(".txt", "");
			String userName = fName.split(Pattern.quote("$"))[0];
			String gameName = fName.split(Pattern.quote("$"))[1];
			gameBoard.setUsername(userName);
			gameBoard.setGameName(gameName);
			gameBoards.add(gameBoard);
		}
		games.setGames(gameBoards);
		return games;
	}
	
	//method that gets all registered users
	private List<String> getAllUsers() {
		List<String> users = new ArrayList<String>();
		
		// load user-info to read all usernames
		File file = new File("WebContent/WEB-INF/data/user-info.txt");

		// reads the file created by selecting questions for jeopardy board
		Scanner inputFile = null;
		try {
			inputFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while (inputFile.hasNext()){
			String input = inputFile.nextLine();
			String[] parts = input.split(",");
			users.add(parts[0]);
		}
		
		inputFile.close();
		
		return users;
	}
}


