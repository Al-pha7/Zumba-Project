package com.zumbaapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantDAO {
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

    public void addParticipant(Participant participant) {
        String query = "INSERT INTO participants (name, email, phoneNumber, batchId) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, participant.name());
            statement.setString(2, participant.email());
            statement.setString(3, participant.phoneNumber());
            statement.setInt(4, participant.batchId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Participant> getAllParticipants() {
        List<Participant> participants = new ArrayList<>();
        String query = "SELECT * FROM participants";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                participants.add(new Participant(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phoneNumber"),
                    rs.getInt("batchId")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return participants;
    }
}
