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

@WebServlet("/ReturnBookServlet")
public class ReturnBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String deleteBorrowQuery = "DELETE FROM borrow WHERE id=?";
    private static final String updateBookStatusQuery = "UPDATE book_data SET status='Available' WHERE id=(SELECT book_id FROM borrow WHERE id=?)";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");

        // Récupérer l'ID de l'emprunt à retourner
        int borrowId = Integer.parseInt(request.getParameter("id"));

     // Charger le driver JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        // Mettre à jour le statut du livre à "Available"
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book","root","");
             PreparedStatement updateStmt = con.prepareStatement(updateBookStatusQuery)) {
            updateStmt.setInt(1, borrowId);
            int updatedRows = updateStmt.executeUpdate();

            if (updatedRows > 0) {
                pw.println("<div style='color: green; font-size: 18px;'>Book Status Updated Successfully</div>");
            } else {
                pw.println("<div style='color: red; font-size: 18px;'>Failed to Update Book Status</div>");
            }
        } catch(SQLException se) {
            se.printStackTrace();
            pw.println("<div style='color: red; font-size: 18px;'>Failed to Update Book Status</div>");
        }

        // Delete l'emprunt de la table borrow
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book","root","");
             PreparedStatement deleteStmt = con.prepareStatement(deleteBorrowQuery)) {
            deleteStmt.setInt(1, borrowId);
            int deletedRows = deleteStmt.executeUpdate();

            if (deletedRows > 0) {
                pw.println("<div style='color: green; font-size: 18px;'>Book Returned Successfully</div>");
            } else {
                pw.println("<div style='color: red; font-size: 18px;'>Failed to Return Book</div>");
            }
        } catch(SQLException se) {
            se.printStackTrace();
            pw.println("<div style='color: red; font-size: 18px;'>Failed to Return Book</div>");
        }

        pw.println("<a href='home.jsp' style='text-decoration: none; color: #007bff; font-size: 18px;'>Home</a>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
