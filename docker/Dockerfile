FROM tomcat:8-jdk11-openjdk
COPY context.xml /usr/local/tomcat/webapps.dist/manager/META-INF/context.xml
RUN cp -avT $CATALINA_HOME/webapps.dist/manager $CATALINA_HOME/webapps/manager
COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
