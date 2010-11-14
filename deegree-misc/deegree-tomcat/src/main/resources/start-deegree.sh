#!/bin/bash

java -classpath deegree-javacheck.jar org.deegree.JavaCheck
RETVAL=$?
[ $RETVAL -ne 0 ] && exit

export JAVA_OPTS=-Xmx1024M
bin/catalina.sh run &

# TODO proper browser startup after Tomcat started
sleep 5
firefox http://localhost:8080

# Wait forever
cat
