package org.chenmin.auto.client.api;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AirLineVerifierAsync {

	void getVerifier(String url, AsyncCallback<Verifier[]> callback);

	void isMe(String url, AsyncCallback<Boolean> callback);

	void isValid(String url, String orderID, Verifier[] v, AsyncCallback<Boolean> callback);

}
