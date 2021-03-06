package org.chenmin.auto.client.ui;

import java.util.Date;

import org.chenmin.auto.client.api.Factory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class LogBox extends Composite implements HasText {

	private static LogBoxUiBinder uiBinder = GWT.create(LogBoxUiBinder.class);

	interface LogBoxUiBinder extends UiBinder<Widget, LogBox> {
	}

	public LogBox() {
		initWidget(uiBinder.createAndBindUi(this));
		hide();
	}
	
	public void info(String msg){
		GWT.log(msg);
		String old = getText();
		if(old!=null&&old.length()!=0)
			old+="<br />";
		String newString =Factory.sdf_hm.format(new Date())+ "<font color='black' size='1'>"+msg+"</font>";
		setText(old+newString);
	}
	
	public void clear(){
		GWT.log("log.clear()");
		setText("");
	}
	
	public void error(String msg){
		GWT.log(msg);
		String old = getText();
		if(old!=null&&old.length()!=0)
			old+="<br />";
		String newString =Factory.sdf_hm.format(new Date())+ "<font color='red' size='1'>"+msg+"</font>";
		setText(old+newString);
	}

	@UiField
	HTML log;
	
	@UiField
	ScrollPanel scroll;

	@UiField
	Button hideButton;
	
	@UiField
	Button showButton;
	
	@UiHandler("hideButton")
	void onHide(ClickEvent e) {
		Factory.log.clear();
		hide();
	}

	public void hide() {
		this.scroll.setVisible(false);
	}
	
	@UiHandler("showButton")
	void onShow(ClickEvent e) {
		show();
	}

	public void show() {
		this.scroll.setVisible(true);
	}
	
	public void setText(String text) {
		show();
		log.setHTML(text);
		scroll.scrollToBottom();
	}

	public String getText() {
		return log.getHTML();
	}

}
