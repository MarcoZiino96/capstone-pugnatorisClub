package it.epicode.pugnatorisClub.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;


@Data
public class UtenteRequest{
    @NotBlank(message = "nome obbligatorio")
    private String nome;

    @NotBlank(message = "cognome obbligatorio")
    private String cognome;

    @NotBlank(message = "cognome obbligatorio")
    private String username;

    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "email non valida")
    private String email;

    @NotBlank(message = "password request")
    @Pattern(regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>.]).{8,}$",
            message = "Password deve contenere: 1 lettera maiuscola, 1 lettera minuscola, 1 numero, 1 carattere speciale, Min 8 caratteri")
    private String password;

    @NotNull(message = "data di nascita obbligatoria")
    private LocalDate dataNascita;
}
