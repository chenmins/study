package org.chenmin.auto.server;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.chenmin.auto.client.api.AirLine;
import org.chenmin.auto.server.db.Test;
import org.chenmin.auto.shared.FlightWG;
import org.chenmin.auto.shared.OrderWG;
import org.chenmin.auto.shared.PassengerWG;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AirLineImpl extends RemoteServiceServlet implements AirLine  {
	
	SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");
	
	private String getDate(){
		return sdf.format(new Date());
	}

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
		System.out.println( getDate()+"orderID:"+orderID);
		System.out.println(getDate()+order);
		return order;
	}

	@Override
	public void uploadOrderValid(String orderID, boolean success, String log) throws Exception {
		System.out.println(getDate()+"orderID:"+orderID);
		System.out.println(getDate()+"success:"+success);
		System.out.println(getDate()+"log:"+log);
	}

}
