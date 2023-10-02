package com.alcon3sl.cms.repository.util.country_state;

import com.alcon3sl.cms.model.util.countrystate.CountryState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryStateRepository extends JpaRepository<CountryState, Long> {
    @Query(value = "SELECT cs.id, cs.name, cs.code_country, c.id as cId, c.name as cName, " +
            "c.nationality as cNationality, c.code as cCode, c.phone_code as cPhoneCode " +
            "FROM country_state cs INNER JOIN country c ON (cs.code_country = c.code) " +
            "WHERE cs.name ~* ?1 OR c.name ~* ?1 OR c.nationality ~* ?1 " +
            "ORDER BY cs.name ASC, c.name ASC", nativeQuery = true)
    List<CountryState> findAllByNameOrCountry(String filter);

    @Query(value = "SELECT * FROM country_state c WHERE UPPER(c.code_country) = ?1", nativeQuery = true)
    List<CountryState> findAllByCountryCode(String code);

    List<CountryState> findByName(String name);
}
