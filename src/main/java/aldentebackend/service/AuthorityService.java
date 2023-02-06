package aldentebackend.service;

import aldentebackend.dto.user.UserCreateDTO;
import aldentebackend.model.Authority;
import aldentebackend.model.User;

public interface AuthorityService extends JPAService<Authority> {

    Authority findByName(String name);

    String getCurrentUsername();

}
