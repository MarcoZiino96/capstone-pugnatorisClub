package it.epicode.pugnatorisClub.service;

import it.epicode.pugnatorisClub.enums.ArtiMarziali;
import it.epicode.pugnatorisClub.exception.NotFoundException;
import it.epicode.pugnatorisClub.model.Abbonamento;
import it.epicode.pugnatorisClub.model.Corso;
import it.epicode.pugnatorisClub.model.Prenotazione;
import it.epicode.pugnatorisClub.model.Utente;
import it.epicode.pugnatorisClub.repository.AbbonamentoRepository;
import it.epicode.pugnatorisClub.request.AbbonamentoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@Service
public class AbbonamentoService {

    @Autowired
    AbbonamentoRepository abbonamentoRepository;

    @Autowired
    CorsoService corsoService;

    @Autowired
    UtenteService utenteService;

    @Autowired
    PrenotazioneService prenotazioneService;

    public List<Abbonamento> getAll(){
        return abbonamentoRepository.findAll();
    }

    public Abbonamento getAbbonamentoById(int id){
        return abbonamentoRepository.findById(id).orElseThrow(()-> new NotFoundException("L' abbonamento non è stato trovato"));
    }

    public Abbonamento save(@RequestBody AbbonamentoRequest abbonamentoRequest){
        Abbonamento abbonamento = new Abbonamento();
        Corso corso = corsoService.getCorsoById(abbonamentoRequest.getCorso());
        Utente utente = utenteService.getUtenteById(abbonamentoRequest.getUtente());
        List<ArtiMarziali> listCategorieUtente = utente.getAbbonamenti().stream().map(Abbonamento::getCorso).toList().stream().map(Corso::getCategoria).toList();
        if (listCategorieUtente.contains(corso.getCategoria())) throw  new RuntimeException("Questo utente ha gia una abbonamento per questo corso");
        if(corso.getAbbonati().size() > corso.getNumeroMassimoPartecipanti()) throw  new RuntimeException("Il corso ha raggiunto il numero massimo di partecipanti");

        abbonamento.setDurata(abbonamentoRequest.getDurata());
        abbonamento.setCorso(corso);
        abbonamento.setUtente(utente);
        abbonamento.setAbbonamento();
        return abbonamentoRepository.save(abbonamento);
    }

    public void delete(int id){
        Abbonamento abbonamento = getAbbonamentoById(id);
        abbonamentoRepository.delete(abbonamento);
    }

    public void aggiornaDataBase(){

        List<Prenotazione> prenotazioni = prenotazioneService.getAll();
        List<Abbonamento> abbonamenti = getAll();
        LocalDate dataGiornaliera = LocalDate.now();

        for(Abbonamento abbonamento : abbonamenti){
            if (abbonamento.getDataScadenza().isBefore(dataGiornaliera)){
                delete(abbonamento.getId());
            }
        }

        for(Prenotazione prenotazione : prenotazioni){
            if (prenotazione.getDataScadenza().isBefore(dataGiornaliera)){
                prenotazioneService.delete(prenotazione.getId());
            }
        }
    }

}
