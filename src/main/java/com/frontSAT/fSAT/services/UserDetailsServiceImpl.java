package com.frontSAT.fSAT.services;


import com.frontSAT.fSAT.model.User;
import com.frontSAT.fSAT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private User userConnecte;

    @Autowired
    UserRepository userRepository;

    @Override//classe herite
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
        this.userConnecte=user;
        return UserPrinciple.build(user);
    }

    public User getUserConnecte() {//pour recuperer l utilisateur connecte
        return userConnecte;
    }
}
