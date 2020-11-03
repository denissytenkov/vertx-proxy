### About
Quick sketch of vertx proxy server. The server will proxy a request to the host provided in `x-destination` header with url of initial request.

###How to run
Start server with
``gradle run``

Send a request with
``curl -v -H"x-destination:vertx.io" "http://localhost:8081/docs/vertx-core/java/"``