package com.ani.coding.assignment.authentication.controller;

import com.ani.coding.assignment.authentication.domain.AppUser;
import com.ani.coding.assignment.authentication.domain.LoginDto;
import com.ani.coding.assignment.authentication.repository.AppUserRepository;
import com.ani.coding.assignment.http.ResMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@RestController(value = "/user")
public class AppAuthController {

    @Autowired
    private AppUserRepository repository;

    @PostMapping(value = "/signin", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResMsg<Optional<AppUser>>> signIn(@RequestBody LoginDto dto) {
        Optional<AppUser> optional = repository.signIn(dto.getUserName(),dto.getPassword());
        return ResponseEntity.ok(
                ResMsg.<Optional<AppUser>>builder()
                        .payload(optional)
                        .msg("user available in db")
                        .sts("success")
                        .build()
        );
    }

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResMsg<AppUser>> signUp(@RequestBody AppUser user) {
        AppUser userSaved = repository.save(user);
        return ResponseEntity.ok(
                ResMsg.<AppUser>builder()
                        .msg("user saved successfully")
                        .sts("success")
                        .payload(userSaved)
                        .build()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(""+e.getMessage());
    }
}
