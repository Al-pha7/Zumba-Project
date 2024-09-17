package com.zumbaapp;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/participants")
public class ParticipantsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ParticipantDAO participantDAO;

    // Servlet initialization
    public void init() {
        participantDAO = new ParticipantDAO();
    }

    // Handling POST requests (e.g., form submissions)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Retrieve form parameters
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            
            // Validate and parse batchId
            String batchIdParam = request.getParameter("batchId");
            int batchId = 0;
            if (batchIdParam != null && !batchIdParam.isEmpty()) {
                batchId = Integer.parseInt(batchIdParam);
            }

            // Create a new participant object
            Participant participant = new Participant(0, name, email, phoneNumber, batchId);

            // Add participant using DAO
            participantDAO.addParticipant(participant);

            // Redirect after successful form submission
            response.sendRedirect("participants");
        } catch (NumberFormatException e) {
            // Handle invalid batchId
            request.setAttribute("error", "Invalid batch ID. Please enter a valid number.");
            request.getRequestDispatcher("/pages/ParticipantForm.jsp").forward(request, response);
        } catch (Exception e) {
            // Handle other exceptions
            throw new ServletException(e);
        }
    }

    // Handling GET requests (e.g., displaying participants)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Retrieve all participants from DAO
            List<Participant> participants = participantDAO.getAllParticipants();

            // Set the participants list as a request attribute
            request.setAttribute("participants", participants);

            // Forward the request to the JSP for rendering
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/ViewParticipants.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            // Handle potential exceptions
            throw new ServletException(e);
        }
    }
}
