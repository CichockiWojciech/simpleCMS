package pl.controller.listener;

import pl.model.domain.ContentDAO;
import pl.model.domain.UserDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Optional;

@WebListener()
public class Listener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    // Public constructor is required by servlet spec
    public Listener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
      EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("database");
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      UserDAO userDAO = new UserDAO(entityManager);
      ContentDAO contentDAO = new ContentDAO(entityManager);
      sce.getServletContext().setAttribute("entityManagerFactory", entityManagerFactory);
      sce.getServletContext().setAttribute("entityManager", entityManager);
      sce.getServletContext().setAttribute("userDAO", userDAO);
      sce.getServletContext().setAttribute("contentDAO", contentDAO);
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
      Optional.ofNullable((EntityManager) sce.getServletContext().getAttribute("entityManager")).
              ifPresent((entityManager) -> entityManager.close());
      Optional.ofNullable((EntityManagerFactory) sce.getServletContext().getAttribute("entityManagerFactory"))
              .ifPresent((entityManagerFactory) -> entityManagerFactory.close());
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
    }
}
