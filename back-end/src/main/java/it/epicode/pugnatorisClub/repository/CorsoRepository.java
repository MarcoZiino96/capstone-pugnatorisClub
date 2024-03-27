package it.epicode.pugnatorisClub.repository;

import it.epicode.pugnatorisClub.enums.ArtiMarziali;
import it.epicode.pugnatorisClub.model.Corso;
import it.epicode.pugnatorisClub.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CorsoRepository extends JpaRepository<Corso, Long> {

    @Query("SELECT c FROM Corso c WHERE c.categoria = :categoria")
    Optional<Corso> findByCategoria(ArtiMarziali categoria);

    @Query("SELECT t FROM Corso c JOIN c.turni t WHERE c.id= :id")
    List<Turno> turniCorso(long id);
}
