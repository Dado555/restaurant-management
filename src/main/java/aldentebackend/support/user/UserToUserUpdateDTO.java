package aldentebackend.support.user;

import aldentebackend.dto.user.UserInfoDTO;
import aldentebackend.dto.user.UserUpdateDTO;
import aldentebackend.model.Authority;
import aldentebackend.model.User;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserToUserUpdateDTO extends AbstractConverter<User, UserUpdateDTO> {

    @Override
    public UserUpdateDTO convert(@NonNull User user) {
//        return getModelMapper().map(user, UserUpdateDTO.class);
        UserUpdateDTO userUpdateDTO = getModelMapper().map(user, UserUpdateDTO.class);
        userUpdateDTO.setRole(((Authority) user.getAuthorities().toArray()[0]).getName());

        return userUpdateDTO;
    }
}
