package com.aman.payment.maazoun.model.dto;

import org.json.JSONObject;

public class WarehouseAuditDTO {

	private Long bookTotalZawagMuslimTransactions;
	private Long bookTotalUnderReviewZawagMuslimTransactions;
	private Long bookTotalNewZawagMuslimTransactions;
	private Long bookTotalSoldZawagMuslimTransactions;
	private Long bookTotalAvailableZawagMuslimTransactions;
	
	private Long bookTotalTalakTransactions;
	private Long bookTotalUnderReviewTalakTransactions;
	private Long bookTotalNewTalakTransactions;
	private Long bookTotalSoldTalakTransactions;
	private Long bookTotalAvailableTalakTransactions;
	
	private Long bookTotalTasadokTransactions;
	private Long bookTotalUnderReviewTasadokTransactions;
	private Long bookTotalNewTasadokTransactions;
	private Long bookTotalSoldTasadokTransactions;
	private Long bookTotalAvailableTasadokTransactions;
	
	private Long bookTotalZawagMelalyTransactions;
	private Long bookTotalUnderReviewZawagMelalyTransactions;
	private Long bookTotalNewZawagMelalyTransactions;
	private Long bookTotalSoldZawagMelalyTransactions;
	private Long bookTotalAvailableZawagMelalyTransactions;
	
	private Long bookTotalMoragaaTransactions;
	private Long bookTotalUnderReviewMoragaaTransactions;
	private Long bookTotalNewMoragaaTransactions;
	private Long bookTotalSoldMoragaaTransactions;
	private Long bookTotalAvailableMoragaaTransactions;
	
	private Long bookTotalSectorTransactions;
	private Long bookTotalUnderReviewSectorTransactions;
	private Long bookTotalNewSectorTransactions;
	private Long bookTotalSoldSectorTransactions;
	private Long bookTotalAvailableSectorTransactions;
	
//	private Long bookTotalRefundZawagMuslimTransactions;
//	private Long bookTotalRefundTalakTransactions;
//	private Long bookTotalRefundTasadokTransactions;
//	private Long bookTotalRefundZawagMelalyTransactions;
//	private Long bookTotalRefundMoragaaTransactions;

//	private Long bookTotalReceivedZawagMuslimTransactions;
//	private Long bookTotalReceivedTalakTransactions;
//	private Long bookTotalReceivedTasadokTransactions;
//	private Long bookTotalReceivedZawagMelalyTransactions;
//	private Long bookTotalReceivedMoragaaTransactions;
//
//	private Long bookTotalUnderDeliveryZawagMuslimTransactions;
//	private Long bookTotalUnderDeliveryTalakTransactions;
//	private Long bookTotalUnderDeliveryTasadokTransactions;
//	private Long bookTotalUnderDeliveryZawagMelalyTransactions;
//	private Long bookTotalUnderDeliveryMoragaaTransactions;
//
//	private Long bookTotalDeliveredZawagMuslimTransactions;
//	private Long bookTotalDeliveredTalakTransactions;
//	private Long bookTotalDeliveredTasadokTransactions;
//	private Long bookTotalDeliveredZawagMelalyTransactions;
//	private Long bookTotalDeliveredMoragaaTransactions;
//
//	private Long bookTotalWithMaazounZawagMuslimTransactions;
//	private Long bookTotalWithMaazounTalakTransactions;
//	private Long bookTotalWithMaazounTasadokTransactions;
//	private Long bookTotalWithMaazounZawagMelalyTransactions;
//	private Long bookTotalWithMaazounMoragaaTransactions;

	

	private String cityName;
	private String cityId;
	private String locationName;
	private String locationId;
	private String sectorId;
	private String sectorName;
	private String bookContractCount;
	private String createdAt;

	public WarehouseAuditDTO() {
		super();
	}

	public Long getBookTotalZawagMuslimTransactions() {
		return bookTotalZawagMuslimTransactions;
	}

