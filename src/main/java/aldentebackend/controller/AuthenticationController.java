package aldentebackend.controller;

import aldentebackend.dto.authentication.AuthenticationMasterPasswordDTO;
import aldentebackend.dto.authentication.AuthenticationRequestDTO;
import aldentebackend.dto.authentication.AuthenticationResponseDTO;
import aldentebackend.model.Authority;
import aldentebackend.security.JwtUtil;
import aldentebackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/authentication")
public class AuthenticationController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@Valid @RequestBody AuthenticationRequestDTO authenticationRequestDTO)  {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getUsername(), authenticationRequestDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(authentication);
        String username = jwtUtil.extractUsernameFromToken(token);

        var user = userService.findByUsernameWithAuthorities(username);
        var authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toList());
        var authenticationResponseDTO = new AuthenticationResponseDTO(user.getId(), token, username, user.getFirstName(), user.getLastName(), authorities);
        return new ResponseEntity<>(authenticationResponseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "master-login")
    public ResponseEntity<Boolean> masterLogin(@Valid @RequestBody AuthenticationMasterPasswordDTO authenticationMasterPasswordDTO)  {
        if (authenticationMasterPasswordDTO.getPassword().equals("MASTER"))
            return new ResponseEntity<>(true, HttpStatus.OK);

        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }
}
