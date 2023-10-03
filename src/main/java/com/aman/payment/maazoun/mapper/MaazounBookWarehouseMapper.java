package com.aman.payment.maazoun.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.SubService;
import com.aman.payment.auth.model.SubServiceQuota;
import com.aman.payment.auth.service.SubServiceQuotaService;
import com.aman.payment.maazoun.model.MaazounBookQuota;
import com.aman.payment.maazoun.model.MaazounBookWarehouse;
import com.aman.payment.maazoun.model.dto.BookDTO;
import com.aman.payment.util.StatusConstant;

@Service
public class MaazounBookWarehouseMapper extends MaazounBookQuota{

	/*
	 * maazounBookWarehouse to bookDTO
	 * maazounBookWarehouseList to bookDTOs
	 * ****************************************************************************************************
	 */
	@Value("${aman.logo}")
	private String amanLogoUrl;
	@Autowired
	private SubServiceQuotaService subServiceQuotaService;
	
	public BookDTO maazounBookWarehouseToBookDTO(MaazounBookWarehouse maazounBookWarehouse, SubService subService) {
		if (maazounBookWarehouse!=null) {
	        return createBookDTO(maazounBookWarehouse, subService);

		}
		else return null ;
    }
	
	public BookDTO maazounBookWarehouseToBookDTO(MaazounBookWarehouse maazounBookWarehouse) {
		if (maazounBookWarehouse!=null) {
	        return createBookDTO(maazounBookWarehouse);

		}
		else return null ;
    }
	
	public BookDTO previewContractCollectionDTO(MaazounBookWarehouse maazounBookWarehouse, SubService subService) {
		if (maazounBookWarehouse!=null) {
	        return createPreviewContractCollectionDTO(maazounBookWarehouse, subService);

		}
		else return null ;
    }
	
	public List<BookDTO> maazounBookWarehouseSetToBookDTOs(List<MaazounBookWarehouse> maazounBookWarehouseSet) {
		List<BookDTO> dtoList = new ArrayList<BookDTO>();
		maazounBookWarehouseSet.stream().forEach(s -> {
			dtoList.add(maazounBookWarehouseToBookDTO(s));
		});
		return dtoList;
		
	}
	
	private BookDTO createPreviewContractCollectionDTO(MaazounBookWarehouse maazounBookWarehouse, SubService subService) {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setLocationName(maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getLocationFk().getName():null);
//		bookDTO.setSectorName(maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getName():null);
//		bookDTO.setSectorId(maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?String.valueOf(maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getId()):null);
		
		bookDTO.setSectorName(maazounBookWarehouse.getMaazounBookRequestInfoFk()!=null?maazounBookWarehouse.getMaazounBookRequestInfoFk().getSectorFk().getName():
			maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getName():null);
		bookDTO.setSectorId(maazounBookWarehouse.getMaazounBookRequestInfoFk()!=null?String.valueOf(maazounBookWarehouse.getMaazounBookRequestInfoFk().getSectorFk().getId()):
			maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?String.valueOf(maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getId()):null);

		bookDTO.setBookTypeName(subService.getBookType());
		bookDTO.setMaazounWarehouseId(String.valueOf(maazounBookWarehouse.getId()));
		bookDTO.setBookTypeId(String.valueOf(maazounBookWarehouse.getBookTypeId()));
		bookDTO.setSerialNumber(maazounBookWarehouse.getSerialNumber());
		bookDTO.setBookFinancialNumber(maazounBookWarehouse.getBookFinancialNumber());
		bookDTO.setStatus(maazounBookWarehouse.getStatusFk());
		bookDTO.setContractCount(maazounBookWarehouse.getContractCount());
		bookDTO.setContractNumber(maazounBookWarehouse.getContractNumber());
		bookDTO.setContractFinancialNumber(maazounBookWarehouse.getContractFinancialNumber());
		if(maazounBookWarehouse.getMaazounBookRequestInfoFk() != null) {
			bookDTO.setMaazounNationalId(String.valueOf(maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazounProfileFk().getNationalId()));
			bookDTO.setMaazounId(maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazounProfileFk()!=null?String.valueOf(maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazounProfileFk().getId()):null);
			bookDTO.setMaazouniaChurchNameType(maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazouniaName());
		}
		
		return bookDTO;
	}
	
