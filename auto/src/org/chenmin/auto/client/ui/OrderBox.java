/**
 * 
 */
package org.chenmin.auto.client.ui;

import java.util.List;

import org.chenmin.auto.client.api.Factory;
import org.chenmin.auto.client.api.Store;
import org.chenmin.auto.client.api.VerifierException;
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
	
	@UiField
	HTML info;
	
	private Grid fight = new Grid();
	private Grid pass = new Grid();
	
	private void initEvent() {
		GWT.log("initEvent");
//		info.setHTML(Factory.loading+":1s:"+GWT.getModuleBaseForStaticFiles());
//		button.setVisible(false);
		String order = Store.getItem("order");
		if(order!=null){
			orderID.setText(order);
			put(orderID.getText());
		}
	}


	@UiHandler("button")
	void onClick(ClickEvent e) {
		valid();
	}


	private void valid() {
		info(Factory.loading+"正在为你努力验证，订单"+orderID.getText() + "");
		if(Factory.order==null){
			AsyncCallback<OrderWG> callback = new AsyncCallback<OrderWG>() {
				
				@Override
				public void onSuccess(OrderWG result) {
					info("");
					validReal();
				}
				
				@Override
				public void onFailure(Throwable caught) {
					error(caught.getMessage());
				}
			};
			Factory.getOrder(orderID.getText(), callback);
		}else{
			validReal();
		}
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
		if (a == 16) {
			put(orderID.getText());
			Store.setItem("order", orderID.getText());
		}
		if(a==13){
			valid();
		}
	}
	
	public void info(String infoHTML){
		info.setHTML(infoHTML);
	}
	
	public void error(String infoHTML){
		info.setHTML("<font color='red' size='6'>"+infoHTML+"</font>");
	}
	
	public void success(String infoHTML){
		info.setHTML("<font color='#006600' size='6'>"+infoHTML+"</font>");
	}

	public void put(String textToServer) {
		info(Factory.loading+"正在为你努力查询，订单"+orderID.getText() + "");
		fightPanel.clear();
		AsyncCallback<OrderWG> callback = new AsyncCallback<OrderWG>() {

			@Override
			public void onSuccess(OrderWG result) {
				info("");
				putOrder(result);
				orderID.setFocus(true);
				GWT.log("setFocus");
			}

			@Override
			public void onFailure(Throwable caught) {
				info(caught.getMessage());
				orderID.setFocus(true);
				GWT.log("setFocus");
			}
		};
		Factory.getOrder(textToServer, callback);
	}

	public void putOrder(OrderWG result) {
		
		List<FlightWG> flight = result.getFlight();
		List<PassengerWG> passenger = result.getPassenger();
		GWT.log("flight:" + flight);
		GWT.log("passenger:" + passenger);
		if (!flight.isEmpty()) {
			fight = new Grid(flight.size() + 1, 6);
			fight.setHTML(0, 0, "状态");
			fight.setHTML(0, 1, "航司");
			fight.setHTML(0, 2, "出发");
			fight.setHTML(0, 3, "到达");
			fight.setHTML(0, 4, "起飞时间");
			fight.setHTML(0, 5, "航班号");
			int index = 1;
			for (FlightWG f : flight) {
				fight.setHTML(index, 0, "<span id='fight"+(index-1)+"'></span>");
				fight.setHTML(index, 1, f.getCarrier());
				fight.setHTML(index, 2, f.getDepAirportCode());
				fight.setHTML(index, 3, f.getArrAirportCode());
				fight.setHTML(index, 4, "<span id='dep"+(index-1)+"'>"+f.getDepTime()+"</span>" ) ;
				fight.setHTML(index, 5, f.getFlightNum());
				index++;
			}
			fightPanel.add(fight);
		} else {
			info(orderID.getText() + "订单号不存在请查证，如果确认无误咨询王贵哥哥");
		}
		if (!passenger.isEmpty()) {
			pass = new Grid(passenger.size() + 1, 10);
			fight.setHTML(0, 0, "状态");
			pass.setHTML(0, 1, "姓");
			pass.setHTML(0, 2, "名");
			pass.setHTML(0, 3, "性");
			pass.setHTML(0, 4, "类型");
			// pass.setHTML(0, 5, "证件");
			pass.setHTML(0, 6, "证件ID");
			// pass.setHTML(0,7, "证件归宿");
			pass.setHTML(0, 8, "证件生日");
			pass.setHTML(0, 9, "过期日期");
			int index = 1;
			for (PassengerWG f : passenger) {
				pass.setHTML(index, 0, "<span id='pass"+(index-1)+"'></span>");
				pass.setHTML(index, 1, "<span id='passFirst"+(index-1)+"'>"+f.getFirstname()+"</span>");
				pass.setHTML(index, 2, "<span id='passLast"+(index-1)+"'>"+f.getLastname()+"</span>");
				pass.setHTML(index, 3, f.getSexy());
				pass.setHTML(index, 4, f.getType());
				// pass.setHTML(index, 5, f.getCredentialsname());
				pass.setHTML(index, 6, f.getCredentialsID());
				// pass.setHTML(index, 7, f.getNationality());
				pass.setHTML(index, 8, f.getBirthday() );
				pass.setHTML(index, 9, f.getExpirydate() );
				index++;
			}
			fightPanel.add(pass);
		}
	}


	private void validReal() {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("uploadOrderValid:"+caught.getMessage());
			}
		};
		try {
			boolean b = Factory.isValid(orderID.getText());
			if(b){
				success("订单"+orderID.getText() + "，验证通过");
				Factory.log.hide();
				Factory.uploadOrderValid(orderID.getText(), true, Factory.log.getText() + info.getHTML(), callback );
			}else{
				error("订单"+orderID.getText() + "，验证失败");
				Factory.log.error("订单"+orderID.getText() + "，验证失败");
				Factory.uploadOrderValid(orderID.getText(), false, Factory.log.getText()+ info.getHTML(), callback );
			}
		} catch (VerifierException e1) {
			error("订单"+orderID.getText() + "，验证失败：<br />"+e1.getLocalizedMessage());
			Factory.uploadOrderValid(orderID.getText(), false, Factory.log.getText()+ info.getHTML(), callback );
		}
	}
}
