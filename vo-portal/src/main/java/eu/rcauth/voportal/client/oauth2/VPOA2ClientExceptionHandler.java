package eu.rcauth.voportal.client.oauth2;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;

import edu.uiuc.ncsa.myproxy.oa4mp.client.servlet.ClientServlet;
import edu.uiuc.ncsa.oa4mp.oauth2.client.servlet.OA2ClientExceptionHandler;
import edu.uiuc.ncsa.security.servlet.ServiceClientHTTPException;
import edu.uiuc.ncsa.security.core.util.MyLoggingFacade;

/**
 * Exception handler for the VO portal servlets
 */
public class VPOA2ClientExceptionHandler extends OA2ClientExceptionHandler {

    MyLoggingFacade logger;

    public VPOA2ClientExceptionHandler(ClientServlet clientServlet, MyLoggingFacade myLogger) {
        super(clientServlet, myLogger);
        this.logger = myLogger;
    }

    /**
     * handles exceptions, including a {@link ServiceClientHTTPException} which is
     * thrown when an error is returned from the mp-server endpoint. In that
     * case the content of the response is typically a HTML page which is parsed
     * by {@link OA2ClientExceptionHandler#parseContent(String, HttpServletRequest)}
     */
    @Override
    public void handleException(Throwable t, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.warn("Handling exception: " + t.getMessage());
        super.handleException(t, request, response);
    }

}
