package it.epicode.pugnatorisClub.controller;


import it.epicode.pugnatorisClub.exception.BadRequestException;
import it.epicode.pugnatorisClub.exception.CustomResponse;
import it.epicode.pugnatorisClub.model.Abbonamento;
import it.epicode.pugnatorisClub.request.AbbonamentoRequest;
import it.epicode.pugnatorisClub.service.AbbonamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/abbonamento")
public class AbbonamentoController {

    @Autowired
    AbbonamentoService abbonamentoService;

    @Autowired
    CorsoController corsoController;

    @Autowired
    UtenteController utenteController;


    @GetMapping("")
    public ResponseEntity<CustomResponse> getAllAbbonamento(){
        return  CustomResponse.success(HttpStatus.OK.toString(), abbonamentoService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getAbbonamentoById(@PathVariable int id){
        return  CustomResponse.success(HttpStatus.OK.toString(), abbonamentoService.getAbbonamentoById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public  ResponseEntity<CustomResponse> saveAbbonamento(@RequestBody AbbonamentoRequest abbonamentoRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());

        return CustomResponse.success(HttpStatus.OK.toString(),abbonamentoService.save(abbonamentoRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deletePrenotazione(@PathVariable int id){
        Abbonamento abbonamento = abbonamentoService.getAbbonamentoById(id);
        abbonamentoService.delete(id);
        return CustomResponse.emptyResponse("Il tuo abbonamento con id = " +id+ " è stato cancellato", HttpStatus.OK);
    }

    @DeleteMapping("/delete/dettagli")
    public ResponseEntity<CustomResponse> aggiornaDatabase(){
        abbonamentoService.aggiornaDataBase();
        return CustomResponse.emptyResponse("Il database è stato aggiornato", HttpStatus.OK);
    }
}


