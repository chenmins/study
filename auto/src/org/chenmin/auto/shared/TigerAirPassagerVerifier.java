package org.chenmin.auto.shared;

import java.util.List;

import org.chenmin.auto.client.api.Factory;
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
		if(Factory.order.getFlight().isEmpty()){
			throw new VerifierException("订单不存在，无法校验");
		}
		if(this.data==null||this.data.isEmpty()){
			throw new VerifierException("表单数据不存在，无法校验");
		}
		Factory.log.info("订单乘客开始核对，以下为表单乘客数据");
		Factory.log.info(getData());
		List<PassengerWG> p = Factory.order.getPassenger();
		Factory.log.info("订单乘客开始核对，以下为订单乘客数据");
		Factory.log.info(p.toString());
		
		return false;
	}

	@Override
	public boolean isMe(String url) {
//		return url.contains("tigerair.com");
		return true;
	}
	
}