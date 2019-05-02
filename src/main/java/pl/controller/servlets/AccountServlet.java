package pl.controller.servlets;

import javafx.util.Pair;
import pl.model.domain.*;
import pl.model.util.Session;
import pl.model.util.View;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@WebServlet(name = "AccountServlet", urlPatterns = {"/account"})
@MultipartConfig
public class AccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext context = getServletContext();
        String path = context.getInitParameter("resourcePath");
        request.setCharacterEncoding("UTF-8");

        User user = Session.getLogInUser(request, response, context, true);
        View.setPersonalData(request, user);
        UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
        String action = request.getParameter("action");
        switch (action.toLowerCase()){
            case "edytuj":{
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String login = request.getParameter("login");
                String email = request.getParameter("email");
                synchronized (request.getSession()){
                    update(user, firstName, lastName, login, email);
                    Pair<Boolean, String> canCreate = userDAO.isValidOnUpdate(user);
                    if(canCreate.getKey()){
                        update(user, firstName, lastName, login, email);
                        userDAO.update(user);
                        View.setPersonalData(request, user);
                        context.getRequestDispatcher(path + "/account.jsp").forward(request, response);
                    }else{
                        userDAO.refresh(user);
                        View.setPersonalData(request, user);
                        request.setAttribute("info", canCreate.getValue());
                        context.getRequestDispatcher(path + "/edit.jsp").forward(request, response);
                    }
                }
                break;
            }
            case "wstaw":{
                String title = request.getParameter("title");
                String text = request.getParameter("text");
                Content content = new Content(title, text);
                ContentDAO contentDAO = (ContentDAO) context.getAttribute("contentDAO");
                Pair<Boolean, String> canCreate = contentDAO.isValid(content);
                if(canCreate.getKey()){
                    user.addContent(content);
                    contentDAO.save(content);
                    userDAO.save(user);
                    context.getRequestDispatcher(path + "/account.jsp").forward(request, response);
                } else{
                    request.setAttribute("info", canCreate.getValue());
                    context.getRequestDispatcher(path + "/create.jsp").forward(request, response);
                }
                break;
            }
            case "dodaj": {
                final Part avatarPart = request.getPart("avatar");
                if("".equals(avatarPart.getSubmittedFileName())){
                    request.setAttribute("info", "nie wybrano pliku");
                    context.getRequestDispatcher(path + "/account.jsp").forward(request, response);
                    return;
                }
                final BufferedImage img = View.resize(ImageIO.read(avatarPart.getInputStream()), 130, 130);
                ByteArrayOutputStream bytesImg = new ByteArrayOutputStream();
                ImageIO.write(img, "png", bytesImg);
                byte[] bytes = bytesImg.toByteArray();

                Avatar avatar = new Avatar(img.getWidth(), img.getHeight(), bytes);
                AvatarDAO avatarDAO = (AvatarDAO) context.getAttribute("avatarDAO");
                Pair<Boolean, String> canCreate = avatarDAO.isValid(avatar);
                if(canCreate.getKey()){
                    Avatar oldAvatar = user.getAvatar();
                    if(oldAvatar != null){
                        avatarDAO.delete(oldAvatar);
                    }
                    user.setAvatar(avatar);
                    avatarDAO.save(avatar);
                    userDAO.save(user);
                    request.setAttribute("info", "zmieniono awatar");
                    context.getRequestDispatcher(path + "/account.jsp").forward(request, response);

                }else{
                    request.setAttribute("info", canCreate.getValue());
                    context.getRequestDispatcher(path + "/account.jsp").forward(request, response);
                }
                break;
            }
            case "zmien kolor":{
                String headerColor = request.getParameter("headerColor");
                String asideColor = request.getParameter("asideColor");
                String contentColor = request.getParameter("contentColor");
                String linkColor = request.getParameter("linkColor");
                String footerColor = request.getParameter("footerColor");
                if(!headerColor.equals(""))
                    user.setHeaderColor(headerColor);
                if(!asideColor.equals(""))
                    user.setAsideColor(asideColor);
                if(!contentColor.equals(""))
                    user.setContentColor(contentColor);
                if(!linkColor.equals(""))
                    user.setLinkColor(linkColor);
                if(!footerColor.equals(""))
                    user.setFooterColor(footerColor);

                final Pair<Boolean, String> canCreate = userDAO.isColorValid(user);
                if(canCreate.getKey()){
                    userDAO.save(user);
                    View.setPersonalData(request, user);
                    context.getRequestDispatcher(path + "/account.jsp").forward(request, response);
                }else {
                    request.setAttribute("info", canCreate.getValue());
                    context.getRequestDispatcher(path + "/color.jsp").forward(request, response);
                }
                break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServletContext context = getServletContext();
        String path = context.getInitParameter("resourcePath");

        String action = request.getParameter("action");
        User user = Session.getLogInUser(request, response, context, true);
        View.setPersonalData(request, user);
        switch (action.toLowerCase()){
            // handle aside
            case "profil":{
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
                context.getRequestDispatcher(path + "/create.jsp").forward(request, response);
                break;
            }
            case "ustaw kolor":{
                context.getRequestDispatcher(path + "/color.jsp").forward(request, response);
                break;
            }
            case "edytuj":{
                context.getRequestDispatcher(path + "/edit.jsp").forward(request, response);
                break;
            }
        }
    }

    private void update(User user, String firstName, String lastName, String login, String email){
        if(!firstName.isEmpty())
            user.setFirstName(firstName);
        if(!lastName.isEmpty())
            user.setLastName(lastName);
        if(!login.isEmpty())
            user.setLogin(login);
        if(!email.isEmpty())
            user.setEmail(email);
    }
}
