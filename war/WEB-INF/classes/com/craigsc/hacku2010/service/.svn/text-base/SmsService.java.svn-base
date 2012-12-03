package com.craigsc.hacku2010.service;

import java.util.HashMap;
import java.util.Map;

import com.craigsc.hacku2010.domain.Subscription;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.TwilioRestResponse;

public class SmsService {
	private static final String APIVERSION = "2008-08-01";
	private static final String SID = "AC6497b88b1a1ae9a54a4d7fb983ba42cf";
	private static final String AUTHTOKEN = "88c74095df26cd54834ee00fa1c6e581";
	private static final String CALLERID = "5124877082";
	
	public SmsService() {}
	
	public static boolean sendSms(Subscription sub, String text) {
		TwilioRestClient client = new TwilioRestClient(SID, AUTHTOKEN, null);
        Map<String,String> params = new HashMap<String,String>();
        params.put("From", CALLERID);
        params.put("To", sub.getNumber());
        params.put("Body", text);
        TwilioRestResponse response;
        try {
        	response = client.request("/"+APIVERSION+"/Accounts/"+client.getAccountSid()+"/SMS/Messages", "POST", params);
        	if (response.isError()) {
        		System.out.println("OH SHIT!!!");
        		return false;
        	}
        	return true;
        } catch (TwilioRestException e) {
        	e.printStackTrace();
        	return false;
        }
	}

}
