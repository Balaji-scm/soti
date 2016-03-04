package com.jamcracker.soti.commons;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.bouncycastle.util.encoders.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jamcracker.jif.dataobject.JIFRequest;


public class SotiUtil {
	
	private final static org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(SotiUtil.class);
	
	public static boolean isCustomerExists(String sotiCompanyId,JIFRequest jifRequest) throws HttpException, IOException, ParseException
	{
		LOG.debug("isCustomerExists starts...");
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(
				"https://api.soti.net/api/orders/queryCustomerOrder");
		// Provide custom retry handler is necessary
		method.getParams().setParameter(
				HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		String sotiUserName=jifRequest.getServiceField("aspusername");
    	String sotiPassword=jifRequest.getServiceField("asppassword");
    	LOG.debug("sotiUserName :"+sotiUserName+"sotiPassword :"+sotiPassword.length());
         byte[] bytesEncoded = Base64.encode((sotiUserName+ ":" +sotiPassword)
				.getBytes());
		method.setRequestHeader("Authorization", "Basic "
				+ new String(bytesEncoded));
		method.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");

		method.setQueryString("CompanyID=" + sotiCompanyId
				+ "&LicenseSkuNumber=SOTI-MCL-DEV-API");
		LOG.debug("QueryString is "+method.getQueryString());
		int statusCode = client.executeMethod(method);
		if (statusCode != HttpStatus.SC_OK) {
			LOG.debug("Method failed: Subscriber Doesnot exists returning false"
					+ method.getStatusLine());
		     	return false;
		}
		byte[] response1 = method.getResponseBody();
		LOG.debug("get Customer Query Response :"+ new String(response1));
		
		if (response1 != null) {
			JSONParser parser = new JSONParser();
			JSONObject params = (JSONObject) parser	.parse(new String(response1));
			LOG.debug("companyId from SOTI response  :" + params.get("companyId"));
			if (params.get("companyId") != null) {
				String companyId = (String) params.get("companyId");
				if(companyId.equalsIgnoreCase(sotiCompanyId))
					return true;
				else 
					return false;
		}
		}
		return false;

}
}
