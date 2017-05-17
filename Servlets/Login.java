package cs4640work;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/")
public class Login extends HttpServlet{

	private static final long serialVersionUID = -6270152549200920887L;

	public static final String USERNAME = "username";
	public static final String GAMES = "games";
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
		
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		//check if someone is already logged in - if so, redirect to the browse screen
		HttpSession session=req.getSession();
		if(session.getAttribute(Login.USERNAME)!=null){
				res.sendRedirect("/cs4640work/Browse"); 
		}
		
		out.println("<html>");
		out.println("   <head>");
		out.println("      <title>Jeopardy</title>");
		out.println("      <style>");
		out.println("          @import");
		out.println("          url(https://fonts.googleapis.com/css?family=Roboto:300);");
		out.println("          .login-page { width: 360px; padding: 2% 0 0; margin: auto; }");
		out.println("          .form { position: relative; z-index: 1; background: #FFFFFF; max-width: 360px;");
		out.println("          margin: 0 auto 100px; padding: 45px; text-align: center; box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24); }");
		out.println("          .form input { font-family: 'Roboto', sans-serif; outline: 0; background: #f2f2f2; width: 100%;");
		out.println("          border: 0; margin: 0 0 15px; padding: 15px; box-sizing: border-box; font-size: 14px; }");
		out.println("          input[type=submit] { font-family: 'Roboto', sans-serif; text-transform: uppercase; outline: 0;");
		out.println("          background: #4CAF50; width: 100%; border: 0; padding: 15px; color: #FFFFFF; font-size: 14px; -webkit-transition: all 0.3 ease;");
		out.println("          transition: all 0.3 ease; cursor: pointer; }");
		//navigation bar css/styling
		out.println("           ul { list-style-type: none; margin: 0; padding: 0; overflow: hidden; background-color: #333; }");
		out.println("           li a { display: block; color: white; text-align: center; padding: 14px 16px; text-decoration: none; }");
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
		out.println("           <li><a class='active' style='float:left;' href='/cs4640work/'>Login</a></li>");
		out.println("           <li><a class='questions' style='float:left;' href='/cs4640work/Questions'>Create Game</a></li>");
		out.println("       </ul>");
		out.println("  <center>");
		out.println("<div class ='container'>");
		out.println("<div class='jumbotron'>");
		out.println("     <h2>Jeopardy Maker<g;/h2><br></br>");
		out.println(" <p>Annette Chun (amc4sq) and Mina Ahn (ma3hd)</p>");
		out.println("</div>");
		out.println("</div>");
		out.println("Please login or register to access the Jeopardy Game system.");
		out.println("   <div class='login-page'>");
		out.println("   <div class='form'>");
		out.println("       <form method='post' action='Login'>");
		out.println("           Username:<input type='text' name='username' /><br/>");
		out.println("           Password:<input type='password' name='password' /><br/>");
		out.println("           <input type='submit' name='login' value='Login' />");
		out.println("           <input type='submit' name='register' value='Register' />");
		out.println("       </form>");
		out.println("   </div>");
		out.println("   </div>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		//check if username is empty
		if (username.isEmpty()){
			out.println("<html><body><script>alert('Please input a username.' );window.location='javascript:history.back()';</script></body></html>");
			return;
		}
		
		//check if password is empty
		if (password.isEmpty()){
			out.println("<html><body><script>alert('Please input a password.' );window.location='javascript:history.back()';</script></body></html>");
			return;
		}
		
		File file = new File("WebContent/WEB-INF/data/user-info.txt");
		
		//read in user-info file into arraylist
		ArrayList<String[]> users = new ArrayList<String[]>();

		//reads the file of the existing users
		Scanner inputFile = null;
		try {
			inputFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (inputFile.hasNext()){
			String[] inputs = inputFile.nextLine().split(",");
			users.add(inputs);
		}
		inputFile.close();
		
		//register
		if(req.getParameter("register")!=null){
			//1. check if user exists
			//a. if so, send error message and go back to the login page with cleared information
			for(int i = 0; i < users.size(); i++){
				String[] user = users.get(i);
				if (username.equals(user[0])){
					out.println("<html><body><script>alert('The username already exists. Please try again.' );window.location='javascript:history.back()';</script></body></html>");
					return;
				}
			}
			//VALIDATE USERNAME/PASSWORD
			//check if password is at least 6 characters long
			if (password.length() < 6){
				out.println("<html><body><script>alert('Password needs to be at least 6 characters long.' );window.location='javascript:history.back()';</script></body></html>");
				return;
			}
			
			if (password.toLowerCase().equals("password")){
				out.println("<html><body><script>alert('Password is too common! Please try a different password.' );window.location='javascript:history.back()';</script></body></html>");
				return;
			}
			
			if (password.toLowerCase().contains(username.toLowerCase())){
				out.println("<html><body><script>alert('Your password cannot contain your username. Please try a different password.' );window.location='javascript:history.back()';</script></body></html>");
				return;
			}
			//b. if not, add name + pass to the text file and go to the browse screen
			try {
				FileWriter fout = new FileWriter(file, true);
				fout.write(username + "," + password);
				fout.write("\n");
				fout.flush();
				fout.close();
				//start new session - User Object
				HttpSession session = req.getSession();
				session.setAttribute(Login.USERNAME, username);
				//redirect to browse screen
				res.sendRedirect("/cs4640work/Browse");
			} catch (java.io.IOException e) {
				System.out.println("Error: cannot write to file" + e.toString());
				e.printStackTrace();
			}
		}
		
		//login
		if(req.getParameter("login")!=null){
		//1. check if username and password match
		//a. if so, login and redirect to browse screen
			for(int i = 0; i < users.size(); i++){
				String[] user = users.get(i);
				if (username.equals(user[0]) && password.equals(user[1])){
					//start new session - User Object
					HttpSession session = req.getSession();
					session.setAttribute(Login.USERNAME, username);
					//redirect to browse screen
					res.sendRedirect("/cs4640work/Browse");
				}
			}
		//b. if not, send error message and go back to the login page with cleared information
			out.println("<html><body><script>alert('The username and/or password is incorrect. Please try again.' );window.location='javascript:history.back()';</script></body></html>");
			return;
		}
		
		out.println("<html>");
		out.close();
	}
}
