package it.epicode.pugnatorisClub.request;

import it.epicode.pugnatorisClub.enums.ArtiMarziali;
import it.epicode.pugnatorisClub.enums.Durata;
import it.epicode.pugnatorisClub.model.Insegnante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CorsoRequest {

    @NotNull(message = "categoria obbligatoria")
    private ArtiMarziali categoria;

    @NotBlank(message = "descrizione obbligatoria")
    private String descrizione;

    @NotNull(message = "numero massimo partecipanti obbligatorio")
    private int numeroMassimoPartecipanti;

    @NotNull(message = "durata del corso obbligatoria")
    private Durata durata;

    @NotNull(message = "durata del corso obbligatoria")
    private Double costoMensile;

}
