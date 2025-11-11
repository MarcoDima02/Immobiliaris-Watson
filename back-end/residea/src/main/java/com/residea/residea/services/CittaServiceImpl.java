package com.residea.residea.services;

import com.residea.residea.entities.Citta;
import com.residea.residea.repos.CittaRepo;
import com.residea.residea.services.CittaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CittaServiceImpl implements CittaService {

    @Autowired
    private CittaRepo cittaRepo;

    @Override
    public List<Citta> getAllCitta() {
        return cittaRepo.findAll();
    }

    @Override
    public Citta getCittaById(Integer idCitta) {
        return cittaRepo.findById(idCitta).orElse(null);
    }

    @Override
    public List<Citta> getCittaByNome(String nome) {
        return cittaRepo.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    public List<Citta> getCittaByProvincia(String provincia) {
        return cittaRepo.findByProvinciaIgnoreCase(provincia);
    }

    @Override
    public List<Citta> getCittaByRegione(String regione) {
        return cittaRepo.findByRegioneIgnoreCase(regione);
    }

    @Override
    public Citta getCittaByCodiceIstat(String codiceIstat) {
        return cittaRepo.findByCodiceIstat(codiceIstat).orElse(null);
    }

    @Override
    public Citta salvaCitta(Citta citta) {
        return cittaRepo.save(citta);
    }

    @Override
    public Citta aggiornaCitta(Citta cittaAggiornata) {
        Optional<Citta> esistente = cittaRepo.findById(cittaAggiornata.getIdCitta());
        if (esistente.isPresent()) {
            return cittaRepo.save(cittaAggiornata);
        }
        return null;
    }

    @Override
    public void eliminaCitta(Integer idCitta) {
        cittaRepo.deleteById(idCitta);
    }
}
