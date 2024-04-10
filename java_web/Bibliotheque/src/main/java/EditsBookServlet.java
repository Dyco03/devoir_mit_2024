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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class EditBook
 */
public class EditsBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String query = "SELECT bookName,bookEdition,bookPrice FROM book_data where id=?";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditsBookServlet() {
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
        //prendre l'id
        int id = Integer.parseInt(request.getParameter("id"));
        // Charger le driver JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        //generer la connection
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book","root","");
                PreparedStatement stmt = con.prepareStatement(query);){
                stmt.setInt(1,id);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                pw.println("<style>");
                pw.println("body {font-family: Arial, sans-serif;}");
                pw.println("table { margin: 20px auto; }");
                pw.println("td {padding: 10px;}");
                pw.println("input[type='text'], input[type='submit'], input[type='reset'] {font-size : large;width: calc(100% - 20px); padding: 10px; margin-bottom: 10px; border: 1px solid #ccc; border-radius: 5px;}");
                pw.println("input[type='submit'], input[type='reset'] {font-size : large;background-color: #007bff; color: #fff; border: none; cursor: pointer;}");
                pw.println("input[type='submit']:hover, input[type='reset']:hover {font-size : large;background-color: #0056b3;}");
                pw.println("a {\n"
                		+ "    display: block;\n"
                		+ "    text-align: center;\n"
                		+ "    text-decoration: none;\n"
                		+ "    color: #007bff;\n"
                		+ "    margin-top: 10px;\n"
                		+ "}\n"
                		+ "\n"
                		+ "a:hover {\n"
                		+ "    text-decoration: underline;\n"
                		+ "}");
                pw.println("</style>");
                pw.println("<form action='EditServlet?id="+id+"' method='post'>");
                pw.println("<table>");
                pw.println("<tr>");
                pw.println("<td>Book Name</td>");
                pw.println("<td><input type='text' name='bookName' value='"+rs.getString(1)+"'></td>");
                pw.println("</tr>");
                pw.println("<tr>");
                pw.println("<td>Book Edition</td>");
                pw.println("<td><input type='text' name='bookEdition' value='"+rs.getString(2)+"'></td>");
                pw.println("</tr>");
                pw.println("<tr>");
                pw.println("<td>Book Price</td>");
                pw.println("<td><input type='text' name='bookPrice' value='"+rs.getString(3)+"'></td>");
                pw.println("</tr>");
                pw.println("<tr>");
                pw.println("<td><input type='submit' value='Edit'></td>");
                pw.println("<td><input type='reset' value='cancel'></td>");
                pw.println("</tr>");
                pw.println("</table>");
                pw.println("</form>");
        } catch(SQLException se) {
            se.printStackTrace();
            pw.println("<h1>"+se.getMessage()+"</h1>");
        } catch(Exception e) {
            e.printStackTrace();
            pw.println("<h1>"+e.getMessage()+"</h1>");
        }
        pw.println("<a href='home.jsp'>Home</a>");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

}
