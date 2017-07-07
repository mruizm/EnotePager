/*
Class that models the object of a SMS sent by eNote
Author:     Marco Ruiz Mora (mruizm@hpe.com)
Date:       26 nov 2016

Constructor:
EnotePagerNotification()

Public Methods:
Getters:
String: getIncidentId()
String: getIncidentPriority()
Date:   getIncidentReceivedTimeStamp()
String: getIncidentCustomer
String: getIncidentWorkGroup()
String: getIncidentSloType()
String: getIncidentSloPercent()

Setters:
String: setIncidentId()
String: setIncidentPriority()
String: setIncidentCustomer()
String: setIncidentWorkGroup()
String: setIncidentSloType()
String: setIncidentSloPercent()
*/
package com.hpe.rumarco.enotepager;

public class EnoteNotificationCustomObject {
    private String mCustomRuleName;
    private String mIsCustomRuleActive;

    public EnoteNotificationCustomObject() {
        EnoteSmsInterceptor smsInterceptor = new EnoteSmsInterceptor();
    }

    public String getCustomRuleName() {
        return mCustomRuleName;
    }

    public void setCustomRuleName(String customRuleName) {
        mCustomRuleName = customRuleName;
    }

    public String getIsCustomRuleActive() {
        return mIsCustomRuleActive;
    }

    public void setIsCustomRuleActive(String isCustomRuleActive) {
        mIsCustomRuleActive = isCustomRuleActive;
    }
}

