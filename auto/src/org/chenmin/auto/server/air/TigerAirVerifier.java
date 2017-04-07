package org.chenmin.auto.server.air;

import org.chenmin.auto.client.api.AirLineVerifier;
import org.chenmin.auto.client.api.Verifier;
import org.chenmin.auto.client.api.VerifierException;
import org.chenmin.auto.shared.TigerAirPassagerVerifier;

public class TigerAirVerifier implements AirLineVerifier {

	@Override
	public boolean isMe(String url) {
		return url.contains("tigerair.com");
	}

	@Override
	public Verifier[] getVerifier(String url) {
		return new Verifier[]{new TigerAirPassagerVerifier()};
	}

	@Override
	public boolean isValid(String url, String orderID, Verifier[] vs) throws VerifierException {
		System.out.println("isValid:"+url);
		System.out.println("orderID:"+orderID);
		for(Verifier v:vs){
			System.out.println("name:"+v.name());
			System.out.println("getData:"+v.getData());
			if(v.isValid(orderID)==false)
				return false;
		}
		return true;
	}

}
