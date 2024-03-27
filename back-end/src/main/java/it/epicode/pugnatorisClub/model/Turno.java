package it.epicode.pugnatorisClub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.pugnatorisClub.enums.GiornoSettimana;
import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    private GiornoSettimana giornoLezione;

    private LocalTime inizioLezione;

    private LocalTime fineLezione;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    @JsonIgnore
    private Corso corso;


    @OneToMany(mappedBy = "turno", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Prenotazione> prenotazioni;


}
