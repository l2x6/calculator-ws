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
package org.jboss.as.quickstarts.wscalculator.bare;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import javax.jws.WebService;

import org.jboss.as.quickstarts.wscalculator.Operands;
import org.jboss.as.quickstarts.wscalculator.Result;

/**
 * The implementation of the CalculatorService
 */
@WebService(serviceName = "BareCalculatorService", portName = "BareCalculator", name = "BareCalculator", endpointInterface = "org.jboss.as.quickstarts.wscalculator.bare.BareCalculatorService", targetNamespace = BareCalculatorService.TARGET_NS)
public class BareCalculatorServiceImpl implements BareCalculatorService {

    /** You can pass {@code -e ADD_TO_RESULT=1} to the container to get a service instance behaving slightly differently.
     * This might be useful when testing multiple clients accessing distinct remote endpoints that implement the same interface */
    private static final int ADD_TO_RESULT = Integer.parseInt(Optional.ofNullable(System.getenv("ADD_TO_RESULT")).orElse("0"));

    @Override
    public int echo(int intA) {
        return intA + ADD_TO_RESULT;
    }

    @Override
    public Result addOperands(Operands operands) {
        return new Result(operands.getA() + operands.getB() + ADD_TO_RESULT, operands);
    }


    @Override
    public long addArray(long... array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        return LongStream.of(array).sum() + ADD_TO_RESULT;
    }


}
