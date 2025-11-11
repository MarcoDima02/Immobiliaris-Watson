package com.residea.residea.services;

import java.util.List;
import java.util.Optional;

import com.residea.residea.entities.Lead;

public interface LeadService {

    Lead createLead(Lead lead);

    List<Lead> getAllLeads();

    Optional<Lead> getLeadById(Integer idLead);

    Lead updateLead(Integer idLead, Lead updatedLead);

    void deleteLead(Integer idLead);

    List<Lead> findByEmail(String email);

    List<Lead> findByCitta(String citta);
}
