package com.aman.payment.maazoun.model.dto;

import org.json.JSONObject;

public class MaazounBookAuditSectorDTO {

	private String bookZawagMuslimName;
	private String bookTalakName;
	private String bookTasadokName;
	private String bookZawagMelalyName;
	private String bookMoragaaName;

	private Long bookZawagMuslimTransactions8 =(long)0;
	private Long bookTalakTransactions8 =(long)0;
	private Long bookTasadokTransactions8 =(long)0;
	private Long bookZawagMelalyTransactions8 =(long)0;
	private Long bookMoragaaTransactions8 =(long)0;
	
	private Long bookZawagMuslimTransactions15 =(long)0;
	private Long bookTalakTransactions15 =(long)0;
	private Long bookTasadokTransactions15 =(long)0;
	private Long bookZawagMelalyTransactions15 =(long)0;
	private Long bookMoragaaTransactions15 =(long)0;
	
	private Long totalSectorTransactions  =(long)0;

	private Double bookZawagMuslimAmount8 = 0.0;
	private Double bookTalakAmount8 = 0.0;
	private Double bookTasadokAmount8 = 0.0;
	private Double bookZawagMelalyAmount8 = 0.0;
	private Double bookMoragaaAmount8 = 0.0;
	
	private Double bookZawagMuslimAmount15 = 0.0;
	private Double bookTalakAmount15 = 0.0;
	private Double bookTasadokAmount15 = 0.0;
	private Double bookZawagMelalyAmount15 = 0.0;
	private Double bookMoragaaAmount15 = 0.0;
	
	private Double totalSectorAmount = 0.0;

	private String cityName;
	private String cityId;
	private String locationName;
	private String locationId;
	private String sectorId;
	private String sectorName;
	private String courtName;

	public MaazounBookAuditSectorDTO() {
		super();
	}

	public String getBookZawagMuslimName() {
		return bookZawagMuslimName;
	}

	public void setBookZawagMuslimName(String bookZawagMuslimName) {
		this.bookZawagMuslimName = bookZawagMuslimName;
	}

	public String getBookTalakName() {
		return bookTalakName;
	}

	public void setBookTalakName(String bookTalakName) {
		this.bookTalakName = bookTalakName;
	}

	public String getBookTasadokName() {
		return bookTasadokName;
	}

	public void setBookTasadokName(String bookTasadokName) {
		this.bookTasadokName = bookTasadokName;
	}

	public String getBookZawagMelalyName() {
		return bookZawagMelalyName;
	}

	public void setBookZawagMelalyName(String bookZawagMelalyName) {
		this.bookZawagMelalyName = bookZawagMelalyName;
	}

	public String getBookMoragaaName() {
		return bookMoragaaName;
	}

	public void setBookMoragaaName(String bookMoragaaName) {
		this.bookMoragaaName = bookMoragaaName;
	}

	public Long getBookZawagMuslimTransactions8() {
		return bookZawagMuslimTransactions8;
	}

	public void setBookZawagMuslimTransactions8(Long bookZawagMuslimTransactions8) {
		this.bookZawagMuslimTransactions8 = bookZawagMuslimTransactions8;
	}

	public Long getBookTalakTransactions8() {
		return bookTalakTransactions8;
	}

	public void setBookTalakTransactions8(Long bookTalakTransactions8) {
		this.bookTalakTransactions8 = bookTalakTransactions8;
	}

	public Long getBookTasadokTransactions8() {
		return bookTasadokTransactions8;
	}

	public void setBookTasadokTransactions8(Long bookTasadokTransactions8) {
		this.bookTasadokTransactions8 = bookTasadokTransactions8;
	}

	public Long getBookZawagMelalyTransactions8() {
		return bookZawagMelalyTransactions8;
	}

	public void setBookZawagMelalyTransactions8(Long bookZawagMelalyTransactions8) {
		this.bookZawagMelalyTransactions8 = bookZawagMelalyTransactions8;
	}

	public Long getBookMoragaaTransactions8() {
		return bookMoragaaTransactions8;
	}

	public void setBookMoragaaTransactions8(Long bookMoragaaTransactions8) {
		this.bookMoragaaTransactions8 = bookMoragaaTransactions8;
	}

	public Long getBookZawagMuslimTransactions15() {
		return bookZawagMuslimTransactions15;
	}

	public void setBookZawagMuslimTransactions15(Long bookZawagMuslimTransactions15) {
		this.bookZawagMuslimTransactions15 = bookZawagMuslimTransactions15;
	}

	public Long getBookTalakTransactions15() {
		return bookTalakTransactions15;
	}

	public void setBookTalakTransactions15(Long bookTalakTransactions15) {
		this.bookTalakTransactions15 = bookTalakTransactions15;
	}

	public Long getBookTasadokTransactions15() {
		return bookTasadokTransactions15;
	}

	public void setBookTasadokTransactions15(Long bookTasadokTransactions15) {
		this.bookTasadokTransactions15 = bookTasadokTransactions15;
	}

