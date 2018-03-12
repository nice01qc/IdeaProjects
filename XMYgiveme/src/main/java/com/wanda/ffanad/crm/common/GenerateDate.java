package com.wanda.ffanad.crm.common;

import java.text.*;
import java.util.Date;
import java.util.TreeMap;

public class GenerateDate {
	String dateFormat = "yyyy-MM-dd";
	SimpleDateFormat format = new SimpleDateFormat(dateFormat);
	
	/*
	public static void main(String[] args) {
		GenerateDate t = new GenerateDate();
		
		String date1 = "2012-02-26";
		String date2 = "2012-03-04";
		
		t.process(date1, date2);
	}
	*/

	public TreeMap process(String date1, String date2){
		TreeMap datamap = new TreeMap();
		if(date1.equals(date2)){
		    System.out.println("两个日期相等!");	
		    return datamap;
		}
		
		String tmp;
		if(date1.compareTo(date2) >= 0){  //确保 date1的日期不晚于date2
			tmp = date1; date1 = date2; date2 = tmp;
		}
		
		tmp = format.format(str2Date(date1).getTime());
		
        int num = 0; 
        while(tmp.compareTo(date2) <= 0){        	        
        	System.out.println(tmp);    
        	datamap.put(tmp, 0);
        	num++;
        	tmp = format.format(str2Date(tmp).getTime() + 3600*24*1000);
        }
        
        if(num == 0) {
			System.out.println("两个日期相邻!");
		}
        return datamap;
	}

private Date str2Date(String str) {
		if (str == null) return null;
		
		try {
			return format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
