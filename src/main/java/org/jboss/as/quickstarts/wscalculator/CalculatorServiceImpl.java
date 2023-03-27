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
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.jws.WebService;

/**
 * The implementation of the CalculatorService
 */
@WebService(serviceName = "CalculatorService", portName = "Calculator", name = "Calculator", endpointInterface = "org.jboss.as.quickstarts.wscalculator.CalculatorService", targetNamespace = CalculatorService.TARGET_NS)
public class CalculatorServiceImpl implements CalculatorService {

    /** You can pass {@code -e ADD_TO_RESULT=1} to the container to get a service instance behaving slightly differently.
     * This might be useful when testing multiple clients accessing distinct remote endpoints that implement the same interface */
    private static final int ADD_TO_RESULT = Integer.parseInt(Optional.ofNullable(System.getenv("ADD_TO_RESULT")).orElse("0"));

    @Override
    public int subtract(int intA, int intB) {
        return intA - intB + ADD_TO_RESULT;
    }

    @Override
    public int divide(int intA, int intB) {
        return intA / intB + ADD_TO_RESULT;
    }

    @Override
    public int add(int intA, int intB) {
        return intA + intB + ADD_TO_RESULT;
    }

    @Override
    public Result addOperands(Operands operands) {
        return new Result(operands.getA() + operands.getB() + ADD_TO_RESULT, operands);
    }

    @Override
    public int addNumberAndOperands(int a, Operands operands) {
        return a + operands.getA() + operands.getB() + ADD_TO_RESULT;
    }

    @Override
    public int addArray(int... array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        return IntStream.of(array).sum() + ADD_TO_RESULT;
    }

    @Override
    public int addList(List<Integer> list) {
        return list.stream()
                .mapToInt(Integer::intValue)
                .sum() + ADD_TO_RESULT;
    }

    @Override
    public int multiply(int intA, int intB) {
        return intA * intB + ADD_TO_RESULT;
    }


}
