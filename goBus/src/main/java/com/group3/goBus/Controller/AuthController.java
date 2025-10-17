package com.group3.goBus.Controller;

import com.group3.goBus.Configurations.JWT;
import com.group3.goBus.Model.User;
import com.group3.goBus.Repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*") // Allows all front end requests
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public String signup(@RequestBody User user, HttpServletResponse response) {

        if( userRepository.findByUsername( user.getUsername() ) != null )
            return "User name already exists!";
        if( userRepository.findByEmail( user.getEmail() ) != null )
            return "Email already exists";
        if( userRepository.findByPhoneNumber(user.getPhoneNumber() ) != null )
            return "Phone number already exists";

        System.out.print( user );
        userRepository.save( user ); // Storing the data in database
        String token = JWT.generateToken( user.getUsername() ); // Creating token

        Cookie cookie = new Cookie("Token", token);
        cookie.setHttpOnly(true); // Prevent JS access
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); // 1 hour
        response.addCookie(cookie);

        return "User signed in successfully!";

    }

}
