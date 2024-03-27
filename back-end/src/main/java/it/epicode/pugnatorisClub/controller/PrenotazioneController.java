package it.epicode.pugnatorisClub.controller;



import it.epicode.pugnatorisClub.exception.BadRequestException;
import it.epicode.pugnatorisClub.exception.CustomResponse;
import it.epicode.pugnatorisClub.model.Prenotazione;
import it.epicode.pugnatorisClub.request.PrenotazioneRequest;
import it.epicode.pugnatorisClub.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prenotazione")
public class PrenotazioneController{

    @Autowired
    PrenotazioneService prenotazioneService;

    @Autowired
    CorsoController corsoController;

    @Autowired
    UtenteController utenteController;


    @GetMapping("")
    public ResponseEntity<CustomResponse> getAllPrenotazioni(){
        return  CustomResponse.success(HttpStatus.OK.toString(), prenotazioneService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getPrenotazioneById(@PathVariable int id){
        return  CustomResponse.success(HttpStatus.OK.toString(), prenotazioneService.getPrenotazioneById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public  ResponseEntity<CustomResponse> savePrenotazione(@RequestBody PrenotazioneRequest prenotazioneRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());

        return CustomResponse.success(HttpStatus.OK.toString(),prenotazioneService.save(prenotazioneRequest), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public  ResponseEntity<CustomResponse> updatePrenotazione(@PathVariable int id,@RequestBody PrenotazioneRequest prenotazioneRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());

        return CustomResponse.success(HttpStatus.OK.toString(),prenotazioneService.update( prenotazioneRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")

    public ResponseEntity<CustomResponse> deletePrenotazione(@PathVariable int id){
        Prenotazione prenotazione = prenotazioneService.getPrenotazioneById(id);
        prenotazioneService.delete(id);
        return CustomResponse.emptyResponse("La prenotazione con id = "+id+" è stato cancellato", HttpStatus.OK);
    }
}
