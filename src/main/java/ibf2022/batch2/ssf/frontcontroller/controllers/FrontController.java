package ibf2022.batch2.ssf.frontcontroller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping(path = { "/", "/index.html" })
	public String goToLogin(Model model, HttpSession session) {
		model.addAttribute("userCredentials", new UserCredentials());
		// session.invalidate();
		return "view0";
	}

	@PostMapping(path = { "/login" })
	public String goToProtected(
			Model model,
			HttpSession sess,
			@Valid UserCredentials userCredentials,
			BindingResult bindings) throws Exception {

		if (bindings.hasErrors()) {
			return "view0";
		}

		// pass username and password to authentication service
		try {
			authenticationService.authenticate(userCredentials.getUsername(),
					userCredentials.getPassword());
		} catch (Exception e) {
			// System.out.println("Credentials do not match. ");
			System.out.println("HELLO YOU GOT THIS ERROR: " + e.getMessage());
			return "view0";
		}

		return "view1";
	}
}
