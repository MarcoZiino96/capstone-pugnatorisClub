package it.epicode.pugnatorisClub.request;

import it.epicode.pugnatorisClub.model.Corso;
import it.epicode.pugnatorisClub.model.Turno;
import it.epicode.pugnatorisClub.model.Utente;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PrenotazioneRequest {

    @NotNull(message = ("campo obbligatorio"))
    private Long corso;

    @NotNull(message = ("campo obbligatorio"))
    private Long utente;

    @NotNull(message = ("campo obbligatorio"))
    private Integer turno;
}
