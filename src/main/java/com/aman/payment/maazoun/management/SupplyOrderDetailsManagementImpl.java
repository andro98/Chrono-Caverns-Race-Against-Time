package com.aman.payment.maazoun.management;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.auth.service.SectorService;
import com.aman.payment.core.exception.TransactionException;
import com.aman.payment.maazoun.mapper.SupplyOrderDetailsMapper;
import com.aman.payment.maazoun.model.SupplyOrder;
import com.aman.payment.maazoun.model.SupplyOrderDetails;
import com.aman.payment.maazoun.model.dto.BooksPagingDTO;
import com.aman.payment.maazoun.model.dto.SupplyOrderDetailsDTO;
import com.aman.payment.maazoun.model.payload.AddSupplyOrderDetails;
import com.aman.payment.maazoun.model.payload.AddSupplyOrderDetailsListRequest;
import com.aman.payment.maazoun.model.payload.BooksRequest;
import com.aman.payment.maazoun.model.payload.SupplyOrderDetailsBetweenDates;
import com.aman.payment.maazoun.model.payload.SupplyOrderDetailsSearchRequest;
import com.aman.payment.maazoun.service.CryptoMngrMaazounService;
import com.aman.payment.maazoun.service.SupplyOrderDetailsService;
import com.aman.payment.maazoun.service.SupplyOrderService;
import com.aman.payment.util.StatusConstant;
import com.aman.payment.util.Util;

