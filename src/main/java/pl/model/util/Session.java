package pl.model.util;

import pl.model.domain.User;
import pl.model.domain.UserDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class Session {
    public static boolean checkIfLogIn(HttpServletRequest request){
        Object user = request.getSession().getAttribute("user");
        return user instanceof User;
    }

    public static Optional<User> getLogInUser(HttpServletRequest request, ServletContext context){
        if(checkIfLogIn(request)){
            UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
            User user = (User) request.getSession().getAttribute("user");
            return userDAO.findOne(user.getId());
        }else
            return Optional.empty();
    }

    public static User getLogInUser(HttpServletRequest request, HttpServletResponse response,
                                              ServletContext context, boolean exit) throws ServletException, IOException {
        if(exit){
            Optional<User> user = getLogInUser(request, context);
            if(!user.isPresent()){
                request.setAttribute("info", "nie jestes zalogowny");
                context.getRequestDispatcher(context.getInitParameter("resourcePath") + "/error.jsp").forward(request, response);
            }
            return user.orElse(null);
        }
        return getLogInUser(request, context).orElse(null);
    }
}
