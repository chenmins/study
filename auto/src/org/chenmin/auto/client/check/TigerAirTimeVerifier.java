package org.chenmin.auto.client.check;

import java.util.Date;
import java.util.List;

import org.chenmin.auto.client.api.Factory;
import org.chenmin.auto.client.api.JS;
import org.chenmin.auto.client.api.Verifier;
import org.chenmin.auto.client.api.VerifierException;
import org.chenmin.auto.shared.FlightWG;

public class TigerAirTimeVerifier implements Verifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7464837675497160315L;

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
		return "TigerAirTimeVerifier";
	}

	@Override
	public String sels() {
		return ".price__time";
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
		Factory.log.info("航班时间信息开始核对");
		Factory.log.info(getData());
//    <div class="media-body">
//        <div class="price__time--time">6:05AM</div><span>Thu, Apr 20</span>
//    </div>
		String media_body = JS.matcherOne(getData(), "<div class=\"media-body\">([A-Za-z0-9]+)</div>", 1);
		String time = JS.matcherOne(media_body, "<div class=\"price__time--time\">([A-Za-z0-9]+)</div>", 1);
		String date = JS.matcherOne(media_body, "<span>([A-Za-z0-9]+)</span>", 1);
		String time_date = time+" "+date;
//		6:05AM Thu, Apr 20
//		h:mm a EEE, MMM d
		List<FlightWG> ft = Factory.order.getFlight();
		for(FlightWG fw:ft){
			Date dtime = fw.getDepTime();
			String dt = Factory.sdf_hmmd.format(dtime);
			String ddtime = Factory.sdf_ymdhm.format(dtime);
			Factory.log.info("ddtime:"+ddtime);
			Factory.log.info("time_date:"+time_date+",dt:"+dt);
			if(time_date.equals(dt)){
				Factory.log.info("航班路线时间信息ok");
				return true;
			}
		}
		return false;
	}

}
