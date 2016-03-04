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

	
    import org.apache.log4j.Logger;

import com.jamcracker.jif.adapter.BaseCompanyEventsAdapter;
import com.jamcracker.jif.dataobject.JIFRequest;
import com.jamcracker.jif.dataobject.JIFResponse;
import com.jamcracker.jif.dataobject.SuccessResponse;
import com.jamcracker.jif.dataobject.WaitResponse;
import com.jamcracker.jif.exception.JIFException;


	/**
	 * @author vmhardhe
	 *
	 */
	public class CompanyEventsAdapter extends BaseCompanyEventsAdapter {
		
		@SuppressWarnings("unused")
		private static final Logger LOG = Logger.getLogger(CompanyEventsAdapter.class);
		
		/* (non-Javadoc)
		 * @see com.jamcracker.jif.adapter.IJIFAdapter#createCompany(com.jamcracker.jif.dataobject.JIFRequest)
		 */
		public JIFResponse createCompany(JIFRequest jifRequest) throws JIFException {
			 String accountID= null;
			try{
				LOG.debug("createCompany starts ......");
                 SotiAdapter sotiadapter= new SotiAdapter();
                  accountID= sotiadapter.sotiCreateCompany(jifRequest); 
             
                 SuccessResponse jifResponse=null;
                 if (accountID.substring(0, 3).equals("201")||accountID.substring(0, 3).equals("200") ){
     				
     				String companyAccountID = accountID.substring(4);
     				LOG.debug("Inside If Block Of response code 201, Response code with Company account ID "+ companyAccountID);
     				WaitResponse waitResponse = new WaitResponse();     				    				
     				waitResponse.setCompanyField("SOTI_COMPANY_ID", companyAccountID); 				
     				return waitResponse;
     				
     				}
     			
     			else if (accountID.substring(0, 3).equals("401")){
     				
     				LOG.debug("Inside Else If Block Of response code 401, Response code with account ID "+ accountID);
     				accountID= accountID.substring(4);
     				LOG.debug("Inside Else If Block Of response code 401, Final account ID Message "+ accountID);
     				
     				jifResponse = new SuccessResponse();     				
     				jifResponse.setFaultCode("401");
     				jifResponse.setFaultString(accountID);   			
     				return jifResponse;
     			}
     			
     			else {
     				LOG.debug("Inside else block Unknown Exception due to unknown exception from BOX "+ accountID);
     				jifResponse = new SuccessResponse();     				
     				jifResponse.setFaultCode("12345");
     				jifResponse.setFaultString("UnKnown Exception Please check with Administrator");	    				
     				return jifResponse;
     				
     			}
				
						
			}catch(Exception e){
				
				throw new JIFException("404", "Account not able to create");
			}


			
		}
		
		/* (non-Javadoc)
		 * @see com.jamcracker.jif.adapter.IJIFAdapter#updateCompany(com.jamcracker.jif.dataobject.JIFRequest)
		 */
		public JIFResponse updateCompany(JIFRequest jifRequest) throws JIFException{
			
			try{
				 SotiAdapter sotiadapter= new SotiAdapter();
                 String accountID= sotiadapter.sotiUpdateCompany(jifRequest); 
				
                 SuccessResponse jifResponse=null;
                 if (accountID.substring(0, 3).equals("200")){
     				
     				String companyAccountID = accountID.substring(4);
     				LOG.debug("Inside If Block Of response code 201, Response code with Company account ID "+ companyAccountID);
     				WaitResponse waitResponse = new WaitResponse();     				    				
     				waitResponse.setCompanyField("companyAccountID", companyAccountID); 				
     				return waitResponse;
     				
     				}
     			
     			else if (accountID.substring(0, 3).equals("401")){
     				
     				LOG.debug("Inside Else If Block Of response code 401, Response code with account ID "+ accountID);
     				accountID= accountID.substring(4);
     				LOG.debug("Inside Else If Block Of response code 401, Final account ID Message "+ accountID);
     				
     				jifResponse = new SuccessResponse();     				
     				jifResponse.setFaultCode("401");
     				jifResponse.setFaultString(accountID);   			
     				return jifResponse;
     			}
     			
     			else {
     				LOG.debug("Inside else block Unknown Exception due to unknown exception from BOX "+ accountID);
     				jifResponse = new SuccessResponse();     				
     				jifResponse.setFaultCode("12345");
     				jifResponse.setFaultString("UnKnown Exception Please check with Administrator");	    				
     				return jifResponse;
     				
     			}

			}catch(Exception e){				
				throw new JIFException("404", "Account not able to update");
			}
		}

		
		
		/* (non-Javadoc)
		 * @see com.jamcracker.jif.adapter.IJIFAdapter#deleteCompany(com.jamcracker.jif.dataobject.JIFRequest)
		 */
		public JIFResponse deleteCompany(JIFRequest jifRequest) throws JIFException{
			
			try{
				SuccessResponse jifResponse = new SuccessResponse();
				return jifResponse;

			}catch(Exception e){
				
				throw new JIFException("404", "Account Not Found");
			}

		}

	}
