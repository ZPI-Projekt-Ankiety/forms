package com.forms.formswebapp.user.domain;

import com.forms.formswebapp.user.domain.model.response.UserDto;
import com.forms.formswebapp.user.domain.model.response.UserResponse;
import com.forms.formswebapp.user.domain.model.shared.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    Map<String, Object> getUserClaims(final String email) {
        final User user = getUserByEmailOrThrow(email);
        final Set<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        return Map.of("roles", authorities);
    }

    public User getUserByEmailOrThrow(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    User saveUser(final User user) {
        return userRepository.save(user);
    }
    
    public UserResponse getUsersAdmin(final String email) {
        if (StringUtils.hasText(email)) {
            return UserResponse.from(userRepository.findByEmailContaining(email));
        }
        return UserResponse.from(userRepository.findAll());
    }

    public void deleteUser(final String email) {
        final User user = getUserByEmailOrThrow(email);
        this.checkIfNotAdmin(user);
        userRepository.delete(user);
    }

    public UserDto promoteUser(final String email) {
        final User user = getUserByEmailOrThrow(email);
        this.checkIfNotAdmin(user);
        user.setRole(Role.ADMIN);
        final User saved = userRepository.save(user);
        return UserDto.from(saved);
    }

    public UserDto demoteUser(final String email) {
        final User user = getUserByEmailOrThrow(email);
        this.checkIfAdmin(user);
        user.setRole(Role.USER);
        final User saved = userRepository.save(user);
        return UserDto.from(saved);
    }

    private void checkIfNotAdmin(final User user) {
        if (user.getRole() == Role.ADMIN) {
            throw new IllegalStateException("Cannot perform this operation on admin user");
        }
    }

    private void checkIfAdmin(final User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new IllegalStateException("Cannot perform this operation on regular user");
        }
    }

}