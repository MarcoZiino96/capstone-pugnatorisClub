package it.epicode.pugnatorisClub.request;

import it.epicode.pugnatorisClub.enums.ArtiMarziali;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
public class InsegnanteRequest {

    @NotBlank(message = "nome obbligatorio")
    private String nome;

    @NotBlank(message = "cognome obbligatorio")
    private String cognome;

    @NotNull(message = "data di nascita obbligatorio")
    private LocalDate dataNascita;

    @NotNull(message = "campo obbligatorio")
    private List<ArtiMarziali> discipline;

}
