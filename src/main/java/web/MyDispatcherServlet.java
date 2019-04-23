package web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyDispatcherServlet extends HttpServlet {

    private AnnotationConfigApplicationContext webCtx;

    @Override
    public void init() {
        Class<?> webConfigClass = getWebConfigClass();
        webCtx = new AnnotationConfigApplicationContext(webConfigClass);

        webCtx.setParent(parentContext());
        //TODO: webCtx.setParent(parentContext);
        // should be loaded parent context with business logic
    }

    private Class<?> getParentConfigClass() {
        String contextRootConfigLocation = getInitParameter("contextConfigLocation");
        try {
            return Class.forName(contextRootConfigLocation);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private ApplicationContext parentContext() {
        return new AnnotationConfigApplicationContext(getParentConfigClass());
    }

    private Class<?> getWebConfigClass() {
        String contextConfigClass =
                getServletContext().getInitParameter("contextConfigLocation");

        try {
            return Class.forName(contextConfigClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String controllerName = getControllerNameFromRequest(req);
        System.out.println(controllerName);
        MyController controller =
                (MyController) webCtx.getBean(controllerName);
        controller.handleRequest(req, resp);
    }

    private String getControllerNameFromRequest(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return requestURI.substring(requestURI.lastIndexOf("/") + 1);
    }
}
