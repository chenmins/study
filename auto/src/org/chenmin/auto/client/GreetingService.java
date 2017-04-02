package org.chenmin.auto.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	
	boolean isValid(String url,String orderID,String formdata) throws Exception;
	
	String getFormSel(String url) throws IllegalArgumentException;
}