	public void setBookTotalZawagMuslimTransactions(Long bookTotalZawagMuslimTransactions) {
		this.bookTotalZawagMuslimTransactions = bookTotalZawagMuslimTransactions;
	}

	public Long getBookTotalTalakTransactions() {
		return bookTotalTalakTransactions;
	}

	public void setBookTotalTalakTransactions(Long bookTotalTalakTransactions) {
		this.bookTotalTalakTransactions = bookTotalTalakTransactions;
	}

	public Long getBookTotalTasadokTransactions() {
		return bookTotalTasadokTransactions;
	}

	public void setBookTotalTasadokTransactions(Long bookTotalTasadokTransactions) {
		this.bookTotalTasadokTransactions = bookTotalTasadokTransactions;
	}

	public Long getBookTotalZawagMelalyTransactions() {
		return bookTotalZawagMelalyTransactions;
	}

	public void setBookTotalZawagMelalyTransactions(Long bookTotalZawagMelalyTransactions) {
		this.bookTotalZawagMelalyTransactions = bookTotalZawagMelalyTransactions;
	}

	public Long getBookTotalMoragaaTransactions() {
		return bookTotalMoragaaTransactions;
	}

	public void setBookTotalMoragaaTransactions(Long bookTotalMoragaaTransactions) {
		this.bookTotalMoragaaTransactions = bookTotalMoragaaTransactions;
	}

	public Long getBookTotalUnderReviewZawagMuslimTransactions() {
		return bookTotalUnderReviewZawagMuslimTransactions;
	}

	public void setBookTotalUnderReviewZawagMuslimTransactions(Long bookTotalUnderReviewZawagMuslimTransactions) {
		this.bookTotalUnderReviewZawagMuslimTransactions = bookTotalUnderReviewZawagMuslimTransactions;
	}

	public Long getBookTotalUnderReviewTalakTransactions() {
		return bookTotalUnderReviewTalakTransactions;
	}

	public void setBookTotalUnderReviewTalakTransactions(Long bookTotalUnderReviewTalakTransactions) {
		this.bookTotalUnderReviewTalakTransactions = bookTotalUnderReviewTalakTransactions;
	}

	public Long getBookTotalUnderReviewTasadokTransactions() {
		return bookTotalUnderReviewTasadokTransactions;
	}

	public void setBookTotalUnderReviewTasadokTransactions(Long bookTotalUnderReviewTasadokTransactions) {
		this.bookTotalUnderReviewTasadokTransactions = bookTotalUnderReviewTasadokTransactions;
	}

	public Long getBookTotalUnderReviewZawagMelalyTransactions() {
		return bookTotalUnderReviewZawagMelalyTransactions;
	}

	public void setBookTotalUnderReviewZawagMelalyTransactions(Long bookTotalUnderReviewZawagMelalyTransactions) {
		this.bookTotalUnderReviewZawagMelalyTransactions = bookTotalUnderReviewZawagMelalyTransactions;
	}

	public Long getBookTotalUnderReviewMoragaaTransactions() {
		return bookTotalUnderReviewMoragaaTransactions;
	}

	public void setBookTotalUnderReviewMoragaaTransactions(Long bookTotalUnderReviewMoragaaTransactions) {
		this.bookTotalUnderReviewMoragaaTransactions = bookTotalUnderReviewMoragaaTransactions;
	}

	public Long getBookTotalNewZawagMuslimTransactions() {
		return bookTotalNewZawagMuslimTransactions;
	}

	public void setBookTotalNewZawagMuslimTransactions(Long bookTotalNewZawagMuslimTransactions) {
		this.bookTotalNewZawagMuslimTransactions = bookTotalNewZawagMuslimTransactions;
	}

	public Long getBookTotalNewTalakTransactions() {
		return bookTotalNewTalakTransactions;
	}

