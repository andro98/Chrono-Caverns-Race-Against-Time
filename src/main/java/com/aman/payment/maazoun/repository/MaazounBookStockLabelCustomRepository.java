package com.aman.payment.maazoun.repository;

import com.aman.payment.maazoun.model.MaazounBookStockLabel;
import com.aman.payment.maazoun.model.payload.SearchStockLabelRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface MaazounBookStockLabelCustomRepository {
    Page<MaazounBookStockLabel> findBy(SearchStockLabelRequest searchStockLabelRequest, Set<Long> locationIds, Pageable pageable);

    List<MaazounBookStockLabel> findBy(SearchStockLabelRequest searchStockLabelRequest, Set<Long> locationIds);
}
