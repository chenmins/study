package org.chenmin.auto.client.ui;

import static com.google.gwt.query.client.GQuery.$;

import java.util.List;

import org.chenmin.auto.client.api.GreetingService;
import org.chenmin.auto.client.api.GreetingServiceAsync;
import org.chenmin.auto.client.api.JS;
import org.chenmin.auto.client.api.Store;
import org.chenmin.auto.shared.FlightWG;
import org.chenmin.auto.shared.OrderWG;
import org.chenmin.auto.shared.PassengerWG;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Grid;
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
	private Button getOrderButton = new Button("获得订单");
	private Button validButton = new Button("校验订单");
	private Button put = new Button("put!");
	private Button get = new Button("get!");
	private Label label = new Label("wait!");
	private Label info = new Label("wait!");
	private TextBox key = new TextBox();
	private TextBox val = new TextBox();

	private VerticalPanel fightPanel = new VerticalPanel();
	private Grid fight = new Grid();
	private Grid pass = new Grid();
	
	private static DateTimeFormat sdf_ymd = DateTimeFormat.getFormat("yyyy-MM-dd");
	private static DateTimeFormat sdf_ymdhm = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm");

	final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);

	public Order() {
		super();
		init();
		this.initWidget(panel);
	}

	private void init() {

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(getOrderButton);
		hp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;"));
		hp.add(validButton);
		hp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;"));
		hp.add(put);
		hp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;"));
		hp.add(get);
		this.panel.add(hp);
		label.setText("HelloChenmin123:");
		this.panel.add(label);
		this.panel.add(new Label("表单定位:"));
		this.panel.add(key);
		this.panel.add(new Label("订单ID:"));
		this.panel.add(val);
		this.panel.add(fightPanel);
		this.panel.setWidth("430px");
		
		simplePopup.ensureDebugId("cwBasicPopup-simplePopup");
		simplePopup.setWidth("150px");
		simplePopup.setWidget(info);

		initEvent();

	}

	private void initEvent() {
		GWT.log("initEvent");
		String order = Store.getItem("order");
		if(order!=null){
			val.setText(order);
			put(val.getText());
		}
		
		getOrderButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// String keys = key.getText();
				put(val.getText());
				Store.setItem("order", val.getText());
				// showinfo(event, keys + "get ok!");
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
		validButton.addClickHandler(new ClickHandler() {

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

	public void put( String textToServer) {
		AsyncCallback<OrderWG> callback = new AsyncCallback<OrderWG>() {

			@Override
			public void onSuccess(OrderWG result) {
				putOrder(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		};
		greetingService.getOrder(textToServer, callback);
	}

	public void putOrder(OrderWG result) {
		fightPanel.clear();
		List<FlightWG> flight = result.getFlight();
		List<PassengerWG> passenger = result.getPassenger();
		GWT.log("flight:"+flight);
		GWT.log("passenger:"+passenger);
		if(!flight.isEmpty()){
			fight = new Grid(flight.size()+1,5);
			fight.setHTML(0, 0, "航司");
			fight.setHTML(0, 1, "出发");
			fight.setHTML(0, 2, "到达");
			fight.setHTML(0, 3, "起飞时间");
			fight.setHTML(0, 4, "航班号");
			int index=1;
			for(FlightWG f:flight){
				fight.setHTML(index, 0, f.getCarrier());
				fight.setHTML(index, 1, f.getDepAirportCode());
				fight.setHTML(index, 2, f.getArrAirportCode());
				fight.setHTML(index, 3,sdf_ymdhm.format(f.getDepTime()));
				fight.setHTML(index, 4, f.getFlightNum());
				index++;
			}
			fightPanel.add(fight);
		}
		if(!passenger.isEmpty()){
			pass = new Grid(passenger.size()+1,9);
			pass.setHTML(0, 0, "firstname");
			pass.setHTML(0, 1, "lastname");
			pass.setHTML(0, 2, "sexy");
			pass.setHTML(0, 3, "type");
//			pass.setHTML(0, 4, "证件");
			pass.setHTML(0, 5, "证件ID");
//			pass.setHTML(0, 6, "证件归宿");
			pass.setHTML(0, 7, "证件生日");
			pass.setHTML(0, 8, "过期日期");
			int index=1;
			for(PassengerWG f:passenger){
				pass.setHTML(index, 0, f.getFirstname());
				pass.setHTML(index, 1, f.getLastname());
				pass.setHTML(index, 2, f.getSexy());
				pass.setHTML(index, 3, f.getType());
//				pass.setHTML(index, 4, f.getCredentialsname());
				pass.setHTML(index, 5, f.getCredentialsID());
//				pass.setHTML(index, 6, f.getNationality());
				pass.setHTML(index, 7,sdf_ymd.format(f.getBirthday()));
				pass.setHTML(index, 8,sdf_ymd.format(f.getExpirydate()));
				index++;
			}
			fightPanel.add(pass);
		}
	}

	public void getFormsel() {
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
		GWT.log("Window.Location.getHostName():" + Window.Location.getHostName());
		GWT.log("Window.Location.getHref():" + Window.Location.getHref());
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
		greetingService.isValid(Window.Location.getHref(), orderID, formdata, callback);
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
