package it.epicode.pugnatorisClub.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LoginRequest {

    @NotBlank(message = "cognome obbligatorio")
    private String username;

    @NotBlank(message = "password obbligatoria")
    private String password;
}
