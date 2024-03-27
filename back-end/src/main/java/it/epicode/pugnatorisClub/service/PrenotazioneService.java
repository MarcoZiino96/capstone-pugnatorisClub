package it.epicode.pugnatorisClub.service;
import it.epicode.pugnatorisClub.enums.ArtiMarziali;
import it.epicode.pugnatorisClub.exception.CustomResponse;
import it.epicode.pugnatorisClub.exception.NotFoundException;
import it.epicode.pugnatorisClub.model.Corso;
import it.epicode.pugnatorisClub.model.Prenotazione;
import it.epicode.pugnatorisClub.model.Turno;
import it.epicode.pugnatorisClub.model.Utente;
import it.epicode.pugnatorisClub.repository.PrenotazioneRepository;
import it.epicode.pugnatorisClub.request.PrenotazioneRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrenotazioneService {

   @Autowired
    PrenotazioneRepository prenotazioneRepository;
   @Autowired
    CorsoService corsoService;

   @Autowired
    UtenteService utenteService;

    @Autowired
    TurnoService turnoService;

   public List<Prenotazione> getAll(){
     return   prenotazioneRepository.findAll();
   }

   public Prenotazione getPrenotazioneById(int id){
       return prenotazioneRepository.findById(id).orElseThrow(()-> new NotFoundException("Prenotazione non trovata"));
   }

   public Prenotazione save(PrenotazioneRequest prenotazioneRequest){

       Prenotazione prenotazione = new Prenotazione();
       Corso corso = corsoService.getCorsoById(prenotazioneRequest.getCorso());
       Utente utente = utenteService.getUtenteById(prenotazioneRequest.getUtente());
       Turno turno = turnoService.getTurnoById(prenotazioneRequest.getTurno());
       List<ArtiMarziali> listCategorieUtente = utente.getPrenotazioni().stream().map(Prenotazione::getCorso).toList().stream().map(Corso::getCategoria).toList();

       if (listCategorieUtente.contains(corso.getCategoria())) throw  new RuntimeException("Questo utente ha gia una prenotazione per questo corso");
       if(corso.getAbbonati().size() > corso.getNumeroMassimoPartecipanti()) throw  new RuntimeException("Il corso ha raggiunto il numero massimo di partecipanti");
       if(!corso.getTurni().contains(turno)) throw  new RuntimeException("Il turno che hai scelto non appartiene a questo corso");

       prenotazione.setCorso(corso);
       prenotazione.setUtente(utente);
       prenotazione.setDataPrenotazione(LocalDate.now());

       prenotazione.setTurno(turno);
       prenotazione.setDataScadenza(Prenotazione.calcolaDataScadenza(prenotazione.getDataPrenotazione(),turno.getGiornoLezione()));

      return prenotazioneRepository.save(prenotazione);
   }
    public Prenotazione update(PrenotazioneRequest prenotazioneRequest,int id){
        Prenotazione prenotazione = getPrenotazioneById(id);
        Corso corso = corsoService.getCorsoById(prenotazioneRequest.getCorso());
        Utente utente = utenteService.getUtenteById(prenotazioneRequest.getUtente());
        Turno turno = turnoService.getTurnoById(prenotazioneRequest.getTurno());
        List<ArtiMarziali> listCategorieUtente = utente.getPrenotazioni().stream().map(Prenotazione::getCorso).toList().stream().map(Corso::getCategoria).toList();

        if (listCategorieUtente.contains(corso.getCategoria())) throw  new RuntimeException("Questo utente ha gia una prenotazione per questo corso");
        if(corso.getAbbonati().size() > corso.getNumeroMassimoPartecipanti()) throw  new RuntimeException("Il corso ha raggiunto il numero massimo di partecipanti");
        if(!corso.getTurni().contains(turno)) throw  new RuntimeException("Il turno che hai scelto non appartiene a questo corso");

        prenotazione.setCorso(corso);
        prenotazione.setUtente(utente);
        prenotazione.setDataPrenotazione(LocalDate.now());
        prenotazione.setTurno(turno);
        prenotazione.setDataScadenza(Prenotazione.calcolaDataScadenza(prenotazione.getDataPrenotazione(),turno.getGiornoLezione()));


        return prenotazioneRepository.save(prenotazione);
    }

    public void delete(int id){
       Prenotazione prenotazione = getPrenotazioneById(id);
       prenotazioneRepository.delete(prenotazione);


    }
}


