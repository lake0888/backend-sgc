package com.alcon3sl.cms.controller.bank;

import com.alcon3sl.cms.model.bank.BankAccount;
import com.alcon3sl.cms.services.bank.DbBankAccountService;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "account")
public class BankAccountController {
    private final DbBankAccountService bankAccountService;

    public BankAccountController(DbBankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public ResponseEntity<Page<BankAccount>> findAll(@RequestParam Optional<String> filter, Pageable pageable) {
        var page = bankAccountService.findAll(
                filter.orElse(""),
                PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    pageable.getSortOr(Sort.by(Sort.Direction.ASC, "number"))
        ));
        if (page.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping(path = "{accountId}")
    public ResponseEntity<BankAccount> findById(@PathVariable(name = "accountId") Long accountId) {
        return new ResponseEntity<>(bankAccountService.findById(accountId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BankAccount> save(@RequestPart(name = "account") BankAccount account) {
        return new ResponseEntity<>(bankAccountService.save(account), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{accountId}")
    public ResponseEntity<BankAccount> deleteById(@PathVariable(name = "accountId") Long accountId) {
        return new ResponseEntity<>(bankAccountService.deleteById(accountId), HttpStatus.OK);
    }

    @PutMapping(path = "{accountId}")
    public ResponseEntity<BankAccount> updateById(
            @PathVariable(name = "accountId") Long accountId,
            @RequestPart(name = "account") BankAccount tempData
    ) {
        return new ResponseEntity<>(bankAccountService.updateById(accountId, tempData), HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllById")
    public ResponseEntity<List<BankAccount>> deleteAllById(
            @RequestParam(name = "listId") List<Long> listId
    ) {
        var accountList = bankAccountService.deleteAllById(listId);
        if (accountList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }
}
