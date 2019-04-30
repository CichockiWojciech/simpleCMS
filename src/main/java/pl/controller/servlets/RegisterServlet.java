package pl.controller.servlets;

import javafx.util.Pair;
import pl.model.domain.User;
import pl.model.domain.UserDAO;
import pl.model.util.View;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/index.html", "/register.html", "/register.do", "/register"})
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String firstName, lastName, login, email, password, repeatPassword;
        ServletContext context = request.getServletContext();
        String path = context.getInitParameter("resourcePath");

        firstName = request.getParameter("firstName");
        lastName = request.getParameter("lastName");
        login = request.getParameter("login");
        email = request.getParameter("email");
        password = request.getParameter("password");
        repeatPassword = request.getParameter("repeatPassword");

        if(password.equals(repeatPassword)){
            User newUser = new User(firstName, lastName, email, login, password);
            synchronized (getServletContext()) {
                UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
                Pair<Boolean, String> canCreate = userDAO.isValid(newUser);
                if (canCreate.getKey()) {
                    userDAO.save(newUser);
                    request.getSession().setAttribute("user", newUser);
                    View.setPersonalData(request, newUser);
                    context.getRequestDispatcher(path + "/account.jsp").forward(request, response);
                } else {
                    request.setAttribute("info", canCreate.getValue());
                    context.getRequestDispatcher(path + "/register.jsp").forward(request, response);
                }
            }
        }else{
            request.setAttribute("info", "hasła nie są zgodne");
            context.getRequestDispatcher(path + "/register.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext context = getServletContext();

        context.getRequestDispatcher(context.getInitParameter("resourcePath") + "/register.jsp").forward(request,response);
    }
}
