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

        if(validInput(firstName, lastName, login, email, password, repeatPassword)){
            synchronized (getServletContext()){
                UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
                Optional<User> user = userDAO.findOne("SELECT u FROM User u " +
                                "WHERE u.login = :login AND u.password = :password",
                        ImmutableMap.of("login", login, "password", password));

                if(user.isPresent()){
                    request.setAttribute("info", "Konto o podanym loginie lub email jest już zajęte");
                    context.getRequestDispatcher(path + "/result.jsp").forward(request, response);
                }else {
                    User newUser = new User(firstName, lastName, email, login, password);
                    userDAO.save(newUser);
                    request.getSession().setAttribute("user", newUser);
                    Util.setPersonalData(request, newUser);
                    context.getRequestDispatcher(path + "/account.jsp").forward(request, response);
                }
            }
        }else{
            request.setAttribute("info", "Błędnie wypełniony formularz");
            context.getRequestDispatcher(path + "/result.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext context = getServletContext();

        context.getRequestDispatcher(context.getInitParameter("resourcePath") + "/register.html").forward(request,response);
    }

    private static boolean validInput(String firstName, String lastName, String login,
                                      String email, String password, String repeatPassword){

        return length(firstName, 4, 50) &&
                length(lastName, 4, 50) &&
                length(login, 4, 50) &&
                length(email, 4, 50) &&
                length(password, 4, 50) &&
                password.equals(repeatPassword);
    }

    private static boolean length(String arg, int from, int to){
        return arg.length() > from && arg.length() < to;
    }
}
