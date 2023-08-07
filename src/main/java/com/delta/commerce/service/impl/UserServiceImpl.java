package com.delta.commerce.service.impl;


import com.delta.commerce.dto.request.CreateUserRequestDto;
import com.delta.commerce.dto.request.RecoverPassRequestDto;
import com.delta.commerce.dto.request.UpdateUserRequestDto;
import com.delta.commerce.dto.response.UserResponse;
import com.delta.commerce.entity.Profile;
import com.delta.commerce.entity.User;
import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import com.delta.commerce.mappers.UserMapper;
import com.delta.commerce.repository.ProfileRepository;
import com.delta.commerce.repository.UserRepository;
import com.delta.commerce.scheduler.UpdateCode;
import com.delta.commerce.service.MailService;
import com.delta.commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Override
    public void createUser(CreateUserRequestDto dto) {

        var emailAlreadyExist = this.userRepository.findUserByUserEmail(dto.getUserEmail())
                .isPresent();
        if (emailAlreadyExist) {
            throw new CustomException(ErrorCustom.USER_EMAIL_ALREADY_EXISTS);
        }

        var nameAlreadyExist = this.userRepository.findUserByUserCompleteName(dto.getUserCompleteName())
                .isPresent();
        if (nameAlreadyExist) {
            throw new CustomException(ErrorCustom.USER_ALREADY_EXISTS);
        }

//        Set<Profile> profiles = new HashSet<>();
//
//        for (Integer role : dto.getProfiles()) {
//            profiles.add(this.profileRepository.findById((long) role)
//                    .orElseThrow(() -> new CustomException(ErrorCustom.ROLE_NOT_EXISTS)));
//        }

        var profile = this.profileRepository.findById((long)dto.getProfileId())
                .orElseThrow(()-> new CustomException(ErrorCustom.NOT_FOUND));

        User user = new User(
                dto.getUserCompleteName().toUpperCase(),
                dto.getUserEmail(),
                passwordEncoder.encode(dto.getUserPassword()),
                dto.getUserEnrollment(),
                true,
                LocalDateTime.now(),
                profile
        );

        this.userRepository.save(user);

    }

    @Override
    public Page<UserResponse> getAllUsers(String name, Pageable pageable) {
        return this.userRepository.findUsersByUserCompleteNameContainingIgnoreCase(name, pageable)
                .map(userMapper::toDto);
    }

    @Override
    public UserResponse userUpdate(UpdateUserRequestDto dto, Long id) {

        var user = this.userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));

        var currentUser = this.getLoggedInUser();

//        var isAdmin = currentUser.getProfiles().stream()
//                .anyMatch(profile -> profile.getProfileName().equalsIgnoreCase("ADMIN"));

        var isAdmin = currentUser.getProfile().getProfileName().equalsIgnoreCase("ADMIN");

        if (!isAdmin) {
            if (!this.isUserLoggedIn(user)) {
                throw new CustomException(ErrorCustom.USER_UPDATE_NOT_ALLOWED);
            }
        }


        if (dto.getRole() != null) {

//            Set<Profile> roles = dto.getRoles().stream()
//                    .map(roleId -> this.profileRepository.findById((long) roleId))
//                    .flatMap(optionalRole -> optionalRole.stream())
//                    .collect(Collectors.toSet());

            var role = this.profileRepository.findById((long)dto.getRole())
                    .orElseThrow(()-> new CustomException(ErrorCustom.NOT_FOUND));

            user.setProfile(role);

        }

        user.setUserCompleteName(dto.getUserCompleteName().toUpperCase());
        user.setUserEmail(dto.getUserEmail());
        user.setUserEnrollment(dto.getUserEnrollment());

        var userSaved = this.userRepository.save(user);

        return this.userMapper.toDto(userSaved);
    }

    @Override
    public User findById(Long id) {
        var user = this.userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));

        return user;
    }

    @Override
    public void recoverCode(String email) {
        Random random = new Random();

        int randomNumber = random.nextInt(900000) + 100000;

        var code = Integer.toString(randomNumber);

        var user = this.userRepository.findUserByUserEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));

        user.setRecoverCode(code);

        userRepository.save(user);

        try {
            mailService.sendEmail(email, "Code to recover password", "Seu código: " + code);
        } catch (Exception ex) {
            ex.getMessage();
        }

        Timer timer = new Timer();
        long delay = 5 * 60 * 1000; // 5 minutos em milissegundos
        UpdateCode task = new UpdateCode(email, userRepository);
        timer.schedule(task, delay);
    }

    @Override
    public void recoverPass(RecoverPassRequestDto dto) {

        var user = this.userRepository.findUserByUserEmailAndRecoverCode(dto.getEmail(), dto.getCode())
                .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));

        var passEncode = passwordEncoder.encode(dto.getPass());

        user.setUserPassword(passEncode);
        user.setRecoverCode("");
        this.userRepository.save(user);

    }

    @Override
    public UserResponse changeStatus(Long user_id) {

        var isRegistered = this.userRepository.findById(user_id)
                .orElseThrow(() -> new CustomException(ErrorCustom.USER_NOT_FOUND));

        isRegistered.setActive(!isRegistered.isActive());

        return this.userMapper.toDto(this.userRepository.save(isRegistered));
    }


    @Override
    public User getLoggedInUser() {
        User loggedInUser = ((Optional<User>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userRepository.findUserByUserEmail(loggedInUser.getUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + loggedInUser.getUserEmail()));
    }


    @Override
    public boolean isUserLoggedIn(User user) {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return user.getUserEmail().equals(loggedInUsername);
    }

    @Override
    public User findUserByEmail(String email) {
        return this.userRepository.findUserByUserEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));
    }
}
