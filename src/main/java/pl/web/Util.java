package pl.web;

import pl.domain.Content;
import pl.domain.User;
import pl.domain.UserDAO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class Util {
    public static boolean checkIfLogIn(HttpServletRequest request){
        Object user = request.getSession().getAttribute("user");
        return user instanceof User;
    }

    public static void setPersonalData(HttpServletRequest request, User user){
        request.setAttribute("firstName", user.getFirstName());
        request.setAttribute("lastName", user.getLastName());
        request.setAttribute("email", user.getEmail());
        request.setAttribute("color", user.getContentColor());
        request.setAttribute("list", user.getContents().stream().map(Content::getTitle).iterator());
    }

    public static User getLogInUser(HttpServletRequest request, ServletContext context){
        UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
        User user = (User) request.getSession().getAttribute("user");
        return userDAO.findOne(user.getId()).orElseGet(null);
    }
}
