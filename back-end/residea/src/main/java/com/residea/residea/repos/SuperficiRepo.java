package com.residea.residea.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.residea.residea.entities.Superficie;

@Repository
public interface SuperficiRepo extends JpaRepository<Superficie, Integer> {}
