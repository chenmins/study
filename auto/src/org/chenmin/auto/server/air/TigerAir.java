package org.chenmin.auto.server.air;

import org.chenmin.auto.server.AirValid;

public class TigerAir implements AirValid {

	@Override
	public boolean isMe(String url) {
		System.out.println("isMe:"+url);
		return true;
	}

	@Override
	public String getFormSel(String url) {
		System.out.println("getFormSel:"+url);
		return "form[action=\"/BookFlight/Passengers\"]";
	}

	@Override
	public boolean isValid(String url, String orderID, String formdata) throws Exception {
		System.out.println("isValid:"+url);
		System.out.println("orderID:"+orderID);
		System.out.println("formdata:"+formdata);
		return false;
	}
 

}
