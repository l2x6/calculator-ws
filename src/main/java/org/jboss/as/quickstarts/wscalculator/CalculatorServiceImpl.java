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
package org.jboss.as.quickstarts.wscalculator;

import javax.jws.WebService;

/**
 * The implementation of the CalculatorService
 */
@WebService(serviceName = "CalculatorService", portName = "Calculator", name = "Calculator", endpointInterface = "org.jboss.as.quickstarts.wscalculator.CalculatorService", targetNamespace = CalculatorService.TARGET_NS)
public class CalculatorServiceImpl implements CalculatorService {
    @Override
    public int subtract(int intA, int intB) {
        return intA - intB;
    }

    @Override
    public int divide(int intA, int intB) {
        return intA / intB;
    }

    @Override
    public int add(int intA, int intB) {
        return intA + intB;
    }

    @Override
    public int multiply(int intA, int intB) {
        return intA * intB;
    }

}
