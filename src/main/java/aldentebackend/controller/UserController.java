package aldentebackend.controller;

import aldentebackend.dto.user.UserCreateDTO;
import aldentebackend.dto.user.UserInfoDTO;
import aldentebackend.dto.user.UserUpdateDTO;
import aldentebackend.model.User;
import aldentebackend.service.AuthorityService;
import aldentebackend.service.UserService;
import aldentebackend.support.IConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserService userService;
    private final AuthorityService authorityService;

    private final IConverter<User, UserInfoDTO> toUserInfoDTO;
    private final IConverter<UserCreateDTO, User> toUserFromUserCreate;
    private final IConverter<UserUpdateDTO, User> toUserFromUserUpdate;
    private final IConverter<User, UserUpdateDTO> toUserUpdateDTO;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<UserUpdateDTO>> getAllUsers()  {
        List<User> users = userService.findAllWithAuthorities();
        return new ResponseEntity<>(toUserUpdateDTO.convert(users), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoDTO> getOneUser(@PathVariable("id") Long id)  {
        User user = userService.findOne(id);
        return new ResponseEntity<>(toUserInfoDTO.convert(user), HttpStatus.OK);
    }

    @GetMapping(value = "/count-workers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getWorkersCount()  {
        return new ResponseEntity<>(userService.getWorkersCount(), HttpStatus.OK);
    }

    @GetMapping(value = "/workers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<UserInfoDTO>> getAllWorkers()  {
        return new ResponseEntity<>(toUserInfoDTO.convert(userService.getAllWorkers()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        User user = userService.registerUser(toUserFromUserCreate.convert(userCreateDTO));
        user.getAuthorities().add(authorityService.findByName(userCreateDTO.getRole()));
        userService.save(user);

        return new ResponseEntity<>(toUserInfoDTO.convert(user), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoDTO> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.findOne(id);

        User user = userService.update(toUserFromUserUpdate.convert(userUpdateDTO));

        return new ResponseEntity<>(toUserInfoDTO.convert(user), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
