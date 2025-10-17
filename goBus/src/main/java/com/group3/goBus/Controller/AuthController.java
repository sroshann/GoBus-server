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
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // Allows all front end requests
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public Object signup(@RequestBody User user, HttpServletResponse response) {

        if( userRepository.findByUsername( user.getUsername() ) != null )
            return "User name already exists!";
        if( userRepository.findByEmail( user.getEmail() ) != null )
            return "Email already exists";
        if( userRepository.findByPhoneNumber(user.getPhoneNumber() ) != null )
            return "Phone number already exists";

        try {

            System.out.println( user.getEmail() );
            userRepository.save( user ); // Storing the data in database
            String token = JWT.generateToken( user.getUsername() ); // Creating token

            Cookie cookie = new Cookie("Token", token);
            cookie.setHttpOnly(true); // Prevent JS access
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60); // 1 hour
            response.addCookie(cookie);

            return user;

        } catch( Exception error ) {

            error.printStackTrace(); // Log error in console
            return "Error occurred while signing up: " + error.getMessage();

        }

    }

    @PostMapping("/login")
    public Object login( @RequestBody User user, HttpServletResponse response ) {

        User existingUser =  userRepository.findByEmail( user.getEmail() );
        if( existingUser == null ) return "No such user found";

        if(existingUser.getPassword().equals( user.getPassword() ) ) {

            String token = JWT.generateToken( existingUser.getUsername() );

            Cookie cookie = new Cookie("Token", token);
            cookie.setHttpOnly( true );
            cookie.setSecure( false );
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);

            return existingUser;

        } else return "Invalid user credentials";

    }

    @GetMapping("/logout")
    public String logOut( HttpServletResponse response ) {

        // Send delete instruction for the existing cookie
        Cookie deleteCookie = new Cookie("Token", null);
        deleteCookie.setPath("/");
        deleteCookie.setHttpOnly(true);
        deleteCookie.setSecure(false); // true in production
        deleteCookie.setMaxAge(0); // expires immediately
        response.addCookie(deleteCookie);

        return "Logged out successfully!";

    }

}
