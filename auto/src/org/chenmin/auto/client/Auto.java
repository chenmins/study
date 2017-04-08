package org.chenmin.auto.client;

import org.chenmin.auto.client.api.JS;
import org.chenmin.auto.client.ui.OrderBox;

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
		dialogBox.setText("小助理 V1.16:" + Window.getTitle());
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("隐藏");
		closeButton.getElement().setId("closeButton");

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
//		dialogVPanel.add(new Order());
		dialogVPanel.add(new OrderBox());
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
		JS.export();
		show();
	}
	
}
