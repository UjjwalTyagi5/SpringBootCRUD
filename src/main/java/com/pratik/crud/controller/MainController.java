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
@PreAuthorize("hasRole('ADMIN')")
public Iterable<User> getAll() {
    // Authorization check: only ADMIN role can access all users
    return userRepo.findAll();
  }

@PostMapping(path= "/reg", consumes = "application/json", produces = "application/json")
@PreAuthorize("hasRole('ADMIN')")
public  String addNewUser (@RequestBody User user) 
{
    // Authorization check: only ADMIN role can add new users
    User u=(User)user;
    System.out.println(u);
    userRepo.save(u);
    
    return "Data successfully saved in Database";
    
}

//@PutMapping(path= "/update", consumes = "application/json", produces = "application/json")
@PutMapping("/update")
@PreAuthorize("hasRole('ADMIN')")
public  String updateData (@RequestBody User user) 
{
    // Authorization check: only ADMIN role can update users
    Optional<User> u=userRepo.findById(user.getId());
    System.out.println(u);
    if(u.isPresent())
    {
    System.out.println(u);
    userRepo.save(user);
    return "Data updated successfully saved in Database";
    }
    else
    {
        
        return "Data not exist in Database";
    }

}

@DeleteMapping(path= "/delete", consumes = "application/json", produces = "application/json")
@PreAuthorize("hasRole('ADMIN')")
public  String deleteData (@RequestParam int id ) 
{
    // Authorization check: only ADMIN role can delete users
    userRepo.deleteById(id);
    
    return "Data Deleted ";
    
}
	

	
	
	

}
