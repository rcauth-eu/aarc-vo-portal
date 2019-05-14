# RELEASE NOTES

## Version 0.2.0

If you are upgrading from a previous release, you will need to make several
changes:

#### Update the client config file `/var/www/voportal/conf/cfg.xml`

* Add the list of required scopes into a `<scopes>` element in the relevant
  `<client>` element(s).  
  By default the basic scopes are already enabled
  (`openid`, `email`, `profile` and `edu.uiuc.ncsa.myproxy.getcert`) and
  only `openid` and `edu.uiuc.ncsa.myproxy.getcert` are required.
  So strictly speaking you don't need to do anything, but for demonstration
  purposes you probably want to **add** `org.cilogon.userinfo`.  

        <scopes>
            <scope>org.cilogon.userinfo</scope>
        </scopes>

  By default, the portal now prints the user's username (`sub` claim)
  and you can also view the rest of the claims.

* Make sure you have configured a `wellKnownURI` element for the Master Portal
  in the relevant `<client>` element(s):
    * `<wellKnownUri>https://mp.example.org/mp-oa2-server/.well-known/openid-configuration</wellKnownUri>`

  When absent, signed tokens will not be verified.

* Add the following element to the relevant `<client>` element(s):
    * `<OIDCEnabled>true</OIDCEnabled>`

  This is currently optional, being the default setting.
