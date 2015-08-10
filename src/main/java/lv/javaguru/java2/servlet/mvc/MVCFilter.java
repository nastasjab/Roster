package lv.javaguru.java2.servlet.mvc;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MVCFilter implements Filter{

    private final Map<String, MVCController> controllers = new HashMap<String, MVCController>();

    private static final Logger logger = Logger.getLogger(MVCFilter.class.getName());

    private ApplicationContext springContext;

    public void init(FilterConfig filterConfig) throws ServletException {

        try {
            springContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        } catch (BeansException e) {
            logger.log(Level.INFO, "Spring context failed to start", e);
        }

        controllers.put("/users", getBean(UserController.class));
        controllers.put("/user", getBean(UserEditController.class));
        controllers.put("/shifts", getBean(ShiftController.class));
        controllers.put("/shift", getBean(ShiftEditController.class));
        controllers.put("/roster", getBean(RosterController.class));
    }

    private MVCController getBean(Class clazz){
            return (MVCController) springContext.getBean(clazz);
        }

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        String contextURI = req.getServletPath();
        MVCController controller = controllers.get(contextURI);
        if (controller != null) {
            MVCModel model = controller.processRequest(req);

            req.setAttribute("model", model.getData());

            ServletContext context = req.getServletContext();
            RequestDispatcher requestDispacher =
                    context.getRequestDispatcher(model.getJspName());
            requestDispacher.forward(req, resp);
        }
        else filterChain.doFilter(request,response);
    }

    public void destroy() {

    }
}
