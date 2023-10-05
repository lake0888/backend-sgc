package com.alcon3sl.cms.services.bank;

import com.alcon3sl.cms.model.bank.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BankAccountService {
    List<BankAccount> findAll(String filter);

    Page<BankAccount> findAll(String filter, PageRequest pageRequest);

    BankAccount findById(Long accountId);

    BankAccount save(BankAccount account);

    BankAccount deleteById(Long accountId);

    BankAccount updateById(Long accountId, BankAccount tempData);

    List<BankAccount> deleteAllById(List<Long> listId);
}
