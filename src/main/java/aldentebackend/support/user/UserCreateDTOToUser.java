package aldentebackend.support.user;

import aldentebackend.dto.user.UserCreateDTO;
import aldentebackend.model.User;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserCreateDTOToUser extends AbstractConverter<UserCreateDTO, User> {

    @Override
    public User convert(@NonNull UserCreateDTO userCreateDTO) {
        try {
            return UserFactory.createUser(userCreateDTO);
        } catch (Exception e) {
            System.out.println("Invalid user role");

            return null;
        }
    }
}
