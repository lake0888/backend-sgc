package com.alcon3sl.cms.services.util.coin;

import com.alcon3sl.cms.model.util.coin.Coin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface CoinService {
    List<Coin> findAll(String name);
    Page<Coin> findAll(String name, PageRequest pageRequest);

    Coin findById(Long coinId);

    Coin save(Coin coin);

    Coin deleteById(Long coinId);

    Coin updateById(Long coinId, Coin tempData);

    List<Coin> deleteAllById(List<Long> listId);
}
