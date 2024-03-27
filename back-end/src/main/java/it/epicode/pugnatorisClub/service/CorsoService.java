package it.epicode.pugnatorisClub.service;
import it.epicode.pugnatorisClub.exception.NotFoundException;
import it.epicode.pugnatorisClub.model.Corso;
import it.epicode.pugnatorisClub.model.Insegnante;
import it.epicode.pugnatorisClub.model.Turno;
import it.epicode.pugnatorisClub.repository.CorsoRepository;
import it.epicode.pugnatorisClub.request.CorsoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class CorsoService{

    @Autowired
    CorsoRepository corsoRepository;

    @Autowired
    InsegnanteService insegnanteService;


    public List<Corso> getAll(){
        return corsoRepository.findAll();
    }

    public Corso getCorsoById(long id){
        return corsoRepository.findById(id).orElseThrow(()->new NotFoundException("Il corso non è presente"));
    }

    public Corso save(CorsoRequest corsoRequest){

        Corso corso = new Corso();

        Optional<Corso> existingCorso = corsoRepository.findByCategoria(corsoRequest.getCategoria());
        if (existingCorso.isPresent()) {
            throw new RuntimeException("Gia esiste un corso per la categoria" + corsoRequest.getCategoria());
        }
        else {
            corso.setDurata(corsoRequest.getDurata());
            corso.setCategoria(corsoRequest.getCategoria());
            corso.setDescrizione(corsoRequest.getDescrizione());
            corso.setNumeroMassimoPartecipanti(corsoRequest.getNumeroMassimoPartecipanti());
            corso.setCostoMensile(corsoRequest.getCostoMensile());

            return corsoRepository.save(corso);
        }
    }

    public Corso update (long id, CorsoRequest corsoRequest){
        Corso corso = getCorsoById(id);
            corso.setDurata(corsoRequest.getDurata());
            corso.setCategoria(corsoRequest.getCategoria());
            corso.setDescrizione(corsoRequest.getDescrizione());
            corso.setNumeroMassimoPartecipanti(corsoRequest.getNumeroMassimoPartecipanti());
            corso.setCostoMensile(corsoRequest.getCostoMensile());
            return corsoRepository.save(corso);
    }

    public Corso updateInsegnante(int idInsegnate, long idCorso){
        Insegnante insegnante = insegnanteService.getInsegnanteById(idInsegnate);
        Corso corso = getCorsoById(idCorso);
         if(corso.getMaestro() != null){
             throw  new RuntimeException("A questo corso e già stato assegnato un maestro");
         } if(insegnante.getDiscipline().contains(corso.getCategoria())){
            corso.setMaestro(insegnante);
        }else{
            throw  new RuntimeException("La categoria del corso non rientra nelle discipline del maestro"+ insegnante.getNome()+" "+ insegnante.getCognome());
        }
        return corsoRepository.save(corso);
    }

    public void delete(long id){
        Corso corso = getCorsoById(id);
        corsoRepository.delete(corso);
    }

    public List<Turno> turniCorsoById(long id){
        return corsoRepository.turniCorso(id);
    }
}
