package cs4640work;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class Logout extends HttpServlet{
	 protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {  
		 res.setContentType("text/html");  
		 PrintWriter out=res.getWriter();  

		 HttpSession session=req.getSession();
		 String username = (String) session.getAttribute(Login.USERNAME);
		 if(username!=null){
			 session.invalidate();
			 out.println("<html><body><script>alert('You have successfully logged out!' );window.location.href='/cs4640work/Login';</script></body></html>");
			 out.close();
		 }
		 else{
			 res.sendRedirect("/cs4640work/");
		 }
}  
}
