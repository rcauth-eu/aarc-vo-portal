package test;

import eu.rcauth.voportal.voms.VPVomsProxyInfo;

public class ProxyVomsInfoCLITester {

    public static void main(String[] args) throws Exception {
        String s = VPVomsProxyInfo.exec("/tmp/test.proxy");
        System.out.println(s);
    }

}
