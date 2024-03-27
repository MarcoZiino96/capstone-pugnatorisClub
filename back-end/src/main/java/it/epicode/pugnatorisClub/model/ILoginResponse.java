package it.epicode.pugnatorisClub.model;

import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ILoginResponse{

    private LocalDateTime date;

    private String accessToken;

    private Utente user;

    public ILoginResponse(String accessToken, Utente user) {
        this.accessToken = accessToken;
        this.user = user;
        this.date= LocalDateTime.now();
    }
}
