package com.aman.payment.maazoun.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.maazoun.model.MaazounBookWarehouse;
import com.aman.payment.maazoun.model.dto.WarehouseAuditDTO;
import com.aman.payment.maazoun.model.payload.BooksFilterRequest;
import com.aman.payment.maazoun.model.payload.BooksRequest;
import com.aman.payment.maazoun.model.payload.WarehouseBookRequest;
import com.aman.payment.util.StatusConstant;

@Repository
public class MaazounBookWarehouseCustomRepositoryImpl implements MaazounBookWarehouseCustomRepository {

	@PersistenceContext
	private EntityManager em;

	public MaazounBookWarehouseCustomRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public Page<MaazounBookWarehouse> findByLocationIdInOrderByCreatedAtDesc(Set<Long> locationIds, Pageable pageable) {

		String jpql = " SELECT mo FROM MaazounBookWarehouse mo "
				+ " JOIN MaazounBookSupplyOrder p ON p.id = mo.maazounBookSupplyOrderFk "
				+ " WHERE mo.statusFk = 'New' AND p.locationId IN ("
				+ locationIds.toString().replace("]", "").replace("[", "") + ") " + " GROUP BY mo.serialNumber";

		String jpqlCount = " SELECT count(mo) FROM MaazounBookWarehouse mo "
				+ " JOIN MaazounBookSupplyOrder p ON p.id = mo.maazounBookSupplyOrderFk "
				+ " WHERE mo.statusFk = 'New' AND p.locationId IN ("
				+ locationIds.toString().replace("]", "").replace("[", "") + ") ";

		TypedQuery<MaazounBookWarehouse> query = em.createQuery(jpql, MaazounBookWarehouse.class);
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		TypedQuery<Long> queryCount = em.createQuery(jpqlCount, Long.class);
		long total = queryCount.getSingleResult();

		return new PageImpl<MaazounBookWarehouse>(query.getResultList(), pageable, total);

	}

	@Override
	public Page<MaazounBookWarehouse> findBySectorFkInOrderByCreatedAtDesc(Set<Long> sectorIds, Pageable pageable,
			CustomUserDetails customUserDetails) {

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		jpql.append(" SELECT mo FROM MaazounBookWarehouse mo "
				+ " JOIN MaazounBookSupplyOrder p ON p.id = mo.maazounBookSupplyOrderFk "
				+ " WHERE mo.statusFk = 'New' ");
		 
		if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {

			jpql.append("AND p.sectorFk IN (" + sectorIds.toString().replace("]", "").replace("[", "") + ")");
		}
		jpql.append(" GROUP BY mo.serialNumber ORDER BY mo.id DESC");

		jpqlCount.append(" SELECT count(mo) FROM MaazounBookWarehouse mo "
				+ " JOIN MaazounBookSupplyOrder p ON p.id = mo.maazounBookSupplyOrderFk "
				+ " WHERE mo.statusFk = 'New' ");

		if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {

			jpqlCount.append("AND p.sectorFk IN (" + sectorIds.toString().replace("]", "").replace("[", "") + ") ");
		}

		jpqlCount.append(" GROUP BY mo.serialNumber");

		TypedQuery<MaazounBookWarehouse> query = em.createQuery(jpql.toString(), MaazounBookWarehouse.class);
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		TypedQuery<Long> queryCount = em.createQuery(jpqlCount.toString(), Long.class);
		long total = queryCount.getResultList().size();

		return new PageImpl<MaazounBookWarehouse>(query.getResultList(), pageable, total);

	}

	@Override
	public List<WarehouseAuditDTO> warehouseReportByLocationAndBookType(CustomUserDetails customUserDetails) {

		List<WarehouseAuditDTO> result = new ArrayList<WarehouseAuditDTO>();

		return result;
	}

