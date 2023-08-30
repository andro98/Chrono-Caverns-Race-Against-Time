package com.aman.payment.util;

public class StatusConstant {

	public static final String STATUS_ACTIVE = "Active";
	public static final String STATUS_INACTIVE = "InActive";
	public static final String STATUS_COMPLETED = "Completed";
	public static final String STATUS_CANCELED = "Canceled";
	public static final String STATUS_EXIST = "Exist";
	public static final String STATUS_FINAL = "Final";
	public static final String STATUS_REVIEWED = "Reviewed";
	public static final String STATUS_REVIEWED_CUSTODY = "Reviewed Custody";
	public static final String STATUS_UNDER_REVIEWED_CUSTODY = "Under Reviewed Custody";
	public static final String STATUS_UNDER_REVIEWED = "Under Reviewed";
	public static final String STATUS_COLLECTED_UNDER_REVIEWED = "Collected Under Reviewed";
	public static final String STATUS_HOLD = "Hold";
	public static final String STATUS_HOLD_UNDER_REVIEW = "Hold Under Review";
	public static final String STATUS_PRINTED = "Printed";
	public static final String STATUS_REFUND = "Refund";
	public static final String STATUS_FAIL = "Fail";
	public static final String STATUS_SUCCEED = "Succeed";
	public static final String STATUS_OPEN = "Open";
	public static final String STATUS_CLOSED = "Closed";
	public static final String STATUS_APPROVED = "Approved";
	public static final String STATUS_REJECTED = "Rejected";
	public static final String STATUS_NEW = "New";
	public static final String STATUS_SOLD = "Sold";
	public static final String STATUS_COLLECTED = "Collected";
	public static final String STATUS_DELIVERED = "Delivered";
	public static final String STATUS_INPROGRERSS = "In Progress";
	public static final String STATUS_UNDERDELIVERY = "Underdelivery";
	public static final String STATUS_RECEIVED = "Received";
	public static final String STATUS_REVIEW_REJECTED = "Review Rejected";
	public static final String STATUS_HOLD_SYNCHED = "HoldAndSynched";
	public static final String STATUS_SYNCHED = "Synched";
	public static final String STATUS_NOT_SYNCHED = "Not Synched";
	
	public static final String STATUS_PENDING = "Pending";
	public static final String STATUS_MATCHED = "Matched";
	public static final String STATUS_MODIFIED = "Modified";
	public static final String STATUS_NOT_MATCHED = "Not Matched";
	
	public static final String STATUS_PRINT_OUT = "Print Out";
	
	public static final String PAYMENT_CASH = "Cash";
	public static final String PAYMENT_VISA = "Visa";
	
	public static final String POS = "POS";
	public static final String SER = "SER";
	public static final String LOC = "LOC";
	public static final String MER = "MER";
	//International Insurance Number
	public static final String IIN = "IIN";
	public static final long IIN_5 = 5;
	
	public static final String ROLE_SUPER_ADMIN = "SUPER_ADMIN";
	public static final String ROLE_AGENT = "ROLE_AGENT";
	public static final String ROLE_AGENT_SUPERVISOR = "ROLE_AGENT_SUPERVISOR";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_SUPERADMIN = "ROLE_SUPERADMIN";
	public static final String ROLE_SUPPORT = "ROLE_SUPPORT";
	public static final String ROLE_AREA_MANAGER = "ROLE_AREA_MANAGER";
	public static final String ROLE_INTEGRATION = "ROLE_INTEGRATION";
	public static final String ROLE_SUPERVISOR = "ROLE_SUPERVISOR";
	public static final String ROLE_FINANCE = "ROLE_FINANCE";
	public static final String ROLE_STOREKEEPER = "ROLE_STOREKEEPER";
	public static final String ROLE_PROSECUTOR = "ROLE_PROSECUTOR";
	public static final String ROLE_FINANCIAL_REVIEW = "ROLE_FINANCIAL_REVIEW";

	
	public static final String BOOK_ZAWAG_MUSLIM_ID_8 = "9";
	public static final String BOOK_TALAK_ID_8 = "10";
	public static final String BOOK_TASADOK_ID_8 = "11";
	public static final String BOOK_MORAGAA_ID_8 = "12";
	public static final String BOOK_ZAWAG_MELALY_ID_8 = "13";
	
	public static final String BOOK_ZAWAG_MUSLIM_ID_15 = "19";
	public static final String BOOK_TALAK_ID_15 = "20";
	public static final String BOOK_TASADOK_ID_15 = "21";
	public static final String BOOK_MORAGAA_ID_15 = "22";
	public static final String BOOK_ZAWAG_MELALY_ID_15 = "23";
	
	public static final String BOOK_ZAWAG_MUSLIM_NAME = "دفتر زواج مسلمين";
	public static final String BOOK_TALAK_NAME = "دفتر طلاق";
	public static final String BOOK_TASADOK_NAME = "دفتر تصادق";
	public static final String BOOK_ZAWAG_MELALY_NAME = "دفتر زواج المللى";
	public static final String BOOK_MORAGAA_NAME = "دفتر مراجعة";
	
	public static final String CONTRACT_ZAWAG_MUSLIM_NAME = "عقود زواج مسلمين";
	public static final String CONTRACT_TALAK_NAME = "عقود طلاق";
	public static final String CONTRACT_TASADOK_NAME = "عقود تصادق";
	public static final String CONTRACT_ZAWAG_MELALY_NAME = "عقود زواج المللى";
	public static final String CONTRACT_MORAGAA_NAME = "عقود مراجعة";
	
	public static final String CONTRACT_ZAWAG_MUSLIM_ID = "14";
	public static final String CONTRACT_TALAK_ID = "15";
	public static final String CONTRACT_TASADOK_ID = "16";
	public static final String CONTRACT_MORAGAA_ID = "17";
	public static final String CONTRACT_ZAWAG_MELALY_ID = "18";
	
	public static final String BOOK_QUOTA_DAREBT_DAMGHA = "ضريبة دمغة";
	public static final String BOOK_QUOTA_RASM_TANMYA = "رسم تنمية";
	public static final String BOOK_QUOTA_FAHS_FANY = "فحص فنى";
	public static final String BOOK_QUOTA_TAKLFA = "تكلفة";
	public static final String BOOK_QUOTA_TAHWEEL_RAKMY_AMAN = "تحويل رقمى أمان";
	public static final String BOOK_QUOTA_TAHWEEL_RAKMY_NYABA = "تحويل رقمى النيابة";
	public static final String BOOK_QUOTA_DAREBT_MABEAT = "ضريبة مبيعات";
	
	public static final String CONTRACT_QUOTA_IDAFY = "إضافى";
	public static final String CONTRACT_QUOTA_ABNYT_MHAKM = "أبنية محاكم";
	public static final String CONTRACT_QUOTA_GAMIYA_MAZOUNEEN = "جمعية مأذونيين";
	public static final String CONTRACT_QUOTA_TAMEEN_OSRA = "تأمين أسرة";
	public static final String CONTRACT_QUOTA_MOKRR = "مقرر";
	public static final String CONTRACT_QUOTA_PERCENTAGE_FIRST_THOUSAND = "على الألف الأولى";
	public static final String CONTRACT_QUOTA_PERCENTAGE_REMAINING_AMOUNT = "على باقى المبلغ";
	
	public static final long BOOK_SERVICE_ID = 3;
	public static final long CONTRACT_SERVICE_ID = 4;
}
