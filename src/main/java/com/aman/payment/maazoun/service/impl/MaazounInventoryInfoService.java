package com.aman.payment.maazoun.service.impl;

import java.time.Year;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.payment.maazoun.model.MaazounBookWarehouse;
import com.aman.payment.maazoun.model.MaazounInventoryNumber;
import com.aman.payment.maazoun.service.MaazounInventoryNumberService;
import com.aman.payment.util.StatusConstant;

@Service
public class MaazounInventoryInfoService {

	private int counter = 0;
	@Autowired
	private MaazounInventoryNumberService maazounInventoryNumberService;

    public synchronized MaazounBookWarehouse incrementCounter(MaazounBookWarehouse maazounBookWarehouse,
			Long sectorId, Date receivedAt) {
        
    	long bookInventoryNumber;
		long year = Year.now().getValue();
		String typeId = null;

		if (String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_ZAWAG_MUSLIM_ID_8)
				|| String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_TASADOK_ID_8)
				|| String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_MORAGAA_ID_8)
				|| String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_ZAWAG_MUSLIM_ID_15)
				|| String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_TASADOK_ID_15)
				|| String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_MORAGAA_ID_15)) {
			typeId = StatusConstant.BOOK_ZAWAG_MUSLIM_ID_8 + "-" + StatusConstant.BOOK_TASADOK_ID_8 + "-"
					+ StatusConstant.BOOK_MORAGAA_ID_8; // 9-11-12
		}else if (String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_ZAWAG_MELALY_ID_8)
				|| String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_ZAWAG_MELALY_ID_15)) {
			typeId = StatusConstant.BOOK_ZAWAG_MELALY_ID_8; //13
		}else if (String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_TALAK_ID_8)
				|| String.valueOf(maazounBookWarehouse.getBookTypeId()).equals(StatusConstant.BOOK_TALAK_ID_15)) {
			typeId = StatusConstant.BOOK_TALAK_ID_8; //10
		}

		Optional<MaazounInventoryNumber> inventoryNumberOptional = maazounInventoryNumberService
				.findBySectorIdAndTypeIdAndYear(sectorId, typeId, year);

		if (inventoryNumberOptional.isPresent()) {
			MaazounInventoryNumber eMaazounInventoryNumber = inventoryNumberOptional.get();

			bookInventoryNumber = eMaazounInventoryNumber.getInventorySequance() + 1;

			eMaazounInventoryNumber.setInventorySequance(bookInventoryNumber);
			eMaazounInventoryNumber.setUpdatedAt(receivedAt);
			maazounInventoryNumberService.save(eMaazounInventoryNumber);
		} else {
			MaazounInventoryNumber eMaazounInventoryNumber = new MaazounInventoryNumber();
			eMaazounInventoryNumber.setInventorySequance((long) 1);
			eMaazounInventoryNumber.setSectorId(sectorId);
			eMaazounInventoryNumber.setTypeId(typeId);
			eMaazounInventoryNumber.setYear(year);
			eMaazounInventoryNumber.setUpdatedAt(receivedAt);
			maazounInventoryNumberService.save(eMaazounInventoryNumber);

			bookInventoryNumber = 1;
		}

		maazounBookWarehouse.setBookInventoryNumber(bookInventoryNumber);
		maazounBookWarehouse.setBookInventoryReference(year);
		
        counter++;
        
        return maazounBookWarehouse;
    }

    public int getCounter() {
        return counter;
    }
	

}
