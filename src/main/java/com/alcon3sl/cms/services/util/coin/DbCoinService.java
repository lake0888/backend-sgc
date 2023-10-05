package com.alcon3sl.cms.services.util.coin;

import com.alcon3sl.cms.exception.CoinNotFoundException;
import com.alcon3sl.cms.model.util.coin.Coin;
import com.alcon3sl.cms.repository.util.coin.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DbCoinService implements CoinService {

    private final CoinRepository coinRepository;

    @Autowired
    public DbCoinService(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    @Override
    public List<Coin> findAll(String name) {
        return this.coinRepository.findAll(name);
    }

    @Override
    public Page<Coin> findAll(String name, PageRequest pageRequest) {
        var coinList = this.findAll(name);

        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), coinList.size());

        var subList = coinList.subList(start, end);
        return new PageImpl<>(subList, pageRequest, coinList.size());
    }

    @Override
    public Coin findById(Long coinId) {
        return this.coinRepository.findById(coinId)
                .orElseThrow(() -> new CoinNotFoundException("Coin not found"));
    }

    @Override
    public Coin save(Coin coin) {
        boolean flag = (coin == null || coin.getCode().isEmpty() || coin.getName().isEmpty());
        if (flag)
            throw new IllegalStateException("Wrong data");
        else if (coin.getCode() != null && !coinRepository.findByCodeIgnoreCase(coin.getCode().trim().toUpperCase()).isEmpty())
            throw new IllegalArgumentException("The code already exists");
        else if (coin.getName() != null && !coinRepository.findByNameIgnoreCase(coin.getName().trim().toUpperCase()).isEmpty())
            throw new IllegalArgumentException("The name already exists");

        return coinRepository.save(coin);
    }

    @Override
    public Coin deleteById(Long coinId) {
        var coin = this.findById(coinId);
        coinRepository.deleteById(coinId);
        return coin;
    }

    @Override
    public Coin updateById(Long coinId, Coin tempData) {
        var coin = this.findById(coinId);

        String code = tempData.getCode();
        if (code != null && !code.isEmpty() && !Objects.equals(coin.getCode(), code)) {
            if (!coinRepository.findByCodeIgnoreCase(code.trim().toUpperCase()).isEmpty())
                throw new IllegalArgumentException("The code already exists");
            else
                coin.setCode(code);
        }

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(coin.getName(), name)) {
            if (!coinRepository.findByNameIgnoreCase(name.trim().toUpperCase()).isEmpty())
                throw new IllegalArgumentException("The name already exists");
            else
                coin.setName(name);
        }

        return coinRepository.save(coin);
    }

    @Override
    public List<Coin> deleteAllById(List<Long> listId) {
        var coinList = coinRepository.findAllById(listId);
        coinRepository.deleteAllById(listId);
        return coinList;
    }
}
