package org.chenmin.auto.client.api;

import org.chenmin.auto.shared.OrderWG;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("airline")
public interface AirLine extends RemoteService {
	
	OrderWG getOrder(String orderID) throws Exception;
	
	void uploadOrderValid(String orderID,boolean success,String log) throws Exception;
}
