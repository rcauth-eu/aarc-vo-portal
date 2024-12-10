# RELEASE NOTES

## Version 0.2.0 ... 0.2.4

If you are upgrading from a previous release, you will need to make several
changes:

#### Update the client config file `/var/www/voportal/conf/cfg.xml`

* Add the list of required scopes into a `<scopes>` element in the relevant
  `<client>` element(s).  
  As a minimum, `openid` and `edu.uiuc.ncsa.myproxy.getcert` are required.  
  By default the basic scopes are enabled (`openid`, `email`, `profile` and
  `edu.uiuc.ncsa.myproxy.getcert`), so strictly speaking you don't need to do
  anything, but for demonstration purposes you probably want to **add**
  `org.cilogon.userinfo`.  

        <scopes>
            <scope>org.cilogon.userinfo</scope>
        </scopes>

* Make sure you have configured a `wellKnownURI` element for the Master Portal
  in the relevant `<client>` element(s):

        <wellKnownUri>https://mp.example.org/mp-oa2-server/.well-known/openid-configuration</wellKnownUri>

  When absent, signed tokens will not be verified.

* Add the following element to the relevant `<client>` element(s):
    
        <OIDCEnabled>false</OIDCEnabled>

  This is currently optional, since the VO portal does not use the ID token
  (only the userinfo endpoint) and the default is true in any case.

#### Other new features

##### VOMS proxies

In addition to plain proxies, it is now possible to directly obtain VOMS
proxies via the VO portal. This does require that the user is already
registered in the VO with their RCauth DN.  
The VOs that are enabled are all those listed in `/etc/vomses` on the host where
the VO portal (and MasterPortal) are running.

Note that the myproxy-server behind the MasterPortal also needs to be configured
for those same VOs, as the VO portal does *not* pass vomses information, only VO
and FQAN information (i.e. it only passes the `voname` parameter, see [getproxy
endpoint](https://wiki.nikhef.nl/grid/OAuth_for_MyProxy_GetProxy_Endpoint)).
Make sure the `myproxy-server.config` contains

        voms_userconf  /etc/vomses
        allow_voms_attribute_requests  True

and that `/etc/vomses` contains the correct information.

##### User information

By default, the portal now prints the user's username (`sub` claim).  
Additionally, you can also view the rest of the received claims (`userinfo`
response).
