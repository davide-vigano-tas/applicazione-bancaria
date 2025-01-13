package eu.tasgroup.applicativo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CustomErrorController implements ErrorController {

	@GetMapping("/error")
	public String handleError(HttpServletRequest request, HttpServletResponse response, Model model) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		
		// Fallback per il codice di stato
		if (statusCode == null) {
			statusCode = response.getStatus();
		}

		switch (statusCode) {
		case 404:
			return "error404";
		case 403:
			return "error403";
		default:
			model.addAttribute("statusCode", statusCode);
			return "error";
		}
	}
}
