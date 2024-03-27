package it.epicode.pugnatorisClub.repository;

import it.epicode.pugnatorisClub.enums.GiornoSettimana;
import it.epicode.pugnatorisClub.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer> {
    @Query("SELECT t FROM Turno t WHERE t.giornoLezione = :giornoLezione " +
            "AND t.inizioLezione = :inizioLezione AND t.fineLezione = :fineLezione")
    Optional<Turno> findByTurnoConflict( GiornoSettimana giornoLezione, LocalTime inizioLezione, LocalTime fineLezione);


}


