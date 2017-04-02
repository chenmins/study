package org.chenmin.auto.server;

public interface AirValid {
	
	boolean isMe(String url);
	
	String getFormSel(String url);
	
	boolean isValid(String url,String orderID,String formdata) throws Exception;
	
}
