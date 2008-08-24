/*
 * Copyright 2004,2005 The Apache Software Foundation.
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

package org.apache.axis2.engine;

import junit.framework.TestCase;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.client.OperationClient;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.engine.util.TestConstants;
import org.apache.axis2.integration.UtilServer;
import org.apache.axis2.util.Utils;
import org.apache.ws.commons.om.OMAbstractFactory;
import org.apache.ws.commons.om.OMElement;
import org.apache.ws.commons.om.OMFactory;
import org.apache.ws.commons.om.OMNamespace;
import org.apache.ws.commons.soap.SOAP11Constants;
import org.apache.ws.commons.soap.SOAP12Constants;
import org.apache.ws.commons.soap.SOAPEnvelope;
import org.apache.ws.commons.soap.SOAPFactory;
import org.apache.wsdl.WSDLConstants;

import javax.xml.namespace.QName;

public class SOAPversionTest extends TestCase implements TestConstants {

    QName assumedServiceName = new QName("AnonymousService");


    private AxisService service;


    protected void setUp() throws Exception {
        UtilServer.start();
        service =
                Utils.createSimpleService(serviceName,
                        Echo.class.getName(),
                        operationName);
        UtilServer.deployService(service);
    }

    protected void tearDown() throws Exception {
        UtilServer.unDeployService(serviceName);
        UtilServer.stop();
        UtilServer.unDeployClientService();
    }


    public void testSOAP11() throws AxisFault {
        OMElement payload = createEnvelope();
        ConfigurationContext configCtx = ConfigurationContextFactory.
                createConfigurationContextFromFileSystem(null, null);
        ServiceClient client = new ServiceClient(configCtx, null);


        Options options = new Options();
        client.setOptions(options);
        options.setSoapVersionURI(
                SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
        options.setTo(targetEPR);
        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);

        OperationClient opClinet = client.createClient(ServiceClient.ANON_OUT_IN_OP);
        opClinet.addMessageContext(prepareTheSOAPEnvelope(payload, options));
        opClinet.execute(true);

        SOAPEnvelope result = opClinet.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE).getEnvelope();
        assertEquals("SOAP Version received is not compatible",
                SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI,
                result.getNamespace().getName());
    }

    public void testSOAP12() throws AxisFault {
        OMElement payload = createEnvelope();
        ConfigurationContext configCtx = ConfigurationContextFactory.
                createConfigurationContextFromFileSystem(null, null);
        ServiceClient client = new ServiceClient(configCtx, null);
        Options options = new Options();
        options.setSoapVersionURI(
                SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
        options.setTo(targetEPR);
        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
        client.setOptions(options);

        OperationClient opClinet = client.createClient(ServiceClient.ANON_OUT_IN_OP);
        opClinet.addMessageContext(prepareTheSOAPEnvelope(payload, options));
        opClinet.execute(true);

        SOAPEnvelope result = opClinet.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE).getEnvelope();
        assertEquals("SOAP Version received is not compatible",
                SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI,
                result.getNamespace().getName());
    }

    private MessageContext prepareTheSOAPEnvelope(OMElement toSend, Options options) throws AxisFault {
        MessageContext msgctx = new MessageContext();
        SOAPFactory sf = getSOAPFactory(options);
        SOAPEnvelope se = sf.getDefaultEnvelope();
        if (toSend != null) {
            se.getBody().addChild(toSend);
        }
        msgctx.setEnvelope(se);
        return msgctx;
    }

    private SOAPFactory getSOAPFactory(Options options) {
        String soapVersionURI = options.getSoapVersionURI();
        if (SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI.equals(soapVersionURI)) {
            return OMAbstractFactory.getSOAP12Factory();
        } else {
            // if its not SOAP 1.2 just assume SOAP 1.1
            return OMAbstractFactory.getSOAP11Factory();
        }
    }

    public void testSOAPfault() throws AxisFault {
        OMElement payload = createEnvelope();
        ConfigurationContext configCtx = ConfigurationContextFactory.
                createConfigurationContextFromFileSystem(null, null);
        ServiceClient client = new ServiceClient(configCtx, null);
        Options options = new Options();
        client.setOptions(options);
        options.setSoapVersionURI(
                SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);

        options.setTo(targetEPR);
        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
        OperationClient opClinet = client.createClient(ServiceClient.ANON_OUT_IN_OP);
        opClinet.addMessageContext(prepareTheSOAPEnvelope(payload, options));
        opClinet.execute(true);

        opClinet.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE).getEnvelope();
//        assertEquals("SOAP Version received is not compatible", SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI, result.getNamespace().getName());

    }

    private OMElement createEnvelope() {
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMNamespace omNs = fac.createOMNamespace("http://localhost/my", "my");
        OMElement method = fac.createOMElement("echoOMElement", omNs);
        OMElement value = fac.createOMElement("myValue", omNs);
        value.addChild(
                fac.createText(value, "Isaac Asimov, The Foundation Trilogy"));
        method.addChild(value);

        return method;
    }


}