package org.chenmin.auto.server;

import java.util.ArrayList;
import java.util.List;

import org.chenmin.auto.client.api.AirLineVerifier;
import org.chenmin.auto.client.api.Verifier;
import org.chenmin.auto.client.api.VerifierException;
import org.chenmin.auto.server.air.TigerAirVerifier;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AirLineVerifilerImpl extends RemoteServiceServlet implements AirLineVerifier {
	static List<AirLineVerifier> airs = new ArrayList<AirLineVerifier>();
	
	static {
		airs.add(new TigerAirVerifier());
	}

	private AirLineVerifier get(String url) {
		for (AirLineVerifier a : airs) {
			if (a.isMe(url))
				return a;
		}
		return null;
	}

	@Override
	public Verifier[] getVerifier(String url) {
		AirLineVerifier airLineVerifier = get(url);
		if (airLineVerifier != null)
			return airLineVerifier.getVerifier(url);
		return null;
	}

	@Override
	public boolean isValid(String url, String orderID, Verifier[] v) throws VerifierException {
		AirLineVerifier airLineVerifier = get(url);
		if (airLineVerifier != null)
			return airLineVerifier.isValid(url, orderID, v);
		return false;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2323799328550165981L;

	@Override
	public boolean isMe(String url) {
		// 不做实现，在分站中实现
		return false;
	}

}
