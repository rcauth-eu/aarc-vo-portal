# aarc-vo-portal
The AARC VO-portal is a demonstration client portal to the AARC
Master Portal which obtains a proxy certificate for the
authenticated user and displays it in the browser.  
It is based on a customised version of the
[OA4MP](https://github.com/rcauth-eu/OA4MP).

## Implementation
The VO portal is an OA4MP client implementation, primarily developed for
demonstration purposes and to serve as a proof of concept. It runs as a tomcat
servlet inside the same tomcat container as the Master Portal itself.

## Compiling

1. You first need to compile and install the two RCauth-adapted dependency
   libraries 
    1. [security-lib](https://github.com/rcauth-eu/security-lib)
    2. [OA4MP](https://github.com/rcauth-eu/OA4MP)
   
   Make sure to use the _same_ version (branch or tag) for both the
   security-lib and OA4MP components.  
   For the **0.2** series of the aarc-vo-portal, you must use the
   **4.2-RCauth** versions.
   
2. Checkout the right version of the aarc-vo-portal.

        git clone https://github.com/rcauth-eu/aarc-vo-portal
        cd aarc-vo-portal

        git checkout v0.2.3
        cd vo-portal

3. Build the vo-portal's war file

        mvn clean package

   After maven has finished you should find the `.war` file in the target
   directory:

        aarc-vo-portal/vo-portal/target/vo-portal.war

## Other Resources

Background information:
* [RCauth.eu and MasterPortal documentation](https://wiki.nikhef.nl/grid/RCauth.eu_and_MasterPortal_documentation)
* [Ansible scripts for the Master Portal](https://github.com/rcauth-eu/aarc-ansible-master-portal)

Related Components:
* [RCauth.eu Delegation Server](https://github.com/rcauth-eu/aarc-delegation-server).
* [AARC Master Portal](https://github.com/rcauth-eu/aarc-master-portal)  
* [SSH key portal](https://github.com/rcauth-eu/aarc-ssh-portal)  
  this component can run inside the master portal's tomcat container,
  leveraging the Master Portal's sshkey upload endpoint.
