package web;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        String configClassName = servletContext.getInitParameter("contextConfigLocation");
        try {
            Class<?> configClass = Class.forName(configClassName);
            AnnotationConfigApplicationContext parentContext =
                    new AnnotationConfigApplicationContext(configClass);
            servletContext.setAttribute("parentContext", parentContext);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        //¯\_(ツ)_/¯
    }
}
