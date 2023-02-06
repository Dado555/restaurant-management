package aldentebackend.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class AuthenticationRequestDTO {

    @NotBlank(message = "Email can't be blank.")
    private String username;

    @NotBlank(message = "Password can't be blank.")
    private String password;
}
