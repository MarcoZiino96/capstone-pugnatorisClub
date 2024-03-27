package it.epicode.pugnatorisClub.model;

import it.epicode.pugnatorisClub.enums.GiornoSettimana;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;


@Entity
@Data
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "corso_id")

    private Corso corso;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    private LocalDate dataPrenotazione;

    private  LocalDate dataScadenza;

    @ManyToOne
    @JoinColumn(name = "turno_id")
    private Turno turno;

    public static LocalDate calcolaDataScadenza(LocalDate dataPrenotazione, GiornoSettimana giornoLezione) {
        DayOfWeek giornoPrenotazione = dataPrenotazione.getDayOfWeek();
        DayOfWeek giornoLezioneEnum = convertiInDayOfWeek(giornoLezione);
        int giorniDaAggiungere = giornoLezioneEnum.getValue() - giornoPrenotazione.getValue();
        if (giorniDaAggiungere <= 0) {
            giorniDaAggiungere += 7;
        }
        return dataPrenotazione.plusDays(giorniDaAggiungere);
    }
    private static DayOfWeek convertiInDayOfWeek(GiornoSettimana giornoLezione) {
        switch (giornoLezione) {
            case LUNEDI:
                return DayOfWeek.MONDAY;
            case MARTEDI:
                return DayOfWeek.TUESDAY;
            case MERCOLEDI:
                return DayOfWeek.WEDNESDAY;
            case GIOVEDI:
                return DayOfWeek.THURSDAY;
            case VENERDI:
                return DayOfWeek.FRIDAY;
            case SABATO:
                return DayOfWeek.SATURDAY;
            default:
                throw new IllegalArgumentException("Enum giornoLezione non valido: " + giornoLezione);
        }
    }
}
