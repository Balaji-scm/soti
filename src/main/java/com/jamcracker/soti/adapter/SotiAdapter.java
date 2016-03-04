/***************************************************
 *
 * This software is the confidential and proprietary information of Jamcracker, Inc.
 * ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you
 *  entered into with Jamcracker, Inc. Copyright (c) 2000 Jamcracker, Inc.  All Rights
 *  Reserved
 *
 * @ClassName com.jamcracker.glue.xstream.api.server.resources.common.xStreamConstants
 * @author vmhardhe
 * @version: 1.0
 * @see
 *
 * 
 *
 ******************************************************/
package com.jamcracker.soti.adapter;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.json.simple.parser.ParseException;

import com.jamcracker.jif.dataobject.JIFRequest;
import com.jamcracker.soti.commons.SotiUtil;


/**
 * @author vmhardhe
 * 
 */
public class SotiAdapter {

	private static final Logger LOG = Logger.getLogger(SotiAdapter.class);

	private String accountID = null;
   
	public String sotiCreateCompany(JIFRequest jifRequest) throws HttpException, IOException, IllegalArgumentException, ParseException {
		String url="https://api.soti.net/api/orders/createCustomerOrder";
		String companyId= jifRequest.getCompanyField("SOTI_COMPANY_ID");
		LOG.debug("Checking companyId");
		if(null == companyId || (companyId.length() < 5)||
				SotiUtil.isCustomerExists(companyId,jifRequest)==false){
			LOG.debug("Inside First If Condition");
		if(companyId==null || (companyId.length() < 5)){
			LOG.debug("Generating a new UUID");
            companyId= UUID.randomUUID().toString();        
		}
		LOG.debug("SOTI_COMPANY_ID :"+companyId);
		HttpClient client = new HttpClient();
		PostMethod method= new PostMethod(url);
       
		// Provide custom retry handler is necessary
	    	method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
	    	String sotiUserName=jifRequest.getServiceField("aspusername");
	    	String sotiPassword=jifRequest.getServiceField("asppassword");
	    	LOG.debug("sotiUserName :"+sotiUserName+"sotiPassword :"+sotiPassword.length());
          byte[] bytesEncoded = Base64.encode((sotiUserName+ ":" +sotiPassword)
					.getBytes());
			method.setRequestHeader("Authorization", "Basic "+ new String(bytesEncoded));
			method.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			
			NameValuePair arr[]= new NameValuePair[13];
			arr[0]=(new NameValuePair("CompanyID",companyId));
			arr[1]=(new NameValuePair("LicenseSkuNumber", "SOTI-MCL-DEV-API"));
			arr[2]=(new NameValuePair("NumberOfLicenses",jifRequest.getCompanyField("licenseNum")));
			arr[3]=(new NameValuePair("CompanyName", jifRequest.getCompanyField("companyName")));
			arr[4]=(new NameValuePair("MainContactEmail",jifRequest.getCompanyField("contactEmail")));
			arr[5]=(new NameValuePair("MainContactName",jifRequest.getCompanyField("contactFirstName")+" "+
					jifRequest.getCompanyField("contactLastName")));
			arr[6]=(new NameValuePair("MainContactNumber", jifRequest.getCompanyField("contactPhone")));
			arr[7]=(new NameValuePair("CompanyStreetAddress",jifRequest
					.getCompanyField("address1")));
			arr[8]=(new NameValuePair("CompanyCityAddress",jifRequest
					.getCompanyField("city")));
			arr[9]=(new NameValuePair("CompanyProvinceAddress", jifRequest.getCompanyField("state")));
			arr[10]=(new NameValuePair("CompanyCountryAddress", jifRequest
					.getCompanyField("country")));
			arr[11]=(new NameValuePair("CompanyPostalCode",jifRequest
					.getCompanyField("zip")));
			Random randomGenerator = new Random();
			arr[12]=(new NameValuePair("OrderId", String.valueOf(randomGenerator.nextInt(10000))));
			method.setRequestBody(arr);
			for (int i=0;i<arr.length;i++)
			{
				LOG.debug(" params "+arr[i]);
			}
	
	   try {
		   client.executeMethod(method);
		   LOG.debug("HTTP status line" + method.getStatusLine()
					+ " creating account\n\n");
		  if (method.getStatusCode() == 201) {

				try {
					LOG.debug("Company Created Successfully....");
					accountID = "201:"+companyId;
					LOG.debug("accountID "+accountID);
					return accountID;
                  
				} catch (Exception e) {
					e.printStackTrace();
	
				}
			}

			else if (method.getStatusCode() == 401) {
				accountID = "401:Un-authorized ";
				LOG.debug(" Inside Error Block 401" + accountID);
			    return accountID;
			}

			else if (method.getStatusCode() == 409) {
				accountID = "409:User Login Already Exists, Please try with Different One";
				LOG.debug(" Inside Error Block 409" + accountID);
				return accountID;

			}

		} catch (HttpException e1) {
           LOG.debug("HttpException :"+e1.getMessage());
		//	e1.printStackTrace();
		} catch (IOException e1) {
			 LOG.debug("HttpException :"+e1.getMessage());
		}

		finally {
			method.releaseConnection();
		}
		return accountID;
		}
		else 
		{ 
			return sotiUpdateCompany(jifRequest);
		}
		
	}
	

