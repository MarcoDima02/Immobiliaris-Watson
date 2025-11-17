package com.residea.residea.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.residea.residea.entities.Lead;
import com.residea.residea.entities.Utente;
import com.residea.residea.repos.LeadRepo;

@Service
@Transactional
public class LeadServiceImpl implements LeadService {

    private final LeadRepo leadRepo;

    public LeadServiceImpl(LeadRepo leadRepo) {
        this.leadRepo = leadRepo;
    }

    @Override
    public Lead createLead(Lead lead) {
        return leadRepo.save(lead);
    }

    @Override
    public List<Lead> getAllLeads() {
        return leadRepo.findAll();
    }

    @Override
    public Optional<Lead> getLeadById(Integer idLead) {
        return leadRepo.findById(idLead);
    }

    @Override
    public Lead updateLead(Integer idLead, Lead updatedLead) {
        return leadRepo.findById(idLead)
                .map(existingLead -> {
                    existingLead.setNome(updatedLead.getNome());
                    existingLead.setEmail(updatedLead.getEmail());
                    existingLead.setTelefono(updatedLead.getTelefono());
                    existingLead.setCitta(updatedLead.getCitta());
                    existingLead.setFonte(updatedLead.getFonte());
                    existingLead.setCampagna(updatedLead.getCampagna());
                    existingLead.setUtmSource(updatedLead.getUtmSource());
                    existingLead.setUtmMedium(updatedLead.getUtmMedium());
                    existingLead.setUtmCampaign(updatedLead.getUtmCampaign());
                    existingLead.setConvertitoInRichiesta(updatedLead.isConvertitoInRichiesta());
                    existingLead.setIdRichiesta(updatedLead.getIdRichiesta());
                    existingLead.setAssegnatoA(updatedLead.getAssegnatoA());
                    existingLead.setNote(updatedLead.getNote());
                    existingLead.setUtente(updatedLead.getUtente());
                    return leadRepo.save(existingLead);
                })
                .orElseThrow(() -> new RuntimeException("Lead non trovato con id: " + idLead));
    }

    @Override
    public void deleteLead(Integer idLead) {
        if (!leadRepo.existsById(idLead)) {
            throw new RuntimeException("Lead non trovato con id: " + idLead);
        }
        leadRepo.deleteById(idLead);
    }

    @Override
    public List<Lead> findByEmail(String email) {
        return leadRepo.findByEmail(email);
    }

    @Override
    public List<Lead> findByCitta(String citta) {
        return leadRepo.findByCitta(citta);
    }

    @Override
    public List<Lead> findByUtente(Utente utente) {
        return leadRepo.findByUtente(utente);
    }
}
