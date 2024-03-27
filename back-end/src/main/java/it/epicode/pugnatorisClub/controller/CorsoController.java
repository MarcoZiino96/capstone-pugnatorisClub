package it.epicode.pugnatorisClub.controller;


import it.epicode.pugnatorisClub.exception.BadRequestException;
import it.epicode.pugnatorisClub.exception.CustomResponse;
import it.epicode.pugnatorisClub.request.CorsoRequest;
import it.epicode.pugnatorisClub.service.CorsoService;
import it.epicode.pugnatorisClub.service.InsegnanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/corso")

public class CorsoController {

    @Autowired
    CorsoService corsoService;
    @Autowired
    InsegnanteService insegnanteService;

    @GetMapping("")
    public ResponseEntity<CustomResponse> getAllCorsi(){
        return CustomResponse.success(HttpStatus.OK.toString(), corsoService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getCorsoByid(@PathVariable long id){
        return CustomResponse.success(HttpStatus.OK.toString(),corsoService.getCorsoById(id), HttpStatus.OK );
    }

    @GetMapping("/turni/{id}")
    public ResponseEntity<CustomResponse> getEvent(@PathVariable long id) {
        return CustomResponse.success(HttpStatus.OK.toString(), corsoService.turniCorsoById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CustomResponse> saveCorso(@RequestBody @Validated CorsoRequest corsoRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());

        return CustomResponse.success(HttpStatus.OK.toString(),corsoService.save(corsoRequest), HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<CustomResponse> updateCorso( @PathVariable long id,@RequestBody @Validated CorsoRequest corsoRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());

        return CustomResponse.success(HttpStatus.OK.toString(),corsoService.update(id, corsoRequest), HttpStatus.OK);
    }

    @PatchMapping("/updateMaestro/{id}")
    public ResponseEntity<CustomResponse> updateMaestro(@PathVariable long id, @RequestParam("idMaestro") int idMaestro){
        return CustomResponse.success(HttpStatus.OK.toString(),corsoService.updateInsegnante(idMaestro, id), HttpStatus.OK );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteInsegnante(@PathVariable long id){
        corsoService.delete(id);
        return CustomResponse.emptyResponse("Il turno con id = "+id+" è stato cancellato", HttpStatus.OK);
    }
}
