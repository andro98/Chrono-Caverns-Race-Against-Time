package com.aman.payment.maazoun.management;

import java.util.List;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.model.dto.PagingDTO;
import com.aman.payment.maazoun.model.payload.EditStockLabelRequest;
import com.aman.payment.maazoun.model.payload.EditStockLabelStatusRequest;
import com.aman.payment.maazoun.model.payload.SearchStockLabelRequest;
import com.aman.payment.maazoun.model.payload.StockLabelRequest;

public interface MaazounBookStockLabelManagement {
	
	public List<String> getAllStockLabels();

	public String generateSerialNumber(CustomUserDetails customUserDetails, StockLabelRequest stockLabelRequest);
	
	public String editSerialNumberStatus(EditStockLabelStatusRequest editStockLabelStatusRequest);
	
	public PagingDTO searchStockLabel(SearchStockLabelRequest searchStockLabelRequest, CustomUserDetails customUserDetails);
	
	public String findByStatusFkByLocations(String statusFk, CustomUserDetails customUserDetails);

	public String editStockLabelStatus(EditStockLabelRequest editStockLabelRequest);

	
}
