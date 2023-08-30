/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aman.payment.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UtilCore {

	private static final String TRANSACTION_CODE_DATE_FORMAT = "yyDkmsS";
	private static final String SETTLEMENT_CODE_DATE_FORMAT = "yyD";
	private static final String SAVE_FILE_NAMING_FORMAT = "ddMMyyyy";

	private UtilCore() {
		throw new UnsupportedOperationException("Cannot instantiate a UtilCore class");
	}

	public static String generateTransactionCode(String posId, String serviceId, Date dNow) {	
		return posId + "-" + serviceId + new SimpleDateFormat(TRANSACTION_CODE_DATE_FORMAT).format(dNow);
	}
	
	public static String generateSettelmentCode(String posId, String serviceId, Date dNow) {
		return posId + "-" + serviceId + new SimpleDateFormat(SETTLEMENT_CODE_DATE_FORMAT).format(dNow);
	}
	
	public static String saveFolderNamingFormat() {
		return new SimpleDateFormat(SAVE_FILE_NAMING_FORMAT).format(new Date());
	}

	public static String saveFolderNamingFormat(String date)  {
		if(date == null) date = new Date().toString();
		return new SimpleDateFormat(SAVE_FILE_NAMING_FORMAT).format(formatDate(date));
	}
	
	public static Date formatDate(String dateString) {
		List<String> formats = new ArrayList<String>();
		formats.add("yyyy-MM-dd hh:mm:ss");
		formats.add("yyyy-MM-dd");
		formats.add("EEE MMM dd HH:mm:ss zzz yyyy");
		formats.add("ddMMyyyy");
		formats.add("yyD");
		formats.add("dd/MM/yyyy");
		
		for(String format: formats) {
			DateFormat dateFormat = new SimpleDateFormat(format);
			try {
				return dateFormat.parse(dateString);
			} catch (ParseException e) {
				continue;
			}
			
		}
		return null;
	}
	
	public static Date convertStringToDate(String sDate) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static Date convertStringToStartDateFormatSql(String sDate) {
		
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(sDate));
			c.add(Calendar.HOUR, 0);
			c.add(Calendar.MINUTE, 0);
			c.add(Calendar.SECOND, 0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c.getTime();
	}
	
	public static Date convertStringToEndDateFormatSql(String sDate) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(sDate));
			c.add(Calendar.HOUR, 23);
			c.add(Calendar.MINUTE, 59);
			c.add(Calendar.SECOND, 59);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c.getTime();
	}
	
	public static String changeDateFormat(Date date, String stringFormat) {

		return new SimpleDateFormat(stringFormat).format(date);
	}
	
	public static Date addingYears(Date date, int years) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, years);

		return c.getTime();
	}
	
	public static String smsDatetime() {
		Date createdAt = Date.from(Instant.now());
		return new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(createdAt);
	}
	
	public static int getDayOfMonthFromDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
		
	}
	
	public static boolean isProbablyArabic(String s) {
	    for (int i = 0; i < s.length();) {
	        int c = s.codePointAt(i);
	        if (c >= 0x0600 && c <= 0x06E0)
	            return true;
	        i += Character.charCount(c);            
	    }
	    return false;
	  }
	
}
