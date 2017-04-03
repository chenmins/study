package org.chenmin.auto.server;

import java.util.ArrayList;
import java.util.List;

import org.chenmin.auto.client.api.AirValid;
import org.chenmin.auto.client.api.GreetingService;
import org.chenmin.auto.server.air.TigerAir;
import org.chenmin.auto.server.db.Test;
import org.chenmin.auto.shared.FieldVerifier;
import org.chenmin.auto.shared.FlightWG;
import org.chenmin.auto.shared.OrderWG;
import org.chenmin.auto.shared.PassengerWG;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}
//		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);
		System.out.println("userAgent:"+userAgent);
		System.out.println("input:"+input);
//		return "Hello, " + input + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>"
//				+ userAgent;
	//	return "Hello, " + input +"\n"+Test.getSysdate()+"\n"+serverInfo;
		List<FlightWG> f = Test.getFlightWGs(input);
		List<PassengerWG> p =Test.getPassengerWGs(input); 
		return f.toString()+","+ p.toString();
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	
	static List<AirValid> airs = new ArrayList<AirValid>();
	
	static{
		airs.add(new TigerAir());
	}

	@Override
	public boolean isValid(String url,String orderID, String formdata) throws Exception {
		if(orderID.isEmpty())
			throw new IllegalArgumentException("orderID 不为空");
		System.out.println("orderID:"+orderID);
		System.out.println("formdata:"+formdata);
		for(AirValid air:airs){
			if(air.isMe(url)){
				return air.isValid(url, orderID, formdata);
			}
		}
		return false;
	}

	@Override
	public String getFormSel(String url) throws IllegalArgumentException {
		for(AirValid air:airs){
			if(air.isMe(url)){
				return air.getFormSel(url);
			}
		}
		return null;
	}

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
