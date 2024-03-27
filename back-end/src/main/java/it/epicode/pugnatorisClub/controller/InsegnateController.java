package it.epicode.pugnatorisClub.controller;


import com.cloudinary.Cloudinary;
import it.epicode.pugnatorisClub.enums.ArtiMarziali;
import it.epicode.pugnatorisClub.exception.BadRequestException;
import it.epicode.pugnatorisClub.exception.CustomResponse;
import it.epicode.pugnatorisClub.model.Insegnante;
import it.epicode.pugnatorisClub.model.Utente;
import it.epicode.pugnatorisClub.request.InsegnanteRequest;
import it.epicode.pugnatorisClub.service.InsegnanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/insegnante")
public class InsegnateController{

    @Autowired
    InsegnanteService insegnanteService;

    @Autowired
    Cloudinary cloudinary;


    @GetMapping("")
    public ResponseEntity<CustomResponse> getAllInsegnante(Pageable pageable){
        return CustomResponse.success(HttpStatus.OK.toString(), insegnanteService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getInsegnanteByid(@PathVariable int id){
        return CustomResponse.success(HttpStatus.OK.toString(),insegnanteService.getInsegnanteById(id), HttpStatus.OK );
    }


    @PostMapping("/create")
    public ResponseEntity<CustomResponse> saveInsegnante(@RequestBody @Validated InsegnanteRequest insegnanteRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());

        return CustomResponse.success(HttpStatus.OK.toString(),insegnanteService.save(insegnanteRequest), HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<CustomResponse> updateInsegnante( @PathVariable int id,@RequestBody @Validated InsegnanteRequest insegnanteRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());

        return CustomResponse.success(HttpStatus.OK.toString(),insegnanteService.update(id, insegnanteRequest), HttpStatus.OK);
    }

    @PatchMapping("upload/foto/{id}")
    public ResponseEntity<CustomResponse> uploadfotoProfilo(@PathVariable int id,@RequestParam("upload") MultipartFile file) throws IOException {
            Insegnante insegnante = insegnanteService.uploadFotoProfilo(id, (String)
                    cloudinary.uploader().upload(file.getBytes(), new HashMap()).get("url"));
            return CustomResponse.success(HttpStatus.OK.toString(), insegnante, HttpStatus.OK);
    }


    @PatchMapping("delete/disciplina/{id}")
    public ResponseEntity<CustomResponse> deleteDisciplina(@PathVariable int id, @RequestParam("deleteDisciplina") ArtiMarziali artiMarziali){
        insegnanteService.deleteDisciplina(id,artiMarziali);
        return CustomResponse.emptyResponse("La disciplina con nome = "+artiMarziali+" è stata cancellata", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteInsegnante(@PathVariable int id){
        insegnanteService.delete(id);
        return CustomResponse.emptyResponse("L' insegnante con id = "+id+" è stato cancellato", HttpStatus.OK);
    }
}

