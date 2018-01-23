package servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener(value = "avatarInitializer")
public class AvatarInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println(sce.getServletContext().getInitParameter("Jake"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
