package it.epicode.pugnatorisClub.controller;


import com.cloudinary.Cloudinary;
import com.cloudinary.provisioning.Account;
import it.epicode.pugnatorisClub.enums.Ruolo;
import it.epicode.pugnatorisClub.exception.BadRequestException;
import it.epicode.pugnatorisClub.exception.CustomResponse;
import it.epicode.pugnatorisClub.exception.LoginFaultException;
import it.epicode.pugnatorisClub.model.ILoginResponse;
import it.epicode.pugnatorisClub.model.Utente;
import it.epicode.pugnatorisClub.request.PasswordRequest;
import it.epicode.pugnatorisClub.request.RoleRequest;
import it.epicode.pugnatorisClub.request.UtenteRequest;
import it.epicode.pugnatorisClub.request.UtenteRequestUpdate;
import it.epicode.pugnatorisClub.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/utente")
public class UtenteController {

    @Autowired
    UtenteService utenteService;

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    PasswordEncoder encoder;


    @GetMapping("")
    public ResponseEntity<CustomResponse> getAll(){
            return CustomResponse.success(HttpStatus.OK.toString(), utenteService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getUtenteById(@PathVariable long id){
        return CustomResponse.success(HttpStatus.OK.toString(), utenteService.getUtenteById(id), HttpStatus.OK);
    }

    @GetMapping("prenotazioni/{id}")
    public ResponseEntity<CustomResponse> getPrenotazioniUtente(@PathVariable long id){
        return CustomResponse.success((HttpStatus.OK.toString()), utenteService.prenotazioniUser(id), HttpStatus.OK);
    }

    @GetMapping("abbonamenti/{id}")
    public ResponseEntity<CustomResponse> getAbbonamnetiUtente(@PathVariable long id){
        return CustomResponse.success((HttpStatus.OK.toString()), utenteService.abbonamnetiUser(id), HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<CustomResponse> updateUtente(@PathVariable int id, @RequestBody @Validated UtenteRequestUpdate utenteRequestUpdate, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return CustomResponse.success(HttpStatus.OK.toString(), utenteService.update(id, utenteRequestUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteUtente(@PathVariable long id){
      utenteService.delete(id);
        return CustomResponse.emptyResponse("L'utente con id = " + id + " è stato cancellato", HttpStatus.OK);

    }

    @PatchMapping("/upload/{id}")
    public ResponseEntity<CustomResponse> uploadAfotoProfilo(@PathVariable int id,@RequestParam("upload") MultipartFile file) throws IOException {
            Utente utente = utenteService.uploadFotoProfilo(id, (String)
            cloudinary.uploader().upload(file.getBytes(), new HashMap()).get("url"));
            return CustomResponse.success(HttpStatus.OK.toString(), utente, HttpStatus.OK);
    }

    @PatchMapping("/edit/password/{id}")
    public ResponseEntity<CustomResponse> uploadPassword(@PathVariable long id, @RequestBody @Validated PasswordRequest passwordRequest, BindingResult bindingResult){

        if (bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());

        Utente utente = utenteService.getUtenteById(id);

        if (!encoder.matches(passwordRequest.getOldPassword(), utente.getPassword())) throw  new RuntimeException("Password sbagliata");
        if (encoder.matches(passwordRequest.getNewPassword(), utente.getPassword())) throw  new RuntimeException("Password vecchia uguale a quella nuova");

        return CustomResponse.success(HttpStatus.OK.toString(),utenteService.updatePassword(id, passwordRequest.getNewPassword()), HttpStatus.OK);
    }


    @PatchMapping("/edit/role/{id}")
    public ResponseEntity<CustomResponse> uploadRole(@PathVariable long id, @RequestBody @Validated RoleRequest roleRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());

        Utente utente = utenteService.getUtenteById(id);

        if(utente.getRuolo().toString().equals(roleRequest.getRuolo())) throw  new RuntimeException("Questo utente ha già questo ruolo.");
        return CustomResponse.success(HttpStatus.OK.toString(),utenteService.updateRole(id, roleRequest.getRuolo()), HttpStatus.OK);
    }
}
