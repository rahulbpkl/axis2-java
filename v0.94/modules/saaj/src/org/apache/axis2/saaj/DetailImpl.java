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
package org.apache.axis2.saaj;

import org.apache.axis2.om.impl.dom.ElementImpl;
import org.apache.axis2.soap.SOAPFaultDetail;

import javax.xml.soap.Detail;
import javax.xml.soap.DetailEntry;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A container for <code>DetailEntry</code> objects. <code>DetailEntry</code>
 * objects give detailed error information that is application-specific and
 * related to the <code>SOAPBody</code> object that contains it.
 * <p/>
 * A <code>Detail</code> object, which is part of a <code>SOAPFault</code>
 * object, can be retrieved using the method <code>SOAPFault.getDetail</code>.
 * The <code>Detail</code> interface provides two methods. One creates a new
 * <code>DetailEntry</code> object and also automatically adds it to
 * the <code>Detail</code> object. The second method gets a list of the
 * <code>DetailEntry</code> objects contained in a <code>Detail</code>
 * object.
 * <p/>
 * The following code fragment, in which <i>sf</i> is a <code>SOAPFault</code>
 * object, gets its <code>Detail</code> object (<i>d</i>), adds a new
 * <code>DetailEntry</code> object to <i>d</i>, and then gets a list of all the
 * <code>DetailEntry</code> objects in <i>d</i>. The code also creates a
 * <code>Name</code> object to pass to the method <code>addDetailEntry</code>.
 * The variable <i>se</i>, used to create the <code>Name</code> object,
 * is a <code>SOAPEnvelope</code> object.
 * <PRE>
 * Detail d = sf.getDetail();
 * Name name = se.createName("GetLastTradePrice", "WOMBAT",
 * "http://www.wombat.org/trader");
 * d.addDetailEntry(name);
 * Iterator it = d.getDetailEntries();
 * </PRE>
 */
public class DetailImpl extends SOAPFaultElementImpl implements Detail {

//    private SOAPFaultDetail faultDetail;

    /**
     * @param element
     */
    public DetailImpl(SOAPFaultDetail element) {
        super((ElementImpl) element);
//        faultDetail = element;
    }

    /**
     * Creates a new <code>DetailEntry</code> object with the given
     * name and adds it to this <code>Detail</code> object.
     *
     * @param name a <code>Name</code> object identifying the new <code>DetailEntry</code> object
     * @return DetailEntry.
     * @throws SOAPException thrown when there is a problem in adding a DetailEntry object to this Detail object.
     */
    public DetailEntry addDetailEntry(Name name) throws SOAPException {
        SOAPElementImpl childElement = (SOAPElementImpl) addChildElement(name);
        DetailEntryImpl detailEntryImpl = new DetailEntryImpl(childElement.element);
//        faultDetail.addDetailEntry(detailEntryImpl.element);  //This causes infinite recursion
        return detailEntryImpl;
    }

    /**
     * Gets a list of the detail entries in this <code>Detail</code> object.
     *
     * @return an <code>Iterator</code> object over the <code>DetailEntry</code>
     *         objects in this <code>Detail</code> object
     */
    public Iterator getDetailEntries() {
        final Iterator detailEntriesIter = element.getChildElements();
        Collection details = new ArrayList();
        while (detailEntriesIter.hasNext()) {
            details.add(new DetailEntryImpl((ElementImpl) detailEntriesIter.next()));
        }
        return details.iterator();
    }

}