package pl.controller.servlets;

import javafx.util.Pair;
import pl.model.domain.Content;
import pl.model.domain.ContentDAO;
import pl.model.domain.User;
import pl.model.domain.UserDAO;
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

@WebServlet(name = "AccountServlet", urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServletContext context = getServletContext();
        String path = context.getInitParameter("resourcePath");

        String text = request.getParameter("content");
        String title = request.getParameter("title");

        Content content = new Content();
        content.setContent(text);
        content.setTitle(title);
        Optional<User> user = Session.getLogInUser(request, context);
        if(user.isPresent()){
            ContentDAO contentDAO = (ContentDAO) context.getAttribute("contentDAO");
            Pair<Boolean, String> canCreate = contentDAO.isValid(content);
            if(canCreate.getKey()){
                UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
                user.get().addContent(content);
                contentDAO.save(content);
                userDAO.save(user.get());
                View.setPersonalData(request, user.get());
                context.getRequestDispatcher(path + "/account.jsp").forward(request, response);
            } else{
                request.setAttribute("info", canCreate.getValue());
                View.setPersonalData(request, user.get());
                context.getRequestDispatcher(path + "/create.jsp").forward(request, response);
            }
        }else {
            request.setAttribute("info", "Nie jestes zalogowany");
            context.getRequestDispatcher(path + "/error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServletContext context = getServletContext();
        String path = context.getInitParameter("resourcePath");

        String action = request.getParameter("action");
        Optional<User> user = Session.getLogInUser(request, context);
        if(!user.isPresent()){
            request.setAttribute("info", "nie jestes zalogowny");
            context.getRequestDispatcher(path + "/error.jsp").forward(request, response);
        }
        switch (action.toLowerCase()){
            // handle aside
            case "profil":{
                View.setPersonalData(request, user.get());
                context.getRequestDispatcher(path + "/account.jsp").forward(request, response);
                break;
            }
            case "wyloguj":{
                request.getSession().invalidate();
                context.getRequestDispatcher(path + "/login.jsp").forward(request, response);
                break;
            }
            // handle nav
            case "dodaj strone":{
                View.setPersonalData(request, user.get());
                context.getRequestDispatcher(path + "/create.jsp").forward(request, response);
                break;
            }
        }
    }
}
