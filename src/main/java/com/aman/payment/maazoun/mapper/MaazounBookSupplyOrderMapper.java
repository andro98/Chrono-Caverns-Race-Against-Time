package com.aman.payment.maazoun.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aman.payment.maazoun.model.MaazounBookSupplyOrder;
import com.aman.payment.maazoun.model.SupplyOrder;
import com.aman.payment.maazoun.model.dto.SupplyOrderDTO;

@Service
public class MaazounBookSupplyOrderMapper {

	/*
	 * MaazounBookSupplyOrder to MaazounBookSupplyOrderDTO
	 * MaazounBookSupplyOrders to MaazounBookSupplyOrders
	 * ****************************************************************************************************
	 */
	public SupplyOrderDTO maazounBookSupplyOrderToMaazounBookSupplyOrderDTO(MaazounBookSupplyOrder maazounBookSupplyOrder) {
		if (maazounBookSupplyOrder!=null) {
	        return createSupplyOrderDTO(maazounBookSupplyOrder);

		}
		else return null ;
    }
	
	public SupplyOrderDTO supplyOrderToSupplyOrderDTO(SupplyOrder supplyOrder) {
		if (supplyOrder!=null) {
	        return createSupplyOrderDTO(supplyOrder);

		}
		else return null ;
    }
	
	public List<SupplyOrderDTO> maazounBookSupplyOrdersToMaazounBookSupplyOrderDTOs(List<MaazounBookSupplyOrder> maazounBookSupplyOrderSet) {
		return maazounBookSupplyOrderSet.stream().filter(Objects::nonNull)
				.map(this::maazounBookSupplyOrderToMaazounBookSupplyOrderDTO).collect(Collectors.toList());
	}
	
	public List<SupplyOrderDTO> supplyOrdersToSupplyOrderDTOs(List<SupplyOrder> supplyOrderSet) {
		return supplyOrderSet.stream().filter(Objects::nonNull)
				.map(this::supplyOrderToSupplyOrderDTO).collect(Collectors.toList());
	}
	
	
	private SupplyOrderDTO createSupplyOrderDTO(MaazounBookSupplyOrder maazounBookSupplyOrder) {
		
		SupplyOrderDTO supplyOrderDTO = new SupplyOrderDTO();
		supplyOrderDTO.setCreateAt(String.valueOf(maazounBookSupplyOrder.getCreatedAt()));
		supplyOrderDTO.setCreateBy(maazounBookSupplyOrder.getCreatedBy());
		supplyOrderDTO.setId(String.valueOf(maazounBookSupplyOrder.getId()));
		supplyOrderDTO.setLocationId(String.valueOf(maazounBookSupplyOrder.getSectorFk().getLocationFk().getId()));
		supplyOrderDTO.setLocationName(maazounBookSupplyOrder.getSectorFk().getLocationFk().getName());
		supplyOrderDTO.setPosId(String.valueOf(maazounBookSupplyOrder.getPosFk().getId()));
		supplyOrderDTO.setPosName(maazounBookSupplyOrder.getPosFk().getName());
		supplyOrderDTO.setSectorId(String.valueOf(maazounBookSupplyOrder.getSectorFk().getId()));
		supplyOrderDTO.setSectorName(maazounBookSupplyOrder.getSectorFk().getName());
		supplyOrderDTO.setImageUrl(maazounBookSupplyOrder.getImageUrl());
		supplyOrderDTO.setIsReviewed(String.valueOf(maazounBookSupplyOrder.getIsReviewed()));
		supplyOrderDTO.setStatus(maazounBookSupplyOrder.getStatusFk());
		supplyOrderDTO.setUpdatedAt(String.valueOf(maazounBookSupplyOrder.getUpdatedAt()));
		supplyOrderDTO.setUpdatedBy(maazounBookSupplyOrder.getUpdatedBy());
		supplyOrderDTO.setRefSupplyOrderNumber(maazounBookSupplyOrder.getRefSupplyOrderNumber());
		supplyOrderDTO.setSupplyOrderType(maazounBookSupplyOrder.getIsCustody()?"عهده":"الكترونى");
		supplyOrderDTO.setCustody(String.valueOf(maazounBookSupplyOrder.getIsCustody()));

//		maazounBookSupplyOrder.getMaazounBookWarehouseSet().stream().
//        		filter(c-> c.getBookTypeId()==9).collect(Collectors.toList());
		
		Set<String> marrigeBookSet = new HashSet<String>();
		maazounBookSupplyOrder.getMaazounBookWarehouseSet().stream().forEach(s -> {
			if(s.getBookTypeId() == 9 || s.getBookTypeId() == 19) {
				marrigeBookSet.add(s.getSerialNumber());
			}
		});
		
		supplyOrderDTO.setMarriageBookCount(marrigeBookSet.size());
		
		
		Set<String> divorceBookCountSet = new HashSet<String>();
		maazounBookSupplyOrder.getMaazounBookWarehouseSet().stream().forEach(s -> {
			if(s.getBookTypeId() == 10 || s.getBookTypeId() == 20) {
				divorceBookCountSet.add(s.getSerialNumber());
			}
		});
		
 		supplyOrderDTO.setDivorceBookCount(divorceBookCountSet.size());
		
		Set<String> authenticationBookCountSet = new HashSet<String>();
		maazounBookSupplyOrder.getMaazounBookWarehouseSet().stream().forEach(s -> {
			if(s.getBookTypeId() == 11 || s.getBookTypeId() == 21) {
				authenticationBookCountSet.add(s.getSerialNumber());
			}
		});
		
		supplyOrderDTO.setAuthenticationBookCount(authenticationBookCountSet.size());
		
		
		Set<String> reviewBookSet = new HashSet<String>();
		maazounBookSupplyOrder.getMaazounBookWarehouseSet().stream().forEach(s -> {
			if(s.getBookTypeId() == 12 || s.getBookTypeId() == 22) {
				reviewBookSet.add(s.getSerialNumber());
			}
		});
		
		supplyOrderDTO.setReviewBookCount(reviewBookSet.size());
		
		
		Set<String> mullahMarriageBookSet = new HashSet<String>();
		maazounBookSupplyOrder.getMaazounBookWarehouseSet().stream().forEach(s -> {
			if(s.getBookTypeId() == 13 || s.getBookTypeId() == 23) {
				mullahMarriageBookSet.add(s.getSerialNumber());
			}
		});
		
		supplyOrderDTO.setMullahMarriageBookCount(mullahMarriageBookSet.size());
		 
		
		
		return supplyOrderDTO;
	}
	
