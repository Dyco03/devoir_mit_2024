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

public class MemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String query = "INSERT INTO member(first_name,last_name,email,address) VALUES(?,?,?,?)";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberServlet() {
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
        //récuperation des données du formulaire
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
     // Charger le driver JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        //generer la connection
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book","root","");
                PreparedStatement ps = con.prepareStatement(query);){
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, address);
            int count = ps.executeUpdate();
            pw.println("<style>");
            pw.println("h2 {text-align: center;}");
            pw.println("a {display: block; text-align: center; margin-top: 20px; color: #007bff; text-decoration: none;}");
            pw.println("a:hover {text-decoration: underline;}");
            pw.println("</style>");
            if(count == 1) {
                pw.println("<h2>Add Member Successful</h2>");
            } else {
                pw.println("<h2>Add Member Failed</h2>");
            }
            pw.println("<a href='home.jsp'>Home</a>");
            pw.println("<a href='ListRegisterServlet'>Show Book List</a>");
            pw.println("<a href='MemberListServlet'>Show Member List</a>");
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
