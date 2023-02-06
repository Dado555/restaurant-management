package aldentebackend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class UserUpdateDTO {

    @NotNull
    private Long id;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotBlank
    String phoneNumber;

    @NotBlank
    String username;

    @NotBlank
    String password;

    @NotBlank
    String role;

}
