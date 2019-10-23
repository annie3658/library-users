package com.library.users.demo.service;

import ch.qos.logback.classic.Logger;
import com.library.users.demo.config.StatsConfig;
import com.library.users.demo.dto.UserDTO;
import com.library.users.demo.dto.UserStatsDTO;
import com.library.users.demo.entity.User;
import com.library.users.demo.repository.UserRepository;
import com.library.users.demo.util.DTOUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private DTOUtil dtoUtil = new DTOUtil();
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CustomUserDetailsService.class);
    private WebClient.Builder webClientBuilder;
    private StatsConfig config;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder, WebClient.Builder webClientBuilder, StatsConfig config) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.webClientBuilder = webClientBuilder;
        this.config = config;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User dbUser = this.userRepository.findByUsername(username);

        if (dbUser != null) {
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            for (String role : dbUser.getRoles()) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantedAuthorities.add(authority);
            }

            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
                    dbUser.getUsername(), dbUser.getPassword(), grantedAuthorities);
            return user;
        } else {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
    }

    public UserDTO insert(UserDTO user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return dtoUtil.userToDTO(userRepository.save(dtoUtil.dtoToUser(user)));
    }

    public UserDTO update(UserDTO user){
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        return dtoUtil.userToDTO(userRepository.save(dtoUtil.dtoToUser(user)));
    }

    public void delete(String id){
        userRepository.deleteById(id);
    }

    public List<UserDTO> getUsers(){

        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            LOGGER.warn("The users list is empty");
        }
        return users.stream()
                .map(user -> dtoUtil.userToDTO(user))
                .collect(Collectors.toList());
    }

    public List<UserStatsDTO> getUserStats(String id) {

        Flux<UserStatsDTO> flux = webClientBuilder.build()
                .get()
                .uri(config.getUrl() + id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(UserStatsDTO.class);

        List<UserStatsDTO> stats = flux.collectList().block();
        return stats;
    }
}