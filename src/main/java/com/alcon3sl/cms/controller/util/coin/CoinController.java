package com.alcon3sl.cms.controller.util.coin;

import com.alcon3sl.cms.model.util.coin.Coin;
import com.alcon3sl.cms.services.util.coin.DbCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "coin")
public class CoinController {
    private final DbCoinService coinService;

    @Autowired
    public CoinController(DbCoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping
    public ResponseEntity<Page<Coin>> findAll(
            @RequestParam Optional<String> name,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy
            ) {
        var coinList = coinService.findAll(name.orElse(""), PageRequest.of(
                page.orElse(0),
                size.orElse(10),
                Sort.Direction.ASC, sortBy.orElse("name")
        ));
        if (coinList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(coinList, HttpStatus.OK);
    }

    @GetMapping(path = "{coinId}")
    public ResponseEntity<Coin> findById(@PathVariable(name = "coinId") Long coinId) {
        return new ResponseEntity<>(coinService.findById(coinId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Coin> save(@RequestPart(name = "coin") Coin coin) {
        return new ResponseEntity<>(coinService.save(coin), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{coinId}")
    public ResponseEntity<Coin> deleteById(@PathVariable(name = "coinId") Long coinId) {
        return new ResponseEntity<>(coinService.deleteById(coinId), HttpStatus.OK);
    }

    @PutMapping(path = "{coinId}")
    public ResponseEntity<Coin> updateById(
            @PathVariable(name = "coinId") Long coinId,
            @RequestPart(name = "coin") Coin tempData
    ) {
        return new ResponseEntity<>(coinService.updateById(coinId, tempData), HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllById")
    public ResponseEntity<List<Coin>> deleteAllById(
            @RequestParam(name = "listId") List<Long> listId
    ) {
        var coinList = coinService.deleteAllById(listId);
        if (coinList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(coinList, HttpStatus.OK);
    }

    @GetMapping(path = "findAll")
    public ResponseEntity<List<Coin>> findAll(
            @RequestParam(name = "name") Optional<String> name
    ) {
        var coinList = coinService.findAll(name.orElse(""));
        if (coinList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(coinList, HttpStatus.OK);
    }
}
