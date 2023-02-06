package aldentebackend.support.user;

import aldentebackend.dto.user.UserInfoDTO;
import aldentebackend.model.User;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserToUserInfoDTO extends AbstractConverter<User, UserInfoDTO> {

    @Override
    public UserInfoDTO convert(@NonNull User user) {
        return getModelMapper().map(user, UserInfoDTO.class);
    }
}
