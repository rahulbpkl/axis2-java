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
package org.apache.axis2.tool.service.eclipse.ui;


import org.apache.axis2.tool.service.eclipse.plugin.ServiceArchiver;
import org.apache.axis2.tool.service.eclipse.util.SettingsConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.WizardPage;


public abstract class AbstractServiceWizardPage extends WizardPage implements SettingsConstants{
    protected IDialogSettings settings;
    protected boolean restoredFromPreviousSettings = false;
    
    public AbstractServiceWizardPage(String pageName){
        super(pageName+".name");
        init(pageName);
    }
    
    protected void init(String pageName){
        setTitle(ServiceArchiver.getResourceString(pageName+".title"));
        setDescription(ServiceArchiver.getResourceString(pageName+".desc"));
        setImageDescriptor(ServiceArchiver.getWizardImageDescriptor());
        
        /*
         * Get the settings for this page. If there is no section in the
         * Plugin's settings for this OptionsPage, create a new section
         */
        IDialogSettings rootSettings = ServiceArchiver.getDefault()
                .getDialogSettings();
        IDialogSettings section = rootSettings.getSection(this.getClass()
                .getName());
        if (section == null) {
            settings = rootSettings.addNewSection(this.getClass().getName());
            restoredFromPreviousSettings = false;
            initializeDefaultSettings();
        } else {
            restoredFromPreviousSettings=true;
            settings = section;
        }
    }

    protected void updateStatus(String message) {
        setErrorMessage(message);
        setPageComplete(message == null);
    }

    protected abstract void initializeDefaultSettings(); 
    
    protected abstract boolean getWizardComplete(); 
    
    //Default implementation
    public boolean isSkipNext(){
        return false;
    }
    
    //public abstract WizardBean getBean();
    
    
    
    
}
