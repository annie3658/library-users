package com.library.users.demo.util;
import com.library.users.demo.dto.UserDTO;
import com.library.users.demo.entity.User;

public class DTOUtil {

    public User dtoToUser(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setRoles(userDTO.getRoles());
        return  user;
    }

    public UserDTO userToDTO(User user){
        UserDTO userDTO = new UserDTO.Builder(user.getId())
                .withName(user.getName())
                .withUsername(user.getUsername())
                .withPassword(user.getPassword())
                .withRoles(user.getRoles())
                .build();
        return userDTO;
    }
}
