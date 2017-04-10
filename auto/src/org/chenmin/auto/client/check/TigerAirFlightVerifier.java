package org.chenmin.auto.client.check;

import org.chenmin.auto.client.api.Factory;
import org.chenmin.auto.client.api.Verifier;
import org.chenmin.auto.client.api.VerifierException;

public class TigerAirFlightVerifier implements Verifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2003787369494432662L;

	@Override
	public boolean isMe(String url) {
		return url.contains("tigerair.com");
	}

	@Override
	public TypeName type() {
		return TypeName.HTML;
	}

	@Override
	public String name() {
		return "TigerAirFlightVerifier";
	}

	@Override
	public String sels() {
		return ".price__flight";
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
		if(Factory.order.getFlight().isEmpty()){
			throw new VerifierException("订单不存在，无法校验");
		}
		if(this.data==null||this.data.isEmpty()){
			throw new VerifierException("表单数据不存在，无法校验");
		}
		Factory.log.info("航班路线信息开始核对");
		Factory.log.info(getData());
		return false;
	}

}
