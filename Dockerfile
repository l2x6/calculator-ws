FROM quay.io/wildfly/wildfly
ADD target/calculator-ws.war /opt/jboss/wildfly/standalone/deployments/
