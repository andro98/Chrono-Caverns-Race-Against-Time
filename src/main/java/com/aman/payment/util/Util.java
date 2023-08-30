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
package com.aman.payment.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.lang.StringUtils;

import com.aman.payment.auth.model.User;

public class Util {

	private static final String COLON = ":";
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String DATE_MATCH_FORMAT = "\\d{4}-\\d{2}-\\d{2}";
	private static final String DATE_ANGULAR_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	public static final String DATE_FORMAT_FOR_FRONT_END = "yyyy-MM-dd";
	public static final String DATE_FORMAT_FOR_FINAL = "dd MMMM yyyy";

	private Util() {
		throw new UnsupportedOperationException("Cannot instantiate a UtilCore class");
	}

	public static String generateRandomUuid() {
		return UUID.randomUUID().toString();
	}

	public static String getInsuranceFormPath(String settlementCode, String basePackagePath) {

//    	String mainFolderNameWithPOSCode = transactionCode.substring(0, 6);
//		String subMainFolderNameWithJulianDate = transactionCode.substring(6, 11);
		return basePackagePath + "/" + settlementCode.split("-")[0] + "/" + settlementCode.split("-")[1];

	}

	/**
	 * If the input parameter is integer, then converts the String input to Integer
	 * output else return null
	 *
	 * @param input
	 * @return
	 */
	public static Integer isInteger(String input) {
		Integer output = 0;
		if (StringUtils.isNumeric(input)) {
			output = Integer.parseInt(input);
		}
		return output;
	}

	/**
	 * Convert date from String to Date object
	 *
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date matchDate(String date) {
		try {
			if (hasFormat(date)) {
				DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
				return formatter.parse(date);
			}
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return null;
	}

	/**
	 * Check if the string has a Date format
	 *
	 * @param date
	 * @return
	 */
	private static boolean hasFormat(String date) {
		return date.matches(DATE_MATCH_FORMAT);
	}

	/**
	 * Gets random user names
	 *
	 * @return
	 */
//    public static String getRandomUsers() {
//        String[] wordsAsArray = NAMES.split(COLON);
//        int index = new Random().nextInt(wordsAsArray.length);
//
//        return wordsAsArray[index];
//    }

	public static String dateFormat(Instant date) {

		return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Date.from(date));
	}

	public static String dateFormat(Date date) {

		return new SimpleDateFormat("dd/MM/yyyy hh.mm aa").format(date);
	}

	public static String dateFormat() {

		return new SimpleDateFormat("ddMMyyyy").format(new Date());
	}

	public static String dateFormatReport(Date date) {

		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}

	public static String dateFormatSql(Date date) {

		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static String dateFormatReceipt(Date date) {

		return new SimpleDateFormat("yyyy/MM/dd").format(date);
	}

	public static String timeFormatReceipt(Date date) {

		return new SimpleDateFormat("h:mm").format(date);
	}

	public static String externalDateFormat(Date date) {

		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static Date getFirstDayOfMonth() {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal.getTime();
	}

	public static String convertDateToString(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		String dateS = "";
		try {
			dateS = dateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateS;
	}

	public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(n);
		SecretKey key = keyGenerator.generateKey();
		return key;
	}

	public static IvParameterSpec generateIv() {
		byte[] iv = new byte[16];
		new SecureRandom().nextBytes(iv);
		return new IvParameterSpec(iv);
	}

	public static Date getEndSubscriptionDate(int days) {

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy hh:mm");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, -days);
		return c.getTime();

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

	public static Double calculateFirstThousandAndRemainingContractPaid(double contractPaidAmount, double percentageFirstThousandQuota) {

		double perecntFirstThousand = 0;

		if (contractPaidAmount <= 100) {
			perecntFirstThousand = (percentageFirstThousandQuota / 100) * contractPaidAmount;
		} else if (contractPaidAmount > 100) {
			perecntFirstThousand = (percentageFirstThousandQuota / 100) * 100;
		}

		return perecntFirstThousand;

	}
	
	public static Double calculateSecondRemainingContractPaid(double contractPaidAmount, double percentageRemainingQuota) {

		double perecntRemainingAmount = 0;

		if (contractPaidAmount > 100) {
			double calculateSecondContractFees = contractPaidAmount - 100;
			perecntRemainingAmount = (percentageRemainingQuota / 100) * calculateSecondContractFees;
		}

		return perecntRemainingAmount;

	}
	
	public static String getCreatedByFullName(User user) {
		String createdByFirstName = user.getFirstName()!=null?user.getFirstName():"";
		String createdByLastName = user.getLastName()!=null?user.getLastName():"";
		return createdByFirstName+" "+createdByLastName;
	}
	
	public static boolean isValidationAuthCode(String paymentCode) {
		
		String specialCharacters = "[" + "-/@#!*$%^&.'_+={}()"+ "]+" ;
		
		if(paymentCode == null || paymentCode.isEmpty()
				|| paymentCode.equals("0")
				|| paymentCode.matches(specialCharacters)) {
			return false;
		}
		return true;
	}
	
	public static Date convertStringNationalIdToDOB(String sDate) {
		String century = sDate.substring(0, 1);
		if(century.equals("2")) {
			century = "19";
		}else if(century.equals("3")) {
			century = "20";
		}
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat("yyyyMMdd").parse(century+sDate.substring(1, 7)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c.getTime();
	}
	
	public static Date addingYears(int years) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, years);
		return c.getTime();
	}
	
	public static int calculateAge(Date dateOfBirth) {

		Calendar today = Calendar.getInstance();
//		today.setTime(insuranceDate);

		Calendar birthDate = Calendar.getInstance();

		int age = 0;

		birthDate.setTime(dateOfBirth);
		if (birthDate.after(today)) {
			throw new IllegalArgumentException("Can't be born in the future");
		}

		return age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
	}
	
	public static boolean validateApplicantAge(String nationalId) {

		Date applicantDOB = convertStringNationalIdToDOB(nationalId);

		int age = calculateAge(applicantDOB);

		if (age >= 18) {
			return true;
		} else {
			return false;
		}

	}

	public static String getCurrentYearTwoDigits() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Format the current year as two digits (e.g., "23" for the year 2023)
        String currentYearTwoDigits = currentDate.format(DateTimeFormatter.ofPattern("yy"));

        return currentYearTwoDigits;
    }
	
	

}
