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
package com.aman.payment.maazoun.controller;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.annotation.CurrentUser;
import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.core.model.payload.URLRequest;
import com.aman.payment.maazoun.management.MaazounBookWarehouseManagement;
import com.aman.payment.maazoun.management.MaazounJsonObjectFactoryImpl;
import com.aman.payment.maazoun.management.SupplyOrderDetailsManagement;
import com.aman.payment.maazoun.model.dto.BooksPagingDTO;
import com.aman.payment.maazoun.model.dto.SupplyOrderPagingDTO;
import com.aman.payment.maazoun.model.payload.AddBookSupplyOrder;
import com.aman.payment.maazoun.model.payload.AddSupplyOrderDetails;
import com.aman.payment.maazoun.model.payload.BookBySupplyOrderRefNumber;
import com.aman.payment.maazoun.model.payload.BooksByIdRequest;
import com.aman.payment.maazoun.model.payload.BooksFilterRequest;
import com.aman.payment.maazoun.model.payload.BooksRequest;
import com.aman.payment.maazoun.model.payload.ContractByIdRequest;
import com.aman.payment.maazoun.model.payload.EditBookFinancialNumberRequest;
import com.aman.payment.maazoun.model.payload.GenerateBookSerialNumberRequest;
import com.aman.payment.maazoun.model.payload.GetContractRequest;
import com.aman.payment.maazoun.model.payload.ReviewRequest;
import com.aman.payment.maazoun.model.payload.SupplyOrderDetailsBetweenDates;
import com.aman.payment.maazoun.model.payload.SupplyOrderDetailsRequest;
import com.aman.payment.maazoun.model.payload.SupplyOrderDetailsSearchRequest;
import com.aman.payment.maazoun.model.payload.SupplyOrderRequest;
import com.aman.payment.maazoun.model.payload.WarehouseBookRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.util.annotation.Nullable;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/maazoun/warehouse")
@Api(value = "Maazoun Book Warehouse Rest API", description = "Defines endpoints for the Maazoun Book Warehouse. It's secured by default")
public class MaazounBookWarehouseController extends MaazounJsonObjectFactoryImpl {

	final static Logger logger = Logger.getLogger("maazoun");

	private final MaazounBookWarehouseManagement maazounBookWarehouseManagement;
	private final SupplyOrderDetailsManagement supplyOrderDetailsManagement;

	@Autowired
	public MaazounBookWarehouseController(MaazounBookWarehouseManagement maazounBookWarehouseManagement,
			SupplyOrderDetailsManagement supplyOrderDetailsManagement) {
		super();
		this.maazounBookWarehouseManagement = maazounBookWarehouseManagement;
		this.supplyOrderDetailsManagement = supplyOrderDetailsManagement;
	}

	@PostMapping("/books")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured books in warehous. Requires ADMIN Access")
	public ResponseEntity<BooksPagingDTO> getAllBooks(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		BooksRequest decryptBooksRequest = convertJsonStringToObject(jsonString, BooksRequest.class);

		return ResponseEntity.ok(maazounBookWarehouseManagement.getAllBooks(decryptBooksRequest, customUserDetails));
	}

//	   find all supply order Details by sector fk
	@PostMapping("/findAllSupplyOrder")
	@ApiOperation(value = "Returns the list of books  that is Doest NNNNot have Barcode . Requires ADMIN Access")
	public ResponseEntity<BooksPagingDTO> getAllSupplyOrderDetailss(@CurrentUser CustomUserDetails customUserDetails,
			@RequestBody String jsonString) {
		BooksRequest decryptBooksRequest = convertJsonStringToObject(jsonString, BooksRequest.class);

		return ResponseEntity
				.ok(supplyOrderDetailsManagement.getAllSupplyOrderDetails(decryptBooksRequest, customUserDetails));
	}

//// find all supply order Details  by created at between
	@PostMapping("/findByDateBetween")
	public ResponseEntity<BooksPagingDTO> findByDateBetween(@CurrentUser CustomUserDetails customUserDetails,
			@RequestBody String jsonString) {
		SupplyOrderDetailsBetweenDates decryptSupplyOrderDetailsBetweenDates = convertJsonStringToObject(jsonString,
				SupplyOrderDetailsBetweenDates.class);

		return ResponseEntity.ok(supplyOrderDetailsManagement
				.findBookBetweenDates(decryptSupplyOrderDetailsBetweenDates, customUserDetails));
	}

