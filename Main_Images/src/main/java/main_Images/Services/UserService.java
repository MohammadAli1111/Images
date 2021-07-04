package main_Images.Services;

import main_Images.Models.User;
import main_Images.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("UserService")
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Transactional
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(2);
        user.setRole("User");
        return userRepository.save(user);
    }
    @Transactional
    public User saveAdmin(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        user.setRole("ADMIN");
        return userRepository.save(user);
    }
    public boolean isAdmin(User user){


        if (user.getActive()==1) {
            return true;
        } else {
            return false;
        }
    }

    public   List<User> userfindAll(){
        return userRepository.findAll();
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }




}