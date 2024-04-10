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
 * Servlet implementation class RegisterServlet
 */

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String query = "INSERT INTO book_data(bookName,bookEdition,bookPrice) VALUES(?,?,?)";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter pw = response.getWriter();
        //set content type
        response.setContentType("text/html");
        //GET THE book info
        String bookName = request.getParameter("bookName");
        String bookEdition = request.getParameter("bookEdition");
        String bookPrice = request.getParameter("bookPrice");
        // Charger le driver JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        //generer la connection
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book","root","");
                PreparedStatement ps = con.prepareStatement(query);){
            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
            ps.setString(3, bookPrice);
            int count = ps.executeUpdate();
            pw.println("<style>");
            pw.println("h2 {text-align: center;}");
            pw.println("a {display: block; text-align: center; margin-top: 20px; color: #007bff; text-decoration: none;}");
            pw.println("a:hover {text-decoration: underline;}");
            pw.println("</style>");
            if(count == 1) {
                pw.println("<h2>Registration Successful</h2>");
            } else {
                pw.println("<h2>Registration Failed</h2>");
            }
            pw.println("<a href='home.jsp'>Home</a>");
            pw.println("<a href='ListRegisterServlet'>Book List</a>");
        } catch(SQLException se) {
            se.printStackTrace();
            pw.println("<h1>"+se.getMessage()+"</h1>");
            pw.println("<a href='home.jsp'>Home</a>");
        } catch(Exception e) {
            e.printStackTrace();
            pw.println("<h1>"+e.getMessage()+"</h1>");
            pw.println("<a href='home.jsp'>Home</a>");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