	@SuppressWarnings("deprecation")
	public String sotiUpdateCompany(JIFRequest jifRequest) throws HttpException, IOException 
	{
		String url="https://api.soti.net/api/orders/updateCustomerOrder1";
		LOG.debug("Called Update Company....");
		PutMethod method= new PutMethod(url);
		HttpClient httpclient = new HttpClient();	
		// Provide custom retry handler is necessary
	    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
	    
	    String companyId= jifRequest.getCompanyField("SOTI_COMPANY_ID");      
	    LOG.debug("Update companyID :"+companyId);
	    String sotiUserName=jifRequest.getServiceField("aspusername");
    	String sotiPassword=jifRequest.getServiceField("asppassword");
    	LOG.debug("sotiUserName :"+sotiUserName+"sotiPassword :"+sotiPassword.length());
        byte[] bytesEncoded = Base64.encode((sotiUserName+ ":" +sotiPassword)
				.getBytes());
		method.setRequestHeader("Authorization", "Basic "+ new String(bytesEncoded));
		method.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		
		String requestBody="CompanyId="+companyId+"&"+
				"LicenseSkuNumber="+"SOTI-MCL-DEV-API&"+
				"NumberOfLicenses="+jifRequest.getCompanyField("licenseNum")+"&"+
				"CompanyName="+jifRequest.getCompanyField("companyName")+"&"+
				"MainContactEmail="+jifRequest.getCompanyField("contactEmail")+"&"+
				"MainContactName="+jifRequest.getCompanyField("contactFirstName")+" "+
				jifRequest.getCompanyField("contactLastName")+"&"+
				"MainContactNumber="+jifRequest.getCompanyField("contactPhone")+"&"+
				"CompanyStreetAddress="+jifRequest.getCompanyField("address1")+"&"+
				"CompanyCityAddress="+jifRequest.getCompanyField("city")+"&"+
				"CompanyProvinceAddress="+jifRequest.getCompanyField("state")+"&"+
				"CompanyCountryAddress="+jifRequest.getCompanyField("country")+"&"+
				"CompanyPostalCode="+jifRequest.getCompanyField("zip");
		   
		 method.setRequestEntity(new StringRequestEntity(requestBody,
					"application/x-www-form-urlencoded", "UTF-8"));
		 httpclient.executeMethod(method);
		 LOG.debug("requestBody: "+requestBody.toString());
		LOG.debug("HTTP status line" + method.getStatusLine()
					+ " creating account\n\n");
        if (method.getStatusCode() == 200) {

				try {
					LOG.debug("Company updated Successfully....");
					accountID = "200:"+companyId;
					return accountID;				
					
				} catch (Exception e) {
					e.printStackTrace();
	
				}
			}

			else if (method.getStatusCode() == 401) {
				accountID = "401:Un-authorized ";
				LOG.debug(" Inside Error Block 401" + accountID);
				return accountID;
			}
		return accountID;
	}
		
	
}
