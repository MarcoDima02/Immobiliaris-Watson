package com.residea.residea.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.residea.residea.entities.EmailLog;

@Repository
public interface EmailLogRepo extends JpaRepository<EmailLog, Integer> {

}
