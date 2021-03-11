package com.ani.coding.assignment.authentication;

import com.ani.coding.assignment.authentication.domain.AppUser;
import com.ani.coding.assignment.authentication.repository.AppUserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppUserRepoTests {

    @Autowired
    private AppUserRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Order(1)
    @Rollback(value = false)
    @Test
    @DisplayName("SignUp : Saving User In Best Way")
    public void signUpUser() {
        final AppUser user = new AppUser();
        user.setAddress("Xyz Road, Hi Street, Hello Lane 5");
        user.setState("MH");
        user.setCity("TUV");
        user.setEmail("dd@gg.com");
        user.setMobile("+543477098867");
        user.setGender(1);
        user.setUserName("dd1");
        user.setPassword("dd1#4566$");
        user.setSignUpDate(LocalDate.of(2020, 9, 30));
        AppUser saved = repository.save(user);
        entityManager.flush();
        Assertions.assertNotNull(saved);
        Assertions.assertEquals(LocalDate.of(2020, 9, 30), saved.getSignUpDate());
    }

    @Test
    @DisplayName("SignUp : Excluding State, City, Address, Gender, Date")
    public void saveUserValidationCheck1() {
        final AppUser user = new AppUser();
        user.setEmail("dd@gg.comwer");
        user.setMobile("+543477065498867");
        user.setUserName("dd6541");
        user.setPassword("dd154#4566$");
        AppUser saved = repository.save(user);
        entityManager.flush();
        Assertions.assertNotNull(saved);
    }

    @Test
    @DisplayName("SignUp : Validation : Email Null")
    public void saveUserValidationCheck2() {
        final AppUser user = new AppUser();
        user.setMobile("+9545421");
        user.setUserName("dd1");
        user.setPassword("dd1#4566$");
        Exception e = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            repository.save(user);
            entityManager.flush();
        });
        Assertions.assertTrue(e.getMessage().contains("Email is mandatory"));
    }

    @Test
    @DisplayName("SignUp : Validation : Email Pattern")
    public void saveUserValidationCheck3() {
        final AppUser user = new AppUser();
        user.setEmail("aaaaa");
        user.setMobile("+9545421");
        user.setUserName("dd1");
        user.setPassword("dd1#4566$");
        Exception e = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            repository.save(user);
            entityManager.flush();
        });
        Assertions.assertTrue(e.getMessage().contains("Need valid email"));
    }

    @Test
    @DisplayName("SignUp : Validation : Mobile null")
    public void saveUserValidationCheck4() {
        final AppUser user = new AppUser();
        user.setEmail("aaaaa@gmail.com");
        user.setUserName("dd1");
        user.setPassword("dd1#4566$");
        Exception e = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            repository.save(user);
            entityManager.flush();
        });
        Assertions.assertTrue(e.getMessage().contains("Mobile is mandatory"));
    }

    @Test
    @DisplayName("SignUp : Validation : Password null")
    public void saveUserValidationCheck5() {
        final AppUser user = new AppUser();
        user.setEmail("aaaaa@gmail.com");
        user.setMobile("+9545421");
        user.setUserName("dd1");
        Exception e = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            repository.save(user);
            entityManager.flush();
        });
        Assertions.assertTrue(e.getMessage().contains("Password is mandatory"));
    }

    @Test
    @DisplayName("SignUp : Validation : User Name null")
    public void saveUserValidationCheck6() {
        final AppUser user = new AppUser();
        user.setEmail("aaaaa@gmail.com");
        user.setMobile("+9545421");
        user.setPassword("dd1#4566$");
        Exception e = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            repository.save(user);
            entityManager.flush();
        });
        Assertions.assertTrue(e.getMessage().contains("User Name is mandatory"));
    }

    @Order(2)
    @Test
    @DisplayName("SignIn : Best Case")
    public void signIn() {
        final AppUser user = new AppUser();
        user.setUserId(1L);
        user.setAddress("Xyz Road, Hi Street, Hello Lane 5");
        user.setState("MH");
        user.setCity("TUV");
        user.setEmail("dd@gg.com");
        user.setMobile("+543477098867");
        user.setGender(1);
        user.setUserName("dd1");
        user.setPassword("dd1#4566$");
        user.setSignUpDate(LocalDate.of(2020, 9, 30));
        Optional<AppUser> expected = Optional.of(user);

        Optional<AppUser> actual = repository.signIn("dd1", "dd1#4566$");
        Assertions.assertEquals(expected,actual);
    }

    @Order(3)
    @Test
    @DisplayName("SignIn : Wrong User Name")
    public void signIn1() {
        Optional<AppUser> actual = repository.signIn("dd2", "dd1#4566$");
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            actual.orElseThrow( () -> {
                throw new RuntimeException("Wrong Email");
            });
        });
        Assertions.assertTrue(e.getMessage().contains("Wrong Email"));
    }

    @Order(3)
    @Test
    @DisplayName("SignIn : Wrong Password")
    public void signIn2() {
        Optional<AppUser> actual = repository.signIn("dd1", "dd1#4566$$");
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            actual.orElseThrow( () -> {
                throw new RuntimeException("Wrong Password");
            });
        });
        Assertions.assertTrue(e.getMessage().contains("Wrong Password"));
    }
}
