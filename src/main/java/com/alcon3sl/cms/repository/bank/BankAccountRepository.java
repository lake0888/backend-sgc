package com.alcon3sl.cms.repository.bank;

import com.alcon3sl.cms.model.bank.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    @Query(value = "SELECT ba.id, ba.number, ba.iban, ba.coin_id, ba. bank_id, " +
            "c.id as cId, c.code as cCode, c.name as cName, " +
            "b.id as bId, b.code as bCode, b.name as bName, b.swift as bSwift, b.address_id, b. contact_details_id " +
            "FROM bank_account ba " +
            "LEFT JOIN coin c ON (ba.coin_id = c.id) " +
            "LEFT JOIN bank b ON (ba.bank_id = b.id) " +
            "WHERE ba.number ~* ?1 OR c.code ~* ?1 OR b.code ~* ?1", nativeQuery = true)
    List<BankAccount> findAll(String filter);

    List<BankAccount> findByNumberIgnoreCase(String number);
    List<BankAccount> findByIbanIgnoreCase(String iban);
}
