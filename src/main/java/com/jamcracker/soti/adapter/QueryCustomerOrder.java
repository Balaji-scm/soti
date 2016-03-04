package com.jamcracker.soti.adapter;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.bouncycastle.util.encoders.Base64;


public class QueryCustomerOrder {
        
	   public static void main(String args[]) throws HttpException, IOException{
	
		  HttpClient client = new HttpClient();
		GetMethod method= new GetMethod("https://api.soti.net/api/orders/queryCustomerOrder");

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
          byte[] bytesEncoded = Base64.encode(("panasonicorders@soti.net" + ":" + "p86ZD8UaO9571uz")
					.getBytes());
			method.setRequestHeader("Authorization", "Basic "+ new String(bytesEncoded));
			method.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
				method.setQueryString("CompanyID=88c8cf24-ba37-454c-961e-0e11856e3746&LicenseSkuNumber=SOTI-MCL-DEV-API");
		 	int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
			//	System.err.println("Method failed: " + method.getStatusLine());
			}
			byte[] responseBody1 = method.getResponseBody();
			//System.out.println(new String(responseBody1));
	}

	
	   
		// TODO Auto-generated method stub
		
	}

