package ibf2022.batch2.ssf.frontcontroller.services;

import org.springframework.beans.factory.annotation.Value;
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

	// TODO: Task 2
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write the authentication method in here
	public void authenticate(String username, String password) throws Exception {

		// check if username and password are more than 2 characters
		UserCredentials c = new UserCredentials();
		c.setUsername(username);
		c.setPassword(password);

		System.out.println("URL POSTING TO: " + authUrl);
		System.out.println("JSON BEING SENT: " + c.toJson().toString());

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
			String statusCode = exception.getMessage().split(" ")[0];
			System.out.println("STATUS CODE: " + statusCode);
			throw new Exception(statusCode);
		}

		System.out.println("STATUS CODE: " + res.getStatusCode().toString().split(" ")[0]);
	}

	// TODO: Task 3
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to disable a user account for 30 mins
	public void disableUser(String username) {
	}

	// TODO: Task 5
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to check if a given user's login has been disabled
	public boolean isLocked(String username) {
		return false;
	}
}
