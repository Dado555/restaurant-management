package aldentebackend.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class AuthenticationResponseDTO {

    private Long id;
    private String jwt;
    private String username;
    private String firstName;
    private String lastName;
    private List<String> authorities;
}
