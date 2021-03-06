package eu.rcauth.voportal.client.oauth2.servlet;

import edu.uiuc.ncsa.myproxy.oa4mp.client.AssetResponse;
import edu.uiuc.ncsa.myproxy.oa4mp.client.ClientEnvironment;
import edu.uiuc.ncsa.myproxy.oa4mp.client.servlet.ClientServlet;
import edu.uiuc.ncsa.oa4mp.oauth2.client.OA2Asset;
import edu.uiuc.ncsa.oa4mp.oauth2.client.OA2MPProxyService;
import edu.uiuc.ncsa.security.core.exceptions.GeneralException;
import edu.uiuc.ncsa.security.delegation.token.AuthorizationGrant;
import edu.uiuc.ncsa.security.delegation.token.MyX509Proxy;
import edu.uiuc.ncsa.security.delegation.token.impl.AuthorizationGrantImpl;
import edu.uiuc.ncsa.security.oauth_2_0.OA2Constants;
import edu.uiuc.ncsa.security.oauth_2_0.OA2RedirectableError;
import edu.uiuc.ncsa.security.oauth_2_0.UserInfo;
import edu.uiuc.ncsa.security.oauth_2_0.client.ATResponse2;
import edu.uiuc.ncsa.security.servlet.JSPUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.rcauth.voportal.voms.VPVomsProxyInfo;
import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.MessageDigest;

public class VPOA2ReadyServlet extends ClientServlet {

    protected static final String PROXY_TMP_DIR = "/tmp";
    public static final String VOMS_INFO_PAGE = "/pages/vomsinfo.jsp";

    @Override
    protected void doIt(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        if (request.getParameterMap().containsKey(OA2Constants.ERROR)) {
            throw new OA2RedirectableError(request.getParameter(OA2Constants.ERROR),
                    request.getParameter(OA2Constants.ERROR_DESCRIPTION),
                    request.getParameter(OA2Constants.STATE));
        }
        // Get the cert itself. The server itself does a redirect using the callback to this servlet
        // (so it is the portal that actually is invoking this method after the authorization
        // step.) The token and verifier are peeled off and used
        // to complete the request.
        info("2.a. Getting token and state.");
        String token = request.getParameter(CONST(ClientEnvironment.TOKEN));
        String state = request.getParameter(OA2Constants.STATE);
        if (token == null) {
            warn("2.a. The token is null.");
            throw new IllegalArgumentException("Error: Missing token parameter ("+ClientEnvironment.TOKEN+").");
        }
        info("2.a Token found, getting grant.");
        // Create grant from the token (don't need token afterwards)
        AuthorizationGrant grant = new AuthorizationGrantImpl(URI.create(token));

        info("2.a. Getting the cert(s) from the service");
        String identifier = clearCookie(request, response);
        if (identifier == null) {
            info("No cookie found! Cannot identify session!");
            throw new GeneralException("Missing cookie, unable to identify session!");
        }
        OA2Asset asset = (OA2Asset) getCE().getAssetStore().get(identifier);
        if(asset.getState() == null || !asset.getState().equals(state)){
            warn("The expected state from the server was \"" + asset.getState() + "\", but instead \"" + state + "\" was returned. Transaction aborted.");
            throw new IllegalArgumentException("Error: The state returned by the server is invalid.");
        }

        OA2MPProxyService oa2MPService = (OA2MPProxyService) getOA4MPService();
        ATResponse2 atResponse2 = oa2MPService.getAccessToken(asset, grant);
        UserInfo userinfo = oa2MPService.getUserInfo(identifier);
        AssetResponse assetResponse = oa2MPService.getProxy(asset, atResponse2);

        info("2.b. Done! Displaying VOMS INFO.");

        String username = userinfo.getSub();
        byte[] hash = MessageDigest.getInstance("SHA-256").digest( username.getBytes(Charset.forName("UTF-8")) );
        String tmpProxy = PROXY_TMP_DIR + "/" + Hex.encodeHexString(hash) + ".proxy";
        String proxyString = null;

        if ( assetResponse.getCredential() instanceof MyX509Proxy )
            proxyString = ((MyX509Proxy)assetResponse.getCredential()).getX509ProxyPEM();
        else
            proxyString = assetResponse.getCredential().getX509CertificatesPEM();

        String vomsinfo = null;

        try {
            FileOutputStream fOut = new FileOutputStream(new File(tmpProxy));
            fOut.write( proxyString.getBytes() );
            fOut.close();

            vomsinfo = VPVomsProxyInfo.exec(tmpProxy);

            File file = new File(tmpProxy);
            if (file.delete()) {
                info("proxy " + tmpProxy + " successfully deleted.");
            } else {
                warn("proxy " + tmpProxy + " could not be deleted.");
            }
        }
        catch (Exception e) {
            throw new GeneralException("Unable to execute voms-proxy-info on the returned chain!",e);
        }

        request.setAttribute("username", username);
        request.setAttribute("vomsinfo", vomsinfo);
        request.setAttribute("proxy", proxyString);
        request.setAttribute("userinfo", userinfo.toJSon().toString(3));

        String start = getServletConfig().getServletContext().getContextPath();
        // Fix "start" in cases where the server request passes through Apache
        // before going to Tomcat since Apache would redirect from X to X/
        // NOTE: ideally we should also change the contextpath for the current
        // request, however we cannot change that )-:
        if (start.endsWith("/"))
            request.setAttribute("start", start);
        else
            request.setAttribute("start", start + "/");

        info("2.a. Completely finished with delegation.");
        JSPUtil.fwd(request, response, VOMS_INFO_PAGE);

    }

}
