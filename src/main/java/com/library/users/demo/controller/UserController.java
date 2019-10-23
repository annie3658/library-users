package com.library.users.demo.controller;

import com.library.users.demo.dto.UserDTO;
import com.library.users.demo.dto.UserStatsDTO;
import com.library.users.demo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private CustomUserDetailsService userService;

    @Autowired
    public UserController(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDTO> listUser(){
        return userService.getUsers();
    }

    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public List<UserStatsDTO> userStats(@PathVariable(value = "id") String id){
        return userService.getUserStats(id);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public UserDTO create(@RequestBody UserDTO user){
        return userService.insert(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public UserDTO update(@RequestBody UserDTO user){
        return userService.insert(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") String id){
        userService.delete(id);

    }

}