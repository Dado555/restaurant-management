package aldentebackend.service;

import aldentebackend.constants.UserConstants;
import aldentebackend.exception.NotFoundException;
import aldentebackend.model.Bartender;

import aldentebackend.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UsersServiceIntegrationTest {

    @Autowired
    UserService userService;

    @Test
    public void findByUsernameWithAuthorities_calledWithExistingUsername_isSuccessful() {
        User user = userService.findByUsernameWithAuthorities(UserConstants.EXISTING_USERNAME);

        assertNotNull(user);
        assertNotNull(user.getAuthorities());
        assertThat(user.getUsername()).isEqualTo(UserConstants.EXISTING_USERNAME);
    }

    @Test
    public void findByUsernameWithAuthorities_calledWithNullUsername_throwsNotFoundException() {
        assertThrows(NotFoundException.class, () -> userService.findByUsernameWithAuthorities(null));
    }

    @Test
    public void findByUsernameWithAuthorities_calledWithNonExistingUsername_throwsNotFoundException() {
        assertThrows(NotFoundException.class, () -> userService.findByUsernameWithAuthorities(UserConstants.NON_EXISTENT_USERNAME));
    }

    @Test
    public void findOneWithAuthorities_calledWithExistingId_isSuccessful() {
        User user = userService.findOneWithAuthorities(UserConstants.EXISTING_ID);

        assertNotNull(user);
        assertNotNull(user.getAuthorities());
        assertThat(user.getId()).isEqualTo(UserConstants.EXISTING_ID);
    }

    @Test
    public void findOneWithAuthorities_calledWithNullId_throwsNotFoundException() {
        assertThrows(NotFoundException.class, () -> userService.findOneWithAuthorities(null));
    }

    @Test
    public void findOneWithAuthorities_calledWithNonExistingId_throwsNotFoundException() {
        assertThrows(NotFoundException.class, () -> userService.findOneWithAuthorities(UserConstants.NON_EXISTENT_ID));
    }

    @Test
    public void registerUser_calledWithValidData_isSuccess() {
        User user = new Bartender();
        user.setUsername("mickoLoz");
        user.setPassword("micko123");
        user.setFirstName("Micko");
        user.setLastName("Loznica");
        user.setPhoneNumber("0650275573");

        User savedUser = userService.registerUser(user);

        assertNotNull(savedUser);
        assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void registerUser_calledWithNull_throwsNullPointerException() {
        User user = null;

        assertThrows(NullPointerException.class, () -> userService.registerUser(user));
    }

    @Test
    public void registerUser_calledWithAlreadyExistingUser_throwsIllegalArgumentException() {
        User user = userService.findOne(UserConstants.EXISTING_ID);

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));
    }

    @Test
    public void getWorkersCount_isSuccess() {
        Integer workersCount = userService.getWorkersCount();

        assertEquals(workersCount, UserConstants.WORKERS_COUNT);
    }

    @Test
    public void getAllWorkers_isSuccess() {
        List<User> allWorkers = userService.getAllWorkers();

        assertEquals(allWorkers.size(), UserConstants.WORKERS_COUNT);
    }

    @Test
    public void deleteUser_calledWithNonExistingId_throwsNotFoundException() {

        assertThrows(NotFoundException.class, () -> userService.delete(UserConstants.NON_EXISTENT_ID));
    }

    @Test
    public void deleteUser_calledWithValidData() {
        User user = userService.findOne(UserConstants.EXISTING_ID);

        userService.delete(UserConstants.EXISTING_ID);

        assertEquals(user.getActive(), false);
    }

}
