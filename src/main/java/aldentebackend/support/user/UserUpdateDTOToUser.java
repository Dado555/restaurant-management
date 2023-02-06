package aldentebackend.support.user;

import aldentebackend.dto.user.UserInfoDTO;
import aldentebackend.dto.user.UserUpdateDTO;
import aldentebackend.model.User;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserUpdateDTOToUser extends AbstractConverter<UserUpdateDTO, User> {

    @Override
    public User convert(@NonNull UserUpdateDTO userUpdateDTO) {
        try {
            return UserFactory.createUser(userUpdateDTO);
        } catch (Exception e) {
            System.out.println("Invalid user role");

            return null;
        }
    }

}
