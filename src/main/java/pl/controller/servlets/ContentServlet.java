package pl.controller.servlets;

import pl.model.domain.User;
import pl.model.util.Session;
import pl.model.util.View;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "ContentServlet", urlPatterns = {"/content"})
public class ContentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServletContext context = request.getServletContext();
        String path = context.getInitParameter("resourcePath");

        String title = request.getParameter("submit");
        Object userObj = request.getSession().getAttribute("user");
        if(userObj instanceof User){
            Optional<User> user = Session.getLogInUser(request, context);
            if(user.isPresent()){
                String text = user.get().getContentByTitle(title);
                request.setAttribute("title", title);
                request.setAttribute("text", text);
                View.setPersonalData(request, user.get());
            }
            context.getRequestDispatcher(path + "/content.jsp").forward(request, response);
        }else {
            request.setAttribute("info", "nie jestes zalogowny");
            context.getRequestDispatcher(path + "/error.jsp").forward(request, response);
        }

    }
}