	private BookDTO createBookDTO(MaazounBookWarehouse maazounBookWarehouse) {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setMaazounWarehouseId(String.valueOf(maazounBookWarehouse.getId()));
		bookDTO.setCreateAt(maazounBookWarehouse.getMaazounBookSupplyOrderFk() !=null?String.valueOf(maazounBookWarehouse.getMaazounBookSupplyOrderFk().getCreatedAt()):null);
		bookDTO.setCreateBy(maazounBookWarehouse.getMaazounBookSupplyOrderFk() !=null?maazounBookWarehouse.getMaazounBookSupplyOrderFk().getCreatedBy():null);
		bookDTO.setSupplyOrderId(maazounBookWarehouse.getMaazounBookSupplyOrderFk() !=null?String.valueOf(maazounBookWarehouse.getMaazounBookSupplyOrderFk().getId()):null);
		bookDTO.setId(String.valueOf(maazounBookWarehouse.getId()));
		bookDTO.setLocationId(maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?String.valueOf(maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getLocationFk().getId()):null);
		bookDTO.setLocationName(maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getLocationFk().getName():null);
		bookDTO.setPosId(maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?String.valueOf(maazounBookWarehouse.getMaazounBookSupplyOrderFk().getPosFk().getId()):null);
		bookDTO.setPosName(maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?maazounBookWarehouse.getMaazounBookSupplyOrderFk().getPosFk().getName():null);
//		bookDTO.setSectorId(maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?String.valueOf(maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getId()):null);
//		bookDTO.setSectorName(maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getName():null);
		bookDTO.setRefSupplyOrderNumber(maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?maazounBookWarehouse.getMaazounBookSupplyOrderFk().getRefSupplyOrderNumber():"");
		bookDTO.setSectorName(maazounBookWarehouse.getMaazounBookRequestInfoFk()!=null?maazounBookWarehouse.getMaazounBookRequestInfoFk().getSectorFk().getName():
			maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getName():null);
		bookDTO.setSectorId(maazounBookWarehouse.getMaazounBookRequestInfoFk()!=null?String.valueOf(maazounBookWarehouse.getMaazounBookRequestInfoFk().getSectorFk().getId()):
			maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?String.valueOf(maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getId()):null);
		
		
		bookDTO.setSerialNumber(maazounBookWarehouse.getSerialNumber());
		bookDTO.setType(maazounBookWarehouse.getBookTypeName());
		bookDTO.setTypeId(String.valueOf(maazounBookWarehouse.getBookTypeId()));
		bookDTO.setBookType(maazounBookWarehouse.getBookTypeName());
		bookDTO.setBookTypeId(String.valueOf(maazounBookWarehouse.getBookTypeId()));
		bookDTO.setBookFinancialNumber(maazounBookWarehouse.getBookFinancialNumber());
		bookDTO.setStatus(maazounBookWarehouse.getStatusFk());
		bookDTO.setContractCount(maazounBookWarehouse.getContractCount());
		bookDTO.setContractNumber(maazounBookWarehouse.getContractNumber());
		bookDTO.setContractFinancialNumber(maazounBookWarehouse.getContractFinancialNumber());
		bookDTO.setCustody(String.valueOf(maazounBookWarehouse.getMaazounBookSupplyOrderFk().getIsCustody()));

		if(maazounBookWarehouse.getMaazounBookRequestInfoFk() != null) {
			bookDTO.setMaazounname(maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazounProfileFk().getName());
			bookDTO.setMaazounNationalId(String.valueOf(maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazounProfileFk().getNationalId()));
			bookDTO.setMaazounId(maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazounProfileFk()!=null?String.valueOf(maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazounProfileFk().getId()):null);
			bookDTO.setMaazouniaChurchNameType(maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazouniaName()!=null?
					maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazouniaName():
						maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazounProfileFk().
						getMaazounMaazouniaChurch().stream().findFirst().get().getMaazouniaChurchFk().getName());
		}
		if(maazounBookWarehouse.getMaazounBookCollectionInfoFk() != null) {
			bookDTO.setCollectionInfoId(String.valueOf(maazounBookWarehouse.getMaazounBookCollectionInfoFk().getId()));
			bookDTO.setReceivedStatus(maazounBookWarehouse.getMaazounBookCollectionInfoFk().getReceivedStatus());

		}
		else {
			bookDTO.setReceivedStatus("غير محصل");
		}
		
		bookDTO.setAmanLogoUrl(amanLogoUrl);
		bookDTO.setBookInventoryReference(maazounBookWarehouse.getBookInventoryNumber()+"/"+maazounBookWarehouse.getBookInventoryReference());
		
		return bookDTO;
	}
	
