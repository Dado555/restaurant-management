package aldentebackend.service.impl;

import aldentebackend.exception.NotFoundException;
import aldentebackend.model.*;
import aldentebackend.repository.UserRepository;
import aldentebackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl extends JPAServiceImpl<User> implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    protected JpaRepository<User, Long> getEntityRepository() {
        return userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsernameWithAuthorities(String username) {
        return userRepository.findByEmailFetchAuthorities(username).orElseThrow(() -> new NotFoundException("Invalid username: " + username));
    }

    @Override
    @Transactional(readOnly = true)
    public User findOneWithAuthorities(Long id) {
        return userRepository.findOneFetchAuthorities(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getWorkersCount() {
        return userRepository.findAllWorkers().size();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllWorkers() {
        return userRepository.findAllWorkers();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllWithAuthorities() {
        return userRepository.findAllWithAuthorities();
    }

    @Override
    @Transactional
    public User update(User user) {
        User temp_user = findOne(user.getId());

        temp_user.setId(user.getId());
        temp_user.setFirstName(user.getFirstName());
        temp_user.setLastName(user.getLastName());
        temp_user.setPhoneNumber(user.getPhoneNumber());
        temp_user.setUsername(user.getUsername());
        if (!user.getPassword().equals("")) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
            temp_user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return save(temp_user);
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        if (user.getId() != null) {
            Optional<User> temp_user = userRepository.findById(user.getId());
            if (temp_user.isPresent()) {
                throw new IllegalArgumentException("User already exists");
            }
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = findByUsernameWithAuthorities(username);

        String password;
        if (user.getAuthorities().stream().anyMatch(x -> x.getName().equals("ADMIN") || x.getName().equals("MANAGER"))) {
            password = user.getPassword();
        } else {
//            TMP: Password: MASTER
            password = "$2a$10$0DGchQfgcrAek6f7Zy1aIuOS9tKpPJ8t4o68eC3K4sGXkFUyjj4OC";
        }


        return new org.springframework.security.core.userdetails.User(user.getUsername(), password,
                user.getAuthorities());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = findOne(id);
        user.setActive(false);
        save(user);
    }
}
