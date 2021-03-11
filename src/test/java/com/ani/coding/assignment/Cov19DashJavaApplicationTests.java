package com.ani.coding.assignment;

import com.ani.coding.assignment.authentication.domain.AppUser;
import com.ani.coding.assignment.authentication.domain.LoginDto;
import com.ani.coding.assignment.http.ResMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Cov19DashJavaApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper mapper;

	@Order(1)
	@Test
	@DisplayName("SignUp : Best Case")
	public void signUp1() throws JsonProcessingException {
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

		String json = restTemplate.postForObject("http://localhost:"+port+"/signup/", user, String.class);
		ResMsg<AppUser> resUser = mapper.readValue(json, new TypeReference<ResMsg<AppUser>>() {});
		Assertions.assertNotNull(resUser.getPayload());
	}

	@Test
	@DisplayName("SignUp : Removing Non Mandatory")
	public void signUp2() throws JsonProcessingException {
		final AppUser user = new AppUser();
		user.setEmail("dasfsafdd@gg.com");
		user.setMobile("+543477656098867");
		user.setUserName("ddsdf1");
		user.setPassword("ddsdfsdf1#4566$");

		String json = restTemplate.postForObject("http://localhost:"+port+"/signup/", user, String.class);
		ResMsg<AppUser> resUser = mapper.readValue(json, new TypeReference<ResMsg<AppUser>>() {});
		Assertions.assertNotNull(resUser.getPayload());
	}

	@Test
	@DisplayName("SignUp : Email Null")
	public void signUp3() throws JsonProcessingException {
		final AppUser user = new AppUser();
		user.setUserId(1L);
		user.setMobile("+543477098867");
		user.setUserName("dd1");
		user.setPassword("dd1#4566$");

		ResponseEntity<String> entity = restTemplate.postForEntity("http://localhost:"+port+"/signup/", user, String.class);

		ResMsg<String> resMsg = mapper.readValue(entity.getBody(), new TypeReference<ResMsg<String>>() {});
		Assertions.assertTrue(entity.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("SignUp : Invalid Email")
	public void signUp4() throws JsonProcessingException {
		final AppUser user = new AppUser();
		user.setUserId(1L);
		user.setEmail("kjhjh");
		user.setMobile("+5554511");
		user.setUserName("dd1");
		user.setPassword("dd1#4566$");

		ResponseEntity<String> entity = restTemplate.postForEntity("http://localhost:"+port+"/signup/", user, String.class);

		ResMsg<String> resMsg = mapper.readValue(entity.getBody(), new TypeReference<ResMsg<String>>() {});
		Assertions.assertTrue(entity.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("SignUp : Mobile Null")
	public void signUp5() throws JsonProcessingException {
		final AppUser user = new AppUser();
		user.setUserId(1L);
		user.setEmail("kjhjh@gg.com");
		user.setUserName("dd1");
		user.setPassword("dd1#4566$");

		ResponseEntity<String> entity = restTemplate.postForEntity("http://localhost:"+port+"/signup/", user, String.class);

		ResMsg<String> resMsg = mapper.readValue(entity.getBody(), new TypeReference<ResMsg<String>>() {});
		Assertions.assertTrue(entity.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("SignUp : User Name Null")
	public void signUp6() throws JsonProcessingException {
		final AppUser user = new AppUser();
		user.setUserId(1L);
		user.setEmail("kjhjh@gg.com");
		user.setMobile("+962224");
		user.setPassword("dd1#4566$");

		ResponseEntity<String> entity = restTemplate.postForEntity("http://localhost:"+port+"/signup/", user, String.class);

		ResMsg<String> resMsg = mapper.readValue(entity.getBody(), new TypeReference<ResMsg<String>>() {});
		Assertions.assertTrue(entity.getStatusCode().is4xxClientError());
	}

	@Test
	@DisplayName("SignUp : Password Null")
	public void signUp7() throws JsonProcessingException {
		final AppUser user = new AppUser();
		user.setUserId(1L);
		user.setEmail("kjhjh@gg.com");
		user.setMobile("+962224");
		user.setUserName("jhgjhfh");

		ResponseEntity<String> entity = restTemplate.postForEntity("http://localhost:"+port+"/signup/", user, String.class);

		ResMsg<String> resMsg = mapper.readValue(entity.getBody(), new TypeReference<ResMsg<String>>() {});
		Assertions.assertTrue(entity.getStatusCode().is4xxClientError());
	}

	@Order(2)
	@Test
	@DisplayName("Sign In : Best Case")
	public void signIn1() throws JsonProcessingException {
		final LoginDto dto = new LoginDto("dd1", "dd1#4566$");
		ResponseEntity<String> entity = restTemplate.postForEntity("http://localhost:"+port+"/signin/", dto, String.class);

		ResMsg<Optional<AppUser>> resMsg = mapper.readValue(entity.getBody(), new TypeReference<ResMsg<Optional<AppUser>>>() {});
		Assertions.assertNotNull(resMsg.getPayload().get());
	}

	@Order(3)
	@Test
	@DisplayName("Sign In : Wrong User Name")
	public void signIn2() throws JsonProcessingException {
		final LoginDto dto = new LoginDto("dd dd1", "dd1#4566$");
		ResponseEntity<String> entity = restTemplate.postForEntity("http://localhost:"+port+"/signin/", dto, String.class);

		ResMsg<String> resMsg = mapper.readValue(entity.getBody(), new TypeReference<ResMsg<String>>() {});
		Assertions.assertTrue(resMsg.getPayload().contains("User Not Found"));
	}

	@Order(3)
	@Test
	@DisplayName("Sign In : Wrong Password")
	public void signIn3() throws JsonProcessingException {
		final LoginDto dto = new LoginDto("dd1", "dd1#4566$ 000");
		ResponseEntity<String> entity = restTemplate.postForEntity("http://localhost:"+port+"/signin/", dto, String.class);

		ResMsg<String> resMsg = mapper.readValue(entity.getBody(), new TypeReference<ResMsg<String>>() {});
		Assertions.assertTrue(resMsg.getPayload().contains("User Not Found"));
	}
}
