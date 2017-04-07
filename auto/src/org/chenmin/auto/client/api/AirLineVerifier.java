package org.chenmin.auto.client.api;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("airline")
public interface AirLineVerifier extends RemoteService {

	boolean isMe(String url);
	
	Verifier[] getVerifier(String url);
	
	boolean isValid(String url,String orderID,Verifier[] v) throws VerifierException; 
}
