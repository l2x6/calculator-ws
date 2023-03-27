/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.wscalculator.it;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;

import org.assertj.core.api.Assertions;
import org.jboss.as.quickstarts.wscalculator.CalculatorService;
import org.jboss.as.quickstarts.wscalculator.Operands;
import org.jboss.as.quickstarts.wscalculator.Result;
import org.jboss.as.quickstarts.wscalculator.auth.basic.BasicAuthCalculatorService;
import org.jboss.as.quickstarts.wscalculator.bare.BareCalculatorService;
import org.jboss.as.quickstarts.wsscalculator.WssCalculatorService;
import org.junit.Test;

import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.client.ClientTransportException;
import com.sun.xml.ws.developer.WSBindingProvider;
import com.sun.xml.ws.fault.ServerSOAPFaultException;

/**
 * Simple set of tests for the Calculator Web Service to demonstrate accessing the web service using a client
 */
public class ClientIT {

    private static final String BASE_URI = "http://localhost:8088";

    @Test
    public void calculate() throws MalformedURLException {

        final long deadline = System.currentTimeMillis() + 10_000L;
        Service service = null;
        while (true) {
            try {
                service = Service.create(new URL(BASE_URI + "/calculator-ws/CalculatorService?wsdl"),
                        new QName(CalculatorService.TARGET_NS, CalculatorService.class.getSimpleName()));
                break;
            } catch (WebServiceException e) {
                if (System.currentTimeMillis() < deadline) {
                    /* wait and retry */
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    throw e;
                }
            }

        }
        final CalculatorService calculatorService = service.getPort(CalculatorService.class);

        Assertions.assertThat(calculatorService).isNotNull();

        Assertions.assertThat(calculatorService.add(2, 3)).isEqualTo(5);
        Assertions.assertThat(calculatorService.addOperands(new Operands(6, 4))).isEqualTo(new Result(10, new Operands(6, 4)));
        Assertions.assertThat(calculatorService.addNumberAndOperands(2, new Operands(6, 4))).isEqualTo(12);
        Assertions.assertThat(calculatorService.addArray(2, 3, 4)).isEqualTo(9);
        Assertions.assertThat(calculatorService.addList(Arrays.asList(2, 3, 4))).isEqualTo(9);
        Assertions.assertThat(calculatorService.subtract(6, 4)).isEqualTo(2);
        Assertions.assertThat(calculatorService.multiply(6, 7)).isEqualTo(42);
        Assertions.assertThat(calculatorService.divide(10, 2)).isEqualTo(5);

    }


    @Test
    public void bare() throws MalformedURLException {

        final long deadline = System.currentTimeMillis() + 10_000L;
        Service service = null;
        while (true) {
            try {
                service = Service.create(new URL(BASE_URI + "/calculator-ws/BareCalculatorService?wsdl"),
                        new QName(BareCalculatorService.TARGET_NS, BareCalculatorService.class.getSimpleName()));
                break;
            } catch (WebServiceException e) {
                if (System.currentTimeMillis() < deadline) {
                    /* wait and retry */
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    throw e;
                }
            }

        }
        final BareCalculatorService calculatorService = service.getPort(BareCalculatorService.class);

        Assertions.assertThat(calculatorService).isNotNull();

        Assertions.assertThat(calculatorService.echo(2)).isEqualTo(2);
        Assertions.assertThat(calculatorService.addOperands(new Operands(6, 4))).isEqualTo(new Result(10, new Operands(6, 4)));
        Assertions.assertThat(calculatorService.addArray(2, 3, 4)).isEqualTo(9);

    }


    @Test
    public void basicAuth() throws MalformedURLException {

        final long deadline = System.currentTimeMillis() + 10_000L;
        Service service = null;
        while (true) {
            try {
                service = Service.create(new URL(BASE_URI + "/calculator-ws/BasicAuthCalculatorService?wsdl"),
                        new QName(BasicAuthCalculatorService.TARGET_NS, BasicAuthCalculatorService.class.getSimpleName()));
                break;
            } catch (WebServiceException e) {
                if (System.currentTimeMillis() < deadline) {
                    /* wait and retry */
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    throw e;
                }
            }
        }
        final BasicAuthCalculatorService calculatorService = service.getPort(BasicAuthCalculatorService.class);
        Assertions.assertThat(calculatorService).isNotNull();

        /* Make sure that anonymous request fails */
        Assertions.assertThatExceptionOfType(ClientTransportException.class)
                .isThrownBy(() -> calculatorService.securedAdd(0, 0))
                 .withMessageContaining("The server sent HTTP status code 401: Unauthorized");

        /* Now set the proper credentials and make sure that it succeeds */
        Map<String, Object> ctx = ((BindingProvider)calculatorService).getRequestContext();
        ctx.put(BindingProvider.USERNAME_PROPERTY, "tester");
        ctx.put(BindingProvider.PASSWORD_PROPERTY, "s3cr3t!");

        Assertions.assertThat(calculatorService.securedAdd(7,8)).isEqualTo(15);

    }

    @Test
    public void wss() throws MalformedURLException, SOAPException {
        final long deadline = System.currentTimeMillis() + 10_000L;
        Service wssService = null;
        while (true) {
            try {
                wssService = Service.create(new URL(BASE_URI + "/calculator-ws/WssCalculatorService?wsdl"),
                        new QName(WssCalculatorService.TARGET_NS, WssCalculatorService.class.getSimpleName()));
                break;
            } catch (WebServiceException e) {
                if (System.currentTimeMillis() < deadline) {
                    /* wait and retry */
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    throw e;
                }
            }

        }
        final WssCalculatorService wssCalculatorService = wssService.getPort(WssCalculatorService.class);

        /* Make sure that it fails properly when called without a password */
        Assertions.assertThatExceptionOfType(ServerSOAPFaultException.class)
                .isThrownBy(() -> wssCalculatorService.modulo(13, 5))
                .withMessage(
                        "Client received SOAP Fault from server: A security error was encountered when verifying the message Please see the server log to find more detail regarding exact cause of the failure.");


        final String SCHEMA = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
        final String SCHEMA_PREFIX = "wsse";
        // Create a SOAP header
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();

        SOAPHeader header = soapEnvelope.getHeader();
        // Add the security SOAP header element
        SOAPHeaderElement security = header.addHeaderElement(new QName(SCHEMA, "Security", SCHEMA_PREFIX));
        SOAPElement usernameToken = security.addChildElement("UsernameToken", SCHEMA_PREFIX);
        SOAPElement usernameElement = usernameToken.addChildElement("Username", SCHEMA_PREFIX);
        SOAPElement passwordElement = usernameToken.addChildElement("Password", SCHEMA_PREFIX);
        Name typeName = soapEnvelope.createName("Type");
        passwordElement.addAttribute(typeName,
                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");

        usernameElement.setTextContent("cxf-user");
        passwordElement.setTextContent("cxf-secret");

        ((WSBindingProvider) wssCalculatorService).setOutboundHeaders(Headers.create(security));

        Assertions.assertThat(wssCalculatorService.modulo(13, 5)).isEqualTo(3);

    }

}
