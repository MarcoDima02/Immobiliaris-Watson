package com.residea.residea.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.residea.residea.entities.PrezzoPerCap;
import com.residea.residea.entities.Citta;

@Repository
public interface PrezzoPerCapRepo extends JpaRepository<PrezzoPerCap, String> {

    List<PrezzoPerCap> findByCitta(Citta citta);

    List<PrezzoPerCap> findByValidFromAfter(java.time.LocalDate date);

    List<PrezzoPerCap> findByValidToBefore(java.time.LocalDate date);

}
