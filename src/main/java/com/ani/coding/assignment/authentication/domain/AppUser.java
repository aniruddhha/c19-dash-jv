package com.ani.coding.assignment.authentication.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Validated
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "app_user_id")
    private Long userId;

    @NotNull
    @NotBlank(message = "User Name is mandatory")
    private String userName;
    @NotNull
    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotNull
    @NotBlank(message = "Mobile is mandatory")
    private String mobile;

    @Email(message = "Need valid email")
    @NotNull
    @NotBlank(message = "Email is mandatory")
    private String email;

    private Integer gender;
    private String state;
    private String city;
    private String address;

    private LocalDate signUpDate;
}
