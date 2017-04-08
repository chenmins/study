/**
 * 
 */
package org.chenmin.auto.client.ui;

import java.util.List;

import org.chenmin.auto.client.api.Factory;
import org.chenmin.auto.client.api.Store;
import org.chenmin.auto.shared.FlightWG;
import org.chenmin.auto.shared.OrderWG;
import org.chenmin.auto.shared.PassengerWG;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Chenmin
 *
 */
public class OrderBox extends Composite {

	private static OrderBoxUiBinder uiBinder = GWT.create(OrderBoxUiBinder.class);

	interface OrderBoxUiBinder extends UiBinder<Widget, OrderBox> {
	}

	/**
	 * Because this class has a default constructor, it can be used as a binder
	 * template. In other words, it can be used in other *.ui.xml files as
	 * follows: <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder" xmlns:g=
	 * "urn:import:**user's package**"
	 * > <g:**UserClassName**>Hello!</g:**UserClassName> </ui:UiBinder> Note
	 * that depending on the widget that is used, it may be necessary to
	 * implement HasHTML instead of HasText.
	 */
	public OrderBox() {
		initWidget(uiBinder.createAndBindUi(this));
		initEvent();
	}
	@UiField
	Button button;
	
	@UiField
	TextBox orderID;
	
	@UiField
	VerticalPanel fightPanel;
	
	private Grid fight = new Grid();
	private Grid pass = new Grid();
	
	private void initEvent() {
		GWT.log("initEvent");
		
		String order = Store.getItem("order");
		if(order!=null){
			orderID.setText(order);
			put(orderID.getText());
		}
	}


	@UiHandler("button")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}

	@UiHandler("orderID")
	public void onChange(ChangeEvent event) {
		put(orderID.getText());
		Store.setItem("order", orderID.getText());
	}

	@UiHandler("orderID")
	public void onKeyUp(KeyUpEvent event) {
		int a = event.getNativeKeyCode();
		GWT.log("getNativeKeyCode:" + a);
		if (a == 13) {
			put(orderID.getText());
			Store.setItem("order", orderID.getText());
		}
	}

	public void put(String textToServer) {
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
		Factory.getOrder(textToServer, callback);
	}

	public void putOrder(OrderWG result) {
		fightPanel.clear();
		List<FlightWG> flight = result.getFlight();
		List<PassengerWG> passenger = result.getPassenger();
		GWT.log("flight:" + flight);
		GWT.log("passenger:" + passenger);
		if (!flight.isEmpty()) {
			fight = new Grid(flight.size() + 1, 5);
			fight.setHTML(0, 0, "航司");
			fight.setHTML(0, 1, "出发");
			fight.setHTML(0, 2, "到达");
			fight.setHTML(0, 3, "起飞时间");
			fight.setHTML(0, 4, "航班号");
			int index = 1;
			for (FlightWG f : flight) {
				fight.setHTML(index, 0, f.getCarrier());
				fight.setHTML(index, 1, f.getDepAirportCode());
				fight.setHTML(index, 2, f.getArrAirportCode());
				fight.setHTML(index, 3, Factory.sdf_ymdhm.format(f.getDepTime()));
				fight.setHTML(index, 4, f.getFlightNum());
				index++;
			}
			fightPanel.add(fight);
		} else {
			fightPanel.add(new HTML(orderID.getText() + "订单不存在，请查证"));
		}
		if (!passenger.isEmpty()) {
			pass = new Grid(passenger.size() + 1, 9);
			pass.setHTML(0, 0, "firstname");
			pass.setHTML(0, 1, "lastname");
			pass.setHTML(0, 2, "sexy");
			pass.setHTML(0, 3, "type");
			// pass.setHTML(0, 4, "证件");
			pass.setHTML(0, 5, "证件ID");
			// pass.setHTML(0, 6, "证件归宿");
			pass.setHTML(0, 7, "证件生日");
			pass.setHTML(0, 8, "过期日期");
			int index = 1;
			for (PassengerWG f : passenger) {
				pass.setHTML(index, 0, f.getFirstname());
				pass.setHTML(index, 1, f.getLastname());
				pass.setHTML(index, 2, f.getSexy());
				pass.setHTML(index, 3, f.getType());
				// pass.setHTML(index, 4, f.getCredentialsname());
				pass.setHTML(index, 5, f.getCredentialsID());
				// pass.setHTML(index, 6, f.getNationality());
				pass.setHTML(index, 7, Factory.sdf_ymd.format(f.getBirthday()));
				pass.setHTML(index, 8, Factory.sdf_ymd.format(f.getExpirydate()));
				index++;
			}
			fightPanel.add(pass);
		}
	}
}
