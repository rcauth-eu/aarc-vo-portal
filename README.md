# aarc-vo-portal

**NOTE** *This is software is just a proof of concept portal implementation, and it's in no way meant to be used as a production ready component!*

##VO Portal 
The VO Portal is a modified [OA4MP Client](http://grid.ncsa.illinois.edu/myproxy/oauth/client/index.xhtml). The VO Portal has only been developed for demonstration purposes
and only servs as a proof of concept. It simply connects to a Master Portal, retrieves a proxy for the
authenticated user, and displays it in the browser. A more practical implementation of the VO Portal would take
the retrieved proxy and use it to authenticate the user for further operations (depending on usecase).

## Building

In case you wish the build the VO Portal you should first build two of its dependencies in the
following order

1. [ncsa-security-all-fork](https://github.com/ttomttom/ncsa-security-all-fork)
2. [myproxy-fork](https://github.com/ttomttom/myproxy-fork)


