package org.chenmin.auto.client.api;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.GWT;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
public class JS {
	
	public static String getHtml(String sels){
		return $(sels).html();
	}
	
	public static String matcherOne(String r4, String pattern, int group) {
		RegExp r = RegExp.compile(pattern);
		MatchResult m = r.exec(r4);
		if(m.getGroupCount()>0)
			return m.getGroup(group);
		return null;
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
	
	public static String html(String sels){
		String _h = _html(sels);
		GWT.log("sels:"+sels);
		GWT.log("_h_:"+_h);
		return _h;
	}
	
	public static native String _html(String sel) /*-{
	    var t = $wnd.$(sel).html();
	    return t;
	}-*/;
}
