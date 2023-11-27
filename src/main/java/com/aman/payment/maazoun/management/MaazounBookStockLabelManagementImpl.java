package com.aman.payment.maazoun.management;

import java.time.Instant;
import java.util.*;

import com.aman.payment.maazoun.model.payload.*;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.aman.payment.auth.management.LookupManagement;
import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.service.LocationService;
import com.aman.payment.maazoun.mapper.StockLabelMapper;
import com.aman.payment.maazoun.model.MaazounBookStockLabel;
import com.aman.payment.maazoun.model.dto.PagingDTO;
import com.aman.payment.maazoun.service.CryptoMngrMaazounService;
import com.aman.payment.maazoun.service.MaazounBookStockLabelService;
import com.aman.payment.util.StatusConstant;

@Component
public class MaazounBookStockLabelManagementImpl extends ValidationAndPopulateManagement implements MaazounBookStockLabelManagement {

    final static Logger logger = Logger.getLogger("maazoun");


    private final MaazounBookStockLabelService maazounBookStockLabelService;
    private final LocationService locationService;
    private final StockLabelMapper stockLabelMapper;
    private final CryptoMngrMaazounService cryptoMngrMaazounService;
    private final LookupManagement authLookupManagement;
    private final SubServicePriceTierManagement subServicePriceTierManagement;

    @Autowired
    public MaazounBookStockLabelManagementImpl(MaazounBookStockLabelService maazounBookStockLabelService,
                                               LocationService locationService, CryptoMngrMaazounService cryptoMngrMaazounService,
                                               StockLabelMapper stockLabelMapper, LookupManagement authLookupManagement,
                                               SubServicePriceTierManagement subServicePriceTierManagement) {
        super();
        this.maazounBookStockLabelService = maazounBookStockLabelService;
        this.locationService = locationService;
        this.cryptoMngrMaazounService = cryptoMngrMaazounService;
        this.stockLabelMapper = stockLabelMapper;
        this.authLookupManagement = authLookupManagement;
        this.subServicePriceTierManagement = subServicePriceTierManagement;
    }


    @Override
    public List<String> getAllStockLabels() {

        List<String> results = new ArrayList<String>();


        return results;
    }


    @Override
    public String generateSerialNumber(CustomUserDetails customUserDetails, StockLabelRequest stockLabelRequest) {

        Date createdAt = Date.from(Instant.now());

        long sequanceNumber = authLookupManagement.
                getInsuranceNumberWithoutIncrement(stockLabelRequest.getBootTypeId(), stockLabelRequest.getLocationId(), 3);

        List<MaazounBookStockLabel> labels = new ArrayList<MaazounBookStockLabel>();
        for (int i = 1; i <= stockLabelRequest.getNumberOfLabels(); i++) {

            long counter = sequanceNumber + i;
            String serialNumberFormat = String.format("%03d", stockLabelRequest.getLocationId())
                    + String.format("%08d", counter) + String.format("%02d", stockLabelRequest.getBootTypeId());

            MaazounBookStockLabel eMaazounBookStockLabel = new MaazounBookStockLabel();
            eMaazounBookStockLabel.setCreatedAt(createdAt);
            eMaazounBookStockLabel.setCreatedBy(customUserDetails.getUsername());
            eMaazounBookStockLabel.setLabelCode(serialNumberFormat);
            eMaazounBookStockLabel.setLocationId(stockLabelRequest.getLocationId());
            eMaazounBookStockLabel.setBookTypeId(stockLabelRequest.getBootTypeId());
            eMaazounBookStockLabel.setStatusFk(StatusConstant.STATUS_NEW);
            eMaazounBookStockLabel.setBookTierId(stockLabelRequest.getBookTierId());
            labels.add(eMaazounBookStockLabel);

        }

        maazounBookStockLabelService.save(labels);

        authLookupManagement.editInsuranceNumber(3, stockLabelRequest.getLocationId(),
                stockLabelRequest.getNumberOfLabels(), stockLabelRequest.getBootTypeId());

        return cryptoMngrMaazounService.encrypt(
                stockLabelMapper.maazounBookStockLabelsToStockLabelDTOs(labels).toString());
    }


    @Override
    public String editSerialNumberStatus(EditStockLabelStatusRequest editStockLabelStatusRequest) {

        maazounBookStockLabelService.updateStatusByIds(StatusConstant.STATUS_PRINTED,
                editStockLabelStatusRequest.getStockLabelIds());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("response", "Ids Printed Successfully");

        return cryptoMngrMaazounService.encrypt(jsonObject.toString());
    }


