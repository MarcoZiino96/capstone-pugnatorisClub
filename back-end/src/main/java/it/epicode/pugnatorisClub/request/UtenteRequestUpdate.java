package it.epicode.pugnatorisClub.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;


@Data
public class UtenteRequestUpdate {
    @NotBlank(message = "nome obbligatorio")
    private String nome;

    @NotBlank(message = "cognome obbligatorio")
    private String cognome;

    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "email non valida")
    private String email;

    @NotNull(message = "data di nascita obbligatoria")
    private LocalDate dataNascita;
}
