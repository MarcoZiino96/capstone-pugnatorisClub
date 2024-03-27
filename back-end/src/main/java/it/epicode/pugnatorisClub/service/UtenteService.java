package it.epicode.pugnatorisClub.service;


import it.epicode.pugnatorisClub.enums.Ruolo;
import it.epicode.pugnatorisClub.exception.NotFoundException;
import it.epicode.pugnatorisClub.model.Abbonamento;
import it.epicode.pugnatorisClub.model.Prenotazione;
import it.epicode.pugnatorisClub.model.Utente;
import it.epicode.pugnatorisClub.repository.UtenteRepository;
import it.epicode.pugnatorisClub.request.PasswordRequest;
import it.epicode.pugnatorisClub.request.UtenteRequest;
import it.epicode.pugnatorisClub.request.UtenteRequestUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public List<Utente> getAll(){
        return utenteRepository.findAll();
    }

    public Utente getUtenteById(long id){
        return utenteRepository.findById(id).orElseThrow(()->new NotFoundException("Utente non trovato"));
    }

    public Utente getUtenteByUsername(String user){
         return utenteRepository.findByUsername(user).orElseThrow(()->new NotFoundException("Utente non trovato"));
    }

    public Utente save(UtenteRequest utenteRequest){
        Utente utente = new Utente();

        utente.setNome(utenteRequest.getNome());
        utente.setCognome(utenteRequest.getCognome());
        utente.setUsername(utenteRequest.getUsername());
        utente.setDataNascita(utenteRequest.getDataNascita());
        utente.setPassword(encoder.encode(utenteRequest.getPassword()));
        utente.setRuolo(Ruolo.USER);
        utente.setEmail(utenteRequest.getEmail());
        sendEmail(utenteRequest.getEmail());
        return  utenteRepository.save(utente);
    }

    public Utente update(long id, UtenteRequestUpdate utenteRequestUpdate){
        Utente utente = getUtenteById(id);
        utente.setNome(utenteRequestUpdate.getNome());
        utente.setCognome(utenteRequestUpdate.getCognome());
        utente.setDataNascita(utenteRequestUpdate.getDataNascita());
        utente.setEmail(utenteRequestUpdate.getEmail());
        return utenteRepository.save(utente);
    }

    public void delete(long id){
        Utente utente = getUtenteById(id);
        utenteRepository.delete(utente);
    }

    public Utente updateRole(String username, String ruolo) throws NotFoundException {
        Utente u = getUtenteByUsername(username);
        u.setRuolo(Ruolo.valueOf(ruolo));
        return utenteRepository.save(u);

    }

    public Utente uploadFotoProfilo(long id, String url){
        Utente u = getUtenteById(id);
        u.setFotoProfilo(url);

        return utenteRepository.save(u);

    }

    public void sendEmail(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione alla palestra PugnatorisClub");
        message.setText("Benvenuto sulla nostra applicazione, registrazione avvenuta con successo. Dai un'occhiata ai nostri corsi e prenota una lezione di prova gratuita.");
        javaMailSender.send(message);
    }

    public Utente updatePassword(long id, String password) throws NotFoundException {
        Utente u = getUtenteById(id);
        u.setPassword(encoder.encode(password));
       return  utenteRepository.save(u);
    }

    public List<Prenotazione> prenotazioniUser(long id){
        return utenteRepository.prenotazioniUtente(id);
    }


    public List<Abbonamento> abbonamnetiUser(long id){
        return utenteRepository.abbonamentiUtente(id);
    }

}
