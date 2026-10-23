package com.pratik.crud.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratik.crud.model.User;
import com.pratik.crud.userRepository.UserRepository;

@RestController
public class MainController  {
	
	@Autowired
	UserRepository userRepo;
	
@GetMapping("/all")
public Iterable<User> getAll(Principal principal) {
    // Authorization check: only allow if principal is not null (authenticated user)
    if (principal == null) {
        throw new SecurityException("Unauthorized access");
    }
    return userRepo.findAll();
}

@PostMapping(path= "/reg", consumes = "application/json", produces = "application/json")
public String addNewUser(@RequestBody User user, Principal principal) {
    if (principal == null) {
        throw new SecurityException("Unauthorized access");
    }
    userRepo.save(user);
    return "Data successfully saved in Database";
}

@PutMapping("/update")
public String updateData(@RequestBody User user, Principal principal) {
    if (principal == null) {
        throw new SecurityException("Unauthorized access");
    }
    Optional<User> u = userRepo.findById(user.getId());
    if (u.isPresent()) {
        userRepo.save(user);
        return "Data updated successfully saved in Database";
    } else {
        return "Data not exist in Database";
    }
}

@DeleteMapping(path= "/delete", consumes = "application/json", produces = "application/json")
public String deleteData(@RequestParam int id, Principal principal) {
    if (principal == null) {
        throw new SecurityException("Unauthorized access");
    }
    userRepo.deleteById(id);
    return "Data Deleted ";
}
	

	
	
	

}
