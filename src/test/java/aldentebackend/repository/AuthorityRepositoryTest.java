package aldentebackend.repository;

import aldentebackend.model.Authority;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AuthorityRepositoryTest {
    @Autowired
    private AuthorityRepository authorityRepository;

    @ParameterizedTest
    @MethodSource("authorityNotFound")
    public void findByName_calledWithInvalidName_returnsNullAuthority(String name) {
        Authority authority = authorityRepository.findByName(name);

        assertNull(authority, "Authority not found");
    }

    private static Stream<String> authorityNotFound() {
        return Stream.of("", " ", "\n", "\t", null, "AUTH");
    }

    @ParameterizedTest
    @MethodSource("authorityValidNames")
    public void findByName_calledWithValidName_returnsValidAuthorityObject(String name) {
        Authority authority = authorityRepository.findByName(name);

        assertNotNull(authority, "Authority found");
        assertTrue(authority.getAuthority().equalsIgnoreCase(name));
    }

    private static Stream<String> authorityValidNames() {
        return Stream.of("ADMIN", "MANAGER", "WAITER", "BARTENDER", "COOK");
    }
}
