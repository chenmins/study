package org.chenmin.auto.client.api;

import static com.google.gwt.query.client.GQuery.$;
public class JS {
	
	public static String getHtml(String sels){
		return $(sels).html();
	}
	
	public static native void export() /*-{
		$wnd.showAuto = $entry(function() {
			@org.chenmin.auto.client.Auto::showAuto()();
		});
	}-*/;

	public static native String formData(String sel) /*-{
		var d = {};
	    var t = $wnd.$(sel).serializeArray();
	    $wnd.$.each(t, function() {
	      d[this.name] = this.value;
	    });
	    var a = JSON.stringify(d);
	    return a;
	}-*/;
}
