package com.alcon3sl.cms.services.provider;

import com.alcon3sl.cms.model.provider.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProviderService {
    Page<Provider> findAll(String name, PageRequest pageRequest);

    Provider findById(Long providerId);

    Provider save(Provider provider);

    Provider deleteById(Long providerId);

    Provider updateById(Long providerId, Provider tempData);

    List<Provider> deleteAllById(List<Long> listId);

    List<Provider> findByName(String name);
}
