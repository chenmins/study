package org.chenmin.auto.client.api;

import java.util.ArrayList;
import java.util.List;

import org.chenmin.auto.shared.OrderWG;
import org.chenmin.auto.shared.TigerAirPassagerVerifier;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Factory {
	
	public static List<Verifier> vlist = new ArrayList<>();
	
	static{
		vlist.add(new TigerAirPassagerVerifier());
	}
	
	private static List<Verifier> getVerifier(String url){
		List<Verifier> alist = new ArrayList<>();
		for(Verifier v:vlist){
			if(v.isMe(url)){
				alist.add(v);
			}
		}
		return alist;
	}
	
	private static boolean isValid(String orderID,List<Verifier> alist) throws VerifierException{
		for(Verifier v:alist){
			if(!v.isValid(orderID)){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isValid(String orderID,String url) throws VerifierException{
		return isValid(orderID,getVerifier(url));
	}
	
	public static boolean isValid(String orderID) throws VerifierException{
		return isValid(orderID,Window.Location.getHref());
	}
	

	public final static AirLineAsync airLineService =  GWT.create(AirLine.class);

	public static void getOrder(String orderID, AsyncCallback<OrderWG> callback){
		airLineService.getOrder(orderID, callback);
	}
	
	public static DateTimeFormat sdf_ymd = DateTimeFormat.getFormat("yyyy-MM-dd");
	public static DateTimeFormat sdf_ymdhm = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm");
	
}
