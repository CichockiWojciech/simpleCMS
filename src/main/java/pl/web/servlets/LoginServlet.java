package pl.web.servlets;

import com.google.common.collect.ImmutableMap;
import pl.domain.User;
import pl.domain.UserDAO;
import pl.web.Util;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login.do", "/login.html", "/login"})
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String login, password;
        ServletContext context = request.getServletContext();
        String path = context.getInitParameter("resourcePath");

        login = request.getParameter("login");
        password = request.getParameter("password");

        synchronized (getServletContext()) {
            UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
            Optional<User> user = userDAO.findOne("SELECT u FROM User u " +
                            "WHERE u.login = :login AND u.password = :password",
                    ImmutableMap.of("login", login, "password", password));

            if (user.isPresent()) {
                request.getSession().setAttribute("user", user.get());
                System.out.println(user.get().getFirstName());
                Util.setPersonalData(request, user.get());
                context.getRequestDispatcher(path + "/account.jsp").forward(request, response);
            } else {
                request.setAttribute("info", "nieprawidłowy login lub hasło");
                context.getRequestDispatcher(path + "/result.jsp").forward(request, response);
            }
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext context = getServletContext();

        context.getRequestDispatcher(context.getInitParameter("resourcePath") + "/login.html").forward(request,response);
    }
}
