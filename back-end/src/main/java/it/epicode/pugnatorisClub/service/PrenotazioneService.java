package it.epicode.pugnatorisClub.service;
import it.epicode.pugnatorisClub.enums.ArtiMarziali;
import it.epicode.pugnatorisClub.exception.CustomResponse;
import it.epicode.pugnatorisClub.exception.NotFoundException;
import it.epicode.pugnatorisClub.model.*;
import it.epicode.pugnatorisClub.repository.PrenotazioneRepository;
import it.epicode.pugnatorisClub.request.PrenotazioneRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrenotazioneService {

   @Autowired
    private PrenotazioneRepository prenotazioneRepository;
   @Autowired
    private CorsoService corsoService;

   @Autowired
    private UtenteService utenteService;

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private JavaMailSender javaMailSender;

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
       List<ArtiMarziali> listCategorieAbbonamenti = utente.getAbbonamenti().stream().map(Abbonamento::getCorso).toList().stream().map(Corso::getCategoria).toList();


       if (listCategorieUtente.contains(corso.getCategoria())) throw  new RuntimeException("Questo utente ha gia una prenotazione per questo corso");
       if (listCategorieAbbonamenti.contains(corso.getCategoria())) throw  new RuntimeException("Questo utente ha gia un abbonamento per questo corso");
       if(corso.getAbbonati().size() > corso.getNumeroMassimoPartecipanti()) throw  new RuntimeException("Il corso ha raggiunto il numero massimo di partecipanti");
       if(!corso.getTurni().contains(turno)) throw  new RuntimeException("Il turno che hai scelto non appartiene a questo corso");

       prenotazione.setCorso(corso);
       prenotazione.setUtente(utente);
       prenotazione.setDataPrenotazione(LocalDate.now());
       prenotazione.setTurno(turno);
       prenotazione.setDataScadenza(Prenotazione.calcolaDataScadenza(prenotazione.getDataPrenotazione(),turno.getGiornoLezione()));
       sendEmailPrenotation(utente.getEmail(), utente, corso, prenotazione);

      return prenotazioneRepository.save(prenotazione);
   }
    public Prenotazione update(PrenotazioneRequest prenotazioneRequest,int id){
        Prenotazione prenotazione = getPrenotazioneById(id);
        Corso corso = corsoService.getCorsoById(prenotazioneRequest.getCorso());
        Utente utente = utenteService.getUtenteById(prenotazioneRequest.getUtente());
        Turno turno = turnoService.getTurnoById(prenotazioneRequest.getTurno());
        List<ArtiMarziali> listCategorieUtente = utente.getPrenotazioni().stream().map(Prenotazione::getCorso).toList().stream().map(Corso::getCategoria).toList();
        List<ArtiMarziali> listCategorieAbbonamenti = utente.getAbbonamenti().stream().map(Abbonamento::getCorso).toList().stream().map(Corso::getCategoria).toList();

        if (listCategorieUtente.contains(corso.getCategoria())) throw  new RuntimeException("Questo utente ha gia una prenotazione per questo corso");
        if (listCategorieAbbonamenti.contains(corso.getCategoria())) throw  new RuntimeException("Questo utente ha gia un abbonamento per questo corso");
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

    public void sendEmailPrenotation(String email, Utente utente, Corso corso, Prenotazione prenotazione){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Complimenti prenotazione per il corso di "+corso.getCategoria() +" avvenuta con successo");
        message.setText(utente.getNome()+" hai prenotato un lezione gratuita di prova presso la palestra Pugnatoris Club");
        message.setText("Ecco i tuoi dettagli della prenotazione"+"\n"+
                         "CORSO: "+ corso.getCategoria()+"\n"+
                         "DATA LEZIONE: "+ prenotazione.getDataPrenotazione()+"\n"+
                         "START: "+ prenotazione.getTurno().getInizioLezione()+"\n"+
                         "FINISH: "+ prenotazione.getTurno().getFineLezione());
        javaMailSender.send(message);
    }
}


