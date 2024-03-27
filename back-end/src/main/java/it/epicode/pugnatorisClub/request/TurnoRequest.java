package it.epicode.pugnatorisClub.request;

import it.epicode.pugnatorisClub.enums.GiornoSettimana;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class TurnoRequest {

    @NotNull(message = "campo obbligatorio")
   private GiornoSettimana giorniLezione;

    @NotNull(message = "campo obbligatorio")
    private LocalTime inizioLezione;

    @NotNull(message = "campo obbligatorio")
    private LocalTime fineLezione;
}
