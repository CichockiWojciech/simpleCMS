package pl.web.servlets;

import pl.domain.Content;
import pl.domain.ContentDAO;
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
        Object userObj = request.getSession().getAttribute("user");
        if(userObj instanceof User) {
            User user = (User) userObj;
            if (validPageContent(title, text)) {
                UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
                ContentDAO contentDAO = (ContentDAO) context.getAttribute("contentDAO");
                user.addContent(content);
                contentDAO.save(content);
                userDAO.save(user);
                Util.setPersonalData(request, user);
                context.getRequestDispatcher(path + "/account.jsp").forward(request, response);
            } else{
                request.setAttribute("info", "nieprawidlowo wypelniony formularz");
                context.getRequestDispatcher(path + "/result.jsp").forward(request, response);
            }
        }else {
            request.setAttribute("info", "Nie jestes zalogowany");
            context.getRequestDispatcher(path + "/result.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServletContext context = getServletContext();
        String path = context.getInitParameter("resourcePath");

        String action = request.getParameter("action");
        switch (action.toLowerCase()){
            // handle aside
            case "profil":{
                if(Util.checkIfLogIn(request)){
                    User user = (User) request.getSession().getAttribute("user");
                    Util.setPersonalData(request, user);
                    context.getRequestDispatcher(path + "/account.jsp").forward(request, response);
                }else{
                    request.setAttribute("info", "nie jestes zalogowny");
                    context.getRequestDispatcher(path + "/result.jsp").forward(request, response);
                }
                break;
            }
            case "wyloguj":{
                request.getSession().invalidate();
                context.getRequestDispatcher(path + "/login.html").forward(request, response);
                break;
            }
            // handle nav
            case "dodaj strone":{
                if(Util.checkIfLogIn(request)){
                    Util.setPersonalData(request, (User) request.getSession().getAttribute("user"));
                    context.getRequestDispatcher(path + "/manage-content.jsp").forward(request, response);
                }else{
                    request.setAttribute("info", "nie jestes zalogowny");
                    context.getRequestDispatcher(path + "/result.jsp").forward(request, response);
                }
                break;
            }
        }
    }

    private static boolean validPageContent(String title, String text){
        return title.length() > 5 && text.length() > 5;

    }
}
