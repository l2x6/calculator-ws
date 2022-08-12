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

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;

import org.assertj.core.api.Assertions;
import org.jboss.as.quickstarts.wscalculator.CalculatorService;
import org.junit.Test;

/**
 * Simple set of tests for the Calculator Web Service to demonstrate accessing the web service using a client
 */
public class ClientIT {

    private static final String BASE_URI = "http://localhost:8088";

    @Test
    public void calculate() throws MalformedURLException {
        QName serviceName = new QName(CalculatorService.TARGET_NS, CalculatorService.class.getSimpleName());

        final long deadline = System.currentTimeMillis() + 10_000L;

        Service service = null;
        while (true) {
            try {
                service = Service.create(new URL(BASE_URI + "/calculator-ws/CalculatorService?wsdl"), serviceName);
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

        Assertions.assertThat(calculatorService.add(2,3)).isEqualTo(5);
        Assertions.assertThat(calculatorService.subtract(6,4)).isEqualTo(2);
        Assertions.assertThat(calculatorService.multiply(6,7)).isEqualTo(42);
        Assertions.assertThat(calculatorService.divide(10,2)).isEqualTo(5);

    }

}
