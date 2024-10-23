

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out =response.getWriter();
		response.setContentType("text/html");
		
		int num = Integer.parseInt(request.getParameter("num"));
		int amt = Integer.parseInt(request.getParameter("amt"));
		
		
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try 
		{
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,"system","aadn");
			
			String query = "update account set balance = balance + ? where num= ?";
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setInt(1,amt);
			ps.setInt(2,num);

	        
			int count = ps.executeUpdate();	
			if (count>0) {
				out.println("<h4 style ='color:red'> Deposit Successfull  </h4>");
	        	RequestDispatcher rd = request.getRequestDispatcher("Success.html");
	        	rd.include(request, response);
				
			}
	        else {
				out.println("<h4 style = color:red'>Not Deposited</h4>");
				
			}
			con.close();
			
		}
		catch(Exception e)
		{
			out.println("<h4 style='color:red'>Exception : " + e.getMessage()+"</h4>");
		}
	}

}
