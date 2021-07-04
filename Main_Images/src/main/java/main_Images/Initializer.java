package main_Images;

import main_Images.Models.User;
import main_Images.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {
    @Autowired
    UserService userService;

    // Save Role&Users when runtime
    @Override
    public void run(String... args) throws Exception {

//____________Save Admin User_____
        if(userService.userfindAll().isEmpty()) {
            User userAmin = new User();
            userAmin.setUsername("Admin");
            userAmin.setPassword("12345");
            userAmin.setEmail("Admin@gmail.com");
            userService.saveAdmin(userAmin);
        }




    }
}
