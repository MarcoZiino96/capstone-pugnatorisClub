package it.epicode.pugnatorisClub.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordRequest {

    @NotBlank(message = "campo obbligatorio")
    private String oldPassword;

    @NotBlank(message = "campo obbligatorio")
    private String newPassword;
}
