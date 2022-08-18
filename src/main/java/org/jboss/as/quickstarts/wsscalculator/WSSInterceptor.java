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
package org.jboss.as.quickstarts.wsscalculator;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.common.ConfigurationConstants;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;

@ApplicationScoped
public class WSSInterceptor extends WSS4JInInterceptor {

    public WSSInterceptor() {
        super(getProps());
    }

    public static Map<String, Object> getProps() {
        Map<String,Object> props = new HashMap<String,Object>();
        props.put(ConfigurationConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        props.put(ConfigurationConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
        props.put(ConfigurationConstants.PW_CALLBACK_CLASS, UsernameTokenPasswordServerCallback.class.getName());
        return props;
    }

}
