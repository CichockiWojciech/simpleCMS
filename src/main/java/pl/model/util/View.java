package pl.model.util;

import pl.model.domain.Content;
import pl.model.domain.User;

import javax.servlet.http.HttpServletRequest;

public class View {
    public static void setPersonalData(HttpServletRequest request, User user){
        request.setAttribute("firstName", user.getFirstName());
        request.setAttribute("lastName", user.getLastName());
        request.setAttribute("email", user.getEmail());
        request.setAttribute("color", user.getContentColor());
        request.setAttribute("pageList", user.getContents().stream().map(Content::getTitle).iterator());
    }
}
