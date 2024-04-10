package it.epicode.pugnatorisClub.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleRequest {

    @NotBlank(message = "campo obbligatorio")
    private String ruolo;
}