	private SupplyOrderDTO createSupplyOrderDTO(SupplyOrder supplyOrder) {
		
		SupplyOrderDTO supplyOrderDTO = new SupplyOrderDTO();
		supplyOrderDTO.setCreateAt(String.valueOf(supplyOrder.getCreatedAt()));
		supplyOrderDTO.setCreateBy(supplyOrder.getCreatedBy());
		supplyOrderDTO.setId(String.valueOf(supplyOrder.getId()));
		supplyOrderDTO.setLocationId(String.valueOf(supplyOrder.getSectorFk().getLocationFk().getId()));
		supplyOrderDTO.setLocationName(supplyOrder.getSectorFk().getLocationFk().getName());
		supplyOrderDTO.setPosId(String.valueOf(supplyOrder.getPosFk().getId()));
		supplyOrderDTO.setPosName(supplyOrder.getPosFk().getName());
		supplyOrderDTO.setSectorId(String.valueOf(supplyOrder.getSectorFk().getId()));
		supplyOrderDTO.setSectorName(supplyOrder.getSectorFk().getName());
		supplyOrderDTO.setImageUrl(supplyOrder.getImageUrl());
		supplyOrderDTO.setIsReviewed(String.valueOf(supplyOrder.getIsReviewed()));
		supplyOrderDTO.setStatus(supplyOrder.getStatusFk());
		supplyOrderDTO.setUpdatedAt(String.valueOf(supplyOrder.getUpdatedAt()));
		supplyOrderDTO.setUpdatedBy(supplyOrder.getUpdatedBy());
		supplyOrderDTO.setRefSupplyOrderNumber(supplyOrder.getRefSupplyOrderNumber());
		supplyOrderDTO.setSupplyOrderType("الكترونى");

		supplyOrder.getSupplyOrderDetailsSet().stream().forEach(s -> {
			if(Integer.valueOf(s.getBookTypeFK()) == 9) {
				supplyOrderDTO.setMarriageBookCount8(supplyOrderDTO.getMarriageBookCount8() + 
						s.getCurrentBookTypeCount());
			}
			else if(Integer.valueOf(s.getBookTypeFK()) == 19) {
				supplyOrderDTO.setMarriageBookCount15(supplyOrderDTO.getMarriageBookCount15() + 
						s.getCurrentBookTypeCount());
			}
			else if(Integer.valueOf(s.getBookTypeFK()) == 10) {
				supplyOrderDTO.setDivorceBookCount8(supplyOrderDTO.getDivorceBookCount15() + 
						s.getCurrentBookTypeCount());
			}
			else if(Integer.valueOf(s.getBookTypeFK()) == 20) {
				supplyOrderDTO.setDivorceBookCount15(supplyOrderDTO.getDivorceBookCount15() + 
						s.getCurrentBookTypeCount());
			}
			else if(Integer.valueOf(s.getBookTypeFK()) == 11) {
				supplyOrderDTO.setAuthenticationBookCount8(supplyOrderDTO.getAuthenticationBookCount8() + 
						s.getCurrentBookTypeCount());
			}
			else if(Integer.valueOf(s.getBookTypeFK()) == 21) {
				supplyOrderDTO.setAuthenticationBookCount15(supplyOrderDTO.getAuthenticationBookCount15() + 
						s.getCurrentBookTypeCount());
			}
			else if(Integer.valueOf(s.getBookTypeFK()) == 12) {
				supplyOrderDTO.setReviewBookCount8(supplyOrderDTO.getReviewBookCount8() + 
						s.getCurrentBookTypeCount());
			}
			else if(Integer.valueOf(s.getBookTypeFK()) == 22) {
				supplyOrderDTO.setReviewBookCount15(supplyOrderDTO.getReviewBookCount15() + 
						s.getCurrentBookTypeCount());
			}
			else if(Integer.valueOf(s.getBookTypeFK()) == 13) {
				supplyOrderDTO.setMullahMarriageBookCount8(supplyOrderDTO.getMullahMarriageBookCount8() + 
						s.getCurrentBookTypeCount());
			}
			else if(Integer.valueOf(s.getBookTypeFK()) == 23) {
				supplyOrderDTO.setMullahMarriageBookCount15(supplyOrderDTO.getMullahMarriageBookCount15() + 
						s.getCurrentBookTypeCount());
			}
			
		});
		
		return supplyOrderDTO;
	}
	

}
