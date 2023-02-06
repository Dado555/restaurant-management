package aldentebackend.repository;

import aldentebackend.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @MethodSource("categoryNotFound")
    public void findByEmailFetchAuthorities_calledWithInvalidEmail_returnsEmptyOptional(String email) {
        Optional<User> user = userRepository.findByEmailFetchAuthorities(email);

        assertTrue(user.isEmpty(), "User not found");
    }

    private static Stream<String> categoryNotFound() {
        return Stream.of("", " ", "\n", "\t", null, "USER");
    }

    @Test
    public void findByEmailFetchAuthorities_calledWithValidEmail_returnsUser() {
        Optional<User> user = userRepository.findByEmailFetchAuthorities("admin");

        assertFalse(user.isEmpty());
        assertTrue(user.get().getFirstName().equalsIgnoreCase("Admin"));
        assertTrue(user.get().getLastName().equalsIgnoreCase("Adminovic"));
    }

    @ParameterizedTest
    @MethodSource("invalidIds")
    public void findOneFetchAuthorities_calledWithInvalidId_returnsEmptyOptional(Long id) {
        Optional<User> user = userRepository.findOneFetchAuthorities(id);

        assertTrue(user.isEmpty(), "User not found");
    }

    private static Stream<Long> invalidIds() {
        return Stream.of(null, -515L);
    }

    @Test
    public void findOneFetchAuthorities_calledWithValidId_returnsUser() {
        Optional<User> user = userRepository.findOneFetchAuthorities(10008L);

        assertFalse(user.isEmpty());
        assertTrue(user.get().getFirstName().equalsIgnoreCase("Vladan"));
        assertTrue(user.get().getLastName().equalsIgnoreCase("Perisic"));
    }
}
