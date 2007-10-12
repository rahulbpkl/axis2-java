/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.axis2.jaxws.handler.lifecycle.impl;

import javax.xml.ws.handler.Handler;

import org.apache.axis2.jaxws.ExceptionFactory;
import org.apache.axis2.jaxws.core.MessageContext;
import org.apache.axis2.jaxws.description.ServiceDescription;
import org.apache.axis2.jaxws.handler.lifecycle.factory.HandlerLifecycleManager;
import org.apache.axis2.jaxws.injection.ResourceInjectionException;
import org.apache.axis2.jaxws.lifecycle.BaseLifecycleManager;
import org.apache.axis2.jaxws.lifecycle.LifecycleException;
import org.apache.axis2.jaxws.runtime.description.injection.ResourceInjectionServiceRuntimeDescription;
import org.apache.axis2.jaxws.runtime.description.injection.ResourceInjectionServiceRuntimeDescriptionFactory;

public class HandlerLifecycleManagerImpl extends BaseLifecycleManager implements HandlerLifecycleManager {
        
    public Handler createHandlerInstance(MessageContext mc, Class handlerClass) throws LifecycleException, ResourceInjectionException {
        if (handlerClass == null) {
            throw ExceptionFactory.makeWebServiceException("Handler class must be passed");
        }
        
        ServiceDescription serviceDesc = mc.getEndpointDescription().getServiceDescription();        
        ResourceInjectionServiceRuntimeDescription injectionDesc = null;
        if (serviceDesc != null) {
            injectionDesc = ResourceInjectionServiceRuntimeDescriptionFactory.get(serviceDesc, handlerClass);            
        }
        
        try {
            this.instance = handlerClass.newInstance();
        } catch (InstantiationException e) {
            throw ExceptionFactory.makeWebServiceException(e);
        } catch (IllegalAccessException e) {
            throw ExceptionFactory.makeWebServiceException(e);
        }
                
        //Invoke PostConstruct
        if (injectionDesc != null && injectionDesc.getPostConstructMethod() != null) {
            invokePostConstruct(injectionDesc.getPostConstructMethod());
        }
        
        return (Handler)this.instance;
    }
  
}
