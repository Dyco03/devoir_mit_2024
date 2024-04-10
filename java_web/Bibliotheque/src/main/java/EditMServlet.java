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
 * Servlet implementation class EditServlet
 */
public class EditMServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String query = "UPDATE member SET first_name=? ,last_name=?, email=?, address=? where id=?";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditMServlet() {
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
        //Récuperer les données du formulaire
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = (request.getParameter("email"));
        String address = (request.getParameter("address"));
        // Charger le driver JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        //generer la connection
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book","root","");
            PreparedStatement stmt = con.prepareStatement(query);){
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, address);
            stmt.setInt(5, id);
            int count = stmt.executeUpdate();
            pw.println("<style>");
            pw.println("h2 {text-align: center;}");
            pw.println("a {display: block; text-align: center; margin-top: 20px; color: #007bff; text-decoration: none;}");
            pw.println("a:hover {text-decoration: underline;}");
            pw.println("</style>");
            if(count == 1) {
                pw.println("<h2>Edit Member Successful</h2>");
            } else {
                pw.println("<h2>Edit Member Unsuccessful</h2>");
            }
            pw.println("<a href='home.jsp'>Home</a>");
            pw.println("<a href='MemberListServlet'>Member List</a>");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

}
