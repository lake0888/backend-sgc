package com.alcon3sl.cms.services.bank;

import com.alcon3sl.cms.exception.BankAccountNotFoundException;
import com.alcon3sl.cms.model.bank.Bank;
import com.alcon3sl.cms.model.bank.BankAccount;
import com.alcon3sl.cms.model.util.JUtil;
import com.alcon3sl.cms.model.util.coin.Coin;
import com.alcon3sl.cms.repository.bank.BankAccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DbBankAccountService implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public DbBankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public List<BankAccount> findAll(String filter) {
        List<BankAccount> accountList = new ArrayList<>();

        String []filters = filter.replaceAll("\s+", " ").split(" ");
        for (String current : filters) {
            var subList = bankAccountRepository.findAll(current);
            accountList = JUtil.refineList(accountList, subList);
        }

        return accountList;
    }

    @Override
    public Page<BankAccount> findAll(String filter, PageRequest pageRequest) {
        var accountList = this.findAll(filter);

        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), accountList.size());

        var subList = accountList.subList(start, end);
        return new PageImpl<>(subList, pageRequest, accountList.size());
    }

    @Override
    public BankAccount findById(Long accountId) {
        return bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Account not found"));
    }

    @Override
    public BankAccount save(BankAccount account) {
        boolean flag = (account == null || account.getNumber().isEmpty() || account.getCoin() == null || account.getBank() == null);
        if (flag)
            throw new IllegalStateException("Wrong data");
        else if (account.getNumber() != null && !bankAccountRepository.findByNumberIgnoreCase(account.getNumber().trim().toUpperCase()).isEmpty())
            throw new IllegalArgumentException("The account number already exists");
        else if (account.getIban() != null && !bankAccountRepository.findByIbanIgnoreCase(account.getIban().trim().toUpperCase()).isEmpty())
            throw new IllegalArgumentException("The iban number already exists");
        return bankAccountRepository.save(account);
    }

    @Override
    public BankAccount deleteById(Long accountId) {
        var account = this.findById(accountId);
        bankAccountRepository.deleteById(accountId);
        return account;
    }

    @Override
    public BankAccount updateById(Long accountId, BankAccount tempData) {
        var account = this.findById(accountId);

        String number = tempData.getNumber();
        if (number != null && !number.isEmpty() && !Objects.equals(account.getNumber(), number)) {
            if (!bankAccountRepository.findByNumberIgnoreCase(number.trim().toUpperCase()).isEmpty())
                throw new IllegalArgumentException("The account number already exists");
            else
                account.setNumber(number);
        }

        String iban = tempData.getIban();
        boolean flag = (iban == null || iban.isEmpty());
        if (iban != null && !iban.isEmpty() && !Objects.equals(account.getIban(), iban)) {
            if (!bankAccountRepository.findByIbanIgnoreCase(iban.trim().toUpperCase()).isEmpty())
                throw new IllegalArgumentException("The iban number already exists");
            else
                flag = true;
        }
        if (flag)
            account.setIban(iban);

        Coin coin = tempData.getCoin();
        if (coin != null && !Objects.equals(account.getCoin(), coin)) {
            account.setCoin(coin);
        }

        Bank bank = tempData.getBank();
        if (bank != null && !Objects.equals(account.getBank(), bank)) {
            account.setBank(bank);
        }

        return bankAccountRepository.save(account);
    }

    @Override
    public List<BankAccount> deleteAllById(List<Long> listId) {
        var accountList = bankAccountRepository.findAllById(listId);
        bankAccountRepository.deleteAllById(listId);
        return accountList;
    }
}
