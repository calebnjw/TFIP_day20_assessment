package ibf2022.batch2.ssf.frontcontroller.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/protected")
public class ProtectedController {

	// TODO Task 5
	// Write a controller to protect resources rooted under /protected
	@GetMapping(path = { "/", "/view1.html" })
	public String goToProtected(Model model, HttpSession session) {
		Object authenticated = session.getAttribute("authenticated");
		System.out.println("IS USER AUTHENTICATED? " + authenticated);
		// checks if user is authenticated
		// if not authenticated, go to login
		if (authenticated == null || !authenticated.toString().equalsIgnoreCase("true")) {
			return "redirect:/";
		}
		return "view1";
	}

	@GetMapping(path = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
