package org.chenmin.auto.client.api;

import org.chenmin.auto.shared.OrderWG;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AirLineAsync {

	void getOrder(String orderID, AsyncCallback<OrderWG> callback);

	void uploadOrderValid(String orderID, boolean success, String log, AsyncCallback<Void> callback);

}
