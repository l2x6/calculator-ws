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

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 */
@WebService(targetNamespace = CalculatorService.TARGET_NS)
public interface CalculatorService {

    public static final String TARGET_NS = "http://www.jboss.org/eap/quickstarts/wscalculator/Calculator";

    @WebMethod
    public int subtract(int intA, int intB);

    @WebMethod
    public int divide(int intA, int intB);

    @WebMethod
    public int add(int intA, int intB);

    /**
     * @param operands a non-primitive parameter
     * @return
     */
    @WebMethod
    public Result addOperands(Operands operands);

    /**
     * @param a the first operand
     * @param operands the second two operands
     * @return sum of a, {@link Operands#getA()} and {@link Operands#getB()}
     */
    @WebMethod
    public int addNumberAndOperands(int a, Operands operands);

    /**
     * @param array an array of numbers to sum
     * @return sum of the given {@code array} elements
     */
    @WebMethod
    public int addArray(int... array);

    /**
     * @param list a list of numbers to sum
     * @return sum of the given {@code list} elements
     */
    @WebMethod
    public int addList(List<Integer> list);

    @WebMethod
    public int multiply(int intA, int intB);
}