    @Override
    public PagingDTO searchStockLabel(SearchStockLabelRequest searchStockLabelRequest, CustomUserDetails customUserDetails) {

        Set<Long> locationIds = getLocationsFromPos(customUserDetails.getPosSet());

        Pageable pageable = PageRequest.of(searchStockLabelRequest.getPageNo(), searchStockLabelRequest.getPageSize());

        Page<MaazounBookStockLabel> pageResult = null;
        PagingDTO obj = new PagingDTO();

        try {
            if (searchStockLabelRequest.getBookTypeId() != null && searchStockLabelRequest.getLocationId() != null &&
                    searchStockLabelRequest.getStatus() != null) {
                pageResult = maazounBookStockLabelService.findByStatusFkAndBookTypeIdAndLocationId(searchStockLabelRequest.getStatus(),
                        searchStockLabelRequest.getBookTypeId(), searchStockLabelRequest.getLocationId(), pageable);

            } else if (searchStockLabelRequest.getBookTypeId() != null && searchStockLabelRequest.getLocationId() != null) {
                pageResult = maazounBookStockLabelService.findByBookTypeIdAndLocationId(searchStockLabelRequest.getBookTypeId(),
                        searchStockLabelRequest.getLocationId(), pageable);
            } else if (searchStockLabelRequest.getStatus() != null && searchStockLabelRequest.getLocationId() != null) {
                pageResult = maazounBookStockLabelService.findByStatusFkAndLocationId(searchStockLabelRequest.getStatus(),
                        searchStockLabelRequest.getLocationId(), pageable);
            } else if (searchStockLabelRequest.getStatus() != null && searchStockLabelRequest.getBookTypeId() != null) {
                pageResult = maazounBookStockLabelService.findByStatusFkAndBookTypeIdAndLocationIdIn(searchStockLabelRequest.getStatus(),
                        searchStockLabelRequest.getBookTypeId(), locationIds, pageable);

            } else if (searchStockLabelRequest.getStatus() != null) {
                pageResult = maazounBookStockLabelService.findByStatusFkAndLocationIdIn(searchStockLabelRequest.getStatus(), locationIds,
                        pageable);
            } else if (searchStockLabelRequest.getLocationId() != null) {
                pageResult = maazounBookStockLabelService.findByLocationId(searchStockLabelRequest.getLocationId(), pageable);
            } else if (searchStockLabelRequest.getBookTypeId() != null) {
                pageResult = maazounBookStockLabelService.findByBookTypeIdAndLocationIdIn(searchStockLabelRequest.getBookTypeId(), locationIds,
                        pageable);
            }


            obj.setCount(pageResult.getTotalElements());
            obj.setTotalPages(pageResult.getTotalPages());

            List<String> results = new ArrayList<String>();
            pageResult.getContent().stream().forEach(s -> {
                results.add(cryptoMngrMaazounService.encrypt(
                        stockLabelMapper.maazounBookStockLabelToStockLabelDTO(s).toString()));
            });
            obj.setBookRequests(results);
            return obj;

        } catch (Exception e) {
            obj.setCount((long) 0);
            obj.setTotalPages(0);
            obj.setBookRequests(new ArrayList<String>());
            return obj;
        }

    }


    @Override
    public String findByStatusFkByLocations(String statusFk, CustomUserDetails customUserDetails) {

        Set<Long> locationIds = getLocationsFromPos(customUserDetails.getPosSet());

        List<MaazounBookStockLabel> labels = new ArrayList<MaazounBookStockLabel>();
        labels = maazounBookStockLabelService.findByStatusFkAndLocationIdInOrderByIdAsc(statusFk, locationIds);

        return cryptoMngrMaazounService.encrypt(
                stockLabelMapper.maazounBookStockLabelsToStockLabelDTOs(labels).toString());
    }


    @Override
    public String editStockLabelStatus(EditStockLabelRequest editStockLabelRequest) {

        maazounBookStockLabelService.updateStatusById(editStockLabelRequest.getStatus(),
                editStockLabelRequest.getId());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("response", "Status Changed Successfully");

        return cryptoMngrMaazounService.encrypt(jsonObject.toString());
    }

    @Override
    public String feesBySerialNumber(StockLabelBySerialRequest decryptStockLabelRequest) {
        Optional<MaazounBookStockLabel> label = maazounBookStockLabelService
                .findByLabelCode(decryptStockLabelRequest.getSerialNumber());

        JSONObject jsonObject = new JSONObject();
        if (label.isPresent()) {
            Double fees = subServicePriceTierManagement.getFeesByTierId(label.get().getBookTierId());
            jsonObject.put("response", fees.toString());
        } else {
            jsonObject.put("response", "fail");
        }

        return cryptoMngrMaazounService.encrypt(jsonObject.toString());
    }
}
