package com.alcon3sl.cms.services.bank;

import com.alcon3sl.cms.exception.BankNotFoundException;
import com.alcon3sl.cms.model.bank.Bank;
import com.alcon3sl.cms.model.util.JUtil;
import com.alcon3sl.cms.model.util.address.Address;
import com.alcon3sl.cms.model.util.contact_details.ContactDetails;
import com.alcon3sl.cms.repository.bank.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DbBankService implements BankService {

    private final BankRepository bankRepository;

    @Autowired
    public DbBankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public List<Bank> findAll(String filter) {
        List<Bank> bankList = new ArrayList<>();

        String []filters = filter.replaceAll("\s+", " ").split(" ");
        for (String current: filters) {
            var subList = this.bankRepository.findAll(current);
            bankList = JUtil.refineList(bankList, subList);
        }

        return bankList;
    }

    @Override
    public Page<Bank> findAll(String filter, PageRequest pageRequest) {
        var bankList = this.findAll(filter);

        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), bankList.size());

        var subList = bankList.subList(start, end);
        return new PageImpl<>(subList, pageRequest, bankList.size());
    }

    @Override
    public Bank findById(Long bankId) {
        return bankRepository.findById(bankId)
                .orElseThrow(() -> new BankNotFoundException(("Bank not found")));
    }

    @Override
    public Bank save(Bank bank) {
        boolean flag = (bank == null || bank.getCode().isEmpty() || bank.getName().isEmpty());
        if (flag)
            throw new IllegalStateException("Wrong data");
        else if (bank.getCode() != null && !bankRepository.findByCodeIgnoreCase(bank.getCode().trim().toUpperCase()).isEmpty())
            throw new IllegalArgumentException("The code already exists");
        else if (bank.getName() != null && !bankRepository.findByNameIgnoreCase(bank.getName().trim().toUpperCase()).isEmpty())
            throw new IllegalArgumentException("The name already exists");
        else if (bank.getSwift() != null && !bankRepository.findBySwiftIgnoreCase(bank.getSwift().trim().toUpperCase()).isEmpty())
            throw new IllegalArgumentException("The swift already exists");
        return bankRepository.save(bank);
    }

    @Override
    public Bank deleteById(Long bankId) {
        var bank = this.findById(bankId);
        bankRepository.deleteById(bankId);
        return bank;
    }

    @Override
    public Bank updateById(Long bankId, Bank tempData) {
        var bank = this.findById(bankId);

        String code = tempData.getCode();
        if (code != null && !code.isEmpty() && !Objects.equals(bank.getCode(), code)) {
            if (!bankRepository.findByCodeIgnoreCase(code.trim().toUpperCase()).isEmpty())
                throw new IllegalArgumentException("The code already exists");
            else
                bank.setCode(code);
        }

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(bank.getName(), name)) {
            if (!bankRepository.findByNameIgnoreCase(name.trim().toUpperCase()).isEmpty())
                throw new IllegalArgumentException("The name already exists");
            else
                bank.setName(name);
        }

        String swift = tempData.getSwift();
        if (swift != null && !swift.isEmpty() && !Objects.equals(bank.getSwift(), swift)) {
            if (!bankRepository.findBySwiftIgnoreCase(swift.trim().toUpperCase()).isEmpty())
                throw new IllegalArgumentException("The swift already exists");
            else
                bank.setSwift(swift);
        }

        Address address = tempData.getAddress();
        if (address != null && !Objects.equals(bank.getAddress(), address)) {
            bank.setAddress(address);
        }

        ContactDetails contactDetails = tempData.getContactDetails();
        if (contactDetails != null && !Objects.equals(bank.getContactDetails(), contactDetails)) {
            bank.setContactDetails(contactDetails);
        }

        return bankRepository.save(bank);
    }

    @Override
    public List<Bank> deleteAllById(List<Long> listId) {
        var bankList = bankRepository.findAllById(listId);
        bankRepository.deleteAllById(listId);
        return bankList;
    }
}
