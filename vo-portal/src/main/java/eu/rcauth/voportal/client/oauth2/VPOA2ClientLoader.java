package eu.rcauth.voportal.client.oauth2;

import org.apache.commons.configuration.tree.ConfigurationNode;

import edu.uiuc.ncsa.oa4mp.oauth2.client.OA2ClientLoader;

import edu.uiuc.ncsa.myproxy.oa4mp.client.ClientEnvironment;

public class VPOA2ClientLoader<T extends ClientEnvironment> extends OA2ClientLoader<T> {

    public VPOA2ClientLoader(ConfigurationNode node) {
        super(node);
    }

    @Override
    public String getVersionString() {
        return "VO Portal OAuth2/OIDC client configuration loader version " + VERSION_NUMBER;
    }


}
