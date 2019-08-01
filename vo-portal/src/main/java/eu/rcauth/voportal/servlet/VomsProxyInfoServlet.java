package eu.rcauth.voportal.servlet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.rcauth.voportal.voms.VPVomsProxyInfo;

/**
 * NOTE: currently this class is not being used by the VO-portal
 * It takes a username parameter to get a proxy file /tmp/username.proxy
 * and the content is printed via the vomsinfo.jsp.
 *
 * The current VO-portal uses instead a MasterPortal flow to obtain the proxy
 * which is then printed using the VPOA2ReadyServlet instead.
 */
public class VomsProxyInfoServlet extends HttpServlet {

    public static final String VOMS_INFO_PAGE = "/pages/vomsinfo.jsp";

    public static final String REQ_USERNAME = "username";

    public static final String PROXY_DIR = "/tmp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter(REQ_USERNAME);

        if (username == null || username.isEmpty())
            throw new ServletException("No username specified!");

        String proxyFile = PROXY_DIR + "/" + username + ".proxy";
        String vomsinfo = null;
        try {
            vomsinfo = VPVomsProxyInfo.exec(proxyFile);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Unable to get voms-info! \n" , e);
        }

        //read proxy certificate from expected file location
        String proxyString;
        try {
            FileInputStream fis = new FileInputStream(proxyFile);
            BufferedInputStream bis = new BufferedInputStream(fis);

            int len = bis.available();
            byte[] proxy = new byte[len];
            // Note: we ignore the return values here
            int lenread=bis.read(proxy);
            fis.close();
            if (lenread != len) {
                throw new IOException("Expected to read "+len+" bytes, but read() returned "+lenread);
            }

            proxyString = new String(proxy);
        } catch (Exception e) {
            throw new ServletException("Unable to read proxy file\n", e);
        }

        request.setAttribute("username", username);
        request.setAttribute("userinfo", "N/A");
        request.setAttribute("vomsinfo", vomsinfo);
        request.setAttribute("proxy", proxyString);

        RequestDispatcher dispatcher = request.getRequestDispatcher(VOMS_INFO_PAGE);
        dispatcher.forward(request, response);

    }

}