	public void setBookTotalNewTalakTransactions(Long bookTotalNewTalakTransactions) {
		this.bookTotalNewTalakTransactions = bookTotalNewTalakTransactions;
	}

	public Long getBookTotalNewTasadokTransactions() {
		return bookTotalNewTasadokTransactions;
	}

	public void setBookTotalNewTasadokTransactions(Long bookTotalNewTasadokTransactions) {
		this.bookTotalNewTasadokTransactions = bookTotalNewTasadokTransactions;
	}

	public Long getBookTotalNewZawagMelalyTransactions() {
		return bookTotalNewZawagMelalyTransactions;
	}

	public void setBookTotalNewZawagMelalyTransactions(Long bookTotalNewZawagMelalyTransactions) {
		this.bookTotalNewZawagMelalyTransactions = bookTotalNewZawagMelalyTransactions;
	}

	public Long getBookTotalNewMoragaaTransactions() {
		return bookTotalNewMoragaaTransactions;
	}

	public void setBookTotalNewMoragaaTransactions(Long bookTotalNewMoragaaTransactions) {
		this.bookTotalNewMoragaaTransactions = bookTotalNewMoragaaTransactions;
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

	

	public Long getBookTotalAvailableZawagMuslimTransactions() {
		return bookTotalAvailableZawagMuslimTransactions;
	}

	public void setBookTotalAvailableZawagMuslimTransactions(Long bookTotalAvailableZawagMuslimTransactions) {
		this.bookTotalAvailableZawagMuslimTransactions = bookTotalAvailableZawagMuslimTransactions;
	}

	public Long getBookTotalAvailableTalakTransactions() {
		return bookTotalAvailableTalakTransactions;
	}

	public void setBookTotalAvailableTalakTransactions(Long bookTotalAvailableTalakTransactions) {
		this.bookTotalAvailableTalakTransactions = bookTotalAvailableTalakTransactions;
	}

	public Long getBookTotalAvailableTasadokTransactions() {
		return bookTotalAvailableTasadokTransactions;
	}

	public void setBookTotalAvailableTasadokTransactions(Long bookTotalAvailableTasadokTransactions) {
		this.bookTotalAvailableTasadokTransactions = bookTotalAvailableTasadokTransactions;
	}

	public Long getBookTotalAvailableZawagMelalyTransactions() {
		return bookTotalAvailableZawagMelalyTransactions;
	}

	public void setBookTotalAvailableZawagMelalyTransactions(Long bookTotalAvailableZawagMelalyTransactions) {
		this.bookTotalAvailableZawagMelalyTransactions = bookTotalAvailableZawagMelalyTransactions;
	}

	public Long getBookTotalAvailableMoragaaTransactions() {
		return bookTotalAvailableMoragaaTransactions;
	}

	public void setBookTotalAvailableMoragaaTransactions(Long bookTotalAvailableMoragaaTransactions) {
		this.bookTotalAvailableMoragaaTransactions = bookTotalAvailableMoragaaTransactions;
	}

	public String getBookContractCount() {
		return bookContractCount;
	}

	public void setBookContractCount(String bookContractCount) {
		this.bookContractCount = bookContractCount;
	}

	public Long getBookTotalSoldZawagMuslimTransactions() {
		return bookTotalSoldZawagMuslimTransactions;
	}

	public void setBookTotalSoldZawagMuslimTransactions(Long bookTotalSoldZawagMuslimTransactions) {
		this.bookTotalSoldZawagMuslimTransactions = bookTotalSoldZawagMuslimTransactions;
	}

	public Long getBookTotalSoldTalakTransactions() {
		return bookTotalSoldTalakTransactions;
	}

	public void setBookTotalSoldTalakTransactions(Long bookTotalSoldTalakTransactions) {
		this.bookTotalSoldTalakTransactions = bookTotalSoldTalakTransactions;
	}

	public Long getBookTotalSoldTasadokTransactions() {
		return bookTotalSoldTasadokTransactions;
	}

	public void setBookTotalSoldTasadokTransactions(Long bookTotalSoldTasadokTransactions) {
		this.bookTotalSoldTasadokTransactions = bookTotalSoldTasadokTransactions;
	}

	public Long getBookTotalSoldZawagMelalyTransactions() {
		return bookTotalSoldZawagMelalyTransactions;
	}

	public void setBookTotalSoldZawagMelalyTransactions(Long bookTotalSoldZawagMelalyTransactions) {
		this.bookTotalSoldZawagMelalyTransactions = bookTotalSoldZawagMelalyTransactions;
	}

	public Long getBookTotalSoldMoragaaTransactions() {
		return bookTotalSoldMoragaaTransactions;
	}

	public void setBookTotalSoldMoragaaTransactions(Long bookTotalSoldMoragaaTransactions) {
		this.bookTotalSoldMoragaaTransactions = bookTotalSoldMoragaaTransactions;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Long getBookTotalSectorTransactions() {
		bookTotalSectorTransactions = bookTotalZawagMuslimTransactions + bookTotalTalakTransactions
				+ bookTotalTasadokTransactions + bookTotalZawagMelalyTransactions + bookTotalMoragaaTransactions;
		return bookTotalSectorTransactions;
	}

	public void setBookTotalSectorTransactions(Long bookTotalSectorTransactions) {
		this.bookTotalSectorTransactions = bookTotalSectorTransactions;
	}

	public Long getBookTotalUnderReviewSectorTransactions() {
		bookTotalUnderReviewSectorTransactions = bookTotalUnderReviewMoragaaTransactions
				+ bookTotalUnderReviewTalakTransactions + bookTotalUnderReviewTasadokTransactions
				+ bookTotalUnderReviewZawagMelalyTransactions + bookTotalUnderReviewZawagMuslimTransactions;
		return bookTotalUnderReviewSectorTransactions;
	}

	public void setBookTotalUnderReviewSectorTransactions(Long bookTotalUnderReviewSectorTransactions) {
		this.bookTotalUnderReviewSectorTransactions = bookTotalUnderReviewSectorTransactions;
	}

	public Long getBookTotalNewSectorTransactions() {
		bookTotalNewSectorTransactions = bookTotalNewMoragaaTransactions + bookTotalNewTalakTransactions
				+ bookTotalNewTasadokTransactions + bookTotalNewZawagMelalyTransactions
				+ bookTotalNewZawagMuslimTransactions;
		return bookTotalNewSectorTransactions;
	}

	public void setBookTotalNewSectorTransactions(Long bookTotalNewSectorTransactions) {
		this.bookTotalNewSectorTransactions = bookTotalNewSectorTransactions;
	}

	public Long getBookTotalSoldSectorTransactions() {
		bookTotalSoldSectorTransactions = bookTotalSoldMoragaaTransactions + bookTotalSoldTalakTransactions
				+ bookTotalSoldTasadokTransactions + bookTotalSoldZawagMelalyTransactions
				+ bookTotalSoldZawagMuslimTransactions;
		return bookTotalSoldSectorTransactions;
	}

	public void setBookTotalSoldSectorTransactions(Long bookTotalSoldSectorTransactions) {
		this.bookTotalSoldSectorTransactions = bookTotalSoldSectorTransactions;
	}

	public Long getBookTotalAvailableSectorTransactions() {
		bookTotalAvailableSectorTransactions = bookTotalAvailableMoragaaTransactions + bookTotalAvailableTalakTransactions
				+ bookTotalAvailableTasadokTransactions + bookTotalAvailableZawagMelalyTransactions
				+ bookTotalAvailableZawagMuslimTransactions;
		return bookTotalAvailableSectorTransactions;
	}

	public void setBookTotalAvailableSectorTransactions(Long bookTotalAvailableSectorTransactions) {
		this.bookTotalAvailableSectorTransactions = bookTotalAvailableSectorTransactions;
	}

	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

}