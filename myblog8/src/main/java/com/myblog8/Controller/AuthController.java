package com.myblog8.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.myblog8.Entity.User;
import com.myblog8.Payload.JwtResponse;
import com.myblog8.Payload.LoginDto;
import com.myblog8.Payload.SignUpDto;
import com.myblog8.Repository.RoleRepo;
import com.myblog8.Repository.UserRepo;
import com.myblog8.Security.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;
	
	
	@Autowired
    private UserDetailsService userDetailsService;

    
	@Autowired
    private AuthenticationManager manager;
   
   

	@Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	
	@Autowired
    private AuthenticationManager authenticationManager;

	// http://localhost:8182/api/auth/signin
	@PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginDto loginDto){
		this.doAuthenticate(loginDto.getUsernameOrEmail(), loginDto.getPassword());
		
		    UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getUsernameOrEmail());
	        String token = this.helper.generateToken(userDetails);
	        
	        JwtResponse response = JwtResponse.builder()
	                .jwtToken(token)
	                .username(userDetails.getUsername()).build();
	        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
	private void doAuthenticate(String usernameOrEmail, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usernameOrEmail, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid UsernameOrEmail or Password  !!");
        }

    }
	
	 @ExceptionHandler(BadCredentialsException.class)
	    public String exceptionHandler() {

	        return "Credentials Invalid !!";
	    }


	// http://localhost:8182/api/auth/signup

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {

		if (userRepo.existsByUsername(signUpDto.getUsername())) {

			return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
		}

		if (userRepo.existsByEmail(signUpDto.getEmail())) {

			return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setName(signUpDto.getName());
		user.setEmail(signUpDto.getEmail());
		user.setUsername(signUpDto.getUsername());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

		User saveUser = userRepo.save(user);

		return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
	}
}
