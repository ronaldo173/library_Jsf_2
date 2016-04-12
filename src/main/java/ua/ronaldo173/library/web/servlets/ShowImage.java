package ua.ronaldo173.library.web.servlets;

import ua.ronaldo173.library.web.controllers.SearchController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Developer on 11.04.2016.
 */
@WebServlet("/ShowImage")
public class ShowImage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("image/jpeg");


        try (OutputStream out = resp.getOutputStream()) {
            int id = Integer.valueOf(req.getParameter("id"));

            SearchController searchController = (SearchController) req.getSession(false).getAttribute("searchController");

            byte[] image = searchController.getImage(id);
            resp.setContentLength(image.length);
            out.write(image);
        }

    }
}
