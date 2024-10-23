

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


@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out =response.getWriter();
		response.setContentType("text/html");
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try 
		{
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,"system","aadn");
			
			String query = "Select * from account where num =?";
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setInt(1, num);
	        
	        ResultSet rs = ps.executeQuery();
	        if(rs.next()) {
				out.println("Details = "+ rs.getInt(1) +","+ rs.getString(2)+","+rs.getInt(3));
	        	RequestDispatcher rd = request.getRequestDispatcher("Success.html");
	        	rd.include(request, response);
				
			}
	        else {
				out.println("<h4 style = color:red'> Invalid Account Details</h4>");
				
			}
			con.close();
			
		}
		catch(Exception e)
		{
			out.println("<h4 style='color:red'>Exception : " + e.getMessage()+"</h4>");
		}
	}

}
