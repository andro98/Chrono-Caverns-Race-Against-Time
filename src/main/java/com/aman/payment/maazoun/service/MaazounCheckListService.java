package com.aman.payment.maazoun.service;

import java.util.List;

import com.aman.payment.maazoun.model.MaazounCheckListLookup;

public interface MaazounCheckListService extends MaazounGenericService<MaazounCheckListLookup,Long>{

	public List<MaazounCheckListLookup> findBySubServiceId(long subServiceId);
}
