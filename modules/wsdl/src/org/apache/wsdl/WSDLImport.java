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

package org.apache.wsdl;

/**
 * @author chathura@opensource.lk
 */
public interface WSDLImport extends Component {
    /**
     * Gets the location URI of the Include.
     *
     * @return
     */
    public String getLocation();

    /**
     * Sets the location URI of the Include.
     *
     * @param location
     */
    public void setLocation(String location);

    /**
     * Returns the namespace URI
     *
     * @return
     */
    public String getNamespace();

    /**
     * Sets the namespace URI.
     *
     * @param namespace
     */
    public void setNamespace(String namespace);
}