	private BookDTO createBookDTO(MaazounBookWarehouse maazounBookWarehouse, SubService subService) {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setMaazounWarehouseId(String.valueOf(maazounBookWarehouse.getId()));
		bookDTO.setCreateAt(maazounBookWarehouse.getMaazounBookSupplyOrderFk() !=null?String.valueOf(maazounBookWarehouse.getMaazounBookSupplyOrderFk().getCreatedAt()):null);
		bookDTO.setCreateBy(maazounBookWarehouse.getMaazounBookSupplyOrderFk() !=null?maazounBookWarehouse.getMaazounBookSupplyOrderFk().getCreatedBy():null);
		bookDTO.setId(String.valueOf(maazounBookWarehouse.getId()));
		bookDTO.setLocationId(maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?String.valueOf(maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getLocationFk().getId()):null);
		bookDTO.setLocationName(maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getLocationFk().getName():null);
		bookDTO.setPosId(maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?String.valueOf(maazounBookWarehouse.getMaazounBookSupplyOrderFk().getPosFk().getId()):null);
		bookDTO.setPosName(maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?maazounBookWarehouse.getMaazounBookSupplyOrderFk().getPosFk().getName():null);
//		bookDTO.setSectorId(maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?String.valueOf(maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getId()):null);
//		bookDTO.setSectorName(maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getName():null);
		
		bookDTO.setSectorName(maazounBookWarehouse.getMaazounBookRequestInfoFk()!=null?maazounBookWarehouse.getMaazounBookRequestInfoFk().getSectorFk().getName():
			maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getName():null);
		bookDTO.setSectorId(maazounBookWarehouse.getMaazounBookRequestInfoFk()!=null?String.valueOf(maazounBookWarehouse.getMaazounBookRequestInfoFk().getSectorFk().getId()):
			maazounBookWarehouse.getMaazounBookSupplyOrderFk()!=null?String.valueOf(maazounBookWarehouse.getMaazounBookSupplyOrderFk().getSectorFk().getId()):null);
		
		
		bookDTO.setSerialNumber(maazounBookWarehouse.getSerialNumber());
		bookDTO.setType(maazounBookWarehouse.getBookTypeName());
		bookDTO.setTypeId(String.valueOf(maazounBookWarehouse.getBookTypeId()));
		bookDTO.setBookType(maazounBookWarehouse.getBookTypeName());
		bookDTO.setBookTypeId(String.valueOf(maazounBookWarehouse.getBookTypeId()));
		bookDTO.setBookFinancialNumber(maazounBookWarehouse.getBookFinancialNumber());
		bookDTO.setStatus(maazounBookWarehouse.getStatusFk());
		bookDTO.setContractCount(maazounBookWarehouse.getContractCount());
		bookDTO.setContractNumber(maazounBookWarehouse.getContractNumber());
		bookDTO.setBookFinancialNumber(maazounBookWarehouse.getBookFinancialNumber());
		bookDTO.setContractFinancialNumber(maazounBookWarehouse.getContractFinancialNumber());
		List<SubServiceQuota> subServiceQuota = subServiceQuotaService.findBySubServiceFk(subService);
		double fees = (double) 0;
		for(SubServiceQuota quota : subServiceQuota) {
			if(quota.getStatusFk().equals(StatusConstant.STATUS_ACTIVE) && quota.getFeesType().equals("n"))
				fees = fees + quota.getFees();
		}
		bookDTO.setFees(fees);
		bookDTO.setCustody(String.valueOf(maazounBookWarehouse.getMaazounBookSupplyOrderFk().getIsCustody()));
		
		if(maazounBookWarehouse.getMaazounBookRequestInfoFk() != null) {
			bookDTO.setMaazounname(maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazounProfileFk().getName());
			bookDTO.setMaazounNationalId(String.valueOf(maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazounProfileFk().getNationalId()));
			bookDTO.setMaazounId(maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazounProfileFk()!=null?String.valueOf(maazounBookWarehouse.getMaazounBookRequestInfoFk().getMaazounProfileFk().getId()):null);

		}
		if(maazounBookWarehouse.getMaazounBookCollectionInfoFk() != null) {
			bookDTO.setCollectionInfoId(String.valueOf(maazounBookWarehouse.getMaazounBookCollectionInfoFk().getId()));
			bookDTO.setReceivedStatus(maazounBookWarehouse.getMaazounBookCollectionInfoFk().getReceivedStatus());
		}
		
//		SubServiceQuota subServiceQuotaDarebtDamgha = subService.getSubServicesQuota().stream()
//				.filter(x -> x.getName().equalsIgnoreCase(StatusConstant.BOOK_QUOTA_DAREBT_DAMGHA) &&
//						x.getStatusFk().equals(StatusConstant.STATUS_ACTIVE))
//				.findAny().orElse(null);
//		if(subServiceQuotaDarebtDamgha != null)
//			bookDTO.setQuotaBookDarebtDamgha(subServiceQuotaDarebtDamgha.getFees());
//		
//		SubServiceQuota subServiceQuotaFahsFany = subService.getSubServicesQuota().stream()
//				.filter(x -> x.getName().equalsIgnoreCase(StatusConstant.BOOK_QUOTA_FAHS_FANY) &&
//						x.getStatusFk().equals(StatusConstant.STATUS_ACTIVE))
//				.findAny().orElse(null);
//		if(subServiceQuotaFahsFany != null)
//			bookDTO.setQuotaBookFahsFany(subServiceQuotaFahsFany.getFees());
//		
//		SubServiceQuota subServiceQuotaRasmTanmya = subService.getSubServicesQuota().stream()
//				.filter(x -> x.getName().equalsIgnoreCase(StatusConstant.BOOK_QUOTA_RASM_TANMYA) &&
//						x.getStatusFk().equals(StatusConstant.STATUS_ACTIVE))
//				.findAny().orElse(null);
//		if(subServiceQuotaRasmTanmya != null)
//			bookDTO.setQuotaBookRasmTanmya(subServiceQuotaRasmTanmya.getFees());
//		
//		SubServiceQuota subServiceQuotaTaklfa = subService.getSubServicesQuota().stream()
//				.filter(x -> x.getName().equalsIgnoreCase(StatusConstant.BOOK_QUOTA_TAKLFA) &&
//						x.getStatusFk().equals(StatusConstant.STATUS_ACTIVE))
//				.findAny().orElse(null);
//		if(subServiceQuotaTaklfa != null)
//			bookDTO.setQuotaBookTaklefa(subServiceQuotaTaklfa.getFees());
//		
//		SubServiceQuota subServiceQuotaTahweelRakmyAman = subService.getSubServicesQuota().stream()
//				.filter(x -> x.getName().equalsIgnoreCase(StatusConstant.BOOK_QUOTA_TAHWEEL_RAKMY_AMAN) &&
//						x.getStatusFk().equals(StatusConstant.STATUS_ACTIVE))
//				.findAny().orElse(null);
//		double tahweelRakmyValueAman = 0;
//		if(subServiceQuotaTahweelRakmyAman != null) {
//			tahweelRakmyValueAman = subServiceQuotaTahweelRakmyAman.getFees() * maazounBookWarehouse.getContractCount();
//			double tahweelRakmyValueWithAmountAman = subServiceQuotaTahweelRakmyAman.getFees() * (maazounBookWarehouse.getContractCount() - 1);
//			bookDTO.setQuotaBookTahweelRakmyAman(tahweelRakmyValueAman);
//			bookDTO.setFees(bookDTO.getFees() + tahweelRakmyValueWithAmountAman);
//		}
//		
//		SubServiceQuota subServiceQuotaTahweelRakmyNyaba = subService.getSubServicesQuota().stream()
//				.filter(x -> x.getName().equalsIgnoreCase(StatusConstant.BOOK_QUOTA_TAHWEEL_RAKMY_NYABA) &&
//						x.getStatusFk().equals(StatusConstant.STATUS_ACTIVE))
//				.findAny().orElse(null);
//		double tahweelRakmyValueNyaba = 0;
//		if(subServiceQuotaTahweelRakmyNyaba != null) {
//			tahweelRakmyValueNyaba = subServiceQuotaTahweelRakmyNyaba.getFees() * maazounBookWarehouse.getContractCount();
//			double tahweelRakmyValueWithAmountNyaba = subServiceQuotaTahweelRakmyNyaba.getFees() * (maazounBookWarehouse.getContractCount() - 1);
//			bookDTO.setQuotaBookTahweelRakmyNyaba(tahweelRakmyValueNyaba);
//			bookDTO.setFees(bookDTO.getFees() + tahweelRakmyValueWithAmountNyaba);
//		}
//		
//		bookDTO.setQuotaBookTahweelRakmy(tahweelRakmyValueAman + tahweelRakmyValueNyaba);
//		
//		
//		SubServiceQuota subServiceQuotaBookDarebtMabeat = subService.getSubServicesQuota().stream()
//				.filter(x -> x.getName().equalsIgnoreCase(StatusConstant.BOOK_QUOTA_DAREBT_MABEAT) &&
//						x.getStatusFk().equals(StatusConstant.STATUS_ACTIVE))
//				.findAny().orElse(null);
//		
//		if(subServiceQuotaBookDarebtMabeat != null) {
//			double darebtMabeatValue = Double.valueOf(bookDTO.getQuotaBookTahweelRakmy())/100*subServiceQuotaBookDarebtMabeat.getFees();
//			bookDTO.setQuotaBookDarebtMabeat(Double.valueOf(String.format("%.2f", darebtMabeatValue)));
//			double totalAmount = bookDTO.getFees() + darebtMabeatValue;
//			bookDTO.setFees(Double.valueOf(String.format("%.2f", totalAmount)));
//		}

		bookDTO.setAmanLogoUrl(amanLogoUrl);
		
		return bookDTO;
	}
	

}
