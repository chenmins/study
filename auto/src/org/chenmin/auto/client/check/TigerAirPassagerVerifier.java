package org.chenmin.auto.client.check;

import java.util.List;

import org.chenmin.auto.client.api.Factory;
import org.chenmin.auto.client.api.JS;
import org.chenmin.auto.client.api.Verifier;
import org.chenmin.auto.client.api.VerifierBean;
import org.chenmin.auto.client.api.VerifierException;
import org.chenmin.auto.shared.PassengerWG;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class TigerAirPassagerVerifier implements Verifier{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2023501398081077114L;

	public TigerAirPassagerVerifier() {
		this.verifierBean = new VerifierBean[]{new VerifierBean("TigerAirPassager","form[action=\"/BookFlight/Passengers\"]",Verifier.TypeName.FormData)};
	}
	 
	private VerifierBean[] verifierBean;

	public void setVerifierBean(VerifierBean[] verifierBean) {
		this.verifierBean = verifierBean;
	}

	public VerifierBean[] getVerifierBean() {
		return verifierBean;
	}

	@Override
	public boolean isValid() throws VerifierException {
		if(Factory.order.getFlight().isEmpty()){
			throw new VerifierException("订单不存在，无法校验");
		}
		String data  = getVerifierBean()[0].getData().toString();
		if(data==null||data.isEmpty()){
			throw new VerifierException("表单数据不存在，无法校验");
		}
		Factory.log.info("订单乘客开始核对，以下为表单乘客数据");
		Factory.log.info(data);
		List<PassengerWG> p = Factory.order.getPassenger();
		Factory.log.info("订单乘客开始核对，以下为订单乘客数据");
		Factory.log.info(p.toString());
		JSONValue json = JSONParser.parseStrict(data);
		JSONObject o = json.isObject();
//		"insuranceQuoteInput.ParticipantCount": "1",
		if(o.containsKey("insuranceQuoteInput.ParticipantCount")){
			Factory.log.info("订单乘客人数核对，以下为表单乘客人数");
			String participantCount = o.get("insuranceQuoteInput.ParticipantCount").isString().	stringValue() ;
			Factory.log.info(participantCount);
			Factory.log.info("订单乘客人数核对，以下为订单乘客人数");
			Factory.log.info(""+p.size());
			if(!participantCount.equals(""+p.size())){
				throw new VerifierException("订单人数和填写人数不匹配，无法校验");
			}
		}
		int validCount = 0;
		for(int j=0;j<p.size();j++){
			//一层层 匹配
			PassengerWG pw = p.get(j);
			Factory.log.info("开始匹配："+pw.getFirstname()+" "+pw.getLastname());
			for(int i=0;i<p.size();i++){
//		"revPassengersInput.PassengerInfantModels.PassengersInfo[0].Title": "MRS",
				String Title = o.get("revPassengersInput.PassengerInfantModels.PassengersInfo["+i+"].Title").isString().stringValue();
//		"revPassengersInput.PassengerInfantModels.PassengersInfo[0].First": "min",
				String First = o.get("revPassengersInput.PassengerInfantModels.PassengersInfo["+i+"].First").isString().stringValue();
//		"revPassengersInput.PassengerInfantModels.PassengersInfo[0].Last": "chen",
				String Last = o.get("revPassengersInput.PassengerInfantModels.PassengersInfo["+i+"].Last").isString().stringValue();
//		"revPassengersInput.PassengerInfantModels.PassengersInfo[0].DayOfBirth": "14",
				String DayOfBirth = o.get("revPassengersInput.PassengerInfantModels.PassengersInfo["+i+"].DayOfBirth").isString().stringValue();
//		"revPassengersInput.PassengerInfantModels.PassengersInfo[0].MonthOfBirth": "3",
				String MonthOfBirth = o.get("revPassengersInput.PassengerInfantModels.PassengersInfo["+i+"].MonthOfBirth").isString().stringValue();
//		"revPassengersInput.PassengerInfantModels.PassengersInfo[0].YearOfBirth": "1994",
				String YearOfBirth = o.get("revPassengersInput.PassengerInfantModels.PassengersInfo["+i+"].YearOfBirth").isString().stringValue();
				//1.核对姓名
				Factory.log.info("核对姓名");
				String LastName = pw.getFirstname();
				String FirstName =pw.getLastname();
				Factory.log.info("First:"+First+",FirstName:"+FirstName);
				Factory.log.info("Last:"+Last+",LastName:"+LastName);
				if(First.equals(FirstName)&&Last.equals(LastName)){
					//2.核对生日
					Factory.log.info("核对生日");
					String dob = DateTimeFormat.getFormat("dd").format(pw.getBirthday()) ;
					String mob = DateTimeFormat.getFormat("MM").format(pw.getBirthday()) ;
					if(mob.startsWith("0"))
						mob = mob.substring(1);
					String yob = DateTimeFormat.getFormat("yyyy").format(pw.getBirthday()) ;
					Factory.log.info("DayOfBirth:"+DayOfBirth+",dob:"+dob);
					Factory.log.info("MonthOfBirth:"+MonthOfBirth+",mob:"+mob);
					Factory.log.info("YearOfBirth:"+YearOfBirth+",yob:"+yob);
					if(dob.equals(DayOfBirth)&&mob.equals(MonthOfBirth)&&yob.equals(YearOfBirth)){
						//3.核对性别,MR,MRS,MS,MDM
						Factory.log.info("核对性别");
						String sex = pw.getSexy();
						Factory.log.info("Title:"+Title+",sex:"+sex);
						if(sex.equals("男")){
							if(Title.equals("MR")){
								JS.setHtml("#pass"+validCount,Factory.yes);
								validCount++;
								Factory.log.info(validCount+"个乘客核对成功");
								break;
							}
						}else{
							if(Title.equals("MRS")||Title.equals("MS")||Title.equals("MDM")){
								JS.setHtml("#pass"+validCount,Factory.yes);
								validCount++;
								Factory.log.info(validCount+"个乘客核对成功");
								break;
							}
						}
					}
				}
			}
		}
		boolean b = validCount==p.size();
		if(validCount==0)
			throw new VerifierException("只有"+validCount+"人校验成功，请认真填写");
		if(!b)
			throw new VerifierException("只有"+validCount+"人校验成功");
		return b;
	}

	@Override
	public boolean isMe(String url) {
		return url.contains("tigerair");
//		return true;
	}

}