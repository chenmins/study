package org.chenmin.auto.client.check;

import java.util.List;

import org.chenmin.auto.client.api.Factory;
import org.chenmin.auto.client.api.JS;
import org.chenmin.auto.client.api.Verifier;
import org.chenmin.auto.client.api.VerifierException;
import org.chenmin.auto.shared.FlightWG;

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
//		 <div class="media-body">SIN</div>
//         <div class="media-right">HKG</div><span class="icon ico-plane"></span>
		String f = JS.matcherOne(getData(), "<div class=\"media-body\">([A-Za-z0-9]+)</div>", 1);
		String t = JS.matcherOne(getData(), "<div class=\"media-right\">([A-Za-z0-9]+)</div>", 1);
		List<FlightWG> ft = Factory.order.getFlight();
		for(FlightWG fw:ft){
			String ff = fw.getDepAirportCode();
			String tt = fw.getArrAirportCode();
			Factory.log.info("f:"+f+",ff:"+ff);
			Factory.log.info("t:"+t+",tt:"+tt);
			if(ff.equals(f)&&tt.equals(t)){
				Factory.log.info("航班路线信息ok");
				return true;
			}
		}
		return false;
	}
	
	

}
