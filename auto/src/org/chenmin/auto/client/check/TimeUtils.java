package org.chenmin.auto.client.check;

public class TimeUtils {
	
	public static void main(String[] args) {
		System.out.println(parseTime("11:35下午 周五, 11月 22"));
	}
	public static String parseTime(String param){
//		String param = "11:35下午 周五, 9月 22";
		param = param.replaceAll("下午", "PM").replaceAll("上午", "AM").replaceAll("周一", "Mon").replaceAll("周二", "Tue").replaceAll("周三", "Wed")
		.replaceAll("周四", "Thu").replaceAll("周五", "Fri").replaceAll("周六", "Sat").replaceAll("周日", "Sun")
		.replaceAll("11月", "Nov").replaceAll("12月", "Dec").replaceAll("1月", "Jan").replaceAll("2月", "Feb").replaceAll("3月", "Mar").replaceAll("4月", "Apr")
		.replaceAll("5月", "May").replaceAll("6月", "Jun").replaceAll("7月", "Jul").replaceAll("8月", "Aug")
		.replaceAll("9月", "Sep").replaceAll("10月", "Oct");
		return param;
	}

}
