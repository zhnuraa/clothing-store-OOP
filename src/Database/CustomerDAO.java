package database;

import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // INSERT
    public boolean insert(Customer c) {
        String sql =
            "INSERT INTO customers (customer_id, name, preferred_size, points) " +
            "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, c.getCustomerId());
            ps.setString(2, c.getName());
            ps.setString(3, c.getPreferredSize());
            ps.setInt(4, c.getPoints());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("Error inserting customer");
            e.printStackTrace();
            return false;
        }
    }

    // SELECT
    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers ORDER BY customer_id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("preferred_size"),
                        rs.getInt("points")
                );
                list.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error reading customers");
            e.printStackTrace();
        }

        return list;
    }
}
