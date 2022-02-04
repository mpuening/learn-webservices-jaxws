Learn SOAP Web Services (JAX-WS)
================================

[![Continuous Integration](https://github.com/mpuening/learn-webservices-jaxws/actions/workflows/ci.yml/badge.svg)](https://github.com/mpuening/learn-webservices-jaxws/actions/workflows/ci.yml)

This project contains example code to demonstrate proficiency with JAX-WS SOAP web services.

Yes, SOAP isn't dead yet even as I write this 2017. It isn't unusual for a large company to buy some
vendor package that has been around for a while and has never been updated to REST services. So you
as a developer get stuck having to integrate with it by using its SOAP web services.


This project contain three modules:
* learn-webservices-jaxws-stubs
* learn-webservices-jaxws-server
* learn-webservices-jaxws-client

The subject matter of this example is military aircraft: simply keeping track of the types of aircraft and
their manufacturers.

Stubs Module
------------
Most of the SOAP based WSDL files out there are document-literal style. Make sure yours is one them. The other
kind is the older RPC style, which the plug-ins used in this project won't work well with.
 
The stubs module is responsible for running the WSDL to Java plug-in. When integrating with a vendor package it will
likely have some method for you to get the WSDL file. You will want to properly configure the plug-in mappings
so that you get decent looking code. Don't accept the default "namespace to package name" mapping. Chances are
it will give you a horrible looking package structure. Take the time to generate code in the proper packages. 

As a side note, many WSDL files from the early 2000s were based on OAGIS standards. You may have heard of 
noun and verb pairs. OAGIS is where this comes from. For more information on OAGIS, see this link:
http://www.oagi.org/oagis/9.0/Documentation/Architecture.html

My example WSDL file uses pairs, but not the actual OAGIS schemas. (Too big and bulky for an example).
There are many standards for building an API, and this project isn't about that. I'm not even going to
discuss versioning, even though you see versions my example XSD and WSDL files.

Server Module
-------------
Implementing a JAX-WS based service isn't too bad today, and I chose Spring's web service support to implement
mine. There are of course other ways to skin the cat.

The service also has security on it because you will likely face that in an integration.  It wouldn't surprise
me that login credentials actually go into the SOAP message as opposed to using basic authentication. My example
just uses Basic Authentication.

I also included a logging handler to print out the incoming and outgoing SOAP messages. You can use this for
debugging purposes.

Client Module
-------------
The client and stubs module is really what you need for integration. The client module shows how to configure a client
stub to invoke the service on an arbitrary URL with credentials versus what the you see in the WSDL file.

Building and Running the Application
------------------------------------
Simply run "mvn clean package" (or install) to build the applications. There is a Spring Boot App for both the
server and client.

To run the server, run the following from the top level directory of the project.

> java -jar learn-webservices-jaxws-server/target/learn-webservices-jaxws-server-0.0.1-SNAPSHOT.jar

From another command line, you can run the client with with this command:

> java -jar learn-webservices-jaxws-client/target/learn-webservices-jaxws-client-0.0.1-SNAPSHOT.jar

You should see the following output:
> =====================
>
> Ping Message
>
> =====================


The server output should also display the REQUEST and RESPONSE messages. Certainly not exciting, so there are more interesting test cases in the server module.

Future Work
-----------
I want to add an example of an attachment.
