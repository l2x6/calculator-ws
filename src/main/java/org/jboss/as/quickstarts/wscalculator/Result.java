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

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "result", propOrder = {
    "operands",
    "result",
    "even",
    "theAnswer"
})
public class Result {

    private int result;

    /**
     * Have some boolean parameter to be able to test {@code wsdl2java}'s {@code -xjc-Xboolean} parameter
     * when generating classes from the WSDL of the {@link CalculatorService}
     */
    private boolean even;

    /**
     * The defaulValue serves the sole purpose to be able to test {@code wsdl2java}'s {@code -xjc-Xdv} parameter
     * when generating classes from the WSDL of the {@link CalculatorService}
     */
    @XmlElement(defaultValue = "42")
    private String theAnswer;

    private Operands operands;

    public Result() {
    }

    public Result(int result, Operands operands) {
        super();
        this.result = result;
        this.operands = operands;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isEven() {
        return even;
    }

    public void setEven(boolean even) {
        this.even = even;
    }

    public Operands getOperands() {
        return operands;
    }

    public void setOperands(Operands operands) {
        this.operands = operands;
    }

    public String getTheAnswer() {
        return theAnswer;
    }

    public void setTheAnswer(String theAnswer) {
        this.theAnswer = theAnswer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operands, result);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Result other = (Result) obj;
        return Objects.equals(operands, other.operands) && result == other.result;
    }

}
