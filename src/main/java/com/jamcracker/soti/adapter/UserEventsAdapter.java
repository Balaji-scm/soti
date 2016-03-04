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

import com.jamcracker.jif.adapter.BaseUserEventsAdapter;
import com.jamcracker.jif.common.JIFConstants;
import com.jamcracker.jif.dataobject.JIFRequest;
import com.jamcracker.jif.dataobject.JIFResponse;
import com.jamcracker.jif.dataobject.SuccessResponse;
import com.jamcracker.jif.exception.JIFException;

/**
 * @author vmhardhe
 *
 */
public class UserEventsAdapter extends BaseUserEventsAdapter {

	private static final Logger LOG = Logger.getLogger(UserEventsAdapter.class);

	public SotiAdapter sotiAdapter = new SotiAdapter();
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jamcracker.jif.adapter.IJIFAdapter#createUser(com.jamcracker.jif.
	 * dataobject.JIFRequest)
	 * 
	 */

	public JIFResponse createUser(JIFRequest jifRequest) throws JIFException {

		try {
			   SuccessResponse jifResponse = new SuccessResponse();

				jifResponse.setFaultCode("0");
				jifResponse.setFaultString("Success");

				return jifResponse;

		} catch (Exception e) {
			// On error, set proper error code and error response
			throw new JIFException("404", " Exception Occured Please check with Adminstrator");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jamcracker.jif.adapter.IJIFAdapter#updateUser(com.jamcracker.jif.
	 * dataobject.JIFRequest)
	 */
	public JIFResponse updateUser(JIFRequest jifRequest) throws JIFException {

		try {
				SuccessResponse jifResponse = new SuccessResponse();

				jifResponse.setFaultCode("0");
				jifResponse.setFaultString("Success");


				return jifResponse;
		} catch (Exception e) {
			// On error, set proper error code and error response
			throw new JIFException("404", " Exception Occured Please check with Adminstrator");
		}

	}

	

	
}
