package org.chenmin.auto.client.api;

import java.util.ArrayList;
import java.util.List;

import org.chenmin.auto.client.api.Verifier.TypeName;
import org.chenmin.auto.client.check.TigerAirFlightVerifier;
import org.chenmin.auto.client.check.TigerAirPassagerVerifier;
import org.chenmin.auto.client.ui.LogBox;
import org.chenmin.auto.shared.MyConstants;
import org.chenmin.auto.shared.OrderWG;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Factory {

	public static List<Verifier> vlist = new ArrayList<>();

	static {
		vlist.add(new TigerAirFlightVerifier());
		vlist.add(new TigerAirPassagerVerifier());
	}

	private static List<Verifier> getVerifier(String url) {
		List<Verifier> alist = new ArrayList<>();
		for (Verifier v : vlist) {
			if (v.isMe(url)) {
				alist.add(v);
			}
		}
		return alist;
	}

	private static boolean isValid(String orderID, List<Verifier> alist) throws VerifierException {
		if (alist.isEmpty())
			throw new VerifierException("当前页面没有验证规则");
		for(int i=0;i<alist.size();i++){
			Verifier v = alist.get(i);
			VerifierBean[] vbs = v.getVerifierBean();
			VerifierBean[] vbsn = new VerifierBean[vbs.length];
			for(int j=0;j<vbs.length;j++){
				VerifierBean vb = vbs[j];
				TypeName type = vb.getType();
				switch (type) {
					case FormData:
						vb.setData(JS.formData(vb.getSel()));
						break;
					case HTML:
						vb.setData(JS.html(vb.getSel()));
						break;
					default:
						break;
				}
				vbsn[j]=vb;
			}
			v.setVerifierBean(vbsn);
			if (!v.isValid()) {
				return false;
			}
		}
		return true;
	}

	public static boolean isValid(String orderID, String url) throws VerifierException {
		return isValid(orderID, getVerifier(url));
	}

	public static boolean isValid(String orderID) throws VerifierException {
		return isValid(orderID, Window.Location.getHref());
	}

	public final static AirLineAsync airLineService = GWT.create(AirLine.class);

	public static OrderWG order;

	public static void getOrder(String orderID, final AsyncCallback<OrderWG> callback) {
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
		airLineService.getOrder(orderID, callbackPorxy);
	}

	public static LogBox log = new LogBox();

	public static DateTimeFormat sdf_ymd = DateTimeFormat.getFormat("yyyy-MM-dd");
	public static DateTimeFormat sdf_ymdhm = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm");
	public static DateTimeFormat sdf_hm = DateTimeFormat.getFormat("[HH:mm:ss] ");
	public static DateTimeFormat sdf_hmmd = DateTimeFormat.getFormat("h:mma EEE, MMM dd");

	public static String baseURL = GWT.getModuleBaseForStaticFiles().contains("127") ? ""
			: (GWT.getModuleBaseForStaticFiles() + "../");
	public static String loading = "<img src='" + baseURL + "loading.gif'/>";
	public static String yes = "<img src='" + baseURL + "yes.gif'/>";
	public static String no = "<img src='" + baseURL + "no.gif'/>";


	public static MyConstants my = GWT.create(MyConstants.class);
}
