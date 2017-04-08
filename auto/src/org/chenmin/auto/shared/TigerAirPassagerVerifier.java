package org.chenmin.auto.shared;

import org.chenmin.auto.client.api.Verifier;
import org.chenmin.auto.client.api.VerifierException;

public class TigerAirPassagerVerifier implements Verifier{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2023501398081077114L;

	@Override
	public TypeName type() {
		return Verifier.TypeName.FormData;
	}

	@Override
	public String name() {
		return "TigerAirPassagerVerifier";
	}

	@Override
	public String sels() {
		return "form[action=\"/BookFlight/Passengers\"]";
	}
	
	String data;

	@Override
	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String getData() {
		return this.data;
	}

	@Override
	public boolean isValid(String orderID) throws VerifierException {
		return false;
	}

	@Override
	public boolean isMe(String url) {
		return url.contains("tigerair.com");
	}
	
}