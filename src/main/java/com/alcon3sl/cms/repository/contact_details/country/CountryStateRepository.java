package com.alcon3sl.cms.repository.contact_details.country;

import com.alcon3sl.cms.model.contact_details.country.countrystate.CountryState;
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

    @Query(value = "SELECT * FROM country_state c WHERE UPPER(c.name) = ?1", nativeQuery = true)
    List<CountryState> findByName(String name);

    @Query(value = "SELECT * FROM country_state c WHERE c.id IN (?1)", nativeQuery = true)
    List<CountryState> findAllByListId(List<Long> listId);

    @Modifying
    @Query(value = "DELETE FROM country_state c WHERE c.id IN (?1)", nativeQuery = true)
    void deleteAllByListId(List<Long> listId);
}
