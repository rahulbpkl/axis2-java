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


package userguide.clients;

import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.clientapi.MessageSender;
import org.apache.axis2.om.OMElement;

/**
 * Created by IntelliJ IDEA.
 * User: Jaliya
 * Date: Jun 3, 2005
 * Time: 2:11:25 PM
 */
public class PingClient {
    private static EndpointReference targetEPR = new EndpointReference("http://127.0.0.1:8080/axis2/services/MyService/ping");

    public static void main(String[] args) {
        try {
            OMElement payload = ClientUtil.getPingOMElement();

            MessageSender msgSender = new MessageSender();
            msgSender.setTo(targetEPR);
            msgSender.setSenderTransport(Constants.TRANSPORT_HTTP);

            msgSender.send("ping", payload);

        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
    }

}
