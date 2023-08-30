package com.aman.payment.maazoun.management;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.core.model.payload.URLRequest;
import com.aman.payment.maazoun.model.MaazounBookWarehouse;
import com.aman.payment.maazoun.model.dto.BooksPagingDTO;
import com.aman.payment.maazoun.model.dto.SupplyOrderPagingDTO;
import com.aman.payment.maazoun.model.payload.AddBookSupplyOrder;
import com.aman.payment.maazoun.model.payload.BookBySupplyOrderRefNumber;
import com.aman.payment.maazoun.model.payload.BooksByIdRequest;
import com.aman.payment.maazoun.model.payload.BooksFilterRequest;
import com.aman.payment.maazoun.model.payload.BooksRequest;
import com.aman.payment.maazoun.model.payload.ContractByIdRequest;
import com.aman.payment.maazoun.model.payload.GenerateBookSerialNumberRequest;
import com.aman.payment.maazoun.model.payload.GetContractRequest;
import com.aman.payment.maazoun.model.payload.ReviewRequest;
import com.aman.payment.maazoun.model.payload.SupplyOrderDetailsRequest;
import com.aman.payment.maazoun.model.payload.SupplyOrderRequest;
import com.aman.payment.maazoun.model.payload.WarehouseBookRequest;
import com.aman.payment.maazoun.model.payload.EditBookFinancialNumberRequest;

public interface MaazounBookWarehouseManagement {
	
	public List<String> getAllBooks();
	
	public BooksPagingDTO getAllBooks(BooksRequest booksRequest, CustomUserDetails customUserDetails);
	
	public String getBookBySerialNumber(BooksByIdRequest booksByIdRequest, CustomUserDetails customUserDetails);
	
	public String getBookBySerialNumberForSell(BooksByIdRequest booksByIdRequest, CustomUserDetails customUserDetails);
	
	public String getSoldOrNewBySerialNumber(BooksByIdRequest booksByIdRequest, CustomUserDetails customUserDetails);
	
	public String bookDistinctBySerialNumberAndStatus(BooksByIdRequest booksByIdRequest, CustomUserDetails customUserDetails);
	
	public String addBookSupplyOrder(CustomUserDetails customUserDetails, AddBookSupplyOrder addBookSupplyOrder);
	
	public String addCustodyBookSupplyOrder(CustomUserDetails customUserDetails, AddBookSupplyOrder addBookSupplyOrder);
	
	public String generateBookSerialNumber(CustomUserDetails customUserDetails, 
			GenerateBookSerialNumberRequest generateBookSerialNumberRequest);
	
	public SupplyOrderPagingDTO getAllSupplyOrdersCoding(SupplyOrderRequest supplyOrderRequest, CustomUserDetails customUserDetails);
	
	public SupplyOrderPagingDTO getAllSupplyOrders(SupplyOrderRequest supplyOrderRequest, CustomUserDetails customUserDetails);
	
	public List<String> warehouseReportByLocationAndBookType(CustomUserDetails customUserDetails);
	
	public List<String> warehouseReportByBookType(CustomUserDetails customUserDetails, WarehouseBookRequest warehouseBookRequest);
	
	public List<String> warehouseReportByDaily(CustomUserDetails customUserDetails, WarehouseBookRequest warehouseBookRequest);
	
	public String reviewSupplyOrder(CustomUserDetails customUserDetails, ReviewRequest reviewSupplyOrderRequest);
	
	public InputStream downloadFile(URLRequest uRLRequest);
	
	public String getSupplyOrderByRefrenceNumber(BookBySupplyOrderRefNumber bookBySupplyOrderRefNumber);
	
	public String getContractBySerialNumber(CustomUserDetails customUserDetails, ContractByIdRequest contractByIdRequest);
	
	public String getBookContructsBySerialNumber(BooksByIdRequest booksByIdRequest, CustomUserDetails customUserDetails);

	public List<String> getBookReceivedContractsBySerialNumber(BooksByIdRequest booksByIdRequest, CustomUserDetails customUserDetails);

	public String getContractByContractFinancialNumber(CustomUserDetails customUserDetails, ContractByIdRequest contractByIdRequest);

	public String getMaazounBookHistory(BooksByIdRequest booksByIdRequest);

	public BooksPagingDTO getBookByStatus(BooksFilterRequest booksByStatusRequest, CustomUserDetails customUserDetails);
	
	public BooksPagingDTO getBookBySectorId(BooksFilterRequest booksByStatusRequest, CustomUserDetails customUserDetails);
	
	public List<String> getAllSupplyOrderDetails(SupplyOrderDetailsRequest supplyOrderDetailsRequest, CustomUserDetails customUserDetails);
	
	public String editBookFinancialNumber(EditBookFinancialNumberRequest editBookFinancialNumberRequest);

	public String getBookExistingStatusByBookFinancialNumber(BooksByIdRequest booksByIdRequest);
	
	public String getBookExistingStatusByBookSerialNumber(BooksByIdRequest booksByIdRequest);
	
	public String getBookExistingStatusByContractNumber(GetContractRequest getContractRequest);
	
	public String findBySerialNumberOrBookFinancialNumber(BooksByIdRequest decryptBooksRequest);
	
	public String deleteBookByBookFinancialNumber(CustomUserDetails customUserDetails, BooksByIdRequest decryptBooksRequest);

	public String editBookTypeByBookFinancialNumber(EditBookFinancialNumberRequest editBookFinancialNumberRequest);
	
	public String editBookContractCountByBookFinancialNumber(EditBookFinancialNumberRequest editBookFinancialNumberRequest);
	
	public String reviewSupplyOrderDetails(CustomUserDetails customUserDetails, ReviewRequest reviewSupplyOrderRequest);



	

}
