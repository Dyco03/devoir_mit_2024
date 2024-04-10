

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class DeleteServlet
 */
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String query = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				//get PrintWriter
				PrintWriter pw = response.getWriter();
				//set content type
				response.setContentType("text/html");
				//prendre l'if
				int id = Integer.parseInt(request.getParameter("id"));
				String table = request.getParameter("table");
				String redirection = request.getParameter("redirection");
				query = "DELETE FROM " + table + " where id=?";
				 // Charger le driver JDBC
				try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				}catch(ClassNotFoundException cnf) {
					cnf.printStackTrace();
				}
				//generer la connection
				try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book","root","");
					PreparedStatement stmt = con.prepareStatement(query);){
					stmt.setInt(1, id);
					int count = stmt.executeUpdate();
					if(count==1) {
						pw.println("<h2>Delete Successfull</h2>");
						response.sendRedirect(redirection);
					}else {
						pw.println("<h2>Delete Failed</h2>");
					}
						
				}catch(SQLException se) {
					se.printStackTrace();
					pw.println("<h1>"+se.getMessage()+"</h2>");
				}catch(Exception e) {
					e.printStackTrace();
					pw.println("<h1>"+e.getMessage()+"</h2>");
				}
				pw.println("<a href='home.jsp'>Home</a>");
				pw.println("<br>");
				pw.println("<a href='ListRegisterServlet'>Book List</a>");
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
