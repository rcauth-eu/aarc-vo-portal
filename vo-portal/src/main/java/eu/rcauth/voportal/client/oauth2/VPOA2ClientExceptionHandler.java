package eu.rcauth.voportal.client.oauth2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uiuc.ncsa.myproxy.oa4mp.client.servlet.ClientServlet;
import edu.uiuc.ncsa.oa4mp.oauth2.client.servlet.OA2ClientExceptionHandler;
import edu.uiuc.ncsa.security.servlet.ServiceClientHTTPException;
import edu.uiuc.ncsa.security.core.util.MyLoggingFacade;

/**
 * Exception handler for the VO portal servlets.
 * The handling is done by
 * {@link OA2ClientExceptionHandler#handleException(Throwable, HttpServletRequest, HttpServletResponse)}.
 * Exceptions include a {@link ServiceClientHTTPException} which is
 * thrown when an error is returned from the mp-server endpoint. In that
 * case the content of the response is typically a HTML page which is parsed
 * by {@link OA2ClientExceptionHandler#parseContent(String, HttpServletRequest)}.
 */
public class VPOA2ClientExceptionHandler extends OA2ClientExceptionHandler {

//  MyLoggingFacade logger;

    public VPOA2ClientExceptionHandler(ClientServlet clientServlet, MyLoggingFacade myLogger) {
        super(clientServlet, myLogger);
//      this.logger = myLogger;
    }

}