	public Long getBookZawagMelalyTransactions15() {
		return bookZawagMelalyTransactions15;
	}

	public void setBookZawagMelalyTransactions15(Long bookZawagMelalyTransactions15) {
		this.bookZawagMelalyTransactions15 = bookZawagMelalyTransactions15;
	}

	public Long getBookMoragaaTransactions15() {
		return bookMoragaaTransactions15;
	}

	public void setBookMoragaaTransactions15(Long bookMoragaaTransactions15) {
		this.bookMoragaaTransactions15 = bookMoragaaTransactions15;
	}

	public Double getBookZawagMuslimAmount8() {
		return bookZawagMuslimAmount8;
	}

	public void setBookZawagMuslimAmount8(Double bookZawagMuslimAmount8) {
		this.bookZawagMuslimAmount8 = bookZawagMuslimAmount8;
	}

	public Double getBookTalakAmount8() {
		return bookTalakAmount8;
	}

	public void setBookTalakAmount8(Double bookTalakAmount8) {
		this.bookTalakAmount8 = bookTalakAmount8;
	}

	public Double getBookTasadokAmount8() {
		return bookTasadokAmount8;
	}

	public void setBookTasadokAmount8(Double bookTasadokAmount8) {
		this.bookTasadokAmount8 = bookTasadokAmount8;
	}

	public Double getBookZawagMelalyAmount8() {
		return bookZawagMelalyAmount8;
	}

	public void setBookZawagMelalyAmount8(Double bookZawagMelalyAmount8) {
		this.bookZawagMelalyAmount8 = bookZawagMelalyAmount8;
	}

	public Double getBookMoragaaAmount8() {
		return bookMoragaaAmount8;
	}

	public void setBookMoragaaAmount8(Double bookMoragaaAmount8) {
		this.bookMoragaaAmount8 = bookMoragaaAmount8;
	}

	public Double getBookZawagMuslimAmount15() {
		return bookZawagMuslimAmount15;
	}

	public void setBookZawagMuslimAmount15(Double bookZawagMuslimAmount15) {
		this.bookZawagMuslimAmount15 = bookZawagMuslimAmount15;
	}

	public Double getBookTalakAmount15() {
		return bookTalakAmount15;
	}

	public void setBookTalakAmount15(Double bookTalakAmount15) {
		this.bookTalakAmount15 = bookTalakAmount15;
	}

	public Double getBookTasadokAmount15() {
		return bookTasadokAmount15;
	}

	public void setBookTasadokAmount15(Double bookTasadokAmount15) {
		this.bookTasadokAmount15 = bookTasadokAmount15;
	}

	public Double getBookZawagMelalyAmount15() {
		return bookZawagMelalyAmount15;
	}

	public void setBookZawagMelalyAmount15(Double bookZawagMelalyAmount15) {
		this.bookZawagMelalyAmount15 = bookZawagMelalyAmount15;
	}

	public Double getBookMoragaaAmount15() {
		return bookMoragaaAmount15;
	}

	public void setBookMoragaaAmount15(Double bookMoragaaAmount15) {
		this.bookMoragaaAmount15 = bookMoragaaAmount15;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getSectorId() {
		return sectorId;
	}

	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public Long getTotalSectorTransactions() {
		totalSectorTransactions = getBookZawagMuslimTransactions8() + 
				getBookTalakTransactions8() + getBookMoragaaTransactions8() + 
				getBookTasadokTransactions8() + getBookZawagMelalyTransactions8() +
				getBookZawagMuslimTransactions15() + 
				getBookTalakTransactions15() + getBookMoragaaTransactions15() + 
				getBookTasadokTransactions15() + getBookZawagMelalyTransactions15();
		return totalSectorTransactions;
	}

	public void setTotalSectorTransactions(Long totalSectorTransactions) {
		this.totalSectorTransactions = totalSectorTransactions;
	}

	public Double getTotalSectorAmount() {
		totalSectorAmount = bookZawagMuslimAmount8 + bookTalakAmount8 + bookMoragaaAmount8 + bookTasadokAmount8 + bookZawagMelalyAmount8 +
				bookZawagMuslimAmount15 + bookTalakAmount15 + bookMoragaaAmount15 + bookTasadokAmount15 + bookZawagMelalyAmount15;
		return totalSectorAmount = Double.valueOf(String.format("%.2f", totalSectorAmount));
	}

	public void setTotalSectorAmount(Double totalSectorAmount) {
		this.totalSectorAmount = totalSectorAmount;
	}

	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cityId == null) ? 0 : cityId.hashCode());
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
		result = prime * result + ((sectorId == null) ? 0 : sectorId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MaazounBookAuditSectorDTO other = (MaazounBookAuditSectorDTO) obj;
		if (cityId == null) {
			if (other.cityId != null)
				return false;
		} else if (!cityId.equals(other.cityId))
			return false;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		if (sectorId == null) {
			if (other.sectorId != null)
				return false;
		} else if (!sectorId.equals(other.sectorId))
			return false;
		return true;
	}

}