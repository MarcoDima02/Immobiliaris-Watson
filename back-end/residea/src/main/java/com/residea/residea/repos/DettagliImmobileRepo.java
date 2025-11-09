package com.residea.residea.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.residea.residea.entities.DettagliImmobile;

@Repository
public interface DettagliImmobileRepo extends JpaRepository<DettagliImmobile, Integer> {}