@Component
public class SupplyOrderDetailsManagementImpl extends ValidationAndPopulateManagement
	implements SupplyOrderDetailsManagement {

	final static Logger logger = Logger.getLogger("supplyOrderDetails");

	private final SupplyOrderService supplyOrderService;
	private final SupplyOrderDetailsService supplyOrderDetailsService;
	private final SectorService sectorService;
	private final CryptoMngrMaazounService cryptoMngrMaazounService;
	private final SupplyOrderDetailsMapper supplyOrderDetailsMapper;

	@Value("${attachment.maazoun.supplyOrdersDetails}")
	private String supplyOrdersDetailsPathAtt;

	@Autowired
	public SupplyOrderDetailsManagementImpl(SupplyOrderDetailsService supplyOrderDetailsService,
			CryptoMngrMaazounService cryptoMngrMaazounService, SupplyOrderDetailsMapper supplyOrderDetailsMapper,
			SupplyOrderService supplyOrderService, SectorService sectorService) {
		this.supplyOrderDetailsService = supplyOrderDetailsService;
		this.cryptoMngrMaazounService = cryptoMngrMaazounService;
		this.supplyOrderDetailsMapper = supplyOrderDetailsMapper;
		this.supplyOrderService = supplyOrderService;
		this.sectorService = sectorService;
	}

	@Override
	public String addSupplyOrderDetails(CustomUserDetails customUserDetails,
			AddSupplyOrderDetails addSupplyOrderDetails) {
		
		if (customUserDetails == null || addSupplyOrderDetails == null || addSupplyOrderDetails.getSectorId() == null) {
	        throw new IllegalArgumentException("Invalid input parameters.");
	    }
		
		Date createdAt = Date.from(Instant.now());

		Pos pos = getAssignedPOS(customUserDetails.getPosSet(), addSupplyOrderDetails.getSectorId());
		Sector sector = getSectorById(pos, addSupplyOrderDetails.getSectorId());


		String sectorFK = Long.toString(addSupplyOrderDetails.getSectorId());
		
		String atteUrl;
		try {
			atteUrl = saveBrowseAttFile(addSupplyOrderDetails.getAttUrl(), supplyOrdersDetailsPathAtt,
					addSupplyOrderDetails.getSupplyOrderReferenceNumber() + "-" + customUserDetails.getUsername());
		} catch (Exception e) {
			throw new NullPointerException("imge must not be null");
			// e.printStackTrace();
		}
		
		SupplyOrder supplyOrder = new SupplyOrder();
		supplyOrder.setCreatedAt(createdAt);
		supplyOrder.setCreatedBy(customUserDetails.getUsername());
		supplyOrder.setImageUrl(atteUrl);
		supplyOrder.setIsReviewed(false);
		supplyOrder.setPosFk(pos);
		supplyOrder.setSectorFk(sector);
		supplyOrder.setStatusFk(StatusConstant.STATUS_PENDING);
		supplyOrder.setLocationId(sector.getLocationFk().getId());
		supplyOrder.setRefSupplyOrderNumber(addSupplyOrderDetails.getSupplyOrderReferenceNumber());
		
		SupplyOrder supplyOrderFk = supplyOrderService.save(supplyOrder);
		
		List<SupplyOrderDetails> entities = addSupplyOrderDetailsToSupplyOrderDetailsEntity(
				addSupplyOrderDetails, sectorFK, supplyOrderFk, 
				customUserDetails.getUsername(), createdAt);
		
		supplyOrderDetailsService.save(entities);
		
		updateSupplyOrderReference(sector, addSupplyOrderDetails.getSequance(), 
				addSupplyOrderDetails.getCurrentYearTwoDigits());
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("response", "sucsse");

		return cryptoMngrMaazounService.encrypt(jsonObject.toString());
	}
	
	private void updateSupplyOrderReference(Sector sector, long sequance, long year) {
		
		sector.setSupplyOrderSeqRef(sequance);
		sector.setSupplyOrderYearRef(year);
		sectorService.save(sector);
	}
	
	private List<SupplyOrderDetails> addSupplyOrderDetailsToSupplyOrderDetailsEntity(
			AddSupplyOrderDetails addSupplyOrder, String sectorFk, SupplyOrder supplyOrderFk, 
			String username, Date createdAt) {

		List<SupplyOrderDetails> entites = new ArrayList<SupplyOrderDetails>();

		for (AddSupplyOrderDetailsListRequest addSupplyOrderDetailsListRequest : addSupplyOrder
				.getSupplyOrderDetailsList()) {

			SupplyOrderDetails supplyOrderDetails = new SupplyOrderDetails();

			supplyOrderDetails.setCreatedAt(createdAt);
			supplyOrderDetails.setCreatedBy(username);
			supplyOrderDetails.setBookType(addSupplyOrderDetailsListRequest.getBootTypeName());
			supplyOrderDetails.setBookTypeFK(addSupplyOrderDetailsListRequest.getBootTypeId());
			supplyOrderDetails.setSectorFK(sectorFk);
			supplyOrderDetails.setSectorName(addSupplyOrder.getSectorName());
			supplyOrderDetails.setBookTypeCount(addSupplyOrderDetailsListRequest.getCount());
			supplyOrderDetails.setCurrentBookTypeCount(Integer.parseInt(addSupplyOrderDetailsListRequest.getCount()));
			supplyOrderDetails.setRemainingBookTypeCount(Integer.parseInt(addSupplyOrderDetailsListRequest.getCount()));
			supplyOrderDetails.setSupplyOrderFk(supplyOrderFk);
			entites.add(supplyOrderDetails);
		}

		return entites;
	}

	@Override
	public BooksPagingDTO getAllSupplyOrderDetails(SupplyOrderDetailsSearchRequest supplyOrderDetailsSearchRequest,
			CustomUserDetails customUserDetails) {
		Pageable pageable = PageRequest.of(Integer.valueOf(supplyOrderDetailsSearchRequest.getPageNo()),
				Integer.valueOf(supplyOrderDetailsSearchRequest.getPageSize()));

		Page<SupplyOrderDetails> pageResult = null;

		Set<String> sectorIds = new HashSet<String>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			for (Sector sector : s.getSectors()) {
				sectorIds.add(String.valueOf(sector.getId()));
			}
		});

		pageResult = supplyOrderDetailsService.findBysectorFKIn(sectorIds, pageable);

		BooksPagingDTO booksPagingDTO = new BooksPagingDTO();
		booksPagingDTO.setCount(pageResult.getTotalElements());
		booksPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<SupplyOrderDetailsDTO> eBooksDTO = new ArrayList<SupplyOrderDetailsDTO>();
		pageResult.getContent().stream()
				.forEach(s -> {
					if(s.getSupplyOrderFk().getStatusFk().equals(StatusConstant.STATUS_APPROVED))
						eBooksDTO.add(supplyOrderDetailsMapper.supplyOrderDetailsToSupplyOrderDetailsDTO(s));
				});

		List<String> eBooks = new ArrayList<String>();
		eBooksDTO.stream().forEach(s -> {
			eBooks.add(cryptoMngrMaazounService.encrypt(s.toString()));
		});

		booksPagingDTO.setBooks(eBooks);

		return booksPagingDTO;
	}

//                          get all supply order details
	@Override
	public BooksPagingDTO getAllSupplyOrderDetails(BooksRequest booksRequest, CustomUserDetails customUserDetails) {
		Pageable pageable = PageRequest.of(Integer.valueOf(booksRequest.getPageNo()),
				Integer.valueOf(booksRequest.getPageSize()));
		Page<SupplyOrderDetails> pageResult = null;
		Set<Long> sectorIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			for (Sector sector : s.getSectors()) {
				sectorIds.add(sector.getId());
			}
		});
		pageResult = supplyOrderDetailsService.findAllBySectorFK(sectorIds, pageable, customUserDetails);

		BooksPagingDTO booksPagingDTO = new BooksPagingDTO();
		booksPagingDTO.setCount(pageResult.getTotalElements());
		booksPagingDTO.setTotalPages(pageResult.getTotalPages());

		List<SupplyOrderDetailsDTO> eBooksDTO = new ArrayList<SupplyOrderDetailsDTO>();
		supplyOrderDetailsMapper
				.supplyOrdersDetailsToSupplyOrderDetailsDTOs(pageResult.getContent()).stream()
				.forEach(s -> {
					if (!eBooksDTO.contains(s))
						eBooksDTO.add(s);
				});

		List<String> eBooks = new ArrayList<String>();
		eBooksDTO.stream().forEach(s -> {
			eBooks.add(cryptoMngrMaazounService.encrypt(s.toString()));
		});
		booksPagingDTO.setBooks(eBooks);

		return booksPagingDTO;
	}

