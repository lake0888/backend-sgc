package com.alcon3sl.cms.services.bank;

import com.alcon3sl.cms.model.bank.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BankService {
    List<Bank> findAll(String name);
    Page<Bank> findAll(String name, PageRequest pageRequest);
    Bank findById(Long bankId);
    Bank save(Bank bank);
    Bank deleteById(Long bankId);
    Bank updateById(Long bankId, Bank tempData);
    List<Bank> deleteAllById(List<Long> listId);
}
