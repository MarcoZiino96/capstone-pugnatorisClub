package it.epicode.pugnatorisClub.model;


import it.epicode.pugnatorisClub.enums.Durata;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Abbonamento {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private int id;

   @Enumerated(EnumType.STRING)
   private Durata durata;

   private LocalDate dataAttivazione;

   private LocalDate dataScadenza;

   private  double costoAbbonamento;

   @ManyToOne
   @JoinColumn(name = "corso_id")
   private Corso corso;

   @ManyToOne
   @JoinColumn(name = "utente_id")
   private Utente utente;

   public void setAbbonamento() {

      switch (durata) {
         case MENSILE:
            dataAttivazione = LocalDate.now();
            dataScadenza = dataAttivazione.plusMonths(1);
            costoAbbonamento = corso.getCostoMensile();
            break;
         case TRIMESTRALE:
            dataAttivazione = LocalDate.now();
            dataScadenza = dataAttivazione.plusMonths(3);
            costoAbbonamento = corso.getCostoMensile() * 3;

            break;
         case SEMESTRALE:
            dataAttivazione = LocalDate.now();
            dataScadenza = dataAttivazione.plusMonths(6);
            costoAbbonamento = corso.getCostoMensile() * 6;
            break;

         case ANNUALE:
            dataAttivazione = LocalDate.now();
            dataScadenza = dataAttivazione.plusMonths(12);
            costoAbbonamento = corso.getCostoMensile() * 12;
            break;

         default:
            throw new IllegalArgumentException("Durata non gestita: " + durata);
      }
   }

}
