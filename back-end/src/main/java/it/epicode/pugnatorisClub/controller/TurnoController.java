package it.epicode.pugnatorisClub.controller;


import it.epicode.pugnatorisClub.enums.GiornoSettimana;
import it.epicode.pugnatorisClub.exception.BadRequestException;
import it.epicode.pugnatorisClub.exception.CustomResponse;
import it.epicode.pugnatorisClub.request.TurnoRequest;
import it.epicode.pugnatorisClub.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/turno")
public class TurnoController {

    @Autowired
    TurnoService turnoService;

    @GetMapping("")
    public ResponseEntity<CustomResponse> getAllTurni(){
        return CustomResponse.success(HttpStatus.OK.toString(), turnoService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getTurnoByid(@PathVariable int id){
        return CustomResponse.success(HttpStatus.OK.toString(),turnoService.getTurnoById(id), HttpStatus.OK );
    }


    @PostMapping("/create")
    public ResponseEntity<CustomResponse> saveTurno(@RequestBody @Validated TurnoRequest turnoRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());

        return CustomResponse.success(HttpStatus.OK.toString(),turnoService.save(turnoRequest), HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<CustomResponse> updateTurno( @PathVariable int id,@RequestBody @Validated TurnoRequest turnoRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());

        return CustomResponse.success(HttpStatus.OK.toString(),turnoService.update(id,  turnoRequest), HttpStatus.OK);
    }

    @PatchMapping("/updateTurno/{id}")
    public ResponseEntity<CustomResponse> updateMaestro(@PathVariable int id, @RequestParam("idCorso") long idCorso){
        return CustomResponse.success(HttpStatus.OK.toString(),turnoService.updateTurno(id, idCorso), HttpStatus.OK );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteInsegnante(@PathVariable int id){
        turnoService.delete(id);
        return CustomResponse.emptyResponse("Il turno con id = "+id+" è stato cancellato", HttpStatus.OK);
    }
}
