package com.alcon3sl.cms.services.carrier;

import com.alcon3sl.cms.model.carrier.Carrier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CarrierService {
    Page<Carrier> findAll(String filter, PageRequest pageRequest);

    Carrier findById(Long carrierId);

    Carrier save(Carrier carrier);

    Carrier deleteById(Long carrierId);

    Carrier updateById(Long carrierId, Carrier tempData);

    List<Carrier> findAllByListId(List<Long> listId);

    @Transactional
    List<Carrier> deleteAllByListId(List<Long> listId);
}
