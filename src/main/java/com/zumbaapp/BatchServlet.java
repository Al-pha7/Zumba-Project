package com.zumbaapp;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/batches")
public class BatchServlet extends HttpServlet {
    private BatchDAO batchDAO;

    public void init() {
        batchDAO = new BatchDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String batchName = request.getParameter("batchName");
        String timeSlot = request.getParameter("timeSlot");

        Batch batch = new Batch(0, batchName, timeSlot);
        batchDAO.addBatch(batch);
        response.sendRedirect("batches");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Batch> batches = batchDAO.getAllBatches();
        request.setAttribute("batches", batches);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/ViewBatches.jsp");
        dispatcher.forward(request, response);
    }
}
