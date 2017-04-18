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
				//往：f1起飞地点,t1到达地点,time1起飞时间,date1起飞日期；
				//返：f2起飞地点,t2到达地点,time2到达时间,date2到达日期；
				//中转1段：zf1起飞地点,zt1到达地点,ztime1起飞时间,zdate1起飞日期；
				//中转2段：Zf2起飞地点,zt2到达地点,ztime2到达时间,zdate2到达日期；
				new VerifierBean("f1","#booking_summary_display > div > div > div > div:nth-child(1) > div > div.price__inner > div.media.price__flight > div.media-body",Verifier.TypeName.HTML),
				new VerifierBean("t1","#booking_summary_display > div > div > div > div:nth-child(1) > div > div.price__inner > div.media.price__flight > div.media-right",Verifier.TypeName.HTML),
				new VerifierBean("f2","#booking_summary_display > div > div > div > div:nth-child(2) > div > div.price__inner > div.media.price__flight > div.media-body",Verifier.TypeName.HTML),
				new VerifierBean("t2","#booking_summary_display > div > div > div > div:nth-child(2) > div > div.price__inner > div.media.price__flight > div.media-right",Verifier.TypeName.HTML),
				new VerifierBean("time1","#booking_summary_display > div > div > div > div:nth-child(1) > div > div.price__inner > div.media.price__time > div.media-body > div",Verifier.TypeName.HTML),
				new VerifierBean("date1","#booking_summary_display > div > div > div > div:nth-child(1) > div > div.price__inner > div.media.price__time > div.media-body > span",Verifier.TypeName.HTML),
				new VerifierBean("time2","#booking_summary_display > div > div > div > div:nth-child(2) > div > div.price__inner > div.media.price__time > div.media-body > div",Verifier.TypeName.HTML),
				new VerifierBean("date2","#booking_summary_display > div > div > div > div:nth-child(2) > div > div.price__inner > div.media.price__time > div.media-body > span",Verifier.TypeName.HTML),
				new VerifierBean("zf1","#booking_summary_display > div > div > div > div:nth-child(1) > div > div.price__inner > div:nth-child(3) > div.media-body",Verifier.TypeName.HTML),
				new VerifierBean("zt1","#booking_summary_display > div > div > div > div:nth-child(1) > div > div.price__inner > div:nth-child(3) > div.media-right",Verifier.TypeName.HTML),
				new VerifierBean("zf2","#booking_summary_display > div > div > div > div:nth-child(1) > div > div.price__inner > div:nth-child(7) > div.media-body",Verifier.TypeName.HTML),
				new VerifierBean("zt2","#booking_summary_display > div > div > div > div:nth-child(1) > div > div.price__inner > div:nth-child(7) > div.media-right",Verifier.TypeName.HTML),
				new VerifierBean("ztime1","#booking_summary_display > div > div > div > div:nth-child(1) > div > div.price__inner > div:nth-child(4) > div.media-body > div",Verifier.TypeName.HTML),
				new VerifierBean("zdate1","#booking_summary_display > div > div > div > div:nth-child(1) > div > div.price__inner > div:nth-child(4) > div.media-body > span",Verifier.TypeName.HTML),
				new VerifierBean("ztime2","#booking_summary_display > div > div > div > div:nth-child(1) > div > div.price__inner > div:nth-child(8) > div.media-body > div",Verifier.TypeName.HTML),
				new VerifierBean("zdate2","#booking_summary_display > div > div > div > div:nth-child(1) > div > div.price__inner > div:nth-child(8) > div.media-body > span",Verifier.TypeName.HTML),		
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
		
		String zf1=vb[8].getData().toString();
		String zt1=vb[9].getData().toString();
		String zf2=vb[10].getData().toString();
		String zt2=vb[11].getData().toString();
		String ztime1=vb[12].getData().toString();
		String zdate1=vb[13].getData().toString();
		String ztime2=vb[14].getData().toString();
		String zdate2=vb[15].getData().toString();
		//将所有航班状态置为“未验证”
		List<FlightWG> ft = Factory.order.getFlight();
		for(int i=0;i<ft.size();i++){
			JS.setHtml("#fight"+i,Factory.no);
		}
		int index=0;
		//判定是单程、往返or中转
		String from1 = ft.get(0).getDepAirportCode();
		String arr1 = ft.get(0).getArrAirportCode();
		boolean dc=false;
		if(ft.size()==1){
			dc=true;
		}
		if(dc==true){
			//处理单程航线校验
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
		}else{
			String from2 = ft.get(1).getDepAirportCode();
			String arr2 = ft.get(1).getArrAirportCode();
			//处理往返
			//wf为true是往返
			//boolean wf=false;
			if(from1.equals(arr2)&&arr1.equals(from2)){
				//wf=true;
				//验证往返第一段航线起飞、到达、时间、日期
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
				//验证往返第二段航线起飞、到达、时间、日期
				for(FlightWG fw:ft){
					String time_date = time2+" "+date2;
//					Date dtime = fw.getDepTime();
//					String dt = Factory.sdf_hmmd.format(dtime);
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
			}
			//中转
			//zf为true是中转
			//boolean zf=false;
			if(arr1.equals(from2)&&!from1.equals(arr2)){
				//zf=true;
				//验证中转的第一段航线起飞、到达、时间、日期
				for(FlightWG fw:ft){
					String time_date = ztime1+" "+zdate1;
					String dt =  fw.getDepTime();
					String ff = fw.getDepAirportCode();
					String tt = fw.getArrAirportCode();
					Factory.log.info("zf1:"+zf1+",ff:"+ff);
					Factory.log.info("zt1:"+zt1+",tt:"+tt);
					Factory.log.info("time_date:"+time_date+",dt:"+dt);
					if(ff.equals(zf1)&&tt.equals(zt1)&&time_date.equals(dt)){
						Factory.log.info("航班路线信息ok");
						JS.setHtml("#fight"+index,Factory.yes);
						index++;
					}
				}
				//验证中转的第二段航线起飞、到达、时间、日期
				for(FlightWG fw:ft){
					String time_date = ztime2+" "+zdate2;
//							Date dtime = fw.getDepTime();
//							String dt = Factory.sdf_hmmd.format(dtime);
					String dt =  fw.getDepTime();
					String ff = fw.getDepAirportCode();
					String tt = fw.getArrAirportCode();
					Factory.log.info("zf2:"+zf2+",ff:"+ff);
					Factory.log.info("zt2:"+zt2+",tt:"+tt);
					Factory.log.info("time_date:"+time_date+",dt:"+dt);
					if(ff.equals(zf2)&&tt.equals(zt2)&&time_date.equals(dt)){
						Factory.log.info("航班路线信息ok");
						JS.setHtml("#fight"+index,Factory.yes);
						index++;
					}
				}
			}
			
		}
		
		return true;
//		int size = Factory.order.getFlight().size();
//		boolean b = index==size;
//		if(index<size)
//			throw new VerifierException("只有"+index+"个航班验证通过，请认真填写");
//		return b;
	}
	
	

}
