package com.residea.residea.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.residea.residea.entities.PrezzoPerCap;
import com.residea.residea.entities.Citta;

public interface PrezzoPerCapService {

    PrezzoPerCap createPrezzo(PrezzoPerCap prezzo);

    List<PrezzoPerCap> getAllPrezzi();

    Optional<PrezzoPerCap> getPrezzoByCap(String cap);

    PrezzoPerCap updatePrezzo(String cap, PrezzoPerCap updatedPrezzo);

    void deletePrezzo(String cap);

    List<PrezzoPerCap> findByCitta(Citta citta);

    List<PrezzoPerCap> findByValidFromAfter(LocalDate date);

    List<PrezzoPerCap> findByValidToBefore(LocalDate date);
}
