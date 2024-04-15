package it.epicode.pugnatorisClub.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Objects;

@Data
public class RoleRequest {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleRequest that = (RoleRequest) o;
        return Objects.equals(ruolo, that.ruolo);
    }

    @NotBlank(message = "campo obbligatorio")
    private String ruolo;
}
