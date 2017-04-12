package org.chenmin.auto.client.check;

import java.util.List;

import org.chenmin.auto.client.api.Factory;
import org.chenmin.auto.client.api.JS;
import org.chenmin.auto.client.api.Verifier;
import org.chenmin.auto.client.api.VerifierBean;
import org.chenmin.auto.client.api.VerifierException;
import org.chenmin.auto.shared.FlightWG;

public class TigerAirFlightVerifier implements Verifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2003787369494432662L;

	@Override
	public boolean isMe(String url) {
		return url.contains("tigerair")||url.contains("flyscoot")||url.contains("127");
	}
	
	private VerifierBean[] verifierBean;

	public void setVerifierBean(VerifierBean[] verifierBean) {
		this.verifierBean = verifierBean;
	}

	public VerifierBean[] getVerifierBean() {
		return verifierBean;
	}

	public TigerAirFlightVerifier() {
		this.verifierBean =  new VerifierBean[]{
				new VerifierBean("f1","#booking_summary_display > div > div > div > div:nth-child(1) > div > div.price__inner > div.media.price__flight > div.media-body",Verifier.TypeName.HTML),
				new VerifierBean("t1","#booking_summary_display > div > div > div > div:nth-child(1) > div > div.price__inner > div.media.price__flight > div.media-right",Verifier.TypeName.HTML),
				new VerifierBean("f2","#booking_summary_display > div > div > div > div:nth-child(2) > div > div.price__inner > div.media.price__flight > div.media-body",Verifier.TypeName.HTML),
				new VerifierBean("t2","#booking_summary_display > div > div > div > div:nth-child(2) > div > div.price__inner > div.media.price__flight > div.media-right",Verifier.TypeName.HTML),
				new VerifierBean("time1","#booking_summary_display > div > div > div > div:nth-child(1) > div > div.price__inner > div.media.price__time > div.media-body > div",Verifier.TypeName.HTML),
				new VerifierBean("date1","#booking_summary_display > div > div > div > div:nth-child(1) > div > div.price__inner > div.media.price__time > div.media-body > span",Verifier.TypeName.HTML),
				new VerifierBean("time2","#booking_summary_display > div > div > div > div:nth-child(2) > div > div.price__inner > div.media.price__time > div.media-body > div",Verifier.TypeName.HTML),
				new VerifierBean("date2","#booking_summary_display > div > div > div > div:nth-child(2) > div > div.price__inner > div.media.price__time > div.media-body > span",Verifier.TypeName.HTML),
				};
	}

	@Override
	public boolean isValid() throws VerifierException {
		if(Factory.order.getFlight().isEmpty()){
			throw new VerifierException("订单不存在，无法校验");
		}
		Factory.log.info("航班路线信息开始核对");
		VerifierBean[] vb = getVerifierBean() ;
		String f1=vb[0].getData().toString();
		String t1=vb[1].getData().toString();
		String f2=vb[2].getData().toString();
		String t2=vb[3].getData().toString();
		String time1=vb[4].getData().toString();
		String date1=vb[5].getData().toString();
		String time2=vb[6].getData().toString();
		String date2=vb[7].getData().toString();
		
		List<FlightWG> ft = Factory.order.getFlight();
		for(int i=0;i<ft.size();i++){
			JS.setHtml("#fight"+i,Factory.no);
		}
		int index=0;
		for(FlightWG fw:ft){
			String time_date = time1+" "+date1;
			String dt =  fw.getDepTime();
			String ff = fw.getDepAirportCode();
			String tt = fw.getArrAirportCode();
			Factory.log.info("f1:"+f1+",ff:"+ff);
			Factory.log.info("t1:"+t1+",tt:"+tt);
			Factory.log.info("time_date:"+time_date+",dt:"+dt);
			if(ff.equals(f1)&&tt.equals(t1)&&time_date.equals(dt)){
				Factory.log.info("航班路线信息ok");
				JS.setHtml("#fight"+index,Factory.yes);
				index++;
			}
		}
		for(FlightWG fw:ft){
			String time_date = time2+" "+date2;
//			Date dtime = fw.getDepTime();
//			String dt = Factory.sdf_hmmd.format(dtime);
			String dt =  fw.getDepTime();
			String ff = fw.getDepAirportCode();
			String tt = fw.getArrAirportCode();
			Factory.log.info("f2:"+f2+",ff:"+ff);
			Factory.log.info("t2:"+t2+",tt:"+tt);
			Factory.log.info("time_date:"+time_date+",dt:"+dt);
			if(ff.equals(f2)&&tt.equals(t2)&&time_date.equals(dt)){
				Factory.log.info("航班路线信息ok");
				JS.setHtml("#fight"+index,Factory.yes);
				index++;
			}
		}
		int size = Factory.order.getFlight().size();
		boolean b = index==size;
		if(index<size)
			throw new VerifierException("只有"+index+"个航班验证通过，请认真填写");
		return b;
	}
	
	

}
