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
package org.jboss.as.quickstarts.wscalculator.auth.basic;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.jws.WebService;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;

/**
 * The implementation of the CalculatorService
 */
@WebService(serviceName = "BasicAuthCalculatorService", portName = "BasicAuthCalculator", name = "BasicAuthCalculator", endpointInterface = "org.jboss.as.quickstarts.wscalculator.auth.basic.BasicAuthCalculatorService", targetNamespace = BasicAuthCalculatorService.TARGET_NS)
//@WebContext(authMethod = "BASIC", secureWSDLAccess = false)
@RolesAllowed("app-users")
//@SecurityDomain("ApplicationDomain")
public class BasicAuthCalculatorServiceImpl implements BasicAuthCalculatorService {

    /** You can pass {@code -e ADD_TO_RESULT=1} to the container to get a service instance behaving slightly differently.
     * This might be useful when testing multiple clients accessing distinct remote endpoints that implement the same interface */
    private static final int ADD_TO_RESULT = Integer.parseInt(Optional.ofNullable(System.getenv("ADD_TO_RESULT")).orElse("0"));

    @Override
    public int securedAdd(int intA, int intB) {
        return intA + intB + ADD_TO_RESULT;
    }

}
