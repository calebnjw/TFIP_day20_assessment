package ibf2022.batch2.ssf.frontcontroller.services;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ibf2022.batch2.ssf.frontcontroller.models.UserCredentials;

@Service
public class AuthenticationService {
	@Value("${ibf2022.batch3.ssf.auth.url}")
	private String authUrl;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	// TODO: Task 2
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write the authentication method in here
	public void authenticate(String username, String password) throws Exception {

		// check if username and password are more than 2 characters
		UserCredentials c = new UserCredentials();
		c.setUsername(username);
		c.setPassword(password);

		// create post request to send to chuk's url
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<String>(c.toJson().toString(), headers);

		RestTemplate template = new RestTemplate();
		ResponseEntity<String> res = null;

		try {
			res = template.exchange(
					authUrl,
					HttpMethod.POST,
					requestEntity,
					String.class);

		} catch (Exception exception) {
			// throw exception with status code to frontcontroller
			String statusCode = exception.getMessage().split(" ")[0];
			throw new Exception(statusCode);
		}

		String statusCode = res.getStatusCode().toString().split(" ")[0];
	}

	// TODO: Task 3
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to disable a user account for 30 mins
	public void disableUser(String username) {
		redisTemplate.opsForValue().set(username, "true");
		redisTemplate.expire(username, Duration.ofMinutes(30));
	}

	// TODO: Task 5
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to check if a given user's login has been disabled
	public boolean isLocked(String username) {
		return false;
	}
}
