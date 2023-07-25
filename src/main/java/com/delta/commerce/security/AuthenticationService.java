package com.delta.commerce.security;



import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import com.delta.commerce.exception.UserDisabledException;
import com.delta.commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var res = this.userRepository.findUserByUserEmail(username)
                .orElseThrow(() -> new CustomException(ErrorCustom.USER_NOT_FOUND));

        if (!res.isActive()) {
            throw new UserDisabledException(ErrorCustom.DISABLED_USER);
        }

        return res;
    }


}
