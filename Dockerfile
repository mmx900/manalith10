FROM maven:3.9.9-amazoncorretto-11 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn clean package -DskipTests

FROM tomcat:8-jdk11-openjdk
COPY docker/context.xml /usr/local/tomcat/webapps.dist/manager/META-INF/context.xml
RUN cp -avT $CATALINA_HOME/webapps.dist/manager $CATALINA_HOME/webapps/manager
COPY docker/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

COPY docker/wait-for-it.sh /usr/local/bin/wait-for-it.sh
RUN chmod +x /usr/local/bin/wait-for-it.sh
