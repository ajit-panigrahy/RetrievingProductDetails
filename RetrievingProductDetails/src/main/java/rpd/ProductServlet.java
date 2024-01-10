package rpd;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		int productId = Integer.parseInt(request.getParameter("productID"));

		try {
			Connection connection = DatabaseConnector.getConnection();

			String query = "SELECT * FROM PRODUCT WHERE PID = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, productId);

			ResultSet resultSet = preparedStatement.executeQuery();

			out.println("<html><head><title>Product Details</title></head><body>");

			if (resultSet.next()) {
				out.println("<h2>Product Details:</h2>");
				out.println("<table border='1'>");
				out.println("<tr><th>ID</th><th>Name</th><th>Price</th></tr>");
				out.println("<tr>");
				out.println("<td>" + resultSet.getInt("PID") + "</td>");
				out.println("<td>" + resultSet.getString("PNAME") + "</td>");
				out.println("<td>" + resultSet.getInt("PRICE") + "</td>");
				out.println("</tr>");
				out.println("</table>");
			} else {
				out.println("<h2>Product not found!</h2>");
				out.println("<form action='index.html'>");
				out.println("<button type='submit'>Go Back</button>");
				out.println("</form>");
			}

			out.println("</body></html>");

			connection.close();
		} catch (Exception e) {
			out.println("Error: " + e.getMessage());
		} finally {
			out.close();
		}
	}
}
