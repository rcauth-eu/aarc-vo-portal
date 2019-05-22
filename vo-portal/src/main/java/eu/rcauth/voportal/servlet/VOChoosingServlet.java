package eu.rcauth.voportal.servlet;

import org.italiangrid.voms.VOMSError;
import org.italiangrid.voms.request.VOMSESLookupStrategy;
import org.italiangrid.voms.request.VOMSESParser;
import org.italiangrid.voms.request.VOMSESParserFactory;
import org.italiangrid.voms.request.VOMSServerInfo;
import org.italiangrid.voms.request.impl.BaseVOMSESLookupStrategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VOChoosingServlet extends HttpServlet {

    final Logger logger = Logger.getLogger(VOChoosingServlet.class.getName());

    public static final String VO_CHOOSER_PAGE="/pages/chooser.jsp";
    public static final String VO_PORTAL_START="/startRequest";

    public static final String VOMSES_LOCATION_KEY="eu.rcauth.voportal.vomses";

    String[] vomses = null;

    /*
     * Load available VOMSes in memory on startup
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // load vomses
        String vomsesLocation = this.getServletContext().getInitParameter(VOMSES_LOCATION_KEY);
        logger.info("Loading supported VOs from " + vomsesLocation);

        vomses = getVomses(vomsesLocation);

        if (vomses.length>0)
            logger.info(vomses.length + " VOs found");
        else
            logger.info("No (valid) VOs found, will only support plain proxies");
    }

    /*
     * Fill in VOs for the JSP page and display it
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("redirect_host", getServletConfig().getServletContext().getContextPath() + VO_PORTAL_START);
        request.setAttribute("vomses", vomses);

        RequestDispatcher dispatcher = getServletConfig().getServletContext().getRequestDispatcher(VO_CHOOSER_PAGE);
        dispatcher.forward(request, response);

    }

    private String[] getVomses(String vomsesLocation) {
        // Use a BaseVOMSESLookupStrategy as opposed to a
        // DefaultVOMSESLookupStrategy since we only want the explicit location
        BaseVOMSESLookupStrategy vomsesLookupStrategy = new BaseVOMSESLookupStrategy();
        vomsesLookupStrategy.addPath(vomsesLocation);
        List<File> vomsesFileList = vomsesLookupStrategy.lookupVomsesInfo();
        VOMSESParser vomsesParser = VOMSESParserFactory.newVOMSESParser();
        List<String> vos = new ArrayList<>();
        for (File vomsesFile: vomsesFileList) {
            try {
                List<VOMSServerInfo> infoList = vomsesParser.parse(vomsesFile);
                for (VOMSServerInfo vomsServerInfo: infoList) {
                    String vo = vomsServerInfo.getVoName();
                    logger.info("Adding VO " + vo);
                    vos.add(vo);
                }
            } catch (VOMSError e) {
                logger.severe("Failed to parse "+vomsesFile.toString()+": "+e.getMessage());
                // Useful errors are typically down the stacktrace so print all causes
                Throwable t = e;
                while ( (t=t.getCause()) != null)
                    logger.warning(t.getClass().getSimpleName()+": "+t.getMessage());
            }
        }
        return vos.toArray(new String[0]);
    }
}