//                        	find all supply order between two dates
	@Override
	public BooksPagingDTO findBookBetweenDates(SupplyOrderDetailsBetweenDates supplyOrderDetailsBetweenDates,
			CustomUserDetails customUserDetails) {
		// pagable
		Pageable pageable = PageRequest.of(Integer.valueOf(supplyOrderDetailsBetweenDates.getPageNo()),
				Integer.valueOf(supplyOrderDetailsBetweenDates.getPageSize()));

		Page<SupplyOrderDetails> pageResult = null;

		Set<Long> sectorIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			for (Sector sector : s.getSectors()) {
				sectorIds.add(sector.getId());
			}
		});
		String CreateAt = supplyOrderDetailsBetweenDates.getCreateAt();
		String CreateAtEnd = supplyOrderDetailsBetweenDates.getCreateAtEnd();
		pageResult = supplyOrderDetailsService.findAllByCreatedAtBetweenAndSectorFK(CreateAt, CreateAtEnd, sectorIds,
				pageable);

		BooksPagingDTO booksPagingDTOOo = new BooksPagingDTO();
		booksPagingDTOOo.setCount(pageResult.getTotalElements());
		booksPagingDTOOo.setTotalPages(pageResult.getTotalPages());

		List<SupplyOrderDetailsDTO> eBooksDTO = new ArrayList<SupplyOrderDetailsDTO>();
		supplyOrderDetailsMapper
				.supplyOrdersDetailsToSupplyOrderDetailsDTOs(pageResult.getContent()).stream()
				.forEach(a -> {
					if (!eBooksDTO.contains(a))
						eBooksDTO.add(a);
				});

		// convert SupplyOrderDetailsDTO to String and encrpt it
		List<String> eBooks = new ArrayList<String>();
		eBooksDTO.stream().forEach(w -> {
			eBooks.add(cryptoMngrMaazounService.encrypt(w.toString()));
		});
		booksPagingDTOOo.setBooks(eBooks);

		return booksPagingDTOOo;

	}
	
	public String saveBrowseAttFile(MultipartFile att, String url, String fileName) throws Exception {
		Path fileStorageLocation = null;
		fileName = fileName + "_" + Date.from(Instant.now()).getTime();
		if (att != null && !att.getOriginalFilename().equals("foo.txt")) {
			try {
				fileStorageLocation = Paths.get(url + "/" + Util.dateFormat() + "/" + fileName + "." + "png")
						.toAbsolutePath().normalize();

				Path path = Paths.get(fileStorageLocation.toString());
				// System.gc();
				File f = new File(fileStorageLocation.toString());
				if (f.exists()) {
					FileUtils.forceDelete(new File(fileStorageLocation.toString()));

				}
//				Files.deleteIfExists(Paths.get(fileStorageLocation.toUri()));
				Files.createDirectories(path);
				Files.copy(att.getInputStream(), Paths.get(fileStorageLocation.toUri()),
						StandardCopyOption.REPLACE_EXISTING);

			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("saveAttFile An exception when saveAttachedFile! ", ex);
				throw new TransactionException("saveAttFile An exception when saveAttachedFile with! " + url);
			}

		}
		return fileStorageLocation.toString();

	}
	
	private Sector getSectorById(Pos pos, Long sectorId) {
	    if (sectorId == null) {
	        throw new IllegalArgumentException("Sector ID cannot be null.");
	    }

	    Optional<Sector> foundSector = pos.getSectors().stream()
	            .filter(sector -> sector.getId().equals(sectorId))
	            .findAny();

	    return foundSector.orElse(null);
	}

	@Override
	public List<String> searchForSupplyOrderDetails(SupplyOrderDetailsSearchRequest supplyOrderDetailsSearchRequest,
			CustomUserDetails customUserDetails) {

		Set<Long> sectorIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			for (Sector sector : s.getSectors()) {
				sectorIds.add(sector.getId());
			}
		});
		
		 List<SupplyOrderDetails> list = supplyOrderDetailsService.searchSupplyOrderDetails(supplyOrderDetailsSearchRequest, sectorIds);
 

		List<SupplyOrderDetailsDTO> eBooksDTO = new ArrayList<SupplyOrderDetailsDTO>();
		list.stream()
				.forEach(s -> {
					if(s.getSupplyOrderFk().getStatusFk().equals(StatusConstant.STATUS_APPROVED))
						eBooksDTO.add(supplyOrderDetailsMapper.supplyOrderDetailsToSupplyOrderDetailsDTO(s));
				});

		List<String> eBooks = new ArrayList<String>();
		eBooksDTO.stream().forEach(s -> {
			eBooks.add(cryptoMngrMaazounService.encrypt(s.toString()));
		});
		
		return eBooks;
	}

}
