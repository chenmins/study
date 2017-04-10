package org.chenmin.auto.client.api;

import java.util.ArrayList;
import java.util.List;

import org.chenmin.auto.client.check.TigerAirFlightVerifier;
import org.chenmin.auto.client.check.TigerAirPassagerVerifier;
import org.chenmin.auto.client.check.TigerAirTimeVerifier;
import org.chenmin.auto.client.ui.LogBox;
import org.chenmin.auto.shared.MyConstants;
import org.chenmin.auto.shared.OrderWG;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Factory {
	
	public static List<Verifier> vlist = new ArrayList<>();
	
	static{
		vlist.add(new TigerAirFlightVerifier());
		vlist.add(new TigerAirTimeVerifier());
		vlist.add(new TigerAirPassagerVerifier());
	}
	
	private static List<Verifier> getVerifier(String url){
		List<Verifier> alist = new ArrayList<>();
		for(Verifier v:vlist){
			if(v.isMe(url)){
				log.info("Verifier:"+v.name()+" 匹配成功！");
				alist.add(v);
			}
		}
		return alist;
	}
	
	private static boolean isValid(String orderID,List<Verifier> alist) throws VerifierException{
		if(alist.isEmpty())
			throw new VerifierException("当前页面没有验证规则");
		for(Verifier v:alist){
			Factory.log.info("type:"+v.type());
			Factory.log.info("sels:"+v.sels());
			switch (v.type()) {
			case FormData:
				v.setData(JS.formData(v.sels())); 
				break;
			case HTML:
				v.setData(JS.getHtml(v.sels())); 
				break;
			default:
				break;
			}
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
	
	public static OrderWG order;

	public static void getOrder(String orderID,final AsyncCallback<OrderWG> callback){
		AsyncCallback<OrderWG> callbackPorxy = new AsyncCallback<OrderWG>() {
			
			@Override
			public void onSuccess(OrderWG result) {
				order = result;
				callback.onSuccess(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}
		};
		airLineService.getOrder(orderID, callbackPorxy );
	}
	
	public static LogBox log = new LogBox();
	
	public static DateTimeFormat sdf_ymd = DateTimeFormat.getFormat("yyyy-MM-dd");
	public static DateTimeFormat sdf_ymdhm = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm");
	public static DateTimeFormat sdf_hm = DateTimeFormat.getFormat("[HH:mm:ss] ");
	public static DateTimeFormat sdf_hmmd = DateTimeFormat.getFormat("h:mma EEE, MMM dd");

	public static String baseURL = GWT.getModuleBaseForStaticFiles().contains("127")?"":(GWT.getModuleBaseForStaticFiles()+"../");
	public static String loading = "<img src='"+baseURL+"loading.gif'/>";
	
	public static MyConstants my = GWT.create(MyConstants.class);
}
