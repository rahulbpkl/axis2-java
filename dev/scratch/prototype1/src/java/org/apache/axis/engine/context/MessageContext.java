/*
 * Copyright 2001-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.axis.engine.context;

import java.util.HashMap;

import javax.xml.namespace.QName;

import org.apache.axis.engine.AxisFault;
import org.apache.axis.engine.Constants;
import org.apache.axis.engine.Operation;
import org.apache.axis.engine.Service;
import org.apache.axis.engine.addressing.EndPointReferance;
import org.apache.axis.engine.registry.EngineRegistry;
import org.apache.axis.om.soap.SOAPMessage;

/**
 *  The palce where all the service specific states are kept. 
 *  All the Global states kept in the <code>EngineRegistry</code> and all the 
 *  Service states kept in the <code>MessageContext</code>. Other runtime
 *  artifacts does not keep states foward from the execution.  
 */
public class MessageContext {
    private int messageStyle = Constants.SOAP_STYLE_RPC_ENCODED;
//    private HashMap messages = new HashMap();
    
	public static final String USER_NAME = "USER";
	public static final String PASSWARD = "PASSWD";
	public static final String TRANSPORT_TYPE = "TRANSPORT_TYPE";
    public static final String SOAP_ACTION = "SOAP_ACTION";
    public static final String TRANSPORT_DATA = "TRANSPORT_DATA";
    
    private boolean processingFault = false;
    private EndPointReferance to;
    private EndPointReferance from;
    private EndPointReferance relatesTo;
    private EndPointReferance replyTo;
    private EndPointReferance faultTo;

    //there is a no use cas found to set those proprties 
    //so declare them final    
    private final HashMap properties;
    private final GlobalContext globalContext;

    private SessionContext sessionContext;
    private Service service;
    private SOAPMessage message; 
    private boolean responseWritten;
    private boolean infaultFlow;
    private boolean serverSide;
    private String messageID;
    private Operation operation;
    private boolean newThreadRequired = false;
    
    public MessageContext(EngineRegistry er) throws AxisFault{
        this.globalContext = new GlobalContext(er);
        this.sessionContext = new SimpleSessionContext();
        properties = new HashMap();
    }

    
    /**
     * @return
     */
    public EndPointReferance getFaultTo() {
        return faultTo;
    }

    /**
     * @return
     */
    public EndPointReferance getFrom() {
        return from;
    }

    /**
     * @return
     */
    public GlobalContext getGlobalContext() {
        return globalContext;
    }

    /**
     * @return
     */
    public boolean isInfaultFlow() {
        return infaultFlow;
    }

    /**
     * @return
     */
    public SOAPMessage getMessage() {
        return message;
    }

    /**
     * @return
     */
    public String getMessageID() {
        return messageID;
    }


    /**
     * @return
     */
    public boolean isProcessingFault() {
        return processingFault;
    }

    /**
     * @return
     */
    public Object getProperty(Object key) {
        return properties.get(key);
    }

    /**
     * @return
     */
    public EndPointReferance getRelatesTo() {
        return relatesTo;
    }

    /**
     * @return
     */
    public EndPointReferance getReplyTo() {
        return replyTo;
    }

    /**
     * @return
     */
    public boolean isResponseWritten() {
        return responseWritten;
    }

    /**
     * @return
     */
    public boolean isServerSide() {
        return serverSide;
    }

    /**
     * @return
     */
    public SessionContext getSessionContext() {
        return sessionContext;
    }

    /**
     * @return
     */
    public EndPointReferance getTo() {
        return to;
    }

    /**
     * @param referance
     */
    public void setFaultTo(EndPointReferance referance) {
        faultTo = referance;
    }

    /**
     * @param referance
     */
    public void setFrom(EndPointReferance referance) {
        from = referance;
    }

    /**
     * @param b
     */
    public void setInfaultFlow(boolean b) {
        infaultFlow = b;
    }

    /**
     * @param message
     */
    public void setMessage(SOAPMessage message) {
        this.message = message;
    }

    /**
     * @param string
     */
    public void setMessageID(String string) {
        messageID = string;
    }


    /**
     * @param b
     */
    public void setProcessingFault(boolean b) {
        processingFault = b;
    }

    /**
     * @param map
     */
    public void setProperty(Object key,Object value) {
        properties.put(key,value);
    }

    /**
     * @param referance
     */
    public void setRelatesTo(EndPointReferance referance) {
        relatesTo = referance;
    }

    /**
     * @param referance
     */
    public void setReplyTo(EndPointReferance referance) {
        replyTo = referance;
    }

    /**
     * @param b
     */
    public void setResponseWritten(boolean b) {
        responseWritten = b;
    }

    /**
     * @param b
     */
    public void setServerSide(boolean b) {
        serverSide = b;
    }


    /**
     * @param referance
     */
    public void setTo(EndPointReferance referance) {
        to = referance;
    }

    /**
     * @return
     */
    public Service getService() {
        return service;
    }


    /**
     * @return
     */
    public Operation getOperation() {
        return operation;
    }

    /**
     * @param operation
     */
    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    /**
     * @return
     */
    public boolean isNewThreadRequired() {
        return newThreadRequired;
    }

    /**
     * @param b
     */
    public void setNewThreadRequired(boolean b) {
        newThreadRequired = b;
    }

    /**
     * @param service
     */
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * @param context
     */
    public void setSessionContext(SessionContext context) {
        sessionContext = context;
    }

    /**
     * @return
     */
    public int getMessageStyle() {
        return messageStyle;
    }

    /**
     * @param i
     */
    public void setMessageStyle(int i) {
        messageStyle = i;
    }

}
