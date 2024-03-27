package it.epicode.pugnatorisClub.request;

import it.epicode.pugnatorisClub.enums.Durata;
import it.epicode.pugnatorisClub.model.Corso;
import it.epicode.pugnatorisClub.model.Utente;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;


@Data
public class AbbonamentoRequest {

    @NotNull(message = "categoria obbligatoria")
    private Durata durata;

    @NotNull(message = "categoria obbligatoria")
    private Long corso;

    @NotNull(message = "categoria obbligatoria")
    private Long utente;


}
