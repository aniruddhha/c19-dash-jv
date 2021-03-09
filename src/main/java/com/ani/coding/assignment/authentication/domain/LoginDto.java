package com.ani.coding.assignment.authentication.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDto {
    private String userName;
    private String password;
}
