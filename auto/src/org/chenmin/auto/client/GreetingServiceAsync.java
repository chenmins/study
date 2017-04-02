package org.chenmin.auto.client;

import org.chenmin.auto.shared.OrderWG;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;

	void isValid(String url,String orderID, String formdata, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	void getFormSel(String url, AsyncCallback<String> callback) throws IllegalArgumentException;

	void getOrder(String orderID, AsyncCallback<OrderWG> callback)  ;
}
