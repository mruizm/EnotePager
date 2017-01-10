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

public class EnoteNotificationObject
{
    private String mIncidentId;
    private String mIncidentPriority;
    private String mIncidentReceivedTimeStamp;
    private String mIncidentCustomer;
    private String mIncidentWorkGroup;
    private String mIncidentSloType;
    private String mIncidentSloPercent;
    private String mIncidentAck;

    public EnoteNotificationObject()
    {
        EnoteSmsInterceptor smsInterceptor = new EnoteSmsInterceptor();
        mIncidentReceivedTimeStamp = smsInterceptor.getreadabledate();
        mIncidentAck = "0";
    }

    public String getIncidentId()
    {
        return mIncidentId;
    }

    public void setIncidentId(String incidentId)
    {
        mIncidentId = incidentId;
    }

    public String getIncidentPriority()
    {
        return mIncidentPriority;
    }

    public void setIncidentPriority(String incidentPriority)
    {
        mIncidentPriority = incidentPriority;
    }

    public String getIncidentReceivedTimeStamp()
    {
        return mIncidentReceivedTimeStamp;
    }

    public void setIncidentReceivedTimeStamp(String incidentReceivedTimeStamp)
    {
        mIncidentReceivedTimeStamp = incidentReceivedTimeStamp;
    }

    public String getIncidentCustomer()
    {
        return mIncidentCustomer;
    }

    public void setIncidentCustomer(String incidentCustomer)
    {
        mIncidentCustomer = incidentCustomer;
    }

    public String getIncidentWorkGroup()
    {
        return mIncidentWorkGroup;
    }

    public void setIncidentWorkGroup(String incidentWorkGroup)
    {
        mIncidentWorkGroup = incidentWorkGroup;
    }

    public String getIncidentSloType()
    {
        return mIncidentSloType;
    }

    public void setIncidentSloType(String incidentSloType)
    {
        mIncidentSloType = incidentSloType;
    }

    public String getIncidentSloPercent()
    {
        return mIncidentSloPercent;
    }

    public void setIncidentSloPercent(String incidentSloPercent)
    {
        mIncidentSloPercent = incidentSloPercent;
    }

    public String getIncidentAck() {
        return mIncidentAck;
    }

    public void setIncidentAck(String incidentAck) {
        mIncidentAck = incidentAck;
    }
}
