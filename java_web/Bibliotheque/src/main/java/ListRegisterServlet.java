import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String query = "SELECT * FROM book_data";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");

        // Récupérer le terme de recherche depuis la requete
        String searchKeyword = request.getParameter("search");

        // Afficher le formulaire de recherche
        pw.println("<form method='get' action='ListRegisterServlet'>");
        pw.println("<input type='text' name='search' placeholder='Search book'>");
        pw.println("<input type='submit' value='Search'>");
        pw.println("<a href='ListRegisterServlet'>Show All</a>");
        pw.println("</form>");

        // Vérifier si un terme de recherche a été fourni
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
        	// Si il y a correspondance, effectuer une recherche
            performSearch(pw, searchKeyword);
        } else {
            // Sinon, afficher tous les livres
            showAllBooks(pw);
        }
        
        pw.println("<a href='home.jsp'>Home</a>");
    }

    private void performSearch(PrintWriter pw, String searchKeyword) {
        // Préparer la requete de recherche avec le terme de recherche
        String searchQuery = "SELECT * FROM book_data WHERE bookName like ?";
        
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book","root","");
             PreparedStatement stmt = con.prepareStatement(searchQuery);) {

            stmt.setString(1, "%" + searchKeyword + "%");
            ResultSet rs = stmt.executeQuery();
            // Afficher les résultats de la recherche
            displayResultSet(pw, rs);

        } catch(SQLException se) {
            se.printStackTrace();
            pw.println("<h1>"+se.getMessage()+"</h1>");
        } catch(Exception e) {
            e.printStackTrace();
            pw.println("<h1>"+e.getMessage()+"</h1>");
        }
    }

    private void showAllBooks(PrintWriter pw) {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book","root","");
             PreparedStatement stmt = con.prepareStatement(query);	
            
            ResultSet rs = stmt.executeQuery();
            // Afficher tous les livres
            displayResultSet(pw, rs);
        
        } catch(SQLException se) {
            se.printStackTrace();
            pw.println("<h1>"+se.getMessage()+"</h1>");
        } catch(Exception e) {
            e.printStackTrace();
            pw.println("<h1>"+e.getMessage()+"</h1>");
        }
    }

    private void displayResultSet(PrintWriter pw, ResultSet rs) throws SQLException {
        // Afficher les résultats 
        pw.println("<style>");
        pw.println("table {border-collapse: collapse; width: 80%; margin: 20px auto;}");
        pw.println("th, td {border: 1px solid #ddd; padding: 8px; text-align: left;}");
        pw.println("th {background-color: #f2f2f2;}");
        pw.println("th, td {font-family: Arial, sans-serif;}");
        pw.println("a {text-decoration: none; color: #007bff;}");
        pw.println("a:hover {text-decoration: underline;}");
        pw.println("</style>");
        pw.println("<table>");
        pw.println("<tr>");
        pw.println("<th>Book Id</th>");
        pw.println("<th>Book Name</th>");
        pw.println("<th>Book Edition</th>");
        pw.println("<th>Book Price</th>");
        pw.println("<th>Book Status</th>");
        pw.println("<th>Edit</th>");
        pw.println("<th>Delete</th>");
        pw.println("</tr>");
        while(rs.next()) {
            pw.println("<tr>");
            pw.println("<td>"+rs.getInt(1)+"</td>");
            pw.println("<td>"+rs.getString(2)+"</td>");
            pw.println("<td>"+rs.getString(3)+"</td>");
            pw.println("<td>"+rs.getString(4)+"</td>");
            pw.println("<td>"+rs.getString(5)+"</td>");
            pw.println("<td><a href='EditsBookServlet?id="+rs.getInt(1)+"'>Edit</a></td>");
            pw.println("<td><a href='DeleteServlet?id=" + rs.getInt(1) + "&table=book_data&redirection=ListRegisterServlet'>Delete</a></td>");
            pw.println("</tr>");
        }
        pw.println("</table>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // rien
    }
}
