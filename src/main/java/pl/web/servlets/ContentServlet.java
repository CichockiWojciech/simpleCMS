package pl.web.servlets;

import pl.domain.User;
import pl.web.Util;

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
            User user = Util.getLogInUser(request, context);
            String text = user.getContent(title);
            System.out.println("text: " + Optional.ofNullable(text).orElse("null"));
            System.out.println("title: " + Optional.ofNullable(title).orElse("null"));
            request.setAttribute("title", title);
            request.setAttribute("text", text);
            Util.setPersonalData(request, user);
            context.getRequestDispatcher(path + "/content.jsp").forward(request, response);
        }else {
            request.setAttribute("info", "nie jestes zalogowny");
            context.getRequestDispatcher(path + "/result.jsp").forward(request, response);
        }

    }
}
