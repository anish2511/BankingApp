

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out =response.getWriter();
		response.setContentType("text/html");
		
		String uname = request.getParameter("uname");
		String pwd = request.getParameter("pwd");
		
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/college";
		try
		{
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,"root","Anish@123");
			
			String query = "select * from register where uname =? and pwd =?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, uname);
			ps.setString(2, pwd);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
//				out.println("<h1 style ='color:blue'> Login Success </h1>");
				RequestDispatcher rd = request.getRequestDispatcher("Success.html");
				rd.forward(request, response);
				
				
			}
			else {
				out.println("<h4 style = color:red'> Login Failed / Try Again </h4>");
				RequestDispatcher rd = request.getRequestDispatcher("Login.html");
				rd.include(request, response);
			}
			
			con.close();
			
		}
		catch(Exception e)
		{
			out.println("<h4 style='color:red'>Exception : " + e.getMessage()+"</h4>");
		}

	}

}
