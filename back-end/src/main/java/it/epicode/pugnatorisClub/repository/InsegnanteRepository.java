package it.epicode.pugnatorisClub.repository;

import it.epicode.pugnatorisClub.model.Insegnante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InsegnanteRepository extends JpaRepository<Insegnante, Integer>, PagingAndSortingRepository<Insegnante, Integer> {
}
