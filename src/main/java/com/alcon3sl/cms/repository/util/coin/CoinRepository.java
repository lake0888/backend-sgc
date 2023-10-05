package com.alcon3sl.cms.repository.util.coin;

import com.alcon3sl.cms.model.util.coin.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {
    @Query(value = "SELECT * FROM coin c WHERE c.name ~* ?1", nativeQuery = true)
    List<Coin> findAll(String name);

    List<Coin> findByCodeIgnoreCase(String code);

    List<Coin> findByNameIgnoreCase(String name);
}
