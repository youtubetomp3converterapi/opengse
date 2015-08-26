OpenGSE is a suite of servlet spec compliance tests along with a minimal servlet engine which passes those tests (see http://java.sun.com/products/servlet/index.jsp if you don't know what servlets are).

The tests consist of WAR files that you deploy to your favourite servlet engine and client-side code which you run against that servlet engine (see http://en.wikipedia.org/wiki/Sun_WAR_(file_format) for more information about WAR files).

# What do I need? #

You need to have a Java Developer Kit (or JDK) installed on your computer (see http://java.com/en/download/index.jsp)

and Apache Ant (see http://ant.apache.org/)

# What do I do? #
Once you have checked the project out from subversion, you can build the war files with:
```
ant build-wars      # this assumes you are in the root directory of the project
```

You should then have WAR files in testing/server-side/webapps

Once those WAR files have been deployed in your favourite servlet engine (how this is done depends on the servlet engine) you can run the compliance tests with:

```
cd testing/client-side
ant -Dhost=localhost -Dport=8080     # this runs the tests against localhost:8080
```

# Building the "toy" servlet engine (miniGSE) #

You can build the "toy" servlet engine with:
```
ant build-minigse      # this assumes you are in the root directory of the project
```

# Deploying the WAR files in miniGSE #

You can deploy the wars in miniGSE with:
```
ant deploy-wars-in-mini-engine  # this assumes you are in the root directory of the project
```

or with:
```
java -jar minigse/opengse.jar --webapps=./testing/server-side/webapps --port=8080  # this assumes you are in the root directory of the project
```