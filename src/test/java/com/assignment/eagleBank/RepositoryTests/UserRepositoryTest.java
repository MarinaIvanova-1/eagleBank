package com.assignment.eagleBank.RepositoryTests;

import com.assignment.eagleBank.TestUtils;
import com.assignment.eagleBank.entity.User;
import com.assignment.eagleBank.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;
    @Test
    void whenFindByEmail_thenReturnCorrectUser() {
        User user = TestUtils.createNewUser(TestUtils.createNewAddress());
        entityManager.persistAndFlush(user);

        User returnedUser = userRepository.findByEmail(user.getEmail()).orElse(null);

        assertThat(returnedUser).isNotNull();
        assertThat(returnedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(returnedUser.getName()).isEqualTo(user.getName());
    }
}
