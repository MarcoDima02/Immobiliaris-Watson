package com.residea.residea.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.residea.residea.entities.Immagine;

@Repository
public interface ImmagineRepo extends JpaRepository<Immagine, Integer> {
    List<Immagine> findByImmobile_IdImmobile(Integer idImmobile);
}
