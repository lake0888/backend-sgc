package com.alcon3sl.cms.controller.bank;

import com.alcon3sl.cms.model.bank.Bank;
import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.services.bank.DbBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "bank")
public class BankController {
    private final DbBankService bankService;

    @Autowired
    public BankController(DbBankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    public ResponseEntity<Page<Bank>> findAll(
            @RequestParam Optional<String> filter,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy
            ) {
        var bankList = bankService.findAll(filter.orElse(""), PageRequest.of(
                page.orElse(0),
                size.orElse(10),
                Sort.Direction.ASC, sortBy.orElse("name")
        ));
        if (bankList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(bankList, HttpStatus.OK);
    }

    @GetMapping(path = "{bankId}")
    public ResponseEntity<Bank> findById(@PathVariable(name = "bankId") Long bankId) {
        return new ResponseEntity<>(bankService.findById(bankId), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Bank> save(
            @RequestPart(name = "bank") Bank bank,
            @RequestPart(name = "imageFile") MultipartFile imageFile
            ) throws IOException {
        Image image = new Image(
                imageFile.getOriginalFilename(),
                imageFile.getContentType(),
                imageFile.getBytes());
        bank.getContactDetails().setImage(image);
        return new ResponseEntity<>(bankService.save(bank), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{bankId}")
    public ResponseEntity<Bank> deleteById(@PathVariable(name = "bankId") Long bankId) {
        return new ResponseEntity<>(bankService.deleteById(bankId), HttpStatus.OK);
    }

    @PutMapping(path = "{bankId}")
    public ResponseEntity<Bank> updateById(
            @PathVariable(name = "bankId") Long bankId,
            @RequestPart(name = "bank") Bank tempData,
            @RequestPart(name = "imageFile") MultipartFile imageFile
    ) throws IOException {
        Image image = new Image(
                imageFile.getOriginalFilename(),
                imageFile.getContentType(),
                imageFile.getBytes());
        tempData.getContactDetails().setImage(image);
        return new ResponseEntity<>(bankService.updateById(bankId, tempData), HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllById")
    public ResponseEntity<List<Bank>> deleteAllById(
            @RequestParam(name = "listId") List<Long> listId
    ) {
        var bankList = bankService.deleteAllById(listId);
        if (bankList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(bankList, HttpStatus.OK);
    }

}
