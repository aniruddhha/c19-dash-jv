package com.ani.coding.assignment.authentication;

import com.ani.coding.assignment.authentication.controller.AppAuthController;
import com.ani.coding.assignment.authentication.domain.AppUser;
import com.ani.coding.assignment.authentication.domain.LoginDto;
import com.ani.coding.assignment.authentication.exception.AppUserNotFoundException;
import com.ani.coding.assignment.authentication.repository.AppUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AppAuthController.class)
public class AppUserControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AppUserRepository repository;

    @Test
    @DisplayName("Sign Up : Best Way")
    public void signUp() throws Exception {
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

       Mockito.when(
                repository.save(Mockito.any())
        ).thenReturn(
                user
        );

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/signup/")
                        .content(
                                mapper.writeValueAsString(user)
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers
                        .status()
                        .isOk()
        );
    }

    @Test
    @DisplayName("Sign Up : No Gender, Address, State, City, Date")
    public void signUp1() throws Exception {
        final AppUser user = new AppUser();
        user.setEmail("dd@gg.com");
        user.setMobile("+543477098867");
        user.setUserName("dd1");
        user.setPassword("dd1#4566$");

        Mockito.when(
                repository.save(Mockito.any())
        ).thenReturn(
                user
        );

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/signup/")
                        .content(
                                mapper.writeValueAsString(user)
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers
                        .status()
                        .isOk()
        );
    }

    @Test
    @DisplayName("Sign Up : No Email")
    public void signUp2() throws Exception {
        final AppUser user = new AppUser();
        user.setMobile("+543477098867");
        user.setUserName("dd1");
        user.setPassword("dd1#4566$");

       Mockito.when(
                repository.save(user)
        ).thenThrow(ConstraintViolationException.class);

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/signup/")
                        .content(
                                mapper.writeValueAsString(user)
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers
                        .status()
                        .isBadRequest()
        );
    }

    @Test
    @DisplayName("Sign Up : Invalid Email")
    public void signUp3() throws Exception {
        final AppUser user = new AppUser();
        user.setEmail("aaaaa");
        user.setMobile("+543477098867");
        user.setUserName("dd1");
        user.setPassword("dd1#4566$");

        Mockito.when(
                repository.save(user)
        ).thenThrow(ConstraintViolationException.class);

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/signup/")
                        .content(
                                mapper.writeValueAsString(user)
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers
                        .status()
                        .isBadRequest()
        );
    }

    @Test
    @DisplayName("Sign Up : No Mobile Number")
    public void signUp4() throws Exception {
        final AppUser user = new AppUser();
        user.setEmail("aaaaa@gg.com");
        user.setUserName("dd1");
        user.setPassword("dd1#4566$");

        Mockito.when(
                repository.save(user)
        ).thenThrow(ConstraintViolationException.class);

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/signup/")
                        .content(
                                mapper.writeValueAsString(user)
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers
                        .status()
                        .isBadRequest()
        );
    }

    @Test
    @DisplayName("Sign Up : No User Name ")
    public void signUp5() throws Exception {
        final AppUser user = new AppUser();
        user.setEmail("aaaaa@gg.com");
        user.setMobile("+6544871252");
        user.setPassword("dd1#4566$");

        Mockito.when(
                repository.save(user)
        ).thenThrow(ConstraintViolationException.class);

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/signup/")
                        .content(
                                mapper.writeValueAsString(user)
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers
                        .status()
                        .isBadRequest()
        );
    }

    @Test
    @DisplayName("Sign Up : No Password ")
    public void signUp6() throws Exception {
        final AppUser user = new AppUser();
        user.setEmail("aaaaa@gg.com");
        user.setMobile("+6544871252");
        user.setUserName("bbnnn");

        Mockito.when(
                repository.save(user)
        ).thenThrow(ConstraintViolationException.class);

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/signup/")
                        .content(
                                mapper.writeValueAsString(user)
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers
                        .status()
                        .isBadRequest()
        );
    }

    @Test
    @DisplayName("Sign In : Best Way ")
    public void signIn() throws Exception {
        AppUser user = new AppUser();
        user.setUserId(1L);
        user.setEmail("aaaaa@gg.com");
        user.setMobile("+6544871252");
        user.setUserName("dd");
        user.setPassword("dd");

        final Optional<AppUser> optional = Optional.of(user);

        Mockito.when(
                repository.signIn("dd", "dd")
        ).thenReturn(optional);

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/signin/")
                        .content(
                                mapper.writeValueAsString(new LoginDto("dd", "dd"))
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.payload.mobile", Matchers.is("+6544871252"))
        );
    }

    @Test
    @DisplayName("Sign In : Wrong User Name ")
    public void signIn1() throws Exception {

        final Optional<AppUser> optional = Optional.empty();

        final String userName = "dd1xcvxv";
        final String password = "dd";

        Mockito.when(
                repository.signIn(userName, password)
        ).thenReturn(optional);

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/signin/")
                        .content(
                                mapper.writeValueAsString(new LoginDto(userName, password))
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                result -> Assertions.assertTrue(
                        result.getResolvedException() instanceof AppUserNotFoundException
                )
        );
    }

    @Test
    @DisplayName("Sign In : Wrong Password ")
    public void signIn2() throws Exception {

        final Optional<AppUser> optional = Optional.empty();

        final String userName = "dd";
        final String password = "dd2";

        Mockito.when(
                repository.signIn(userName, password)
        ).thenReturn(optional);

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/signin/")
                        .content(
                                mapper.writeValueAsString(new LoginDto(userName, password))
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                result -> Assertions.assertTrue(
                        result.getResolvedException() instanceof AppUserNotFoundException
                )
        );
    }
}
