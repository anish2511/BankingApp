

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

@WebServlet("/CreateServlet")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out =response.getWriter();
		response.setContentType("text/html");
		
		int num = Integer.parseInt(request.getParameter("num"));
		String name = request.getParameter("name");
		int balance = Integer.parseInt(request.getParameter("balance"));
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try 
		{
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,"root","Anish@123");
			
			String query = "insert into account values(?,?,?)";
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setInt(1, num);
	        ps.setString(2, name);
	        ps.setInt(3, balance);
	        
	        int count = ps.executeUpdate();
			if (count >0) {
//				out.println("<h4 style ='color:red'> Inserted Successfull  </h4>");
				RequestDispatcher rd = request.getRequestDispatcher("Success.html");
				rd.forward(request, response);
				
			}
			else {
				out.println("<h4 style = color:red'> Insertion Failed / Try Again </h4>");
				RequestDispatcher rd = request.getRequestDispatcher("Create.html");
				rd.forward(request, response);
			}
			con.close();
			
		}
		catch(Exception e)
		{
			out.println("<h4 style='color:red'>Exception : " + e.getMessage()+"</h4>");
		}

	}

}