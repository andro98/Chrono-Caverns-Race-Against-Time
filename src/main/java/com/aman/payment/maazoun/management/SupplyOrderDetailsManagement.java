package com.aman.payment.maazoun.management;

import java.util.List;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.model.SupplyOrderDetails;
import com.aman.payment.maazoun.model.dto.BooksPagingDTO;
import com.aman.payment.maazoun.model.payload.AddSupplyOrderDetails;
import com.aman.payment.maazoun.model.payload.BooksRequest;
import com.aman.payment.maazoun.model.payload.SupplyOrderDetailsBetweenDates;
import com.aman.payment.maazoun.model.payload.SupplyOrderDetailsSearchRequest;

public interface SupplyOrderDetailsManagement {

	public String addSupplyOrderDetails(CustomUserDetails customUserDetails,
			AddSupplyOrderDetails addSupplyOrderDetails);

	public BooksPagingDTO getAllSupplyOrderDetails(SupplyOrderDetailsSearchRequest supplyOrderDetailsSearchRequest,
			CustomUserDetails customUserDetails);

	public BooksPagingDTO getAllSupplyOrderDetails(BooksRequest booksRequest, CustomUserDetails customUserDetails);

	public BooksPagingDTO findBookBetweenDates(SupplyOrderDetailsBetweenDates supplyOrderDetailsBetweenDates,
			CustomUserDetails customUserDetails);
	
	public List<String> searchForSupplyOrderDetails(SupplyOrderDetailsSearchRequest supplyOrderDetailsSearchRequest,
			CustomUserDetails customUserDetails);
	

}
