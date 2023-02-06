package aldentebackend.service;

import aldentebackend.constants.UserConstants;
import aldentebackend.exception.NotFoundException;
import aldentebackend.model.Bartender;
import aldentebackend.model.Cook;
import aldentebackend.model.User;
import aldentebackend.model.Waiter;
import aldentebackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceUnitTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findByUsernameWithAuthorities_calledWithExistingEmail_isSuccess() {
        Bartender bartender = new Bartender();
        bartender.setUsername(UserConstants.EXISTING_USERNAME);

        doReturn(Optional.of(bartender)).when(userRepository).findByEmailFetchAuthorities(UserConstants.EXISTING_USERNAME);

        assertThat(bartender.getUsername()).isEqualTo(UserConstants.EXISTING_USERNAME);
    }

    @Test
    public void findByUsernameWithAuthorities_calledWithNonExistingEmail_throwsNotFoundException() {
        doReturn(Optional.empty()).when(userRepository).findByEmailFetchAuthorities(UserConstants.NON_EXISTENT_USERNAME);

        assertThrows(NotFoundException.class, () -> userService.findByUsernameWithAuthorities(UserConstants.NON_EXISTENT_USERNAME));
    }

    @Test
    public void findOneWithAuthorities_calledWithExistingId_isSuccess() {
        Bartender bartender = new Bartender();
        bartender.setId(UserConstants.EXISTING_ID);

        doReturn(Optional.of(bartender)).when(userRepository).findOneFetchAuthorities(UserConstants.EXISTING_ID);

        assertThat(bartender.getId()).isEqualTo(UserConstants.EXISTING_ID);
    }

    @Test
    public void findOneWithAuthorities_calledWithNonExistingId_throwsNotFoundException() {
        doReturn(Optional.empty()).when(userRepository).findOneFetchAuthorities(UserConstants.NON_EXISTENT_ID);

        assertThrows(NotFoundException.class, () -> userService.findOneWithAuthorities(UserConstants.NON_EXISTENT_ID));
    }

    @Test
    public void registerUser_calledWithValidData_isSuccess() {
        User bartender = new Bartender();
        bartender.setId(UserConstants.NON_EXISTENT_ID);
        bartender.setUsername(UserConstants.NON_EXISTENT_USERNAME);

        doReturn(Optional.of(bartender)).when(userRepository).save(bartender);

        assertThat(bartender.getId()).isEqualTo(UserConstants.NON_EXISTENT_ID);
    }

    @Test
    public void registerUser_calledWithInvalidData_throwsIllegalArgumentException() {
        User user = new Waiter();

        doThrow(IllegalArgumentException.class).when(userRepository).save(user);

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));
    }

    @Test
    public void deleteUser_calledWithValidData_isSuccess(){
        Cook user = new Cook();
        user.setId(UserConstants.EXISTING_ID);
        user.setActive(true);

        doReturn(Optional.of(user)).when(userRepository).findById(UserConstants.EXISTING_ID);
        doReturn(user).when(userRepository).save(user);

        userService.delete(UserConstants.EXISTING_ID);

        assertEquals(user.getActive(), false);
    }

}
