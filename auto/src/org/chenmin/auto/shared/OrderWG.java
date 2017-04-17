package org.chenmin.auto.shared;

import java.util.ArrayList;
import java.util.List;

public class OrderWG implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2561159932119332534L;
	
	private List<FlightWG> flight = new ArrayList<FlightWG>();
	
	private List<PassengerWG> passenger = new ArrayList<PassengerWG>();

	public List<FlightWG> getFlight() {
		return flight;
	}

	public void setFlight(List<FlightWG> flight) {
		this.flight = flight;
	}

	public List<PassengerWG> getPassenger() {
		return passenger;
	}

	public void setPassenger(List<PassengerWG> passenger) {
		this.passenger = passenger;
	}

	@Override
	public String toString() {
		return "OrderWG [flight=" + flight + ", passenger=" + passenger + "]";
	}

}