	@PostMapping("/bookBySerialNumber")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by seial number in warehous. Requires ADMIN Access")
	public ResponseEntity<String> getBookBySerialNumber(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		BooksByIdRequest decryptBooksRequest = convertJsonStringToObject(jsonString, BooksByIdRequest.class);

		return ResponseEntity
				.ok(maazounBookWarehouseManagement.getBookBySerialNumber(decryptBooksRequest, customUserDetails));

	}

	@PostMapping("/bookBySerialNumberForSell")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by seial number in warehous. Requires ADMIN Access")
	public ResponseEntity<String> bookBySerialNumberForSell(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		BooksByIdRequest decryptBooksRequest = convertJsonStringToObject(jsonString, BooksByIdRequest.class);

		return ResponseEntity.ok(
				maazounBookWarehouseManagement.getBookBySerialNumberForSell(decryptBooksRequest, customUserDetails));

	}

	@PostMapping("/editBookFinancialNumber")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by seial number in warehous. Requires ADMIN Access")
	public ResponseEntity<String> editBookFinancialNumber(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		EditBookFinancialNumberRequest decryptBookFinancialNumberRequest = convertJsonStringToObject(jsonString,
				EditBookFinancialNumberRequest.class);

		return ResponseEntity
				.ok(maazounBookWarehouseManagement.editBookFinancialNumber(decryptBookFinancialNumberRequest));

	}

	@PostMapping("/bookDistinctBySerialNumberAndStatus")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by seial number in warehous. Requires ADMIN Access")
	public ResponseEntity<String> getBookDistinctBySerialNumberAndStatus(
			@CurrentUser CustomUserDetails customUserDetails, @Valid @RequestBody String jsonString) {

		BooksByIdRequest decryptBooksRequest = convertJsonStringToObject(jsonString, BooksByIdRequest.class);

		return ResponseEntity.ok(maazounBookWarehouseManagement.bookDistinctBySerialNumberAndStatus(decryptBooksRequest,
				customUserDetails));

	}

	@PostMapping("/bookReceivedContractsBySerialNumber")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by seial number in warehous. Requires ADMIN Access")
	public ResponseEntity<List<String>> getBookReceivedContractsBySerialNumber(
			@CurrentUser CustomUserDetails customUserDetails, @Valid @RequestBody String jsonString) {

		BooksByIdRequest decryptBooksRequest = convertJsonStringToObject(jsonString, BooksByIdRequest.class);

		return ResponseEntity.ok(maazounBookWarehouseManagement
				.getBookReceivedContractsBySerialNumber(decryptBooksRequest, customUserDetails));

	}

	@PostMapping("/bookContractsBySerialNumber")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by seial number in warehous. Requires ADMIN Access")
	public ResponseEntity<String> getBookContractsBySerialNumber(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		BooksByIdRequest decryptBooksRequest = convertJsonStringToObject(jsonString, BooksByIdRequest.class);

		return ResponseEntity.ok(
				maazounBookWarehouseManagement.getBookContructsBySerialNumber(decryptBooksRequest, customUserDetails));

	}

	@PostMapping("/contractBySerialNumber")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by seial number in warehous. Requires ADMIN Access")
	public ResponseEntity<String> getContractBySerialNumber(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		ContractByIdRequest decryptContractByIdRequest = convertDecryptedJsonStringToObject(jsonString,
				ContractByIdRequest.class);

		return ResponseEntity.ok(maazounBookWarehouseManagement.getContractBySerialNumber(customUserDetails,
				decryptContractByIdRequest));
	}

	@PostMapping("/contractByContractFinancialNumber")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by seial number in warehous. Requires ADMIN Access")
	public ResponseEntity<String> getContractByContractFinancialNumber(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		ContractByIdRequest decryptContractByIdRequest = convertJsonStringToObject(jsonString,
				ContractByIdRequest.class);

		return ResponseEntity.ok(maazounBookWarehouseManagement.getContractByContractFinancialNumber(customUserDetails,
				decryptContractByIdRequest));
	}

	// when add new supply order(Labeled books) decrement by the number from supply
	// order Details(Not Labeled books)
	@PostMapping("/addBookSupplyOrderCoding")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Add books batch coding. Requires ADMIN Access")
	public ResponseEntity<String> addBookSupplyOrderCoding(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestPart String jsonString,
			@Nullable @RequestPart(name = "supplyOrderImge", required = false) MultipartFile supplyOrderImge) {

		AddBookSupplyOrder decryptAddBookSupplyOrder = convertJsonStringToObject(jsonString, AddBookSupplyOrder.class);
		decryptAddBookSupplyOrder.setImageUrl(supplyOrderImge);

		return ResponseEntity
				.ok(maazounBookWarehouseManagement.addBookSupplyOrderCoding(customUserDetails, decryptAddBookSupplyOrder));
	}

