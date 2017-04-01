package org.chenmin.auto.client;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Order extends Composite {
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	private VerticalPanel panel = new VerticalPanel();
	private Button clear = new Button("获得订单");
	private Button put = new Button("put!");
	private Button get = new Button("get!");
	private Button click = new Button("click!");
	private Label label = new Label("wait!");
	private Label info = new Label("wait!");
	private TextBox key = new TextBox();
	private TextBox val = new TextBox();
	final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
	
	public Order() {
		super();
		init();
		this.initWidget(panel);
	}
	

	private void init() {

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(clear);
		hp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;"));
		hp.add(put);
		hp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;"));
		hp.add(get);
		hp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;"));
		hp.add(click);
		this.panel.add(hp);
		label.setText("Hello:");
		this.panel.add(label);
		this.panel.add(new Label("key:"));
		this.panel.add(key);
		this.panel.add(new Label("val:"));
		this.panel.add(val);
		simplePopup.ensureDebugId("cwBasicPopup-simplePopup");
		simplePopup.setWidth("150px");
		simplePopup.setWidget(info);

		initEvent();

	}

	private void initEvent() {
		GWT.log("initEvent");
		clear.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String keys = key.getText();
				put(event,keys);
//				key.setText("");
//				val.setText("");
				showinfo(event, keys+"get ok!");
			}
		});
		put.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String keys = key.getText();
				String vals = val.getText();
				$(keys).val(vals);
				
			}
		});

		get.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String keys = key.getText();
				val.setText($(keys).val());
			}
		});
		click.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String keys = key.getText();
				 $(keys).click();
			}
		});
	}
	
	public void put(final ClickEvent event,String textToServer){
		greetingService.greetServer(textToServer, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
//				showinfo(event,caught.getMessage());
				Window.alert(caught.getMessage());
			}
	
			public void onSuccess(String result) {
//				showinfo(event,result);
				Window.alert(result);
			}
		});
	}

	public void showinfo(ClickEvent event, String text) {
		Widget source = (Widget) event.getSource();
		int left = source.getAbsoluteLeft() + 50;
		int top = source.getAbsoluteTop() + 50;
		simplePopup.setPopupPosition(left, top);
		info.setText(text);
		simplePopup.show();
	}
}
