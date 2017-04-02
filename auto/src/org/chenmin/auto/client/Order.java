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
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	private VerticalPanel panel = new VerticalPanel();
	private Button clear = new Button("获得订单");
	private Button put = new Button("put!");
	private Button get = new Button("get!");
	private Button click = new Button("校验订单");
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
		hp.add(click);
		hp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;"));
		hp.add(put);
		hp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;"));
		hp.add(get);
		this.panel.add(hp);
		label.setText("Hello:");
		this.panel.add(label);
		this.panel.add(new Label("表单:"));
		this.panel.add(key);
		this.panel.add(new Label("订单:"));
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
//				String keys = key.getText();
				put(event, val.getText());
//				showinfo(event, keys + "get ok!");
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
				String vals = val.getText();
				// $(keys).click();
				// formData("#aa1")
				// Window.alert(JS.formData(keys));
				click(vals, JS.formData(keys));
			}
		});
		getFormsel();
	}

	public void put(final ClickEvent event, String textToServer) {
		greetingService.greetServer(textToServer, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				// showinfo(event,caught.getMessage());
				Window.alert(caught.getMessage());
			}

			public void onSuccess(String result) {
				// showinfo(event,result);
				Window.alert(result);
			}
		});
	}

	public void getFormsel(){
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				key.setText(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		};
		GWT.log("Window.Location.getHostName():"+Window.Location.getHostName());
		GWT.log("Window.Location.getHref():"+Window.Location.getHref());
		greetingService.getFormSel(Window.Location.getHref(), callback);
	}
	
	public void click(final String orderID, final String formdata) {
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					Window.alert("订单：" + orderID + "\n校验成功！");
				} else {
					Window.alert("订单：" + orderID + "\n\n\n校验失败！");
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		};
		greetingService.isValid(Window.Location.getPath(),orderID, formdata, callback);
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
