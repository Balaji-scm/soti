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

import com.jamcracker.jif.adapter.Authentication;
import com.jamcracker.jif.dataobject.AuthInfo;
import com.jamcracker.jif.exception.AuthenticationException;


/**
 * @author vmhardhe
 *
 */
public class AuthenticationImpl implements Authentication {

	public void authenticate(AuthInfo authInfo) throws AuthenticationException {
		//String authLogin = authInfo.getUserName();
		//String authPassword = authInfo.getPassword();
		//If the auth info is not valid throw exception
		
		//throw new AuthenticationException(1000,"Authorization failed");
	}

}
