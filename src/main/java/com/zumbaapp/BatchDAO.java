package com.zumbaapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BatchDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/zumba";
    private String jdbcUsername = "root";
    private String jdbcPassword = "password";

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database.");
        }
    }

    public void addBatch(Batch batch) {
        String query = "INSERT INTO batches (batchName, timeSlot) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, batch.batchName());
            statement.setString(2, batch.timeSlot());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Batch> getAllBatches() {
        List<Batch> batches = new ArrayList<>();
        String query = "SELECT * FROM batches";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                batches.add(new Batch(
                    rs.getInt("id"),
                    rs.getString("batchName"),
                    rs.getString("timeSlot")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return batches;
    }
}
