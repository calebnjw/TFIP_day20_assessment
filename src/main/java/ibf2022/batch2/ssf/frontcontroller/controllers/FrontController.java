package ibf2022.batch2.ssf.frontcontroller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ibf2022.batch2.ssf.frontcontroller.models.UserCredentials;
import ibf2022.batch2.ssf.frontcontroller.services.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class FrontController {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	Integer authFailCount = 0;

	@GetMapping(path = { "/", "/index.html", "/login" })
	public String goToLogin(Model model, HttpSession session) {
		session.invalidate();
		model.addAttribute("userCredentials", new UserCredentials());
		return "view0";
	}

	@PostMapping(path = { "/login" })
	public String authenticateLogin(
			Model model,
			HttpSession session,
			@Valid UserCredentials userCredentials,
			BindingResult bindings) throws Exception {

		// if any of the fields has errors
		if (bindings.hasErrors()) {
			return "view0";
		}

		String username = userCredentials.getUsername();
		String password = userCredentials.getPassword();

		session.setAttribute("username", username);

		String disabled = redisTemplate.opsForValue().get(username);
		if (disabled != null && disabled.equalsIgnoreCase("true")) {
			return "redirect:/disabled";
		}

		try {
			// pass username and password to authentication service
			authenticationService.authenticate(
					username,
					password);
		} catch (Exception e) {
			// catch exception when status code is not 200s
			String statusCode = e.getMessage();
			String message = getAuthenticationMessage(statusCode);
			if (authFailCount < 2) {
				authFailCount += 1;
			} else {
				authenticationService.disableUser(username);
				authFailCount = 0;
				return "redirect:/disabled";
			}

			if (!message.isBlank()) {
				model.addAttribute("authenticationFail", true);
				model.addAttribute("authenticationMessage", message);
			}

			// insert captcha thing here
			// if (authFailCount > 0) {
			// Captcha captcha = new Captcha();
			// model.addAttribute("captcha", captcha.toString());
			// }

			// show login page again
			return "view0";
		}

		session.setAttribute("authenticated", true);
		return "redirect:/protected/view1.html";
	}

	@GetMapping(path = "/disabled")
	public String goToDisabled(Model model, HttpSession session) {
		String username = (String) session.getAttribute("username");
		model.addAttribute("username", username);

		return "view2";
	}

	private String getAuthenticationMessage(String statusCode) {
		String message = "";

		switch (statusCode) {
			case "400":
				message = "Invalid payload.";
				break;
			case "401":
				message = "Incorrect username and/or password.";
				break;
			default:
				break;
		}

		return message;
	}
}
