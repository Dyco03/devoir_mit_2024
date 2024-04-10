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

@WebServlet("/BorrowServlet")
public class BorrowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String borrowQuery = "INSERT INTO borrow(member_id, book_id, borrow_date, return_date) VALUES(?,?,?,?)";
    private static final String checkMemberQuery = "SELECT * FROM member WHERE id=?";
    private static final String checkBookQuery = "SELECT * FROM book_data WHERE id=?";
    private static final String checkBookStatusQuery = "SELECT status FROM book_data WHERE id=?";

    public BorrowServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");

        // Récupérer les données du formulaire
        int userId = Integer.parseInt(request.getParameter("userId"));
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        String borrowDate = request.getParameter("borrowDate");
        String returnDueDate = request.getParameter("returnDueDate");

        // Charger le driver JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        // Vérifier l'existence du membre
        boolean memberExists = false;
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book","root","");
             PreparedStatement stmt = con.prepareStatement(checkMemberQuery);){
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                memberExists = true;
            }
        } catch(SQLException se) {
            se.printStackTrace();
        }

        // Vérifier l'existence et l'état du livre
        boolean bookExists = false;
        boolean bookAvailable = false;
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book","root","");
             PreparedStatement stmt = con.prepareStatement(checkBookQuery);){
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bookExists = true;
                String status = rs.getString("status");
                if ("Available".equals(status)) {
                    bookAvailable = true;
                }
            }
        } catch(SQLException se) {
            se.printStackTrace();
        }
        // ici css
        pw.println("<style>");
        pw.println("h2 {text-align: center;}");
        pw.println("a {display: block; text-align: center; margin-top: 20px; color: #007bff; text-decoration: none;}");
        pw.println("a:hover {text-decoration: underline;}");
        pw.println("</style>");
     // Si le membre et le livre existent et que le livre est disponible, effectuer l'emprunt
        if (memberExists && bookExists && bookAvailable) {
            Connection con = null;
            PreparedStatement borrowStmt = null;
            PreparedStatement updateStatusStmt = null;

            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book","root","");
                con.setAutoCommit(false); // Début de la transaction

                // Effectuer l'emprunt
                borrowStmt = con.prepareStatement(borrowQuery);
                borrowStmt.setInt(1, userId);
                borrowStmt.setInt(2, bookId);
                borrowStmt.setString(3, borrowDate);
                borrowStmt.setString(4, returnDueDate);
                int count = borrowStmt.executeUpdate();

                // Si l'emprunt est réussi, mettre à jour le statut du livre
                if (count == 1) {
                    updateStatusStmt = con.prepareStatement("UPDATE book_data SET status = 'Borrowed' WHERE id = ?");
                    updateStatusStmt.setInt(1, bookId);
                    updateStatusStmt.executeUpdate();
                    con.commit(); // Valider la requete
                    pw.println("<h2>Borrow Successful</h2>");
                } else {
                    pw.println("<h2>Borrow Failed</h2>");
                }
                pw.println("<a href='home.jsp'>Home</a>");
                pw.println("<a href='ListRegisterServlet'>Show Book List</a>");
                pw.println("<a href='MemberListServlet'>Show Member List</a>");
                pw.println("<a href='ListBorrowServlet'>Show Borrow List</a>");
            } catch(SQLException se) {
                if (con != null) {
                    try {
                        con.rollback(); // Annuler la requete en cas d'erreur
                    } catch (SQLException rollbackException) {
                        rollbackException.printStackTrace();
                    }
                }
                se.printStackTrace();
                pw.println("<h1>"+se.getMessage()+"</h1>");
                pw.println("<a href='home.jsp'>Home</a>");
            } finally {
                if (con != null) {
                    try {
                        con.setAutoCommit(true); 
                        con.close();
                    } catch (SQLException closeException) {
                        closeException.printStackTrace();
                    }
                }
                if (borrowStmt != null) {
                    try {
                        borrowStmt.close();
                    } catch (SQLException closeException) {
                        closeException.printStackTrace();
                    }
                }
                if (updateStatusStmt != null) {
                    try {
                        updateStatusStmt.close();
                    } catch (SQLException closeException) {
                        closeException.printStackTrace();
                    }
                }
            }
        }
        //Si le livre est déjà emprunté
        else if (!bookAvailable) {
        	pw.println("<h2>The Book is Borrowed</h2>");
            pw.println("<a href='home.jsp'>Home</a>");
        }
        else {
            // Si le membre ou le livre n'existe pas
            pw.println("<h2>Member or Book does not exist</h2>");
            pw.println("<a href='home.jsp'>Home</a>");
        }

        }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