	@Override
	public List<WarehouseAuditDTO> warehouseReportByBookType(WarehouseBookRequest warehouseBookRequest, CustomUserDetails customUserDetails) {
		HashMap<String, WarehouseAuditDTO> hashResult = new HashMap<String, WarehouseAuditDTO>();
		StringBuilder jpql = new StringBuilder();
		
//		Set<Long> sectorIds = new HashSet<Long>();
//		customUserDetails.getPosSet().stream().forEach(s -> {
//			for(Sector sector : s.getSectors()) {
//				sectorIds.add(sector.getId());
//			}
//		});
		
		Set<Long> sectorIds = customUserDetails.getPosSet()
		        .stream()
		        .flatMap(s -> s.getSectors().stream().map(Sector::getId))
		        .collect(Collectors.toSet());
		
		jpql.append("SELECT " 
				+ " COUNT(DISTINCT w.book_financial_number) as totalBookTransaction,"
				+ " FORMAT(SUM(IF (w.status_fk = 'Pending', 1, 0))/w.contract_count,0) as totalBookUnderReview,"
				+ " FORMAT(SUM(IF (w.status_fk = 'New', 1, 0))/w.contract_count, 0) as totalBookNew,"
//				+ " FORMAT(SUM(IF (w.status_fk = 'Refund', 1, 0))/w.contract_count,0) as totalBookRefund,"
//				+ " FORMAT(SUM(IF (w.status_fk = 'Sold', 1, 0))/w.contract_count,0) as totalBookSold,"
//				+ " FORMAT(SUM(IF (w.status_fk = 'Received', 1, 0))/w.contract_count,0) as totalBookReceived,"
//				+ " FORMAT(SUM(IF (w.status_fk = 'Underdelivery', 1, 0))/w.contract_count,0) as totalBookUnderdelivery,"
//				+ " FORMAT(SUM(IF (w.status_fk = 'Delivered', 1, 0))/w.contract_count,0) as totalBookDelivered,"

				+ " c.name_ar as cityName," + " l.name as locationName," + " s.name as sectorName,"
				+ " w.book_type_id as bookTypeId," + " w.book_type_name as bookTypeName," + " so.sector_fk as sectorId,"
				+ " l.id as locationId," + " c.id as cityId, w.contract_count as bookContractCount"

				+ " FROM maazoun_book_warehouse w"
				+ " JOIN maazoun_book_supply_order so ON so.id = w.maazoun_book_supply_order_fk"
				+ " JOIN sector s ON s.id = so.sector_fk" + " JOIN location l ON l.id = so.location_id"
				+ " JOIN city c ON c.id = l.city_fk"
				+ " WHERE " + "so.created_at >= '"
				+  warehouseBookRequest.getDurationFrom() + " 00:00:00' " + "AND so.created_at <= '" + warehouseBookRequest.getDurationTo()
				+ " 23:59:59' AND w.status_fk != 'Rejected' AND w.status_fk IS NOT NULL AND so.is_custody IS FALSE ");
		
		if (warehouseBookRequest.getSectorId() != null) {
			jpql.append("AND so.sector_fk = " + warehouseBookRequest.getSectorId() + " ");
		}else 
		if (customUserDetails.getRoleFk().getName().equalsIgnoreCase(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equalsIgnoreCase(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equalsIgnoreCase(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equalsIgnoreCase(StatusConstant.ROLE_SUPERVISOR)) {
			jpql.append(" AND so.sector_fk IN ("
					+ sectorIds.toString().replace("]", "").replace("[", "") + ") ");
		}

		jpql.append(" GROUP BY w.contract_count, so.sector_fk ORDER BY so.sector_fk "); //date(so.created_at), 

		System.out.println(jpql.toString());
		Query query = em.createNativeQuery(jpql.toString());

		List<Object[]> list = query.getResultList();
		
		WarehouseAuditDTO totalWarehouseAuditDTO = populateDefaultObj("", "", "",
				"", "", "", "الإجمالى", null);

		for (Object[] q1 : list) {
			
			Long totalBookTransaction = Long.valueOf(q1[0]!=null?q1[0].toString().replace(",", ""):"0");
			Long totalBookUnderReview = Long.valueOf(q1[1]!=null?q1[1].toString().replace(",", ""):"0");
			Long totalBookNew = Long.valueOf(q1[2]!=null?q1[2].toString().replace(",", ""):"0");
//			Long totalBookRefund = Long.valueOf(q1[3]!=null?q1[3].toString().replace(",", ""):"0");
//			Long totalBookSold = Long.valueOf(q1[4]!=null?q1[4].toString().replace(",", ""):"0");
//
//			Long totalBookReceived = Long.valueOf(q1[5]!=null?q1[5].toString().replace(",", ""):"0");
//			Long totalBookUnderdelivery = Long.valueOf(q1[6]!=null?q1[6].toString().replace(",", ""):"0");
//			Long totalBookDelivered = Long.valueOf(q1[7]!=null?q1[7].toString().replace(",", ""):"0");

			String cityName = q1[3].toString();
			String locationName = q1[4].toString();
			String sectorName = q1[5].toString();
			String bookTypeId = q1[6].toString();

			String bookTypeName = q1[7]!=null? q1[7].toString():null;
			String sectorId = q1[8].toString();
			String locationId = q1[9].toString();
			String cityId = q1[10].toString();
			String bookContractCount = q1[11].toString();
			
			String key = sectorId + "-" + bookContractCount;
//			WarehouseAuditDTO eWarehouseAuditDTO = result.stream().filter(x -> 
//					x.getSectorId().equals(sectorId) &&
//					x.getBookContractCount().equals(bookContractCount) &&
//					x.getCreatedAt().equals(createdAt)).findAny().orElse(null);
			
			WarehouseAuditDTO eWarehouseAuditDTO = hashResult.get(key);

			if (eWarehouseAuditDTO == null) {
				eWarehouseAuditDTO = populateDefaultObj(cityId, cityName, locationId,
						locationName, sectorId, sectorName, 
						bookContractCount, null);

				hashResult.put(key, eWarehouseAuditDTO);
			}

			if (bookTypeId.equals(StatusConstant.BOOK_MORAGAA_ID_8) ||
					bookTypeId.equals(StatusConstant.BOOK_MORAGAA_ID_15)) {
				eWarehouseAuditDTO.setBookTotalMoragaaTransactions(
						eWarehouseAuditDTO.getBookTotalMoragaaTransactions() + totalBookTransaction);
				eWarehouseAuditDTO.setBookTotalUnderReviewMoragaaTransactions(
						eWarehouseAuditDTO.getBookTotalUnderReviewMoragaaTransactions() + totalBookUnderReview);
				eWarehouseAuditDTO.setBookTotalNewMoragaaTransactions(
						eWarehouseAuditDTO.getBookTotalNewMoragaaTransactions() + totalBookNew);
				eWarehouseAuditDTO.setBookTotalAvailableMoragaaTransactions(
						eWarehouseAuditDTO.getBookTotalAvailableMoragaaTransactions() + 
						eWarehouseAuditDTO.getBookTotalUnderReviewMoragaaTransactions() + 
						eWarehouseAuditDTO.getBookTotalNewMoragaaTransactions());
				eWarehouseAuditDTO.setBookTotalSoldMoragaaTransactions(
						eWarehouseAuditDTO.getBookTotalSoldMoragaaTransactions() + 
						(eWarehouseAuditDTO.getBookTotalMoragaaTransactions() - 
						eWarehouseAuditDTO.getBookTotalAvailableMoragaaTransactions()));
				
				totalWarehouseAuditDTO.setBookTotalMoragaaTransactions(
						totalWarehouseAuditDTO.getBookTotalMoragaaTransactions() + totalBookTransaction);
				totalWarehouseAuditDTO.setBookTotalUnderReviewMoragaaTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewMoragaaTransactions() + totalBookUnderReview);
				totalWarehouseAuditDTO.setBookTotalNewMoragaaTransactions(
						totalWarehouseAuditDTO.getBookTotalNewMoragaaTransactions() + totalBookNew);
				totalWarehouseAuditDTO.setBookTotalAvailableMoragaaTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewMoragaaTransactions() + 
						totalWarehouseAuditDTO.getBookTotalNewMoragaaTransactions());
				totalWarehouseAuditDTO.setBookTotalSoldMoragaaTransactions(
						totalWarehouseAuditDTO.getBookTotalMoragaaTransactions() - 
								totalWarehouseAuditDTO.getBookTotalAvailableMoragaaTransactions());

			} else if (bookTypeId.equals(StatusConstant.BOOK_TALAK_ID_8) ||
					bookTypeId.equals(StatusConstant.BOOK_TALAK_ID_15)) {
				eWarehouseAuditDTO.setBookTotalTalakTransactions(
						eWarehouseAuditDTO.getBookTotalTalakTransactions() + totalBookTransaction);
				eWarehouseAuditDTO.setBookTotalUnderReviewTalakTransactions(
						eWarehouseAuditDTO.getBookTotalUnderReviewTalakTransactions() + totalBookUnderReview);
				eWarehouseAuditDTO.setBookTotalNewTalakTransactions(
						eWarehouseAuditDTO.getBookTotalNewTalakTransactions() + totalBookNew);
				eWarehouseAuditDTO.setBookTotalAvailableTalakTransactions(
						eWarehouseAuditDTO.getBookTotalAvailableTalakTransactions() + 
						eWarehouseAuditDTO.getBookTotalUnderReviewTalakTransactions() + 
						eWarehouseAuditDTO.getBookTotalNewTalakTransactions());
				eWarehouseAuditDTO.setBookTotalSoldTalakTransactions(
						eWarehouseAuditDTO.getBookTotalSoldTalakTransactions() + 
						(eWarehouseAuditDTO.getBookTotalTalakTransactions() - 
						eWarehouseAuditDTO.getBookTotalAvailableTalakTransactions()));
				
				totalWarehouseAuditDTO.setBookTotalTalakTransactions(
						totalWarehouseAuditDTO.getBookTotalTalakTransactions() + totalBookTransaction);
				totalWarehouseAuditDTO.setBookTotalUnderReviewTalakTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewTalakTransactions() + totalBookUnderReview);
				totalWarehouseAuditDTO.setBookTotalNewTalakTransactions(
						totalWarehouseAuditDTO.getBookTotalNewTalakTransactions() + totalBookNew);
				totalWarehouseAuditDTO.setBookTotalAvailableTalakTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewTalakTransactions() + 
						totalWarehouseAuditDTO.getBookTotalNewTalakTransactions());
				totalWarehouseAuditDTO.setBookTotalSoldTalakTransactions(
						totalWarehouseAuditDTO.getBookTotalTalakTransactions() - 
								totalWarehouseAuditDTO.getBookTotalAvailableTalakTransactions());

			} else if (bookTypeId.equals(StatusConstant.BOOK_TASADOK_ID_8) ||
					bookTypeId.equals(StatusConstant.BOOK_TASADOK_ID_15)) {
				eWarehouseAuditDTO.setBookTotalTasadokTransactions(
						eWarehouseAuditDTO.getBookTotalTasadokTransactions() + totalBookTransaction);
				eWarehouseAuditDTO.setBookTotalUnderReviewTasadokTransactions(
						eWarehouseAuditDTO.getBookTotalUnderReviewTasadokTransactions() + totalBookUnderReview);
				eWarehouseAuditDTO.setBookTotalNewTasadokTransactions(
						eWarehouseAuditDTO.getBookTotalNewTasadokTransactions() + totalBookNew);
				eWarehouseAuditDTO.setBookTotalAvailableTasadokTransactions(
						eWarehouseAuditDTO.getBookTotalAvailableTasadokTransactions() + 
						eWarehouseAuditDTO.getBookTotalUnderReviewTasadokTransactions() + 
						eWarehouseAuditDTO.getBookTotalNewTasadokTransactions());
				eWarehouseAuditDTO.setBookTotalSoldTasadokTransactions(
						eWarehouseAuditDTO.getBookTotalSoldTasadokTransactions() + 
						(eWarehouseAuditDTO.getBookTotalTasadokTransactions() - 
						eWarehouseAuditDTO.getBookTotalAvailableTasadokTransactions()));
				
				totalWarehouseAuditDTO.setBookTotalTasadokTransactions(
						totalWarehouseAuditDTO.getBookTotalTasadokTransactions() + totalBookTransaction);
				totalWarehouseAuditDTO.setBookTotalUnderReviewTasadokTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewTasadokTransactions() + totalBookUnderReview);
				totalWarehouseAuditDTO.setBookTotalNewTasadokTransactions(
						totalWarehouseAuditDTO.getBookTotalNewTasadokTransactions() + totalBookNew);
				totalWarehouseAuditDTO.setBookTotalAvailableTasadokTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewTasadokTransactions() + 
						totalWarehouseAuditDTO.getBookTotalNewTasadokTransactions());
				totalWarehouseAuditDTO.setBookTotalSoldTasadokTransactions(
						totalWarehouseAuditDTO.getBookTotalTasadokTransactions() - 
								totalWarehouseAuditDTO.getBookTotalAvailableTasadokTransactions());

			} else if (bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MELALY_ID_8) ||
					bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MELALY_ID_15)) {
				eWarehouseAuditDTO.setBookTotalZawagMelalyTransactions(
						eWarehouseAuditDTO.getBookTotalZawagMelalyTransactions() + totalBookTransaction);
				eWarehouseAuditDTO.setBookTotalUnderReviewZawagMelalyTransactions(
						eWarehouseAuditDTO.getBookTotalUnderReviewZawagMelalyTransactions() + totalBookUnderReview);
				eWarehouseAuditDTO.setBookTotalNewZawagMelalyTransactions(
						eWarehouseAuditDTO.getBookTotalNewZawagMelalyTransactions() + totalBookNew);
				eWarehouseAuditDTO.setBookTotalAvailableZawagMelalyTransactions(
						eWarehouseAuditDTO.getBookTotalAvailableZawagMelalyTransactions() +
						eWarehouseAuditDTO.getBookTotalUnderReviewZawagMelalyTransactions() + 
						eWarehouseAuditDTO.getBookTotalNewZawagMelalyTransactions());
				eWarehouseAuditDTO.setBookTotalSoldZawagMelalyTransactions(
						eWarehouseAuditDTO.getBookTotalSoldZawagMelalyTransactions() +
						(eWarehouseAuditDTO.getBookTotalZawagMelalyTransactions() - 
						eWarehouseAuditDTO.getBookTotalAvailableZawagMelalyTransactions()));
				
				totalWarehouseAuditDTO.setBookTotalZawagMelalyTransactions(
						totalWarehouseAuditDTO.getBookTotalZawagMelalyTransactions() + totalBookTransaction);
				totalWarehouseAuditDTO.setBookTotalUnderReviewZawagMelalyTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewZawagMelalyTransactions() + totalBookUnderReview);
				totalWarehouseAuditDTO.setBookTotalNewZawagMelalyTransactions(
						totalWarehouseAuditDTO.getBookTotalNewZawagMelalyTransactions() + totalBookNew);
				totalWarehouseAuditDTO.setBookTotalAvailableZawagMelalyTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewZawagMelalyTransactions() + 
						totalWarehouseAuditDTO.getBookTotalNewZawagMelalyTransactions());
				totalWarehouseAuditDTO.setBookTotalSoldZawagMelalyTransactions(
						totalWarehouseAuditDTO.getBookTotalZawagMelalyTransactions() - 
								totalWarehouseAuditDTO.getBookTotalAvailableZawagMelalyTransactions());

			} else if (bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MUSLIM_ID_8) ||
					bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MUSLIM_ID_15)) {
				eWarehouseAuditDTO.setBookTotalZawagMuslimTransactions(
						eWarehouseAuditDTO.getBookTotalZawagMuslimTransactions() + totalBookTransaction);
				eWarehouseAuditDTO.setBookTotalUnderReviewZawagMuslimTransactions(
						eWarehouseAuditDTO.getBookTotalUnderReviewZawagMuslimTransactions() + totalBookUnderReview);
				eWarehouseAuditDTO.setBookTotalNewZawagMuslimTransactions(
						eWarehouseAuditDTO.getBookTotalNewZawagMuslimTransactions() + totalBookNew);
				eWarehouseAuditDTO.setBookTotalAvailableZawagMuslimTransactions(
						eWarehouseAuditDTO.getBookTotalAvailableZawagMuslimTransactions() +
						eWarehouseAuditDTO.getBookTotalUnderReviewZawagMuslimTransactions() + 
						eWarehouseAuditDTO.getBookTotalNewZawagMuslimTransactions());
				eWarehouseAuditDTO.setBookTotalSoldZawagMuslimTransactions(
						eWarehouseAuditDTO.getBookTotalSoldZawagMuslimTransactions() +
						(eWarehouseAuditDTO.getBookTotalZawagMuslimTransactions() - 
						eWarehouseAuditDTO.getBookTotalAvailableZawagMuslimTransactions()));
				
				totalWarehouseAuditDTO.setBookTotalZawagMuslimTransactions(
						totalWarehouseAuditDTO.getBookTotalZawagMuslimTransactions() + totalBookTransaction);
				totalWarehouseAuditDTO.setBookTotalUnderReviewZawagMuslimTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewZawagMuslimTransactions() + totalBookUnderReview);
				totalWarehouseAuditDTO.setBookTotalNewZawagMuslimTransactions(
						totalWarehouseAuditDTO.getBookTotalNewZawagMuslimTransactions() + totalBookNew);
				totalWarehouseAuditDTO.setBookTotalAvailableZawagMuslimTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewZawagMuslimTransactions() + 
						totalWarehouseAuditDTO.getBookTotalNewZawagMuslimTransactions());
				totalWarehouseAuditDTO.setBookTotalSoldZawagMuslimTransactions(
						totalWarehouseAuditDTO.getBookTotalZawagMuslimTransactions() - 
								totalWarehouseAuditDTO.getBookTotalAvailableZawagMuslimTransactions());

			}

