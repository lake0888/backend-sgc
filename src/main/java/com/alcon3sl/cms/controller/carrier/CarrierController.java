package com.alcon3sl.cms.controller.carrier;

import com.alcon3sl.cms.model.carrier.Carrier;
import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.services.carrier.DbCarrierService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "carrier")
public class CarrierController {
    private final DbCarrierService carrierService;
    @Autowired
    public CarrierController(DbCarrierService carrierService) {
        this.carrierService = carrierService;
    }

    @GetMapping
    public ResponseEntity<Page<Carrier>> findAll(
            @RequestParam Optional<String> filter,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy
            ) {
        var carrierList = carrierService.findAll(filter.orElse(""), PageRequest.of(
                page.orElse(0),
                size.orElse(20),
                Sort.Direction.ASC, sortBy.orElse("name")
        ));
        if (carrierList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(carrierList, HttpStatus.OK);
    }

    @GetMapping(path = "{carrierId}")
    public ResponseEntity<Carrier> findById(@PathVariable(name = "carrierId") Long carrierId) {
        return new ResponseEntity<>(carrierService.findById(carrierId), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Carrier> save(
            @RequestPart(name = "carrier") Carrier carrier,
            @RequestPart(name = "imageFile") MultipartFile imageFile) throws IOException {
        Image image = new Image(
                imageFile.getOriginalFilename(),
                imageFile.getContentType(),
                imageFile.getBytes()
        );
        carrier.getContactDetails().setImage(image);
        return new ResponseEntity<>(carrierService.save(carrier), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{carrierId}")
    public ResponseEntity<Carrier> deleteById(@PathVariable(name = "carrierId") Long carrierId) {
        return new ResponseEntity<>(carrierService.deleteById(carrierId), HttpStatus.OK);
    }

    @PutMapping(path = "{carrierId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Carrier> updateById(
            @PathVariable(name = "carrierId") Long carrierId,
            @RequestPart(name = "carrier") Carrier tempData,
            @RequestPart(name = "imageFile") MultipartFile imageFile) throws IOException {
        Image image = new Image(
                imageFile.getOriginalFilename(),
                imageFile.getContentType(),
                imageFile.getBytes()
        );
        tempData.getContactDetails().setImage(image);
        return new ResponseEntity<>(carrierService.updateById(carrierId, tempData), HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllById")
    public ResponseEntity<List<Carrier>> deleteAllById(
            @RequestParam Optional<List<Long>> listId
    ) {
        var carrierList = carrierService.deleteAllById(listId.orElse(new ArrayList<>()));
        if (carrierList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(carrierList, HttpStatus.OK);
    }
}
