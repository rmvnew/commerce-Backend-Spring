package com.delta.commerce.config;

import com.delta.commerce.entity.Profile;
import com.delta.commerce.entity.Transaction;
import com.delta.commerce.entity.User;
import com.delta.commerce.repository.ProfileRepository;
import com.delta.commerce.repository.TransactionsRepository;
import com.delta.commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class SysadminInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${USER_EMAIL}")
    private String email;

    @Value("${ADMIN_PASSWORD}")
    private String password;

    @Value("${ADMIN_NAME}")
    private String adminName;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (userRepository.count() == 0) { // Verifica se não existem usuários
            createUserWithSysadminRole();
        }
    }

    private void createUserWithSysadminRole() {

        Set<Transaction> transactions = new HashSet<>();



        if (transactionsRepository.count() == 0) {
            transactions.add(transactionsRepository.save(new Transaction("ADMIN_WRITE")));
            transactions.add(transactionsRepository.save(new Transaction("ADMIN_READ")));
        }


            var currentProfile = new Profile();
            currentProfile.setTransactions(transactions);
            currentProfile.setActive(true);
            currentProfile.setUpdateAt(LocalDateTime.now());
            currentProfile.setCreateAt(LocalDateTime.now());
            currentProfile.setProfileName("ADMIN");
            var profileSaved = profileRepository.save(currentProfile);


        var user = new User();
        user.setProfile(profileSaved);
        user.setUserEmail(email);
        user.setUserEnrollment("00001");
        user.setUserPassword(passwordEncoder.encode(password));
        user.setUserCompleteName(adminName);
        user.setActive(true);
        user.setCreateAt(LocalDateTime.now());

        this.userRepository.save(user);


    }
}
