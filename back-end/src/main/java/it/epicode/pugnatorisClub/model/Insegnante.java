package it.epicode.pugnatorisClub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.pugnatorisClub.enums.ArtiMarziali;
import it.epicode.pugnatorisClub.enums.GiornoSettimana;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Insegnante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;

    private String cognome;

    private LocalDate dataNascita;

    @Enumerated(EnumType.STRING)
    private List<ArtiMarziali> discipline = new ArrayList<>();

    private String fotoProfilo;

    @JsonIgnore
    @OneToMany(mappedBy = "maestro", cascade = CascadeType.REMOVE)
    private List<Corso> corsi;


    public void addDisciplina(ArtiMarziali disciplina){
            this.discipline.add(disciplina);
    }

    public  void removeDisciplina(ArtiMarziali disciplina){
        discipline.remove(disciplina);
    }
}
