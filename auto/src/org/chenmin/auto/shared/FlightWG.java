package org.chenmin.auto.shared;

import java.util.Date;

public class FlightWG implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2567159932119332534L;
	
	private String orderID;
	private String depAirportCode;
	private String arrAirportCode;
	private Date depTime;
	private String flightNum;
	private String carrier;
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getDepAirportCode() {
		return depAirportCode;
	}
	public void setDepAirportCode(String depAirportCode) {
		this.depAirportCode = depAirportCode;
	}
	public String getArrAirportCode() {
		return arrAirportCode;
	}
	public void setArrAirportCode(String arrAirportCode) {
		this.arrAirportCode = arrAirportCode;
	}
	public Date getDepTime() {
		return depTime;
	}
	public void setDepTime(Date depTime) {
		this.depTime = depTime;
	}
	public String getFlightNum() {
		return flightNum;
	}
	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	
	@Override
	public String toString() {
		return "FlightWG [orderID=" + orderID + ", depAirportCode=" + depAirportCode + ", arrAirportCode="
				+ arrAirportCode + ", depTime=" + depTime + ", flightNum=" + flightNum + ", carrier=" + carrier + "]";
	}

}