	@PostMapping("/addCustodyBookSupplyOrder")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Add books batch. Requires ADMIN Access")
	public ResponseEntity<String> addCustodyBookSupplyOrder(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestPart String jsonString,
			@Nullable @RequestPart(name = "supplyOrderImge", required = false) MultipartFile supplyOrderImge) {

		AddBookSupplyOrder decryptAddBookSupplyOrder = convertJsonStringToObject(jsonString, AddBookSupplyOrder.class);
		decryptAddBookSupplyOrder.setImageUrl(supplyOrderImge);

		return ResponseEntity.ok(
				maazounBookWarehouseManagement.addCustodyBookSupplyOrder(customUserDetails, decryptAddBookSupplyOrder));
	}

	@PostMapping("/generateBookSerialNumber")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Generate Books Serial Number. Requires ADMIN Access")
	public ResponseEntity<String> generateBookSerialNumber(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		GenerateBookSerialNumberRequest decryptGenerateBookSerialNumberRequest = convertJsonStringToObject(jsonString,
				GenerateBookSerialNumberRequest.class);

		return ResponseEntity.ok(maazounBookWarehouseManagement.generateBookSerialNumber(customUserDetails,
				decryptGenerateBookSerialNumberRequest));
	}

	@PostMapping("/supplyOrdersCoding")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured supply orders coding. Requires ADMIN Access")
	public ResponseEntity<SupplyOrderPagingDTO> getAllSupplyOrdersCoding(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		SupplyOrderRequest decryptSupplyOrderRequest = convertJsonStringToObject(jsonString, SupplyOrderRequest.class);

		return ResponseEntity
				.ok(maazounBookWarehouseManagement.getAllSupplyOrdersCoding(decryptSupplyOrderRequest, customUserDetails));
	}
	
	@PostMapping("/supplyOrders")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured supply orders. Requires ADMIN Access")
	public ResponseEntity<SupplyOrderPagingDTO> getAllSupplyOrders(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		SupplyOrderRequest decryptSupplyOrderRequest = convertJsonStringToObject(jsonString, SupplyOrderRequest.class);

		return ResponseEntity
				.ok(maazounBookWarehouseManagement.getAllSupplyOrders(decryptSupplyOrderRequest, customUserDetails));
	}

	@PostMapping("/supplyOrderBySupplyOrderRefNumber")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured supply orders. Requires ADMIN Access")
	public ResponseEntity<String> getAllSupplyOrderBySupplyOrderRefNumber(
			@CurrentUser CustomUserDetails customUserDetails, @Valid @RequestBody String jsonString) {

		BookBySupplyOrderRefNumber decryptBookBySupplyOrderRefNumbert = convertJsonStringToObject(jsonString,
				BookBySupplyOrderRefNumber.class);

		return ResponseEntity
				.ok(maazounBookWarehouseManagement.getSupplyOrderByRefrenceNumber(decryptBookBySupplyOrderRefNumbert));
	}

	@GetMapping("/warehouseReportByLocationAndBookType")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured calculation. Requires ADMIN Access")
	public ResponseEntity<List<String>> warehouseReportByLocationAndBookType(
			@CurrentUser CustomUserDetails customUserDetails) {

		return ResponseEntity
				.ok(maazounBookWarehouseManagement.warehouseReportByLocationAndBookType(customUserDetails));

	}

	@PostMapping("/warehouseReportByBookType")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured calculation. Requires ADMIN Access")
	public ResponseEntity<List<String>> warehouseReportByBookType(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		WarehouseBookRequest decryptWarehouseBookRequest = convertJsonStringToObject(jsonString,
				WarehouseBookRequest.class);

		return ResponseEntity.ok(maazounBookWarehouseManagement.warehouseReportByBookType(customUserDetails,
				decryptWarehouseBookRequest));

	}

	@PostMapping("/warehouseReportByDaily")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured calculation. Requires ADMIN Access")
	public ResponseEntity<List<String>> warehouseReportByDaily(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		WarehouseBookRequest decryptWarehouseBookRequest = convertJsonStringToObject(jsonString,
				WarehouseBookRequest.class);

		return ResponseEntity.ok(
				maazounBookWarehouseManagement.warehouseReportByDaily(customUserDetails, decryptWarehouseBookRequest));

	}

	@PostMapping("/reviewSupplyOrder")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All approved supply orders.")
	public ResponseEntity<String> reviewSupplyOrder(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		ReviewRequest decryptReviewSupplyOrderRequest = convertJsonStringToObject(jsonString, ReviewRequest.class);

		return ResponseEntity.ok(
				maazounBookWarehouseManagement.reviewSupplyOrderCoding(customUserDetails, decryptReviewSupplyOrderRequest));
	}
	
	
	
