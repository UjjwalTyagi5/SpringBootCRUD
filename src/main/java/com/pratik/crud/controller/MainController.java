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
    // Check if principal has permission to view all users
    if (principal == null || !principal.getName().equals("admin")) {
        throw new SecurityException("Access denied: insufficient permissions to view all users.");
    }
    return userRepo.findAll();
}

@PostMapping(path= "/reg", consumes = "application/json", produces = "application/json")
public String addNewUser(@RequestBody User user, Principal principal) {
    // Check if principal has permission to add user
    if (principal == null || !principal.getName().equals("admin")) {
        throw new SecurityException("Access denied: insufficient permissions to add user.");
    }
    userRepo.save(user);
    return "Data successfully saved in Database";
}

@PutMapping("/update")
public String updateData(@RequestBody User user, Principal principal) {
    // Check if principal has permission to update this user
    if (principal == null) {
        throw new SecurityException("Access denied: user not authenticated.");
    }
    Optional<User> existingUser = userRepo.findById(user.getId());
    if (!existingUser.isPresent()) {
        return "Data not exist in Database";
    }
    // Allow update only if the principal is admin or the owner of the user record
    if (!principal.getName().equals("admin") && !principal.getName().equals(existingUser.get().getName())) {
        throw new SecurityException("Access denied: insufficient permissions to update this user.");
    }
    userRepo.save(user);
    return "Data updated successfully saved in Database";
}

@DeleteMapping(path= "/delete", consumes = "application/json", produces = "application/json")
public String deleteData(@RequestParam int id, Principal principal) {
    if (principal == null) {
        throw new SecurityException("Access denied: user not authenticated.");
    }
    Optional<User> existingUser = userRepo.findById(id);
    if (!existingUser.isPresent()) {
        return "Data not exist in Database";
    }
    // Allow delete only if the principal is admin or the owner of the user record
    if (!principal.getName().equals("admin") && !principal.getName().equals(existingUser.get().getName())) {
        throw new SecurityException("Access denied: insufficient permissions to delete this user.");
    }
    userRepo.deleteById(id);
    return "Data Deleted ";
}
	

	
	
	

}
