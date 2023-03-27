FROM eclipse-temurin:11
COPY target/server /opt/jboss/wildfly

# Ensure signals are forwarded to the JVM process correctly for graceful shutdown
ENV LAUNCH_JBOSS_IN_BACKGROUND true

ENV BASIC_AUTH_USER=""
ENV BASIC_AUTH_PASSWORD=""

# Expose the ports we're interested in
EXPOSE 8080

# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interface
ENTRYPOINT ["/bin/sh", "-c", "if [ \"x$BASIC_AUTH_USER\" != \"x\" -a \"x$BASIC_AUTH_PASSWORD\" != \"x\" ]; then /opt/jboss/wildfly/bin/add-user.sh -a -u \"$BASIC_AUTH_USER\" -p \"$BASIC_AUTH_PASSWORD\" -g app-users; fi; /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0"]
