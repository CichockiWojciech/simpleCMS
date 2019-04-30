package pl.model.util;

import pl.model.domain.User;
import pl.model.domain.UserDAO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
}