	@PostMapping("/reviewSupplyOrderDetails")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of All approved supply orders.")
	public ResponseEntity<String> reviewSupplyOrderDetails(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		ReviewRequest decryptReviewSupplyOrderRequest = convertJsonStringToObject(jsonString, ReviewRequest.class);

		return ResponseEntity.ok(
				maazounBookWarehouseManagement.reviewSupplyOrderDetails(customUserDetails, decryptReviewSupplyOrderRequest));
	}

	@PostMapping("/download/att")
	public void downloadFile(@Valid @RequestBody String jsonString, HttpServletResponse response) {

		URLRequest decryptURLRequest = convertJsonStringToObject(jsonString, URLRequest.class);

		try {

			InputStream is = maazounBookWarehouseManagement.downloadFile(decryptURLRequest);
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostMapping("/getMaazounBookHistory")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of maazoun book history. Requires ADMIN Access")
	public ResponseEntity<String> getMaazounBookHistory(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		BooksByIdRequest decryptBooksByIdRequest = convertJsonStringToObject(jsonString, BooksByIdRequest.class);

		return ResponseEntity.ok(maazounBookWarehouseManagement.getMaazounBookHistory(decryptBooksByIdRequest));
	}

	@PostMapping("/filterBooksByStatus")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by status in warehous. Requires ADMIN Access")
	public ResponseEntity<BooksPagingDTO> getBookByStatus(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		BooksFilterRequest decryptBooksByStatusRequest = convertJsonStringToObject(jsonString,
				BooksFilterRequest.class);

		return ResponseEntity
				.ok(maazounBookWarehouseManagement.getBookByStatus(decryptBooksByStatusRequest, customUserDetails));

	}

	@PostMapping("/filterBooksBySectorId")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by sector id in warehous. Requires ADMIN Access")
	public ResponseEntity<BooksPagingDTO> getBookBySectorId(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		BooksFilterRequest decryptBooksByStatusRequest = convertJsonStringToObject(jsonString,
				BooksFilterRequest.class);

		return ResponseEntity
				.ok(maazounBookWarehouseManagement.getBookBySectorId(decryptBooksByStatusRequest, customUserDetails));

	}

	@PostMapping("/supplyOrderDetails")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of added books related to supply order. Requires ADMIN Access")
	public ResponseEntity<List<String>> getAllSupplyOrderDetails(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		SupplyOrderDetailsRequest decryptSupplyOrderDetailsRequest = convertJsonStringToObject(jsonString,
				SupplyOrderDetailsRequest.class);

		return ResponseEntity.ok(maazounBookWarehouseManagement
				.getAllSupplyOrderDetails(decryptSupplyOrderDetailsRequest, customUserDetails));
	}

	@PostMapping("/searchForBookByBookFinancialNumber")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by seial number in warehous. Requires ADMIN Access")
	public ResponseEntity<String> getBookStatus(@Valid @RequestBody String jsonString) {

		BooksByIdRequest decryptBooksRequest = convertJsonStringToObject(jsonString, BooksByIdRequest.class);

		return ResponseEntity
				.ok(maazounBookWarehouseManagement.getBookExistingStatusByBookFinancialNumber(decryptBooksRequest));

	}

	@PostMapping("/searchForBookByBookSerialNumber")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by seial number in warehous. Requires ADMIN Access")
	public ResponseEntity<String> searchForBookByBookSerialNumber(@Valid @RequestBody String jsonString) {

		BooksByIdRequest decryptBooksRequest = convertJsonStringToObject(jsonString, BooksByIdRequest.class);

		return ResponseEntity
				.ok(maazounBookWarehouseManagement.getBookExistingStatusByBookSerialNumber(decryptBooksRequest));

	}

	@PostMapping("/searchForBookByBookSerialNumberOrFinancial")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by seial number in warehous. Requires ADMIN Access")
	public ResponseEntity<String> searchForBookByBookSerialNumberOrFinancial(@Valid @RequestBody String jsonString) {

		BooksByIdRequest decryptBooksRequest = convertJsonStringToObject(jsonString, BooksByIdRequest.class);

		return ResponseEntity
				.ok(maazounBookWarehouseManagement.findBySerialNumberOrBookFinancialNumber(decryptBooksRequest));

	}

	@PostMapping("/searchForBookByContractNumber")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by seial number in warehous. Requires ADMIN Access")
	public ResponseEntity<String> searchForBookByContractNumber(@Valid @RequestBody String jsonString) {

		GetContractRequest decryptGetContractRequest = convertJsonStringToObject(jsonString, GetContractRequest.class);

		return ResponseEntity
				.ok(maazounBookWarehouseManagement.getBookExistingStatusByContractNumber(decryptGetContractRequest));

	}

	@PostMapping("/bookSoldOrNewBySerialNumber")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by seial number in warehous. Requires ADMIN Access")
	public ResponseEntity<String> getSoldOrNewBySerialNumber(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		BooksByIdRequest decryptBooksRequest = convertJsonStringToObject(jsonString, BooksByIdRequest.class);

		return ResponseEntity
				.ok(maazounBookWarehouseManagement.getSoldOrNewBySerialNumber(decryptBooksRequest, customUserDetails));

	}

	@PostMapping("/deleteBookByBookFinancialNumber")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "delete Book By Book Financial Number. Requires ADMIN Access")
	public ResponseEntity<String> deleteBookByBookFinancialNumber(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		BooksByIdRequest decryptBooksRequest = convertJsonStringToObject(jsonString, BooksByIdRequest.class);

		return ResponseEntity.ok(
				maazounBookWarehouseManagement.deleteBookByBookFinancialNumber(customUserDetails, decryptBooksRequest));

	}

	@PostMapping("/editBookTypeByBookFinancialNumber")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by seial number in warehous. Requires ADMIN Access")
	public ResponseEntity<String> editBookTypeByBookFinancialNumber(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody String jsonString) {

		EditBookFinancialNumberRequest decryptBookFinancialNumberRequest = convertJsonStringToObject(jsonString,
				EditBookFinancialNumberRequest.class);

		return ResponseEntity.ok(
				maazounBookWarehouseManagement.editBookTypeByBookFinancialNumber(decryptBookFinancialNumberRequest));

	}

	@PostMapping("/editBookContractCountByBookFinancialNumber")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the of configured books by seial number in warehous. Requires ADMIN Access")
	public ResponseEntity<String> editBookContractCountByBookFinancialNumber(
			@CurrentUser CustomUserDetails customUserDetails, @Valid @RequestBody String jsonString) {

		EditBookFinancialNumberRequest decryptBookFinancialNumberRequest = convertJsonStringToObject(jsonString,
				EditBookFinancialNumberRequest.class);

		return ResponseEntity.ok(maazounBookWarehouseManagement
				.editBookContractCountByBookFinancialNumber(decryptBookFinancialNumberRequest));

	}

	@PostMapping("/addBookSupplyOrderDetails")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Add books batch Details. Requires ADMIN Access")
	public ResponseEntity<String> addBookSupplyOrderDetails(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestPart String jsonString,
			@Nullable @RequestPart(name = "supplyOrderDetailsImge", required = false) MultipartFile supplyOrderDetailsImge) {

		AddSupplyOrderDetails decryptAddBookSupplyOrderDetails = convertJsonStringToObject(jsonString,
				AddSupplyOrderDetails.class);

		decryptAddBookSupplyOrderDetails.setAttUrl(supplyOrderDetailsImge);

		return ResponseEntity.ok(supplyOrderDetailsManagement.addSupplyOrderDetails(customUserDetails,
				decryptAddBookSupplyOrderDetails));
	}

	@PostMapping("/supplyOrderDetailsForSectors")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured books in warehous. Requires ADMIN Access")
	public ResponseEntity<BooksPagingDTO> getAllSupplyOrderDetailsForSectors(
			@CurrentUser CustomUserDetails customUserDetails, @Valid @RequestBody String jsonString) {

		SupplyOrderDetailsSearchRequest decryptSupplyOrderDetailsSearchRequest = convertJsonStringToObject(jsonString,
				SupplyOrderDetailsSearchRequest.class);

		return ResponseEntity.ok(supplyOrderDetailsManagement
				.getAllSupplyOrderDetails(decryptSupplyOrderDetailsSearchRequest, customUserDetails));
	}
	
	
	@PostMapping("/supplyOrderDetailsForSectorsSearch")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured books in warehous. Requires ADMIN Access")
	public ResponseEntity<List<String>>searchSupplyOrderDetailsForSectors(
			@CurrentUser CustomUserDetails customUserDetails, @Valid @RequestBody String jsonString) {

		SupplyOrderDetailsSearchRequest decryptSupplyOrderDetailsSearchRequest = convertJsonStringToObject(jsonString,
				SupplyOrderDetailsSearchRequest.class);

		return ResponseEntity.ok(supplyOrderDetailsManagement
				.searchForSupplyOrderDetails(decryptSupplyOrderDetailsSearchRequest, customUserDetails));
	}
}
