package aldentebackend.service.impl;

import aldentebackend.dto.user.UserCreateDTO;
import aldentebackend.exception.BadRequestException;
import aldentebackend.exception.NotFoundException;
import aldentebackend.model.Authority;
import aldentebackend.model.User;
import aldentebackend.repository.AuthorityRepository;
import aldentebackend.service.AuthorityService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class AuthorityServiceImpl extends JPAServiceImpl<Authority> implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    protected JpaRepository<Authority, Long> getEntityRepository() {
        return authorityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Authority findByName(String name) {
        Authority authority = authorityRepository.findByName(name);
        if (authority == null)
            throw new NotFoundException("Authority not found with name: " + name);

        return authority;
    }

    @Override
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            throw new NotFoundException("User not found");

        return authentication.getName();
    }

}
