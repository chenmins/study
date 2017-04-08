package org.chenmin.auto.server;

import java.util.List;

import org.chenmin.auto.client.api.AirLine;
import org.chenmin.auto.server.db.Test;
import org.chenmin.auto.shared.FlightWG;
import org.chenmin.auto.shared.OrderWG;
import org.chenmin.auto.shared.PassengerWG;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AirLineImpl extends RemoteServiceServlet implements AirLine  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4168725881684464530L;

	@Override
	public OrderWG getOrder(String orderID) throws Exception {
		if(orderID.isEmpty())
			throw new IllegalArgumentException("orderID 不为空");
		List<FlightWG> f = Test.getFlightWGs(orderID);
		List<PassengerWG> p =Test.getPassengerWGs(orderID); 
		OrderWG order = new OrderWG();
		order.setFlight(f);
		order.setPassenger(p);
		return order;
	}

}
