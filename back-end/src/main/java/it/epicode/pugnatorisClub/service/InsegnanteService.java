package it.epicode.pugnatorisClub.service;

import it.epicode.pugnatorisClub.enums.ArtiMarziali;
import it.epicode.pugnatorisClub.exception.NotFoundException;
import it.epicode.pugnatorisClub.model.Insegnante;
import it.epicode.pugnatorisClub.repository.InsegnanteRepository;
import it.epicode.pugnatorisClub.request.InsegnanteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InsegnanteService {

    @Autowired
    InsegnanteRepository insegnanteRepository;


    public Page<Insegnante> getAll(Pageable pageable){
        return insegnanteRepository.findAll(pageable);
    }

    public Insegnante getInsegnanteById(int id){
        return insegnanteRepository.findById(id).orElseThrow(()->new NotFoundException("Insegnante non trovato"));
    }

    public Insegnante save(InsegnanteRequest insegnanteRequest){
        Insegnante insegnante = new Insegnante();

        insegnante.setNome(insegnanteRequest.getNome());
        insegnante.setCognome(insegnanteRequest.getCognome());
        insegnante.setDataNascita(insegnanteRequest.getDataNascita());
        insegnante.setDiscipline(insegnanteRequest.getDiscipline());

        return  insegnanteRepository.save(insegnante);
    }

    public Insegnante update(int id, InsegnanteRequest insegnanteRequest){
        Insegnante insegnante = getInsegnanteById(id);
        insegnante.setNome(insegnanteRequest.getNome());
        insegnante.setCognome(insegnanteRequest.getCognome());
        insegnante.setDataNascita(insegnanteRequest.getDataNascita());
        List<ArtiMarziali> discipline = insegnanteRequest.getDiscipline();

        for (ArtiMarziali disc : discipline)
            if (insegnante.getDiscipline().contains(disc))
                throw new RuntimeException(" Questo disciplina è già inserita nelle discipline dell'insegnante");
           else insegnante.addDisciplina(disc);
        return  insegnanteRepository.save(insegnante);
    }

    public void delete(int id){
        Insegnante insegnante = getInsegnanteById(id);
        insegnanteRepository.delete(insegnante);
    }

    public Insegnante uploadFotoProfilo(int id, String url){
        Insegnante insegnante = getInsegnanteById(id);
        insegnante.setFotoProfilo(url);

        return insegnanteRepository.save(insegnante);

    }


    public void deleteDisciplina(int id, ArtiMarziali disciplina){
        Insegnante insegnante = getInsegnanteById(id);

        if(insegnante.getDiscipline().isEmpty() || !insegnante.getDiscipline().contains(disciplina))
            throw new RuntimeException("La lista è vuota o la disciplina non è presente in questo elenco");
        insegnante.removeDisciplina(disciplina);
         insegnanteRepository.save(insegnante);
    }

}

