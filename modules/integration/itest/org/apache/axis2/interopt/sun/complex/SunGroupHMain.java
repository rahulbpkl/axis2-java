package org.apache.axis2.interopt.sun.complex;

import org.apache.axis2.interopt.sun.round4.complex.*;
import org.apache.axis2.om.OMElement;
import org.apache.axis2.om.OMXMLParserWrapper;
import org.apache.axis2.om.impl.llom.exception.XMLComparisonException;
import org.apache.axis2.om.impl.llom.util.XMLComparator;
import org.apache.axis2.soap.impl.llom.builder.StAXSOAPModelBuilder;
import org.apache.axis2.soap.SOAPEnvelope;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLInputFactory;
import java.io.InputStream;

import junit.framework.TestCase;
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
*
*
*/

/**
 * Author: Deepal Jayasinghe
 * Date: Aug 29, 2005
 * Time: 4:54:58 PM
 */
public class SunGroupHMain extends TestCase {

    EchoBlockingClient client=null;
    OMElement retEle;
    SunGroupHClientUtil util=null;
    String soapAction="";

    public void setUp(){
        client=new EchoBlockingClient();
    }

    private boolean Compare(OMElement retEle,String filepath) throws XMLStreamException,
            XMLComparisonException {
        boolean compare=false;
        if(retEle!=null){
             InputStream stream = Thread.currentThread()
                     .getContextClassLoader().getResourceAsStream("/" + filepath);
            javax.xml.stream.XMLStreamReader parser = XMLInputFactory.newInstance().createXMLStreamReader(stream);
            OMXMLParserWrapper builder = new StAXSOAPModelBuilder(parser, null);
            SOAPEnvelope resEnv = (SOAPEnvelope) builder.getDocumentElement();
            OMElement resElementtobe = resEnv.getBody().getFirstElement();
            XMLComparator comparator = new XMLComparator();
            compare = comparator.compare(retEle,resElementtobe);
        }
        return compare;
    }



    //echoSoapStructFault
    public void testSoapStructFault() throws Exception {
        util=new EchoSOAPStructFaultClientUtil();
        retEle = client.sendMsg(util,soapAction);
        assertTrue(
                Compare(retEle,"/interopt/sun/round4/complex/resSoapStructFault.xml"));
    }


    //echoBaseSoapStructFault
    public void testBaseStructFault()throws Exception{
        util=new EchoBaseStructFaultClientutil();
        retEle = client.sendMsg(util,soapAction);
        assertTrue(
                Compare( retEle,"/interopt/sun/round4/complex/resBaseStrutFault.xml"));

    }

    //echoExtendedStructFault
    public void testExtendedStructFault()throws Exception{
        util=new EchoExtendedStructFaultClientUtil();
        retEle = client.sendMsg(util,soapAction);
        assertTrue(
                Compare(retEle,"/interopt/sun/round4/complex/resExtendedStructFault.xml"));

    }

    //echomultiplefaults1
    public void testMultiplefaults1()throws Exception{
        util=new EchoMultipleFaults1ClientUtil();
        retEle = client.sendMsg(util,soapAction);
        assertTrue(
                Compare( retEle,"/interopt/sun/round4/complex/resMultipleFaults1.xml"));
    }

    //echomultiplefaults2
    public void testMultiplefaults2()throws Exception{
        util=new EchoMultipleFaults2ClientUtil();
        retEle = client.sendMsg(util,soapAction);
        assertTrue(
                Compare( retEle,"/interopt/sun/round4/complex/resMultipleFaults2.xml"));
    }

}