//			result.add(eWarehouseAuditDTO);

		}
		
		List<WarehouseAuditDTO> result = new ArrayList<WarehouseAuditDTO>(hashResult.values());
		result.add(totalWarehouseAuditDTO);

		return result;
	}

	@Override
	public List<WarehouseAuditDTO> warehouseReportByDaily(WarehouseBookRequest warehouseBookRequest, CustomUserDetails customUserDetails) {
		HashMap<String, WarehouseAuditDTO> hashResult = new HashMap<String, WarehouseAuditDTO>();
		StringBuilder jpql = new StringBuilder();
		
		Set<Long> sectorIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			for(Sector sector : s.getSectors()) {
				sectorIds.add(sector.getId());
			}
		});
		
		jpql.append("SELECT " 
				+ " COUNT(DISTINCT w.book_financial_number) as totalBookTransaction,"
				+ " FORMAT(SUM(IF (w.status_fk = 'Pending', 1, 0))/w.contract_count,0) as totalBookUnderReview,"
				+ " FORMAT(SUM(IF (w.status_fk = 'New', 1, 0))/w.contract_count, 0) as totalBookNew,"
//				+ " FORMAT(SUM(IF (w.status_fk = 'Refund', 1, 0))/w.contract_count,0) as totalBookRefund,"
//				+ " FORMAT(SUM(IF (w.status_fk = 'Sold', 1, 0))/w.contract_count,0) as totalBookSold,"
//				+ " FORMAT(SUM(IF (w.status_fk = 'Received', 1, 0))/w.contract_count,0) as totalBookReceived,"
//				+ " FORMAT(SUM(IF (w.status_fk = 'Underdelivery', 1, 0))/w.contract_count,0) as totalBookUnderdelivery,"
//				+ " FORMAT(SUM(IF (w.status_fk = 'Delivered', 1, 0))/w.contract_count,0) as totalBookDelivered,"

				+ " c.name_ar as cityName," + " l.name as locationName," + " s.name as sectorName,"
				+ " w.book_type_id as bookTypeId," + " w.book_type_name as bookTypeName," + " so.sector_fk as sectorId,"
				+ " l.id as locationId," + " c.id as cityId, w.contract_count as bookContractCount,"
				+ " DATE_FORMAT(so.created_at, '%Y/%m/%d') as createDate"
				+ " FROM maazoun_book_warehouse w"
				+ " JOIN maazoun_book_supply_order so ON so.id = w.maazoun_book_supply_order_fk"
				+ " JOIN sector s ON s.id = so.sector_fk" + " JOIN location l ON l.id = so.location_id"
				+ " JOIN city c ON c.id = l.city_fk"
				+ " WHERE " + "so.created_at >= '"
				+  warehouseBookRequest.getDurationFrom() + " 00:00:00' " + "AND so.created_at <= '" + warehouseBookRequest.getDurationTo()
				+ " 23:59:59' AND w.status_fk != 'Rejected' AND w.status_fk IS NOT NULL AND so.is_custody IS FALSE ");
		
		if (warehouseBookRequest.getSectorId() != null) {
			jpql.append("AND so.sector_fk = " + warehouseBookRequest.getSectorId() + " ");
		}else 
		if (customUserDetails.getRoleFk().getName().equalsIgnoreCase(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equalsIgnoreCase(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equalsIgnoreCase(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equalsIgnoreCase(StatusConstant.ROLE_SUPERVISOR)) {
			jpql.append(" AND so.sector_fk IN ("
					+ sectorIds.toString().replace("]", "").replace("[", "") + ") ");
		}

		jpql.append(" GROUP BY date(so.created_at), w.contract_count, so.sector_fk ORDER BY so.created_at DESC "); //date(so.created_at), 

		Query query = em.createNativeQuery(jpql.toString());

		List<Object[]> list = query.getResultList();
		
		WarehouseAuditDTO totalWarehouseAuditDTO = populateDefaultObj("", "", "",
				"", "", "", "الإجمالى", null);

		for (Object[] q1 : list) {
			
			Long totalBookTransaction = Long.valueOf(q1[0]!=null?q1[0].toString().replace(",", ""):"0");
			Long totalBookUnderReview = Long.valueOf(q1[1]!=null?q1[1].toString().replace(",", ""):"0");
			Long totalBookNew = Long.valueOf(q1[2]!=null?q1[2].toString().replace(",", ""):"0");
//			Long totalBookRefund = Long.valueOf(q1[3]!=null?q1[3].toString().replace(",", ""):"0");
//			Long totalBookSold = Long.valueOf(q1[4]!=null?q1[4].toString().replace(",", ""):"0");
//
//			Long totalBookReceived = Long.valueOf(q1[5]!=null?q1[5].toString().replace(",", ""):"0");
//			Long totalBookUnderdelivery = Long.valueOf(q1[6]!=null?q1[6].toString().replace(",", ""):"0");
//			Long totalBookDelivered = Long.valueOf(q1[7]!=null?q1[7].toString().replace(",", ""):"0");

			String cityName = q1[3].toString();
			String locationName = q1[4].toString();
			String sectorName = q1[5].toString();
			String bookTypeId = q1[6].toString();

			String bookTypeName = q1[7]!=null? q1[7].toString():null;
			String sectorId = q1[8].toString();
			String locationId = q1[9].toString();
			String cityId = q1[10].toString();
			String bookContractCount = q1[11].toString();
			String createdAt = q1[12].toString();

			String key = sectorId + "-" + bookContractCount + "-" + createdAt;
//			WarehouseAuditDTO eWarehouseAuditDTO = result.stream().filter(x -> 
//					x.getSectorId().equals(sectorId) &&
//					x.getBookContractCount().equals(bookContractCount) &&
//					x.getCreatedAt().equals(createdAt)).findAny().orElse(null);
			
			WarehouseAuditDTO eWarehouseAuditDTO = hashResult.get(key);

			if (eWarehouseAuditDTO == null) {
				eWarehouseAuditDTO = populateDefaultObj(cityId, cityName, locationId,
						locationName, sectorId, sectorName, 
						bookContractCount, createdAt);

				hashResult.put(key, eWarehouseAuditDTO);
			}

			if (bookTypeId.equals(StatusConstant.BOOK_MORAGAA_ID_8) ||
					bookTypeId.equals(StatusConstant.BOOK_MORAGAA_ID_15)) {
				eWarehouseAuditDTO.setBookTotalMoragaaTransactions(
						eWarehouseAuditDTO.getBookTotalMoragaaTransactions() + totalBookTransaction);
				eWarehouseAuditDTO.setBookTotalUnderReviewMoragaaTransactions(
						eWarehouseAuditDTO.getBookTotalUnderReviewMoragaaTransactions() + totalBookUnderReview);
				eWarehouseAuditDTO.setBookTotalNewMoragaaTransactions(
						eWarehouseAuditDTO.getBookTotalNewMoragaaTransactions() + totalBookNew);
				eWarehouseAuditDTO.setBookTotalAvailableMoragaaTransactions(
						eWarehouseAuditDTO.getBookTotalAvailableMoragaaTransactions() + 
						eWarehouseAuditDTO.getBookTotalUnderReviewMoragaaTransactions() + 
						eWarehouseAuditDTO.getBookTotalNewMoragaaTransactions());
				eWarehouseAuditDTO.setBookTotalSoldMoragaaTransactions(
						eWarehouseAuditDTO.getBookTotalSoldMoragaaTransactions() + 
						(eWarehouseAuditDTO.getBookTotalMoragaaTransactions() - 
						eWarehouseAuditDTO.getBookTotalAvailableMoragaaTransactions()));
				
				totalWarehouseAuditDTO.setBookTotalMoragaaTransactions(
						totalWarehouseAuditDTO.getBookTotalMoragaaTransactions() + totalBookTransaction);
				totalWarehouseAuditDTO.setBookTotalUnderReviewMoragaaTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewMoragaaTransactions() + totalBookUnderReview);
				totalWarehouseAuditDTO.setBookTotalNewMoragaaTransactions(
						totalWarehouseAuditDTO.getBookTotalNewMoragaaTransactions() + totalBookNew);
				totalWarehouseAuditDTO.setBookTotalAvailableMoragaaTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewMoragaaTransactions() + 
						totalWarehouseAuditDTO.getBookTotalNewMoragaaTransactions());
				totalWarehouseAuditDTO.setBookTotalSoldMoragaaTransactions(
						totalWarehouseAuditDTO.getBookTotalMoragaaTransactions() - 
								totalWarehouseAuditDTO.getBookTotalAvailableMoragaaTransactions());

			} else if (bookTypeId.equals(StatusConstant.BOOK_TALAK_ID_8) ||
					bookTypeId.equals(StatusConstant.BOOK_TALAK_ID_15)) {
				eWarehouseAuditDTO.setBookTotalTalakTransactions(
						eWarehouseAuditDTO.getBookTotalTalakTransactions() + totalBookTransaction);
				eWarehouseAuditDTO.setBookTotalUnderReviewTalakTransactions(
						eWarehouseAuditDTO.getBookTotalUnderReviewTalakTransactions() + totalBookUnderReview);
				eWarehouseAuditDTO.setBookTotalNewTalakTransactions(
						eWarehouseAuditDTO.getBookTotalNewTalakTransactions() + totalBookNew);
				eWarehouseAuditDTO.setBookTotalAvailableTalakTransactions(
						eWarehouseAuditDTO.getBookTotalAvailableTalakTransactions() + 
						eWarehouseAuditDTO.getBookTotalUnderReviewTalakTransactions() + 
						eWarehouseAuditDTO.getBookTotalNewTalakTransactions());
				eWarehouseAuditDTO.setBookTotalSoldTalakTransactions(
						eWarehouseAuditDTO.getBookTotalSoldTalakTransactions() + 
						(eWarehouseAuditDTO.getBookTotalTalakTransactions() - 
						eWarehouseAuditDTO.getBookTotalAvailableTalakTransactions()));
				
				totalWarehouseAuditDTO.setBookTotalTalakTransactions(
						totalWarehouseAuditDTO.getBookTotalTalakTransactions() + totalBookTransaction);
				totalWarehouseAuditDTO.setBookTotalUnderReviewTalakTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewTalakTransactions() + totalBookUnderReview);
				totalWarehouseAuditDTO.setBookTotalNewTalakTransactions(
						totalWarehouseAuditDTO.getBookTotalNewTalakTransactions() + totalBookNew);
				totalWarehouseAuditDTO.setBookTotalAvailableTalakTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewTalakTransactions() + 
						totalWarehouseAuditDTO.getBookTotalNewTalakTransactions());
				totalWarehouseAuditDTO.setBookTotalSoldTalakTransactions(
						totalWarehouseAuditDTO.getBookTotalTalakTransactions() - 
								totalWarehouseAuditDTO.getBookTotalAvailableTalakTransactions());

			} else if (bookTypeId.equals(StatusConstant.BOOK_TASADOK_ID_8) ||
					bookTypeId.equals(StatusConstant.BOOK_TASADOK_ID_15)) {
				eWarehouseAuditDTO.setBookTotalTasadokTransactions(
						eWarehouseAuditDTO.getBookTotalTasadokTransactions() + totalBookTransaction);
				eWarehouseAuditDTO.setBookTotalUnderReviewTasadokTransactions(
						eWarehouseAuditDTO.getBookTotalUnderReviewTasadokTransactions() + totalBookUnderReview);
				eWarehouseAuditDTO.setBookTotalNewTasadokTransactions(
						eWarehouseAuditDTO.getBookTotalNewTasadokTransactions() + totalBookNew);
				eWarehouseAuditDTO.setBookTotalAvailableTasadokTransactions(
						eWarehouseAuditDTO.getBookTotalAvailableTasadokTransactions() + 
						eWarehouseAuditDTO.getBookTotalUnderReviewTasadokTransactions() + 
						eWarehouseAuditDTO.getBookTotalNewTasadokTransactions());
				eWarehouseAuditDTO.setBookTotalSoldTasadokTransactions(
						eWarehouseAuditDTO.getBookTotalSoldTasadokTransactions() + 
						(eWarehouseAuditDTO.getBookTotalTasadokTransactions() - 
						eWarehouseAuditDTO.getBookTotalAvailableTasadokTransactions()));
				
				totalWarehouseAuditDTO.setBookTotalTasadokTransactions(
						totalWarehouseAuditDTO.getBookTotalTasadokTransactions() + totalBookTransaction);
				totalWarehouseAuditDTO.setBookTotalUnderReviewTasadokTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewTasadokTransactions() + totalBookUnderReview);
				totalWarehouseAuditDTO.setBookTotalNewTasadokTransactions(
						totalWarehouseAuditDTO.getBookTotalNewTasadokTransactions() + totalBookNew);
				totalWarehouseAuditDTO.setBookTotalAvailableTasadokTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewTasadokTransactions() + 
						totalWarehouseAuditDTO.getBookTotalNewTasadokTransactions());
				totalWarehouseAuditDTO.setBookTotalSoldTasadokTransactions(
						totalWarehouseAuditDTO.getBookTotalTasadokTransactions() - 
								totalWarehouseAuditDTO.getBookTotalAvailableTasadokTransactions());

			} else if (bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MELALY_ID_8) ||
					bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MELALY_ID_15)) {
				eWarehouseAuditDTO.setBookTotalZawagMelalyTransactions(
						eWarehouseAuditDTO.getBookTotalZawagMelalyTransactions() + totalBookTransaction);
				eWarehouseAuditDTO.setBookTotalUnderReviewZawagMelalyTransactions(
						eWarehouseAuditDTO.getBookTotalUnderReviewZawagMelalyTransactions() + totalBookUnderReview);
				eWarehouseAuditDTO.setBookTotalNewZawagMelalyTransactions(
						eWarehouseAuditDTO.getBookTotalNewZawagMelalyTransactions() + totalBookNew);
				eWarehouseAuditDTO.setBookTotalAvailableZawagMelalyTransactions(
						eWarehouseAuditDTO.getBookTotalAvailableZawagMelalyTransactions() +
						eWarehouseAuditDTO.getBookTotalUnderReviewZawagMelalyTransactions() + 
						eWarehouseAuditDTO.getBookTotalNewZawagMelalyTransactions());
				eWarehouseAuditDTO.setBookTotalSoldZawagMelalyTransactions(
						eWarehouseAuditDTO.getBookTotalSoldZawagMelalyTransactions() +
						(eWarehouseAuditDTO.getBookTotalZawagMelalyTransactions() - 
						eWarehouseAuditDTO.getBookTotalAvailableZawagMelalyTransactions()));
				
				totalWarehouseAuditDTO.setBookTotalZawagMelalyTransactions(
						totalWarehouseAuditDTO.getBookTotalZawagMelalyTransactions() + totalBookTransaction);
				totalWarehouseAuditDTO.setBookTotalUnderReviewZawagMelalyTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewZawagMelalyTransactions() + totalBookUnderReview);
				totalWarehouseAuditDTO.setBookTotalNewZawagMelalyTransactions(
						totalWarehouseAuditDTO.getBookTotalNewZawagMelalyTransactions() + totalBookNew);
				totalWarehouseAuditDTO.setBookTotalAvailableZawagMelalyTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewZawagMelalyTransactions() + 
						totalWarehouseAuditDTO.getBookTotalNewZawagMelalyTransactions());
				totalWarehouseAuditDTO.setBookTotalSoldZawagMelalyTransactions(
						totalWarehouseAuditDTO.getBookTotalZawagMelalyTransactions() - 
								totalWarehouseAuditDTO.getBookTotalAvailableZawagMelalyTransactions());

			} else if (bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MUSLIM_ID_8) ||
					bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MUSLIM_ID_15)) {
				eWarehouseAuditDTO.setBookTotalZawagMuslimTransactions(
						eWarehouseAuditDTO.getBookTotalZawagMuslimTransactions() + totalBookTransaction);
				eWarehouseAuditDTO.setBookTotalUnderReviewZawagMuslimTransactions(
						eWarehouseAuditDTO.getBookTotalUnderReviewZawagMuslimTransactions() + totalBookUnderReview);
				eWarehouseAuditDTO.setBookTotalNewZawagMuslimTransactions(
						eWarehouseAuditDTO.getBookTotalNewZawagMuslimTransactions() + totalBookNew);
				eWarehouseAuditDTO.setBookTotalAvailableZawagMuslimTransactions(
						eWarehouseAuditDTO.getBookTotalAvailableZawagMuslimTransactions() +
						eWarehouseAuditDTO.getBookTotalUnderReviewZawagMuslimTransactions() + 
						eWarehouseAuditDTO.getBookTotalNewZawagMuslimTransactions());
				eWarehouseAuditDTO.setBookTotalSoldZawagMuslimTransactions(
						eWarehouseAuditDTO.getBookTotalSoldZawagMuslimTransactions() +
						(eWarehouseAuditDTO.getBookTotalZawagMuslimTransactions() - 
						eWarehouseAuditDTO.getBookTotalAvailableZawagMuslimTransactions()));
				
				totalWarehouseAuditDTO.setBookTotalZawagMuslimTransactions(
						totalWarehouseAuditDTO.getBookTotalZawagMuslimTransactions() + totalBookTransaction);
				totalWarehouseAuditDTO.setBookTotalUnderReviewZawagMuslimTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewZawagMuslimTransactions() + totalBookUnderReview);
				totalWarehouseAuditDTO.setBookTotalNewZawagMuslimTransactions(
						totalWarehouseAuditDTO.getBookTotalNewZawagMuslimTransactions() + totalBookNew);
				totalWarehouseAuditDTO.setBookTotalAvailableZawagMuslimTransactions(
						totalWarehouseAuditDTO.getBookTotalUnderReviewZawagMuslimTransactions() + 
						totalWarehouseAuditDTO.getBookTotalNewZawagMuslimTransactions());
				totalWarehouseAuditDTO.setBookTotalSoldZawagMuslimTransactions(
						totalWarehouseAuditDTO.getBookTotalZawagMuslimTransactions() - 
								totalWarehouseAuditDTO.getBookTotalAvailableZawagMuslimTransactions());

			}

			//.add(eWarehouseAuditDTO);

		}

		List<WarehouseAuditDTO> result = new ArrayList<WarehouseAuditDTO>(hashResult.values());
		result.add(totalWarehouseAuditDTO);

		return result;
	}
	
	@Override
	public Set<WarehouseAuditDTO> warehouseCustodyReportByBookType(CustomUserDetails customUserDetails) {
		Set<WarehouseAuditDTO> result = new HashSet<WarehouseAuditDTO>();
		StringBuilder jpql = new StringBuilder();
		
		Set<Long> sectorIds = new HashSet<Long>();
		customUserDetails.getPosSet().stream().forEach(s -> {
			for(Sector sector : s.getSectors()) {
				sectorIds.add(sector.getId());
			}
		});
		
		jpql.append("SELECT " + " COUNT(DISTINCT w.book_financial_number) as totalBookTransaction,"
				+ " FORMAT(SUM(IF (w.status_fk = 'Pending', 1, 0))/w.contract_count,0) as totalBookUnderReview,"
				+ " FORMAT(SUM(IF (w.status_fk = 'New', 1, 0))/w.contract_count,0) as totalBookNew,"
				+ " FORMAT(SUM(IF (w.status_fk = 'Refund', 1, 0))/w.contract_count,0) as totalBookRefund,"
				+ " FORMAT(SUM(IF (w.status_fk = 'Sold', 1, 0))/w.contract_count,0) as totalBookSold,"
				+ " FORMAT(SUM(IF (w.status_fk = 'Received', 1, 0))/w.contract_count,0) as totalBookReceived,"
				+ " FORMAT(SUM(IF (w.status_fk = 'Underdelivery', 1, 0))/w.contract_count,0) as totalBookUnderdelivery,"
				+ " FORMAT(SUM(IF (w.status_fk = 'Delivered', 1, 0))/w.contract_count,0) as totalBookDelivered,"

				+ " c.name_ar as cityName," + " l.name as locationName," + " s.name as sectorName,"
				+ " w.book_type_id as bookTypeId," + " w.book_type_name as bookTypeName," + " so.sector_fk as sectorId,"
				+ " l.id as locationId," + " c.id as cityId"

				+ " FROM maazoun_book_warehouse w"
				+ " JOIN maazoun_book_supply_order so ON so.id = w.maazoun_book_supply_order_fk"
				+ " JOIN sector s ON s.id = so.sector_fk" + " JOIN location l ON l.id = so.location_id"
				+ " JOIN city c ON c.id = l.city_fk" + " WHERE so.is_custody IS TRUE");

		if (customUserDetails.getRoleFk().getName().equalsIgnoreCase(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equalsIgnoreCase(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equalsIgnoreCase(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equalsIgnoreCase(StatusConstant.ROLE_SUPERVISOR)) {
			jpql.append(" AND so.sector_fk IN ("
					+ sectorIds.toString().replace("]", "").replace("[", "") + ") ");
		}

		jpql.append(" GROUP BY w.book_type_name, so.sector_fk");

		Query query = em.createNativeQuery(jpql.toString());

		List<Object[]> list = query.getResultList();

		for (Object[] q1 : list) {
			
			Long totalBookTransaction = Long.valueOf(q1[0]!=null?q1[0].toString().replace(",", ""):"0");
			Long totalBookUnderReview = Long.valueOf(q1[1]!=null?q1[1].toString().replace(",", ""):"0");
			Long totalBookNew = Long.valueOf(q1[2]!=null?q1[2].toString().replace(",", ""):"0");
			Long totalBookRefund = Long.valueOf(q1[3]!=null?q1[3].toString().replace(",", ""):"0");
			Long totalBookSold = Long.valueOf(q1[4]!=null?q1[4].toString().replace(",", ""):"0");

			Long totalBookReceived = Long.valueOf(q1[5]!=null?q1[5].toString().replace(",", ""):"0");
			Long totalBookUnderdelivery = Long.valueOf(q1[6]!=null?q1[6].toString().replace(",", ""):"0");
			Long totalBookDelivered = Long.valueOf(q1[7]!=null?q1[7].toString().replace(",", ""):"0");
			
			String cityName = q1[8].toString();
			String locationName = q1[9].toString();
			String sectorName = q1[10].toString();
			String bookTypeId = q1[11].toString();

			String bookTypeName = q1[12].toString();
			String sectorId = q1[13].toString();
			String locationId = q1[14].toString();
			String cityId = q1[15].toString();

			WarehouseAuditDTO eWarehouseAuditDTO = result.stream().filter(x -> x.getCityId().equals(cityId)
					&& x.getLocationId().equals(locationId) && x.getSectorId().equals(sectorId)).findAny().orElse(null);

			if (eWarehouseAuditDTO == null) {
				eWarehouseAuditDTO = new WarehouseAuditDTO();

				eWarehouseAuditDTO.setCityId(cityId);
				eWarehouseAuditDTO.setCityName(cityName);
				eWarehouseAuditDTO.setLocationId(locationId);
				eWarehouseAuditDTO.setLocationName(locationName);
				eWarehouseAuditDTO.setSectorId(sectorId);
				eWarehouseAuditDTO.setSectorName(sectorName);
			}

			if (bookTypeId.equals(StatusConstant.BOOK_MORAGAA_ID_8)) {
				eWarehouseAuditDTO.setBookTotalMoragaaTransactions(totalBookTransaction);
				eWarehouseAuditDTO.setBookTotalUnderReviewMoragaaTransactions(totalBookUnderReview);
				eWarehouseAuditDTO.setBookTotalNewMoragaaTransactions(totalBookNew);
				eWarehouseAuditDTO.setBookTotalSoldMoragaaTransactions(totalBookSold);

			} else if (bookTypeId.equals(StatusConstant.BOOK_TALAK_ID_8)) {
				eWarehouseAuditDTO.setBookTotalTalakTransactions(totalBookTransaction);
				eWarehouseAuditDTO.setBookTotalUnderReviewTalakTransactions(totalBookUnderReview);
				eWarehouseAuditDTO.setBookTotalNewTalakTransactions(totalBookNew);
				eWarehouseAuditDTO.setBookTotalSoldTalakTransactions(totalBookSold);

			} else if (bookTypeId.equals(StatusConstant.BOOK_TASADOK_ID_8)) {
				eWarehouseAuditDTO.setBookTotalTasadokTransactions(totalBookTransaction);
				eWarehouseAuditDTO.setBookTotalUnderReviewTasadokTransactions(totalBookUnderReview);
				eWarehouseAuditDTO.setBookTotalNewTasadokTransactions(totalBookNew);
				eWarehouseAuditDTO.setBookTotalSoldTasadokTransactions(totalBookSold);

			} else if (bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MELALY_ID_8)) {
				eWarehouseAuditDTO.setBookTotalZawagMelalyTransactions(totalBookTransaction);
				eWarehouseAuditDTO.setBookTotalUnderReviewZawagMelalyTransactions(totalBookUnderReview);
				eWarehouseAuditDTO.setBookTotalNewZawagMelalyTransactions(totalBookNew);
				eWarehouseAuditDTO.setBookTotalSoldZawagMelalyTransactions(totalBookSold);

			} else if (bookTypeId.equals(StatusConstant.BOOK_ZAWAG_MUSLIM_ID_8)) {
				eWarehouseAuditDTO.setBookTotalZawagMuslimTransactions(totalBookTransaction);
				eWarehouseAuditDTO.setBookTotalUnderReviewZawagMuslimTransactions(totalBookUnderReview);
				eWarehouseAuditDTO.setBookTotalNewZawagMuslimTransactions(totalBookNew);
				eWarehouseAuditDTO.setBookTotalSoldZawagMuslimTransactions(totalBookSold);

			}

			result.add(eWarehouseAuditDTO);

		}

		return result;
	}

	@Override
	public Page<MaazounBookWarehouse> findBySectorFk(BooksFilterRequest booksByStatusRequest, Set<Long> sectorIds,
			CustomUserDetails customUserDetails, Pageable pageable) {

		StringBuilder jpql = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();

		jpql.append(" SELECT distinct mo FROM MaazounBookWarehouse mo "
				+ " JOIN MaazounBookSupplyOrder p ON p.id = mo.maazounBookSupplyOrderFk "
				+ " WHERE p.createdAt >= '" + booksByStatusRequest.getDurationFrom() + " 00:00:00' "
				+ " AND p.createdAt <= '" + booksByStatusRequest.getDurationTo() + " 23:59:59' " );

		if (booksByStatusRequest.getSectorId() != null) {
			jpql.append(" And p.sectorFk = '" + booksByStatusRequest.getSectorId() + "' ");
		}
		if (booksByStatusRequest.getRefSupplyOrderNumber() != null) {
			jpql.append(" And p.refSupplyOrderNumber = '" + booksByStatusRequest.getRefSupplyOrderNumber() + "' ");
		}
		if (booksByStatusRequest.getStatus() != null ) {
			jpql.append("And mo.statusFk = '" + booksByStatusRequest.getStatus() + "' ");
		}

		if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {

 
 				jpql.append("AND p.sectorFk IN (" + sectorIds.toString().replace("]", "").replace("[", "") + ")");
	 

		}
		  jpql.append("GROUP BY mo.bookFinancialNumber");

		jpqlCount.append("SELECT distinct count(mo) FROM MaazounBookWarehouse mo "
				+ " JOIN MaazounBookSupplyOrder p ON p.id = mo.maazounBookSupplyOrderFk  "
				+ " WHERE p.createdAt >= '" + booksByStatusRequest.getDurationFrom() + " 00:00:00' "
				+ " AND p.createdAt <= '" + booksByStatusRequest.getDurationTo() + " 23:59:59' " );


		if (booksByStatusRequest.getSectorId() != null) {
			jpqlCount.append(" And p.sectorFk = '" + booksByStatusRequest.getSectorId() + "' ");
		}
		if (booksByStatusRequest.getRefSupplyOrderNumber() != null) {
			jpqlCount.append(" And p.refSupplyOrderNumber = '" + booksByStatusRequest.getRefSupplyOrderNumber() + "' ");
		}
		if (booksByStatusRequest.getStatus() != null) {
			jpqlCount.append("And mo.statusFk = '" + booksByStatusRequest.getStatus() + "' ");
		}

		if (customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
				|| customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)) {
 			 
				jpqlCount.append("AND p.sectorFk IN (" + sectorIds.toString().replace("]", "").replace("[", "") + ")");
 		}
 		 

		  jpqlCount.append("GROUP BY mo.bookFinancialNumber");

		TypedQuery<MaazounBookWarehouse> query = em.createQuery(jpql.toString(), MaazounBookWarehouse.class);
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		TypedQuery<Long> queryCount = em.createQuery(jpqlCount.toString(), Long.class);
	//	long total = queryCount.getSingleResult();
		long total = queryCount.getResultList().size();

		return new PageImpl<MaazounBookWarehouse>(query.getResultList(), pageable, total);
	}

	@Override
	public Page<MaazounBookWarehouse> findByStatusFkAndPosIn(String statusFk, Set<Long> posIds, Pageable pageable) {
		String jpql = " SELECT mo FROM MaazounBookWarehouse mo "
				+ " JOIN MaazounBookSupplyOrder p ON p.id = mo.maazounBookSupplyOrderFk " + " WHERE mo.statusFk = '"
				+ statusFk + "' AND p.posFk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") "
				+ " GROUP BY mo.serialNumber";

		String jpqlCount = " SELECT count(mo) FROM MaazounBookWarehouse mo "
				+ " JOIN MaazounBookSupplyOrder p ON p.id = mo.maazounBookSupplyOrderFk " + " WHERE mo.statusFk = '"
				+ statusFk + "' AND p.posFk IN (" + posIds.toString().replace("]", "").replace("[", "") + ") "
				+ " GROUP BY mo.serialNumber";
		TypedQuery<MaazounBookWarehouse> query = em.createQuery(jpql, MaazounBookWarehouse.class);
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		TypedQuery<Long> queryCount = em.createQuery(jpqlCount, Long.class);
		long total = queryCount.getResultList().size();

		return new PageImpl<MaazounBookWarehouse>(query.getResultList(), pageable, total);
	}

	private WarehouseAuditDTO populateDefaultObj(String cityId, String cityName, String locationId,
			String locationName, String sectorId, String sectorName, 
			String bookContractCount, String createdAt) {
		
		WarehouseAuditDTO eWarehouseAuditDTO = new WarehouseAuditDTO();

		eWarehouseAuditDTO.setCityId(cityId);
		eWarehouseAuditDTO.setCityName(cityName);
		eWarehouseAuditDTO.setLocationId(locationId);
		eWarehouseAuditDTO.setLocationName(locationName);
		eWarehouseAuditDTO.setSectorId(sectorId);
		eWarehouseAuditDTO.setSectorName(sectorName);
		eWarehouseAuditDTO.setBookContractCount(bookContractCount);
		eWarehouseAuditDTO.setCreatedAt(createdAt);
		eWarehouseAuditDTO.setBookTotalMoragaaTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalUnderReviewMoragaaTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalNewMoragaaTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalAvailableMoragaaTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalSoldMoragaaTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalTalakTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalUnderReviewTalakTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalNewTalakTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalAvailableTalakTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalSoldTalakTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalTasadokTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalUnderReviewTasadokTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalNewTasadokTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalAvailableTasadokTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalSoldTasadokTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalZawagMelalyTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalUnderReviewZawagMelalyTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalNewZawagMelalyTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalAvailableZawagMelalyTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalSoldZawagMelalyTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalZawagMuslimTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalUnderReviewZawagMuslimTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalNewZawagMuslimTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalAvailableZawagMuslimTransactions((long) 0);
		eWarehouseAuditDTO.setBookTotalSoldZawagMuslimTransactions((long) 0);
		
		return eWarehouseAuditDTO;
	}

	@Override
	public long findCountDistinctMaazounBookSoldAndCollectedStatusByMaazounId(long maazounProfileId, 
			long bookTypeId8 , long bookTypeId15) {

		final String jpqlCount = "SELECT COUNT(DISTINCT w.serialNumber) FROM MaazounBookWarehouse w "
	            + "JOIN MaazounBookRequestInfo r ON r.id = w.maazounBookRequestInfoFk "
	            + "WHERE r.maazounProfileFk.id = :maazounProfileId "
	            + "AND (w.statusFk = :statusSold OR w.statusFk = :statusCollected) "
	            + "AND (w.bookTypeId = :bookTypeId8 OR w.bookTypeId = :bookTypeId15)";

	    TypedQuery<Long> queryCount = em.createQuery(jpqlCount, Long.class);
	    queryCount.setParameter("maazounProfileId", maazounProfileId);
	    queryCount.setParameter("statusSold", "Sold");
	    queryCount.setParameter("statusCollected", "Collected");
	    queryCount.setParameter("bookTypeId8", bookTypeId8);
	    queryCount.setParameter("bookTypeId15", bookTypeId15);
	    
		return queryCount.getSingleResult();
		
	}
	
	@Override
	public long findCountMaazounBookSoldAndCollectedStatusByMaazounId(long maazounProfileId, 
			long bookTypeId8 , long bookTypeId15) {

		final String jpqlCount = "SELECT COUNT(w.contractNumber) FROM MaazounBookWarehouse w "
	            + "JOIN MaazounBookRequestInfo r ON r.id = w.maazounBookRequestInfoFk "
	            + "WHERE r.maazounProfileFk.id = :maazounProfileId "
	            + "AND w.statusFk = :statusSold "
	            + "AND (w.bookTypeId = :bookTypeId8 OR w.bookTypeId = :bookTypeId15)";

	    TypedQuery<Long> queryCount = em.createQuery(jpqlCount, Long.class);
	    queryCount.setParameter("maazounProfileId", maazounProfileId);
	    queryCount.setParameter("statusSold", "Sold");
	    queryCount.setParameter("bookTypeId8", bookTypeId8);
	    queryCount.setParameter("bookTypeId15", bookTypeId15);
	    
		return queryCount.getSingleResult();
		
	}
}