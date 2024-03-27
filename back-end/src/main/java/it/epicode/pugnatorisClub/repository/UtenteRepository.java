package it.epicode.pugnatorisClub.repository;

import it.epicode.pugnatorisClub.model.Abbonamento;
import it.epicode.pugnatorisClub.model.Prenotazione;
import it.epicode.pugnatorisClub.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByUsername(String username);

    @Query("SELECT p FROM Utente u JOIN u.prenotazioni p  WHERE u.id= :id")
    List<Prenotazione> prenotazioniUtente(long id);

    @Query("SELECT a FROM Utente u JOIN u.abbonamenti a  WHERE u.id= :id")
    List<Abbonamento> abbonamentiUtente(long id);

}
