package com.myblog8.Payload;

import lombok.Data;

@Data
public class LoginDto {
	
    private String usernameOrEmail;
    private String password;


}
