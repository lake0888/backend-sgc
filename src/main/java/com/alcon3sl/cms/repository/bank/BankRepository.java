package com.alcon3sl.cms.repository.bank;

import com.alcon3sl.cms.model.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    @Query(value = "SELECT * FROM bank b WHERE b.name ~* ?1 OR b.code ~* ?1", nativeQuery = true)
    List<Bank> findAll(String filter);

    List<Bank> findByCodeIgnoreCase(String code);

    List<Bank> findByNameIgnoreCase(String name);
    List<Bank> findBySwiftIgnoreCase(String swift);
}
