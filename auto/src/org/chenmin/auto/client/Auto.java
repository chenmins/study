package org.chenmin.auto.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Auto implements EntryPoint {

	static final DialogBox dialogBox = new DialogBox();
	
	public static void show() {
		dialogBox.setText("小助理:" + Window.getTitle());
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		closeButton.getElement().setId("closeButton");

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new Order());
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});
		dialogBox.center();
		dialogBox.show();
	}
	
	public static void showAuto(){
		if(dialogBox.isShowing()){
			dialogBox.hide();
		}else{
			dialogBox.center();
			dialogBox.show();
		}
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		export();
		show();
	}

	public native void export() /*-{
		$wnd.showAuto = $entry(function() {
			@org.chenmin.auto.client.Auto::showAuto()();
		});
	}-*/;

}