package aldentebackend.service;

import aldentebackend.model.User;

import java.util.List;

public interface UserService extends JPAService<User> {

    User findByUsernameWithAuthorities(String email);

    User findOneWithAuthorities(Long id);

    User update(User user);

    List<User> findAllWithAuthorities();

    User registerUser(User user);

    Integer getWorkersCount();

    List<User> getAllWorkers();
}
