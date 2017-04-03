package org.chenmin.auto.client.api;

public interface AirValid {
	
	boolean isMe(String url);
	
	String getFormSel(String url);
	
	boolean isValid(String url,String orderID,String formdata) throws Exception;
	
}
