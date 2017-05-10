package org.chenmin.auto.client.check;

import org.chenmin.auto.client.api.Verifier;
import org.chenmin.auto.client.api.VerifierBean;
import org.chenmin.auto.client.api.VerifierException;

/**
 * 亚航
 * @author Chenmin
 *
 */
public class AisaAirFlightVerifier implements Verifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3413827377912413839L;

	@Override
	public boolean isMe(String url) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public VerifierBean[] getVerifierBean() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVerifierBean(VerifierBean[] verifierBean) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid() throws VerifierException {
		// TODO Auto-generated method stub
		return false;
	}

}
